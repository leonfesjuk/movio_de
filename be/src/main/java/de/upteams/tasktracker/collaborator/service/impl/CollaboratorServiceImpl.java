package de.upteams.tasktracker.collaborator.service.impl;

import de.upteams.tasktracker.collaborator.entity.Collaborator;
import de.upteams.tasktracker.collaborator.entity.ProjectRoles;
import de.upteams.tasktracker.collaborator.persistence.CollaboratorRepository;
import de.upteams.tasktracker.collaborator.service.interfaces.CollaboratorService;
import de.upteams.tasktracker.user.entity.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollaboratorServiceImpl implements CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;

    @Override
    public boolean isUserInProject(AppUser user) {
        return getCollaborator(user).isPresent();
    }

    @Override
    public Optional<Collaborator> getCollaborator(AppUser user) {
        return collaboratorRepository.findByAppUser(user);
    }

    @Override
    public boolean hasUserPermission(AppUser user, ProjectRoles requiredRole) {
        return hasUserPermission(user, Collections.singletonList(requiredRole));
    }

    @Override
    public boolean hasUserPermission(AppUser user, Collection<ProjectRoles> requiredRoles) {
        return getCollaborator(user)
                .map(collaborator -> hasAnyRequiredRole(collaborator, requiredRoles))
                .orElse(false);
    }

    private boolean hasAnyRequiredRole(Collaborator collaborator, Collection<ProjectRoles> requiredRoles) {
        return collaborator.getProjectRolesSet()
                .stream()
                .anyMatch(requiredRoles::contains);
    }

}
