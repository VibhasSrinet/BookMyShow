package org.example.bookmyshow.services;

import org.example.bookmyshow.exceptions.IncorrectPasswordException;
import org.example.bookmyshow.exceptions.UserAlreadyExistsException;
import org.example.bookmyshow.exceptions.UserNotFoundException;
import org.example.bookmyshow.models.User;
import org.example.bookmyshow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(String email, String password) throws UserNotFoundException, UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User already exists!");
        }
        User user = new User();
        user.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User signIn(String email, String password) throws UserNotFoundException, IncorrectPasswordException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User email does not exist!");
        }
        User user= optionalUser.get();
        System.out.println(user.getPassword()+" "+ password);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }
        return user;
    }
}
