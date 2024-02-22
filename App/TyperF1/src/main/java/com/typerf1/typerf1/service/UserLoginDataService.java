package com.typerf1.typerf1.service;

import com.typerf1.typerf1.repository.UserLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginDataService {

    private final UserLoginDataRepository userLoginDataRepository;

    @Autowired
    public UserLoginDataService(UserLoginDataRepository userLoginDataRepository) {
        this.userLoginDataRepository = userLoginDataRepository;
    }

    public void isLoginAndPasswordCorrect(){

    }

}
