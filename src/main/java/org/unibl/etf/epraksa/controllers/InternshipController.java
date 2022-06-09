package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipDTO;
import org.unibl.etf.epraksa.model.dataTransferObjects.ReportByMentorDTO;
import org.unibl.etf.epraksa.model.entities.InternshipStatus;
import org.unibl.etf.epraksa.model.entities.ReportByMentor;
import org.unibl.etf.epraksa.model.dataTransferObjects.StudentDTO;
import org.unibl.etf.epraksa.model.entities.ReportByMentorQuestions;
import org.unibl.etf.epraksa.model.entities.json.OpinionByMentorJSON;
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


    @PutMapping("/{internshipId}/accept/{flag}")
    public void setAcceptanceStatus(@PathVariable Long internshipId, @PathVariable Boolean flag, @RequestParam(required = false) String reason)
    {
        internshipService.setAcceptanceStatus(internshipId, flag, reason);
    }

    @GetMapping("/{internshipId}/students")
    public List<StudentDTO> getAllStudentsOnInternship(@PathVariable Long internshipId)
    {
        return internshipService.getAllStudentsOnInternship(internshipId, StudentDTO.class);
    }


    @GetMapping
    public List<InternshipDTO> filter(@RequestParam (required = false) String type,
                                      @RequestParam (required = false) Long mentorId,
                                      @RequestParam (required = false)Long companyId,
                                      @RequestParam(required=false) InternshipStatus status) {
        return internshipService.filter(type,mentorId,companyId, status, InternshipDTO.class);
    }

    @GetMapping("/{internshipId}")
    public InternshipDTO getInternship(@PathVariable(name = "internshipId") Long internshipId){
        return internshipService.getInternship(internshipId, InternshipDTO.class);
    }

    @PutMapping("/{internshipId}/finish")
    public InternshipDTO setFinishedStatus(@PathVariable Long internshipId)
    {
        return internshipService.setFinishedStatus(internshipId, InternshipDTO.class);

    }

    @PostMapping
    public InternshipDTO addInternship(@RequestBody @Valid InternshipRequest request)
    {
        return internshipService.insert(request, InternshipDTO.class);
    }

    @PutMapping("/{internshipId}")
    public InternshipDTO update(@PathVariable Long internshipId, @RequestBody @Valid InternshipRequest request)
    {
        return internshipService.update(internshipId, request, InternshipDTO.class);
    }

    @GetMapping("/{internshipId}/{studentId}/report")
    public ReportByMentorDTO getReportFromMentor(@PathVariable(name = "internshipId") Long internshipId,
                                                 @PathVariable(name = "studentId") Long studentId){

        return internshipService.getReport(studentId, internshipId, ReportByMentorDTO.class);
    }

    @GetMapping("/questions")
    public List<ReportByMentorQuestions> getAllQuestions()
    {
        return internshipService.getAllQuestions();
    }

    @PutMapping("/{internshipId}/{studentId}/report")
    ReportByMentorDTO updateReportFromMentor(@PathVariable(name = "internshipId") Long internshipId,
                                             @PathVariable(name = "studentId") Long studentId,
                                             @RequestBody @Valid ReportByMentorDTO request) {
        return internshipService.updateReportFromMentor(internshipId, studentId, request, ReportByMentorDTO.class);
    }

    @DeleteMapping("/{internshipId}/{studentId}/report")
    public void deleteReportFromMentor(@PathVariable(name = "internshipId") Long internshipId,
                                       @PathVariable(name = "studentId") Long studentId) {
        internshipService.deleteReportFromMentor(internshipId, studentId);
    }

    @PutMapping("/{internshipId}/activate")
    public InternshipDTO activateInternship(@PathVariable Long internshipId)
    {
        return internshipService.setActive(internshipId, InternshipDTO.class);
    }

    @GetMapping("/{studentId}/latestReport")
    public ReportByMentorDTO getLatestReport(@PathVariable Long studentId)
    {
        return internshipService.getLatestReport(studentId, ReportByMentorDTO.class);
    }

//    @GetMapping("/{mentorId}")
//    public List<InternshipDTO> getInternshipsByMentor(@PathVariable(name="mentorId") Long mentorId){
//        return internshipService.getInternshipsByMentor(mentorId, InternshipDTO.class);
//    }
}
