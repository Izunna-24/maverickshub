package com.maverickstube.maverickshub.dataTransferObjects.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse {
    private Long id;
    private String email;
    private String message;
}
