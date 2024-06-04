package com.maverickstube.maverickshub.dataTransferObjects.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}
