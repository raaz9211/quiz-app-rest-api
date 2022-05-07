package com.epam.quizapp.service;

import com.epam.quizapp.data.User;
import com.epam.quizapp.data.UserPrincipal;
import com.epam.quizapp.exception.UserException;
import com.epam.quizapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AdminService extends AdminCredentials  implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    public boolean validateAdmin(String userId, String password) {
        return (userId.equals(super.userID) && password.equals(super.password));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        System.out.println(user);
        return new UserPrincipal(user);
    }


    public User insert(User user) {

        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new UserException(user.getUsername() + " is already present");
        }
    }




}
