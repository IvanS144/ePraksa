package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.dataTransferObjects.CommissionMemberNoteDTO;
import org.unibl.etf.epraksa.model.entities.CommissionMemberNote;
import org.unibl.etf.epraksa.repositories.CommissionMemberNoteRepository;
import org.unibl.etf.epraksa.repositories.CommissionMemberRepository;
import org.unibl.etf.epraksa.services.CommissionMemberNoteService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommissionMemberNoteServiceImpl implements CommissionMemberNoteService
{
    private final CommissionMemberNoteRepository commissionMemberNoteRepository;
    private final ModelMapper modelMapper;
    @PersistenceContext
    final EntityManager entityManager;
    private final CommissionMemberRepository commissionMemberRepository;

    public CommissionMemberNoteServiceImpl(CommissionMemberNoteRepository commissionMemberNoteRepository, ModelMapper modelMapper, EntityManager entityManager, CommissionMemberRepository commissionMemberRepository)
    {
        this.commissionMemberNoteRepository = commissionMemberNoteRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.commissionMemberRepository = commissionMemberRepository;
    }

    @Override
    public <T> List<T> getNotes(Long studentId, Long internshipId, Class<T> replyClass)
    {
        return commissionMemberNoteRepository.getNotes(studentId,internshipId)
                .stream()
                .map(note -> modelMapper.map(note, replyClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T insertNote(CommissionMemberNoteDTO newNote, Class<T> replyClass)
    {
        if(!commissionMemberRepository.existsById(newNote.getCommissionMemberId()))
            throw new NotFoundException("Ne postoji clan komisije, ciji je id " + newNote.getCommissionMemberId());

        CommissionMemberNote note = new CommissionMemberNote();
        note.setText(newNote.getText());
        note.setCreatedAt(LocalDate.now());
        note.setLastModifiedDate(LocalDate.now());
        note.setCommissionMember(commissionMemberRepository.getById(newNote.getCommissionMemberId()));
        note = commissionMemberNoteRepository.saveAndFlush(note);

        return modelMapper.map(note, replyClass);
    }

    @Override
    public void deleteNote(Long noteId)
    {
        commissionMemberNoteRepository.deleteById(noteId);
    }
}
