package com.example.CRM.controller;

import com.example.CRM.entity.User;
import com.example.CRM.exceptions.ApiException;
import com.example.CRM.DTO.ApiResponse;
import com.example.CRM.DTO.JwtAuthRequest;
import com.example.CRM.DTO.JwtAuthResponse;
import com.example.CRM.DTO.UserDto;
import com.example.CRM.repository.UserRepo;
import com.example.CRM.security.JwtTokenHelper;
import com.example.CRM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint for user login, generates a JWT token if authentication is successful.
     *
     * @param request JwtAuthRequest containing the username and password.
     * @return ResponseEntity with the JWT token in JwtAuthResponse.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String generatedToken = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(generatedToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Helper method to authenticate user credentials.
     *
     * @param username the username provided by the user.
     * @param password the password provided by the user.
     */
    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException ex) {
            System.out.println("Invalid Details!");
            throw new ApiException("Invalid username or password!");
        }
    }

    /**
     * Endpoint for registering a new user.
     *
     * @param userDto UserDto containing new user's details.
     * @return ResponseEntity with the registered user details or an error message if the email exists.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        // Check if the email already exists
        Optional<User> existingUser = this.userRepo.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse("Email already exists", false), HttpStatus.BAD_REQUEST);
        }

        // Register the new user
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}