package com.Japp.journelApp;

import com.Japp.journelApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;
    @Test
    void testSendMail(){
        emailService.sendEmail("rohit.23252@knit.ac.in","Testing Java Mail Sender","Hey this is Rahul and I am sending you this mail using JavaMailSender");
    }
}
