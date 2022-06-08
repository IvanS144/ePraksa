package org.unibl.etf.epraksa.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.model.dataTransferObjects.UserDTO;
import org.unibl.etf.epraksa.repositories.UserRepository;
import org.unibl.etf.epraksa.services.EpraksaUserDetailsService;

@Service
public class EpraksaUserDetailsServiceImpl implements EpraksaUserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public EpraksaUserDetailsServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDTO loadUserByUsername(String loginMail) throws UsernameNotFoundException {
        return modelMapper.map(userRepository.findByLoginMail(loginMail).orElseThrow(() -> new UsernameNotFoundException(loginMail)), UserDTO.class);
    }
}
