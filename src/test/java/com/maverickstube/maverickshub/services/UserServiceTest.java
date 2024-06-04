package com.maverickstube.maverickshub.services;


import com.maverickstube.maverickshub.dataTransferObjects.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.CreateUserResponse;
import com.maverickstube.maverickshub.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void registerTest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@email.com");
        request.setPassword("password");

        CreateUserResponse response = userService.register(request);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));

    }

    @Test
    @DisplayName("test that user can be retrieved by id")
    @Sql(scripts = {"/db/data.sql"})
    public void testGetUserById(){
        User user  = userService.getById(200L);
        assertThat(user).isNotNull();

    }
}
