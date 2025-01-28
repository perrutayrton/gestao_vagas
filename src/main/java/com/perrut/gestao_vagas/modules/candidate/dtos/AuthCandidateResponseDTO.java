package com.perrut.gestao_vagas.modules.candidate.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCandidateResponseDTO {

    private String acces_token;
    private Long expires_in;
}
