package com.perrut.gestao_vagas.modules.candidate.controllers;

import com.perrut.gestao_vagas.modules.candidate.dtos.AuthCandidateRequestDTO;
import com.perrut.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {

        try {

            return ResponseEntity.ok().body(this.authCandidateUseCase.execute(authCandidateRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
