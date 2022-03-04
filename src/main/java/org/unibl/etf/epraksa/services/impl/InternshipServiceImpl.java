package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.BadRequestException;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.requests.InternshipRequest;
import org.unibl.etf.epraksa.repositories.InternshipRepository;
import org.unibl.etf.epraksa.services.InternshipService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public InternshipServiceImpl(InternshipRepository internshipRepository, ModelMapper modelMapper) {
        this.internshipRepository = internshipRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void setAcceptanceStatus(Long internshipId, Boolean isAccepted) {
        if(!internshipRepository.existsById(internshipId))
        {
            throw new NotFoundException("Ta praksa ne postoji");
        }
        else
        {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setIsAccepted(isAccepted);
            internshipRepository.saveAndFlush(internship);
        }

    }

    @Override
    public <T> T insert(InternshipRequest request, Class<T> replyClass) {
        Internship internship = modelMapper.map(request, Internship.class);
        if(internship.getSubmissionDue().isAfter(internship.getStartDate()) || internship.getEndDate().isBefore(internship.getStartDate()) || internship.getStartDate().isEqual(internship.getEndDate()))
            throw new BadRequestException("Datumi nisu validni");
        internship.setInternshipId(null);
        internship.setIsPublished(false);
        internship.setIsFinished(false);
        internship=internshipRepository.saveAndFlush(internship);
        entityManager.refresh(internship);
        return modelMapper.map(internship, replyClass);

    }

    @Override
    public void setFinishedStatus(Long internshipId, Boolean isFinished) {
        if(!internshipRepository.existsById(internshipId))
        {
            throw new NotFoundException("Ta praksa ne postoji");
        }
        else
        {
            Internship internship = internshipRepository.getById(internshipId);
            internship.setIsFinished(isFinished);
            internshipRepository.saveAndFlush(internship);
        }
    }
}
