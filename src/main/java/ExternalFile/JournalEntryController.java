package ExternalFile;

import com.Japp.journelApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries =  new HashMap<>();

    @GetMapping()
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }
    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry journalEntry){
//    journalEntries.put(journalEntry.getId(),journalEntry);
//    return true;
//    }
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return journalEntries.get(myId);
    }
    @DeleteMapping("id/{myId}")
    public JournalEntry deleteEntryById(@PathVariable long myId){
        return journalEntries.remove(myId);
    }
    @PutMapping("id/{myId}")
    public JournalEntry updateEntryById(@PathVariable long myId,@RequestBody JournalEntry myEntry){
       return journalEntries.put(myId,myEntry);
    }
}
