package org.unibl.etf.epraksa.services;

import org.springframework.http.ResponseEntity;
import org.unibl.etf.epraksa.model.replies.LoginReply;
import org.unibl.etf.epraksa.model.requests.LoginRequest;


public interface AuthService {
    ResponseEntity<LoginReply> login(LoginRequest request);

}
