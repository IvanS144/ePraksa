package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipDTO;
import org.unibl.etf.epraksa.model.entities.InternshipType;
import org.unibl.etf.epraksa.services.InternshipService;

import java.util.List;

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

    @GetMapping
    public List<InternshipDTO> filter(@RequestParam (required = false) Long id,
                                      @RequestParam (required = false) String type,
                                      @RequestParam (required = false) Boolean isPublished){
        return internshipService.filter(id, type, isPublished, InternshipDTO.class);
    }
}
