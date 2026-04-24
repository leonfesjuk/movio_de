package de.upteams.tasktracker.user.persistence;

import de.upteams.tasktracker.invitetoken.entity.InviteToken;
import de.upteams.tasktracker.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {

    @Query("select a from AppUser a where upper(a.email) = upper(?1)")
    Optional<AppUser> findByEmailIgnoreCase(String email);

    @Query("select count(a) > 0 from AppUser a where upper(a.name) = upper(?1)")
    boolean existsByNameIgnoreCase(String name);

    @Query("select u from AppUser u where u.inviteToken = :token and u.id <> :userId")
    List<AppUser> findAllByInviteTokenAndNotId(InviteToken token, UUID userId);
}
