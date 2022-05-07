package com.epam.quizapp.service;

import com.epam.quizapp.data.User;
import com.epam.quizapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    User user;
    @BeforeEach
    void setUp(){
        user = new User();
        user.setRole("USER");
        user.setUsername("username");
        user.setActive(true);
        user.setPassword("password");

    }
    @Test
    void validateAdmin() {

        Boolean result = adminService.validateAdmin("raj", "1234");
        assertTrue(result);
    }

    @Test
    void insert() {

        adminService.insert(user);
        verify(userRepository).save(any());;
    }
    @Test
    void insertError() {
        try {


            when(userRepository.save(any())).thenThrow(RuntimeException.class);

            adminService.insert(user);

            verify(userRepository).save(any());
            ;
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void loadUserByUsername() {
        Optional<User> userOptional= Optional.ofNullable(user);
        given(userRepository.findByUsername(anyString())).willReturn(userOptional);

        UserDetails userDetails = adminService.loadUserByUsername(anyString());
    }
    @Test
    void loadUserByUsernameError() {
        Optional<User> userOptional= Optional.ofNullable(null);
        given(userRepository.findByUsername(anyString())).willReturn(userOptional);
        try {
            UserDetails userDetails = adminService.loadUserByUsername(anyString());
        }catch (Exception e){
            System.out.println(e);
        }
    }

}