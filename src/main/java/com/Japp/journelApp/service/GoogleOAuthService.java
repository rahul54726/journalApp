package com.Japp.journelApp.service;

import com.Japp.journelApp.Entity.User;
import com.Japp.journelApp.repository.UserRepo;
import com.Japp.journelApp.utility.Jwtutil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final Jwtutil jwtutil;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final com.Japp.journelApp.service.UserDetailsServiceImpl userDetailsService;

    public String authenticateWithGoogle(String code) {
        try {
            // Step 1: Exchange code for access token
            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (userDetails == null) {
                    User user = new User();
                    user.setEmail(email);
                    user.setUserName(email);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setRoles(Collections.singletonList("USER"));
                    userRepo.save(user);
                    userDetails = userDetailsService.loadUserByUsername(email);
                }

                // ✅ Return JWT instead of setting security context
                return jwtutil.generateToken(userDetails.getUsername());
            }

        } catch (Exception e) {
            log.error("Google OAuth2 error", e);
        }
        return null;
    }
}
