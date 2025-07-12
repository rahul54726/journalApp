package com.Japp.journelApp.Controller;

import com.Japp.journelApp.Entity.User;
import com.Japp.journelApp.api.response.Root;
import com.Japp.journelApp.repository.UserRepo;
import com.Japp.journelApp.service.UserService;
import com.Japp.journelApp.service.WeatherSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WeatherSer weatherSer;
//    @GetMapping
//    public List<User> getAll(){
//        return userService.getAll();
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
      Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
        User userInDB= userService.findByUserName(userName);
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveNewUser(userInDB);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping()
    public  ResponseEntity<?> deleteUserById(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greatings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Root response = weatherSer.getWeather("Delhi,India");
                String greating = "";
                if(response != null){
                    greating = " , Weather feels like  " + response.getMain().getFeelsLike();
                }
        return new ResponseEntity<>("Hi " + authentication.getName() + greating  ,HttpStatus.OK);
    }
}
