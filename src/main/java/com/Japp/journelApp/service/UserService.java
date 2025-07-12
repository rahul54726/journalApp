package com.Japp.journelApp.service;

import com.Japp.journelApp.Entity.User;
import com.Japp.journelApp.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//Business logic here
@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
//    public void saveEntry(User user){
//        userRepo.save(user);
//    }
    public void saveUser(User user){
        userRepo.save(user);
    }
    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepo.save(user);
            return true;
        }catch (Exception e){
            log.info("Logger is running");
            return false;
        }
    }
    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }
    public List<User> getAll(){
        return userRepo.findAll();
    }
    public Optional<User> findById(ObjectId myId){
        return userRepo.findById(myId);
    }
    public void deleteById(ObjectId myId){
        userRepo.deleteById(myId);
    }
    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
}
