package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.epraksa.model.dataTransferObjects.EntryDTO;
import org.unibl.etf.epraksa.model.reply.WorkDairyReply;
import org.unibl.etf.epraksa.services.WorkDiaryService;

@RestController
@RequestMapping("/workdiary")
public class WorkDiaryController {
    private final WorkDiaryService workDiaryService;

    public WorkDiaryController(WorkDiaryService workDiaryService) {
        this.workDiaryService = workDiaryService;
    }

    @GetMapping("/{workdiaryId}")
    public WorkDairyReply getWorkDiaryEntry(@PathVariable(name = "workdiaryId") Long workDiaryId){
        return workDiaryService.getWorkDiaryEntry(workDiaryId, WorkDairyReply.class);
    }
}
