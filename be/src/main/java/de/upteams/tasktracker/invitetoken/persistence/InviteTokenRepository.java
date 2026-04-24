package de.upteams.tasktracker.invitetoken.persistence;

import de.upteams.tasktracker.invitetoken.entity.InviteToken;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface InviteTokenRepository extends JpaRepository<InviteToken, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<InviteToken> findByToken(String token);

    boolean existsByToken(String token);
}