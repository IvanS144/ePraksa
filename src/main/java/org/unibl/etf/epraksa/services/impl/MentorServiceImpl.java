package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.NotFoundException;
import org.unibl.etf.epraksa.repositories.MentorRepository;
import org.unibl.etf.epraksa.services.MentorService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MentorServiceImpl implements MentorService {
    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;
    public MentorServiceImpl(MentorRepository mentorRepository, ModelMapper modelMapper) {
        this.mentorRepository = mentorRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public <T> List<T> getAllMentors(Long companyId, Class<T> replyClass){
        return mentorRepository.filter(companyId).stream().map(m -> modelMapper.map(m, replyClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T getMentorById(Long mentorId, Class<T> replyClass) {
        return modelMapper.map(mentorRepository.findById(mentorId).orElseThrow(() -> new NotFoundException("Taj mentor ne postoji")), replyClass);
    }
}
