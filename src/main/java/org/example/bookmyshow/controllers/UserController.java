package org.example.bookmyshow.controllers;

import org.example.bookmyshow.dtos.*;
import org.example.bookmyshow.models.User;
import org.example.bookmyshow.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        try{
            User user = userService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
            signUpResponseDto.setUserId(user.getId());
            signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            signUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return signUpResponseDto;
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = new SignInResponseDto();
        try{
            User user = userService.signIn(signInRequestDto.getEmail(), signInRequestDto.getPassword());
            signInResponseDto.setMessage("Login Successful");
        } catch (Exception e) {
            signInResponseDto.setMessage(e.getMessage());
        }
        return signInResponseDto;
    }

}
