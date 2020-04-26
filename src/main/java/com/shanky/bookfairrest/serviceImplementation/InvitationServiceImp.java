package com.shanky.bookfairrest.serviceImplementation;

import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.domain.Invite;
import com.shanky.bookfairrest.enums.InvitationStatus;
import com.shanky.bookfairrest.repository.InvitationRepository;
import com.shanky.bookfairrest.service.InvitationService;
import com.shanky.bookfairrest.utils.AppUtil;
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
    InvitationRepository invitationRepository;

    @Override
    public ResponseDTO<String> sendInvitation(String email) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            Invite invite = findByEmail(email);
            if (invite != null) {
                responseDTO.setFailureResponse(null, "Invitation to this user has already been sent");
            } else {
                invite = new Invite();
                invite.setActive(true);
                invite.setEmail(email);
                invite.setStatus(InvitationStatus.PENDING);
                invite.setVerificationCode(AppUtil.generateRandomNumber());
                invitationRepository.save(invite);
                responseDTO.setSuccessResponse(null, "Invitation has been sent");
            }
        } catch (NullPointerException e) {
            responseDTO.setFailureResponse(null, "We could not able to process this request. Please try again");
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setFailureResponse(null, null);
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
}
