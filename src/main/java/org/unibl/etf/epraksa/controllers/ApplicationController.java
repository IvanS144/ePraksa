package org.unibl.etf.epraksa.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipApplicationDTO;
import org.unibl.etf.epraksa.model.dataTransferObjects.StudentApplicationDTO;
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
    public List<InternshipApplicationDTO> getApplicationsForInternship(@PathVariable(name = "internshipId") Long internshipId,
                                                                       @RequestParam (required = false) State state) {
        return applicationService.getApplicationsForInternship(internshipId, state, InternshipApplicationDTO.class);
    }

    @GetMapping("/{internshipId}/{studentId}")
    public StudentApplicationDTO getApplication(@PathVariable(name = "internshipId") Long internshipId,
                                                @PathVariable(name = "studentId") Long studentId) {
        return applicationService.getApplication(internshipId, studentId, StudentApplicationDTO.class);
    }

    @GetMapping("/student/{studentId}")
    public List<StudentApplicationDTO> getApplicationsForStudent(@PathVariable(name="studentId") Long studentId,
                                                                 @RequestParam (required = false) State state) {
        return applicationService.getApplicationsForStudent(studentId, state, StudentApplicationDTO.class);
    }

    @PostMapping
    //@PreAuthorize("hasRole('STUDENT')")
    public void insert(@RequestBody @Valid ApplicationRequest request) {
        applicationService.insert(request);
    }

    @PutMapping("/{internshipId}/{studentId}/{state}")
    //@PreAuthorize("hasRole('MENTOR')")
    public void setState(@PathVariable Long internshipId, @PathVariable Long studentId, @PathVariable State state, @RequestBody(required = false) Comment comment) {
        applicationService.setState(internshipId, studentId, state, comment);
    }

    @DeleteMapping("/{internshipId}/{studentId}")
    //@PreAuthorize("hasRole('STUDENT')")

    public void delete(@PathVariable Long internshipId, @PathVariable Long studentId )
    {
        applicationService.delete(internshipId, studentId);
    }
}
