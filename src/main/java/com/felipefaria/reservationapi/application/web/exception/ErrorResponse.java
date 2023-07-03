package com.felipefaria.reservationapi.application.web.exception;

import lombok.*;

@Getter
@Setter

@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
    private String rule;
}
