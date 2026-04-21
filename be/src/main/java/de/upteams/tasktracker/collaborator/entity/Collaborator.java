package de.upteams.tasktracker.collaborator.entity;

import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static de.upteams.tasktracker.utils.EntityUtil.getIdForToString;
import static de.upteams.tasktracker.utils.EntityUtil.getIdsForToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Collaborator extends BaseUuidEntity {

    @NotNull
    @ManyToOne
    private AppUser appUser;


    @NotNull
    @Enumerated(EnumType.STRING)
    private final Set<ProjectRoles> projectRolesSet = new HashSet<>();

    @Override
    public String toString() {
        return "Collaborator{" +
                "id=" + id +
                ", projectRolesSet=" + projectRolesSet +
                ", appUserId=" + getIdForToString(appUser) +
                '}';
    }
}
