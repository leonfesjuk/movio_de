package de.upteams.tasktracker.collaborator.persistence;

import de.upteams.tasktracker.collaborator.entity.Collaborator;
import de.upteams.tasktracker.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, UUID> {

    Optional<Collaborator> findByAppUser(AppUser user);

}
