package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.entities.State;
import org.unibl.etf.epraksa.model.requests.ApplicationRequest;
import org.unibl.etf.epraksa.services.ApplicationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public void insert(@RequestBody @Valid ApplicationRequest request)
    {
        applicationService.insert(request);
    }

    @PutMapping("/{internshipId}/{studentId}/{state}")
    public void setState(@PathVariable Long internshipId, @PathVariable Long studentId, @PathVariable State state){
        applicationService.setState(internshipId, studentId, state);
    }
}
