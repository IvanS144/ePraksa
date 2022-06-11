package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.MentorDTO;
import org.unibl.etf.epraksa.services.MentorService;

import java.util.List;

@RestController
@RequestMapping("/mentors")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping("/company/{companyId}")
    public List<MentorDTO> getAllMentorsInCompany(@PathVariable Long companyId){
        return mentorService.getAllMentors(companyId, MentorDTO.class);
    }

    @GetMapping("/{mentorId}")
    public MentorDTO getMentorById(@PathVariable Long mentorId)
    {
        return mentorService.getMentorById(mentorId, MentorDTO.class);
    }
}
