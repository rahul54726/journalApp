package com.Japp.journelApp.Controller;

import com.Japp.journelApp.Entity.User;
import com.Japp.journelApp.repository.UserRepo;
import com.Japp.journelApp.service.GoogleOAuthService;
import com.Japp.journelApp.service.UserDetailsServiceImpl;
import io.jsonwebtoken.security.Password;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private GoogleOAuthService googleOAuthService;
    @GetMapping("/callback")
//    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code){
//        try {
//            // 1. Exchange auth code for tokens
//            String tokenEndPoint = "https://oauth2.googleapis.com/token";
//            MultiValueMap<String ,String> params = new LinkedMultiValueMap<>();
//            params.add("code",code);
//            params.add("client_id",clientId);
//            params.add("client_secret", clientSecret);
//            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
//            params.add("grant_type ", "authorization_code" );
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params,headers);
//            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndPoint, request, Map.class);
//            String idToken = (String)  tokenResponse.getBody().get("id_token");
//            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
//            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
//            if(userInfoResponse.getStatusCode() == HttpStatus.OK){
//                Map<String ,Object> userInfo = userInfoResponse.getBody();
//                String email = (String) userInfo.get("email");
//                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//                if(userDetails == null) {
//                    User user = new User();
//                    user.setEmail(email);
//                    user.setUserName(email);
//                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
//                    user.setRoles(Arrays.asList("USER"));
//                    userRepo.save(user);
//                    userDetails =  userDetailsService.loadUserByUsername(email);
//                }
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                return ResponseEntity.status(HttpStatus.OK).build();
//            }
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }catch (Exception e){
//            log.error("Something worng with OAuth2",e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        String jwt = googleOAuthService.authenticateWithGoogle(code);
        if (jwt != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth2 login failed.");
    }
}
