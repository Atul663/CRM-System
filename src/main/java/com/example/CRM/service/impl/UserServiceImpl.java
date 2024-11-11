package com.example.CRM.service.impl;

import com.example.CRM.config.AppConstant;
import com.example.CRM.entity.Role;
import com.example.CRM.entity.User;
import com.example.CRM.exceptions.ResourceNotFoundException;
import com.example.CRM.DTO.UserDto;
import com.example.CRM.repository.RoleRepo;
import com.example.CRM.repository.UserRepo;
import com.example.CRM.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;
//
//    @Autowired
//    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        //Set encoded password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //Set roles -> New user has normal role only
        Role role= this.roleRepo.findById(AppConstant.NORMAL_USER).get();
        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        //User savedUser = this.userRepo.save(userDto);
        //This will not work, "save" has User datatype in JPARepository function

        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setDepartmentName(userDto.getDepartmentName());
        user.setPosition(userDto.getPosition());


        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos= users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    // @Override
    // public void deleteUser(Integer userId) {
    //     User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
    //     this.userRepo.delete(user);
    // }


    @Override
    @Transactional // Ensure that the method is run within a transaction context
    public void deleteUser(Integer userId) {
        // Find the user by ID
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Remove the user-role relationship (not delete roles themselves)
            user.getRoles().clear();  // This removes the user-role association

            // Save the user after clearing the roles (optional if you want to maintain this in DB)
            userRepo.save(user);

            // Custom query to remove all tasks for this user
//            taskRepository.deleteAllByAssignedUserId(userId);

            // Now delete the user itself
            userRepo.delete(user);
        } else {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }




    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "Email : " + email, 0));
        return this.modelMapper.map(user,UserDto.class);
    }

    public User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }
}
