package com.perrut.gestao_vagas.modules.candidate.useCases;

import com.perrut.gestao_vagas.modules.candidate.CandidateEntity;
import com.perrut.gestao_vagas.modules.candidate.CandidateRepository;
import com.perrut.gestao_vagas.modules.candidate.dtos.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {

        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found");
                });

       return ProfileCandidateResponseDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .description(candidate.getDescription())
                .build();
    }
}
