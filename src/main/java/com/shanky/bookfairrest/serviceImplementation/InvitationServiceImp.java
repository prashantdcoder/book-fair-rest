package com.shanky.bookfairrest.serviceImplementation;

import com.shanky.bookfairrest.DTO.EmailDTO;
import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.aws.AwsSimpleEmailService;
import com.shanky.bookfairrest.domain.Invite;
import com.shanky.bookfairrest.enums.InvitationStatus;
import com.shanky.bookfairrest.repository.InvitationRepository;
import com.shanky.bookfairrest.service.InvitationService;
import com.shanky.bookfairrest.utils.AppUtil;
import com.shanky.bookfairrest.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
public class InvitationServiceImp implements InvitationService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    AwsSimpleEmailService emailClient;

    @Autowired
    InvitationRepository invitationRepository;

    @Override
    public ResponseDTO<String> sendInvitation(String email) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            Invite invite = findByEmail(email);
            if (invite != null) {
                responseDTO.setFailureResponse(null, StringUtil.INVITATION_EXIST);
            } else {

                int verificationCode = AppUtil.generateRandomNumber();
                EmailDTO emailDTO = new EmailDTO("Book Fair Invitation", email);
                emailDTO.setHtmlTemplate(generateInvitationHtmlBody(verificationCode));
                ResponseDTO<String> emailResponseDTO = emailClient.send(emailDTO);
                if (emailResponseDTO.isStatus()) {
                    invite = new Invite();
                    invite.setActive(true);
                    invite.setEmail(email);
                    invite.setStatus(InvitationStatus.PENDING);
                    invite.setVerificationCode(verificationCode);
                    invitationRepository.save(invite);
                    responseDTO.setSuccessResponse(null, StringUtil.INVITATION_SENT);
                } else {
                    responseDTO.setFailureResponse(null, StringUtil.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (NullPointerException e) {
            responseDTO.setFailureResponse(null, StringUtil.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setFailureResponse(null, StringUtil.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }

    private Invite findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invite> query = builder.createQuery(Invite.class);
        Root<Invite> root = query.from(Invite.class);
        query.select(root).where(builder.equal(root.get("email"), email));
        TypedQuery<Invite> resultQuery = entityManager.createQuery(query);
        return resultQuery.getResultList().size() != 0 ? resultQuery.getSingleResult() : null;
    }

    private String generateInvitationHtmlBody(int verificationCode) {
        StringBuilder body = new StringBuilder();
        body.append("<h2>Dear author,</h2><br>");
        body.append("<p>");
        body.append("Thank you for showing interest in our service. Here is your verification code ");
        body.append("<code>");
        body.append(verificationCode);
        body.append("</code>");
        body.append("</p><br>");
        body.append("Thanks & regards<br>");
        body.append("Team bookfair");
        return body.toString();
    }
}
