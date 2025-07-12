package com.Japp.journelApp.repository;

import com.Japp.journelApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
    public  List<User> getUsersForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//        query.addCriteria(Criteria.where("name").is("Rahul"));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
