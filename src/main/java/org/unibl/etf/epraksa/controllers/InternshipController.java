package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.epraksa.services.InternshipService;

@RestController
@RequestMapping("/internships")
public class InternshipController {
    private final InternshipService internshipService;

    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }


    @PutMapping("/{internshipId}/acceptance/{isAccepted}")
    public void setAcceptanceStatus(@PathVariable Long internshipId, @PathVariable Boolean isAccepted)
    {
        internshipService.setAcceptanceStatus(internshipId, isAccepted);
    }
}
