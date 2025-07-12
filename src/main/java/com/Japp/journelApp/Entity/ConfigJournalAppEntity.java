package com.Japp.journelApp.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="config_journal_app")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {
    private String key;
    private String value;
}
