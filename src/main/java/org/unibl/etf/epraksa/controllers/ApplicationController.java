package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.ApplicationDTO;
import org.unibl.etf.epraksa.model.entities.Comment;
import org.unibl.etf.epraksa.model.entities.State;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;
import org.unibl.etf.epraksa.services.ApplicationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/{internshipId}")
    public List<ApplicationDTO> getApplicationsForInternship(@PathVariable(name = "internshipId") Long internshipId,
                                                             @RequestParam (required = false) String status) {
        return applicationService.getApplicationsForInternship(internshipId, status, ApplicationDTO.class);
    }

    @GetMapping("/{internshipId}/{studentId}")
    public ApplicationDTO getApplication(@PathVariable(name = "internshipId") Long internshipId,
                                         @PathVariable(name = "studentId") Long studentId) {
        return applicationService.getApplication(internshipId, studentId, ApplicationDTO.class);
    }

    @GetMapping("/{studentId}")
    public List<ApplicationDTO> getApplicationsForStudent(@PathVariable(name="studentId") Long studentId,
                                                          @RequestParam (required = false) String status) {
        return applicationService.getApplicationsForStudent(studentId,status, ApplicationDTO.class);
    }

    @PostMapping
    public void insert(@RequestBody @Valid ApplicationRequest request)
    {
        applicationService.insert(request);
    }

    @PutMapping("/{internshipId}/{studentId}/{state}")
    public void setState(@PathVariable Long internshipId, @PathVariable Long studentId, @PathVariable State state, @RequestBody(required = false) Comment comment){
        applicationService.setState(internshipId, studentId, state, comment);
    }
}
