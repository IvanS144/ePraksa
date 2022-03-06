package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipDTO;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;
import org.unibl.etf.epraksa.services.InternshipService;
import java.util.List;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;
import javax.validation.Valid;

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
                                      @RequestParam (required = false) Boolean isPublished) {
        return internshipService.filter(id, type, isPublished, InternshipDTO.class);
    }

    @PutMapping("/{internshipId}/{isFinished}")
    public void setFinishedStatus(@PathVariable Long internshipId, @PathVariable Boolean isFinished)
    {
        internshipService.setFinishedStatus(internshipId, isFinished);

    }

    @PostMapping
    public InternshipDTO addInternship(@RequestBody @Valid InternshipRequest request)
    {
        return internshipService.insert(request, InternshipDTO.class);
    }

    @GetMapping("/{internshipId}/{studentId}")
    public ReportByMentor getReportFromMentor(@PathVariable(name = "internshipId") Long internshipId,
                                              @PathVariable(name = "studentId") Long studentId){

        return internshipService.getReport(studentId, internshipId);
    }
}
