package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.MentorDTO;
import org.unibl.etf.epraksa.model.replies.MentorReply;
import org.unibl.etf.epraksa.services.MentorService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/mentors")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping("/{companyId}/")
    public List<MentorDTO> getAllMentors(@PathVariable Long companyId){
        return mentorService.getAllMentors(companyId, MentorDTO.class);
    }

    @GetMapping("/{mentorId}")
    public MentorDTO getById(@PathVariable Long mentorId)
    {
        return mentorService.getMentorById(mentorId, MentorDTO.class);
    }
}
