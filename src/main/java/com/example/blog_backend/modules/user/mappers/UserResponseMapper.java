package com.example.blog_backend.modules.user.mappers;

import com.example.blog_backend.modules.user.User;
import com.example.blog_backend.modules.user.dto.UserResponseDTO;

import java.util.List;

public class UserResponseMapper {

    public static UserResponseDTO userToUserResponseDTO(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public static List<UserResponseDTO> userListToUserResponseDTOList(List<User> users){
        return users.stream().map((user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt()))).toList();
    }
}
