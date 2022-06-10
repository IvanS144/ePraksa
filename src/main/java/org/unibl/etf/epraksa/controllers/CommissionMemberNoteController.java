package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.CommissionMemberNoteDTO;
import org.unibl.etf.epraksa.model.dataTransferObjects.MentorNoteDTO;
import org.unibl.etf.epraksa.services.CommissionMemberNoteService;

import java.util.List;

@RestController
@RequestMapping("/commissionmembernotes")
public class CommissionMemberNoteController
{
    private final CommissionMemberNoteService commissionMemberNoteService;

    public CommissionMemberNoteController(CommissionMemberNoteService commissionMemberNoteService)
    {
        this.commissionMemberNoteService = commissionMemberNoteService;
    }

    @GetMapping("/{studentId}/{internshipId}")
    public List<MentorNoteDTO> getNotes(@PathVariable Long studentId,
                                        @PathVariable Long internshipId)
    {
        return commissionMemberNoteService.getNotes(studentId, internshipId, MentorNoteDTO.class);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId)
    {
        commissionMemberNoteService.deleteNote(noteId);
    }

    @PostMapping
    public CommissionMemberNoteDTO insertNewNote(@RequestBody CommissionMemberNoteDTO newNote)
    {
        return commissionMemberNoteService.insertNote(newNote, CommissionMemberNoteDTO.class);
    }
}
