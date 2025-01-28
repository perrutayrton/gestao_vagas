package com.perrut.gestao_vagas.modules.candidate.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    private String username;
    private String email;
    private String description;
    private UUID id;
    private String name;
}
