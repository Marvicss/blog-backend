package com.example.blog_backend.modules.user;


import com.example.blog_backend.modules.user.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.blog_backend.modules.user.mappers.UserResponseMapper.userListToUserResponseDTOList;
import static com.example.blog_backend.modules.user.mappers.UserResponseMapper.userToUserResponseDTO;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<User> users = userService.findAll();

        List<UserResponseDTO> userDTO = userListToUserResponseDTOList(users);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById (@PathVariable UUID id){
        User user = userService.findById(id);

        UserResponseDTO userDTO = userToUserResponseDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> create(@RequestBody User user){
        User createdUser = userService.create(user);

        UserResponseDTO userDTO = userToUserResponseDTO(createdUser);

        return ResponseEntity.status(201).body(userDTO);

    }
}
