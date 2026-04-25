package de.upteams.tasktracker.invitetoken.controller.interfaces;

import de.upteams.tasktracker.invitetoken.dto.request.GenerateInviteTokensRequest;
import de.upteams.tasktracker.invitetoken.dto.response.InviteTokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/invite-tokens")
public interface InviteTokenApi extends InviteTokenApiSwaggerDoc {

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<InviteTokenResponse>> generateTokens(
            @Valid @RequestBody GenerateInviteTokensRequest request
    );

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<InviteTokenResponse>> getAllTokens();
}
