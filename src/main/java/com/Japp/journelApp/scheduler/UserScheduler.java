package com.Japp.journelApp.scheduler;


import com.Japp.journelApp.Entity.JournalEntry;
import com.Japp.journelApp.Entity.User;
import com.Japp.journelApp.cache.AppCache;
import com.Japp.journelApp.enums.Sentiment;
import com.Japp.journelApp.repository.UserRepoImpl;
import com.Japp.journelApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private AppCache appCache;
    @Scheduled(cron = "0 0 9 ? * SUN")
    public void fetchUsersAndSendSAMail(){
        List<User> users = userRepo.getUsersForSA();
        for(User user :users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
           List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate()
                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                   .map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment:sentiments ){
                if(sentiment != null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
          Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
           if (mostFrequentSentiment != null){
               emailService.sendEmail(user.getEmail(),"Sentiment for Last 7 days ",mostFrequentSentiment.toString());
           }
        }
    }
    @Scheduled(cron = "0 */5 * * * *")
    public void clearAppCache(){
        appCache.init();
    }
}
