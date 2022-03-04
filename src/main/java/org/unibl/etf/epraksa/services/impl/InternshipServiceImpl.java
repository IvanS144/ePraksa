package org.unibl.etf.epraksa.services.impl;

import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.repositories.InternshipRepository;
import org.unibl.etf.epraksa.services.InternshipService;

import javax.transaction.Transactional;

@Service
@Transactional
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;

    public InternshipServiceImpl(InternshipRepository internshipRepository) {
        this.internshipRepository = internshipRepository;
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
