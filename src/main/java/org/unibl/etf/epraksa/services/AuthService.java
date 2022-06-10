package org.unibl.etf.epraksa.services;

import org.springframework.http.ResponseEntity;
import org.unibl.etf.epraksa.model.dataTransferObjects.LoginDTO;
import org.unibl.etf.epraksa.model.requests.LoginRequest;


public interface AuthService {
    ResponseEntity<LoginDTO> login(LoginRequest request);

}
