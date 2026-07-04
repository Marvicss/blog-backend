package com.example.blog_backend.modules.login;


import com.example.blog_backend.modules.login.dto.LoginRequestDTO;
import com.example.blog_backend.modules.login.dto.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping("/")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO login){
        LoginResponseDTO loginResponse=  loginService.login(login.email(),login.password());
        return ResponseEntity.ok(loginResponse);
    }
}
