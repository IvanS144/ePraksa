package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.model.dataTransferObjects.MentorNoteDTO;
import org.unibl.etf.epraksa.model.entities.MentorNote;
import org.unibl.etf.epraksa.repositories.MentorNoteRepository;
import org.unibl.etf.epraksa.repositories.MentorRepository;
import org.unibl.etf.epraksa.services.MentorNoteService;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentorNoteServiceImpl implements MentorNoteService
{
    private final MentorNoteRepository mentorNoteRepository;
    private final ModelMapper modelMapper;
    private final MentorRepository mentorRepository;
    private final EntityManager entityManager;

    public MentorNoteServiceImpl(MentorNoteRepository mentorNoteRepository, ModelMapper modelMapper, MentorRepository mentorRepository, EntityManager entityManager)
    {
        this.mentorNoteRepository = mentorNoteRepository;
        this.modelMapper = modelMapper;
        this.mentorRepository = mentorRepository;
        this.entityManager = entityManager;
    }

    @Override
    public <T> List<T> getNotes(Long studentId, Long internshipId, Class<T> replyClass)
    {
        return mentorNoteRepository.getNotes(studentId, internshipId)
                .stream()
                .map(note -> modelMapper.map(note, replyClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T insertNote(MentorNoteDTO newNote, Class<T> replyClass)
    {
        MentorNote mentorNote = new MentorNote();
        mentorNote.setText(newNote.getText());
        mentorNote.setCreatedAt(LocalDate.now());
        mentorNote.setLastModifiedDate(LocalDate.now());
        mentorNote.setMentor(mentorRepository.getById(newNote.getMentorId()));
        mentorNote = mentorNoteRepository.saveAndFlush(mentorNote);
        entityManager.refresh(mentorNote);
        return modelMapper.map(mentorNote, replyClass);
    }

    @Override
    public void deleteNote(Long noteId)
    {
        mentorNoteRepository.deleteById(noteId);
    }
}
