package com.Japp.journelApp.Repo;

import com.Japp.journelApp.repository.UserRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTests {

    @Autowired
    private UserRepoImpl userRepo;

    @Test
    public void testSaveNewUser(){
        userRepo.getUsersForSA();
    }
}