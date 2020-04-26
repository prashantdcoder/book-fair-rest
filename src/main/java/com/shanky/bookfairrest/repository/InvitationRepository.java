package com.shanky.bookfairrest.repository;

import com.shanky.bookfairrest.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invite, Long> {

}
