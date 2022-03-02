package org.unibl.etf.epraksa.model.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String loginMail;
    @NotBlank
    private String password;
}
