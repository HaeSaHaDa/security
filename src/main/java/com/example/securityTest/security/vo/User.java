package com.example.securityTest.security.vo;

import lombok.Data;

@Data
public class User {

    private int id;
    private String username;
    private String password;
    private String authority;
}
