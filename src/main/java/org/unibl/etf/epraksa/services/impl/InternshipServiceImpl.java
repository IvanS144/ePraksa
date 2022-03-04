package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.model.dataTransferObjects.InternshipDTO;
import org.unibl.etf.epraksa.model.entities.Internship;
import org.unibl.etf.epraksa.model.entities.InternshipType;
import org.unibl.etf.epraksa.repositories.InternshipRepository;
import org.unibl.etf.epraksa.services.InternshipService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final ModelMapper modelMapper;

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
    public <T> List<T> filter(Long id, String type, Boolean isPublished, Class<T> replyClass) {

        InternshipType it = null;

        if (type != null)
            it = InternshipType.valueOf(type);

        return internshipRepository.filter(id, it, isPublished)
                .stream()
                .map(e -> modelMapper.map(e, replyClass))
                .collect(Collectors.toList());
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
