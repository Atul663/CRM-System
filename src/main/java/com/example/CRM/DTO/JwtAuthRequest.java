package com.example.CRM.DTO;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;

}
