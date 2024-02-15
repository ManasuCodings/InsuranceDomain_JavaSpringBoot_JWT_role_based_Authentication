package com.insurance_domain.security;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String email;

    private String password;
}
