package ch.gabi295.reflect.controller;

import ch.gabi295.reflect.dto.JournalRequest;
import ch.gabi295.reflect.model.JournalEntry;
import ch.gabi295.reflect.service.JournalService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping
    public List<JournalEntry> getEntries(@RequestParam(required = false) LocalDate date) {
        return journalService.getEntries(date);
    }

    @PostMapping
    public JournalEntry create(@RequestBody JournalRequest request) {
        return journalService.createEntry(request);
    }

    @PutMapping("/{entryId}")
    public JournalEntry update(@PathVariable Long entryId, @RequestBody JournalRequest request) {
        return journalService.updateEntry(entryId, request);
    }

    @DeleteMapping("/{entryId}")
    public void delete(@PathVariable Long entryId) {
        journalService.deleteEntry(entryId);
    }
}