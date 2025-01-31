package com.perrut.gestao_vagas.modules.candidate.useCases;

import com.perrut.gestao_vagas.exceptions.UserFoundException;
import com.perrut.gestao_vagas.modules.candidate.CandidateEntity;
import com.perrut.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {

        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        candidateEntity.setPassword(passwordEncoder.encode(candidateEntity.getPassword()));
        candidateEntity = candidateRepository.save(candidateEntity);

        return candidateEntity;
    }
}
