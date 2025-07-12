package com.Japp.journelApp.service;

import com.Japp.journelApp.Entity.User;
import com.Japp.journelApp.repository.UserRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
   private UserRepo userRepo;


        @ParameterizedTest
        @ValueSource(strings = {
                "Rahul",
                "Kunal"
        })
    public void testFindByUserName(String name){
        assertNotNull(userRepo.findByUserName(name));
    }
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,5,7",
            "3,6,9"
    })
    public void test(int a ,int b,int exp){
            assertEquals(exp,a+b);
    }
}
