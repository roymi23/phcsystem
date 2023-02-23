package com.phc.identity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActionResult {

    private String actionName;
    private boolean success;
    private String errorMessage;
    private String message;

}
