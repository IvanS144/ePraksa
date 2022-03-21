package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.WorkDiaryRecordDTO;
import org.unibl.etf.epraksa.model.reply.WorkDairyReply;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;
import org.unibl.etf.epraksa.services.WorkDiaryService;

import javax.validation.Valid;

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

//    @PostMapping("/{workdiaryId}/record")
    @PostMapping("/{workdiaryId}/zapisi")
    public WorkDiaryRecordDTO addWorkDiaryEntry(@PathVariable(name = "workdiaryId") Long id,
                                                @RequestBody WorkDiaryEntryRequest request){
//      nisam stavio @Validate zato sto ce se neki parametri u reqest-u naknadno popunjavati
//      osim ako se ne promijeni nacin slanja request-a
        return workDiaryService.insert(request, id, WorkDiaryRecordDTO.class);
    }

    @PutMapping("/{workdiaryId}/zapisi/{entryId}")
    public void updateWorkDiaryEntry(@PathVariable(name = "workdiaryId") Long id,
                                     @PathVariable(name = "entryId") Long entryId,
                                     @RequestBody WorkDiaryEntryRequest request){
//      nisam stavio @Validate zato sto ce se neki parametri u reqest-u naknadno popunjavati
//      osim ako se ne promijeni nacin slanja request-a
        workDiaryService.update(request, id, entryId);
    }
}
