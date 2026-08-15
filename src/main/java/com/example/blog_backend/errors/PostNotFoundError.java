package com.example.blog_backend.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundError extends Error{

    public PostNotFoundError(){
        super("O post buscado não foi encontrado");
    }
}
