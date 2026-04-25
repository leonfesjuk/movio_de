package de.upteams.tasktracker.invitetoken.service.interfaces;

import de.upteams.tasktracker.invitetoken.dto.response.InviteTokenResponse;
import de.upteams.tasktracker.invitetoken.entity.InviteToken;

import java.util.List;

public interface InviteTokenService {

    List<InviteTokenResponse> generateTokens(int count);

    List<InviteTokenResponse> getAllTokens();

    InviteToken validateToken(String rawToken);

    void markTokenAsUsed(String rawToken);
}
