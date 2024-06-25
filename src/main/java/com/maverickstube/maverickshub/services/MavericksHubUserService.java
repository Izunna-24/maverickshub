package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dataTransferObjects.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.CreateUserResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.models.User;
import com.maverickstube.maverickshub.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MavericksHubUserService implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MavericksHubUserService (UserRepository userRepository ,
                                    ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CreateUserResponse register(CreateUserRequest request) {
       User user =  modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user =  userRepository.save(user);
        var response = modelMapper.map(user, CreateUserResponse.class);
        response.setMessage("registered successfully");
        return response;
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("user with id %d not found", id)));
    }

    @Override
    public User getUserByUsername(String username) {
       User user =  userRepository.findByEmail(username)
                .orElseThrow(()-> new UserNotFoundException("user not found")
                );
    return user;
    }
}
