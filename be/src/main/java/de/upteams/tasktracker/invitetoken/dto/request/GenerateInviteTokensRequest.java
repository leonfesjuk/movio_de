package de.upteams.tasktracker.invitetoken.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GenerateInviteTokensRequest(

        @Min(value = 1, message = "Количество токенов должно быть не менее 1")
        @Max(value = 100, message = "Количество токенов не может превышать 100")
        int count
) {}
