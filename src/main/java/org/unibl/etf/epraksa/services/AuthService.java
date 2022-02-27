package org.unibl.etf.epraksa.services;

import org.springframework.http.ResponseEntity;
import org.unibl.etf.epraksa.model.requests.LoginRequest;


public interface AuthService {
    ResponseEntity<Object> login(LoginRequest request);

}
