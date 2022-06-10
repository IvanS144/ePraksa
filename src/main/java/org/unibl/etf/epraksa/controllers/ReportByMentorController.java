package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.ReportByMentorDTO;
import org.unibl.etf.epraksa.model.entities.ReportByMentorQuestions;
import org.unibl.etf.epraksa.services.ReportByMentorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reportbymentor")
public class ReportByMentorController
{
    private final ReportByMentorService reportByMentorService;

    public ReportByMentorController(ReportByMentorService reportByMentorService)
    {
        this.reportByMentorService = reportByMentorService;
    }

    @GetMapping("/questions")
    public List<ReportByMentorQuestions> getAllQuestions()
    {
        return reportByMentorService.getAllQuestions();
    }

    @GetMapping("/{internshipId}/{studentId}")
    public ReportByMentorDTO getReportFromMentor(@PathVariable(name = "internshipId") Long internshipId,
                                                 @PathVariable(name = "studentId") Long studentId) {

        return reportByMentorService.getReport(studentId, internshipId, ReportByMentorDTO.class);
    }

    @PutMapping("/{internshipId}/{studentId}")
    ReportByMentorDTO updateReportFromMentor(@PathVariable(name = "internshipId") Long internshipId,
                                             @PathVariable(name = "studentId") Long studentId,
                                             @RequestBody @Valid ReportByMentorDTO request) {
        return reportByMentorService.updateReportFromMentor(internshipId, studentId, request, ReportByMentorDTO.class);
    }

    @DeleteMapping("/{internshipId}/{studentId}")
    public void deleteReportFromMentor(@PathVariable(name = "internshipId") Long internshipId,
                                       @PathVariable(name = "studentId") Long studentId) {
        reportByMentorService.deleteReportFromMentor(internshipId, studentId);
    }

    @GetMapping("/{studentId}/latestReport")
    public ReportByMentorDTO getLatestReport(@PathVariable Long studentId)
    {
        return reportByMentorService.getLatestReport(studentId, ReportByMentorDTO.class);
    }

}
