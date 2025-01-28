package com.perrut.gestao_vagas.modules.company.controllers;

import com.perrut.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import com.perrut.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) throws Exception {
        try {
            return ResponseEntity.ok().body(this.authCompanyUseCase.execute(authCompanyDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
