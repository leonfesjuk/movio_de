package de.upteams.tasktracker.invitetoken.controller.impl;

import de.upteams.tasktracker.invitetoken.controller.interfaces.InviteTokenApi;
import de.upteams.tasktracker.invitetoken.dto.request.GenerateInviteTokensRequest;
import de.upteams.tasktracker.invitetoken.dto.response.InviteTokenResponse;
import de.upteams.tasktracker.invitetoken.service.interfaces.InviteTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InviteTokenControllerImpl implements InviteTokenApi {

    private final InviteTokenService inviteTokenService;

    @Override
    public ResponseEntity<List<InviteTokenResponse>> generateTokens(GenerateInviteTokensRequest request) {
        List<InviteTokenResponse> tokens = inviteTokenService.generateTokens(request.count());
        return ResponseEntity.status(HttpStatus.CREATED).body(tokens);
    }

    @Override
    public ResponseEntity<List<InviteTokenResponse>> getAllTokens() {
        return ResponseEntity.ok(inviteTokenService.getAllTokens());
    }
}
