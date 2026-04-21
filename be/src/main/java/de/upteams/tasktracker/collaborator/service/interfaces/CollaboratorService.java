package de.upteams.tasktracker.collaborator.service.interfaces;

import de.upteams.tasktracker.collaborator.entity.Collaborator;
import de.upteams.tasktracker.collaborator.entity.ProjectRoles;
import de.upteams.tasktracker.user.entity.AppUser;

import java.util.Collection;
import java.util.Optional;

public interface CollaboratorService {

    boolean isUserInProject(AppUser user);

    Optional<Collaborator> getCollaborator(AppUser user);

    boolean hasUserPermission(AppUser user, ProjectRoles requiredRole);

    boolean hasUserPermission(AppUser user, Collection<ProjectRoles> requiredRoles);
}
