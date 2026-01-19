package com.Hussain.dreamshops.service.user;

import com.Hussain.dreamshops.dto.UserDto;
import com.Hussain.dreamshops.model.User;
import com.Hussain.dreamshops.request.CreateUserRequest;
import com.Hussain.dreamshops.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}