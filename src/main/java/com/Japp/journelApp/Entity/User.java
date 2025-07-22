package com.Japp.journelApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection ="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
// below two annotations are of Lombok Project used to generate Getters And Setters at the time of Compilations
// there are many of them to use
public class User {
    @Id
    private ObjectId id;
    //isko uniqye banane se ye indexing unique nahi hogi numko application properties me jake mention karana pdega ki
    //spring.data.mongodb.auto-index-creation=true;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> JournalEntries = new ArrayList<>();
    private List<String> roles;


}
