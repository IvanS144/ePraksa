package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.EntryDTO;
import org.unibl.etf.epraksa.model.entities.State;
import org.unibl.etf.epraksa.model.replies.WorkDairyReply;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;
import org.unibl.etf.epraksa.services.WorkDiaryService;

import java.util.List;

@RestController
@RequestMapping("/workdiaries")
public class WorkDiaryController {
    private final WorkDiaryService workDiaryService;

    public WorkDiaryController(WorkDiaryService workDiaryService) {
        this.workDiaryService = workDiaryService;
    }

    @GetMapping("/{workdiaryId}")
    public WorkDairyReply getWorkDiary(@PathVariable(name = "workdiaryId") Long workDiaryId){
        return workDiaryService.getWorkDiary(workDiaryId, WorkDairyReply.class);
    }

    @GetMapping("/{studentId}/{internshipId}")
    public WorkDairyReply getWorkDiary(@PathVariable Long studentId, @PathVariable Long internshipId){
        return workDiaryService.getWorkDiary(studentId, internshipId, WorkDairyReply.class);
    }


    @PostMapping("/entries")
    public EntryDTO addWorkDiaryEntry(@RequestBody WorkDiaryEntryRequest request){

        return workDiaryService.insert(request, EntryDTO.class);
    }

    @PutMapping("/{workdiaryId}/entries/{entryId}")
    public void updateWorkDiaryEntry(@PathVariable(name = "workdiaryId") Long id,
                                     @PathVariable(name = "entryId") Long entryId,
                                     @RequestBody WorkDiaryEntryRequest request){

        workDiaryService.update(request, id, entryId);
    }

    @PutMapping("/studentID/{studentId}/internshipID/{internshipId}")
    public void updateWorkDiaryStateByStudentAndInternship(@PathVariable(name = "studentId") Long studentId,
                                                           @PathVariable(name = "internshipId") Long internshipId,
                                                           @RequestParam (name = "state") State state){
        workDiaryService.updateStateByStudentAndInternship(studentId, internshipId, state);
    }

    @PutMapping("/workDiary/{workDiaryId}")
    public void updateWorkDiaryStateByWorkDiary(@PathVariable(name = "workDiaryId") Long workDiaryId,
                                                @RequestParam (name = "state") State state){
        workDiaryService.updateStateByWorkDiary(workDiaryId, state);
    }

    @GetMapping("/student/{studentId}")
    public List<WorkDairyReply> getWorkDiaryByStudent(@PathVariable Long studentId){
        return workDiaryService.getWorkDiariesByStudent(studentId, WorkDairyReply.class);
    }
}
