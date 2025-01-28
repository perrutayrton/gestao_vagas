package com.perrut.gestao_vagas.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.perrut.gestao_vagas.modules.candidate.CandidateRepository;
import com.perrut.gestao_vagas.modules.candidate.dtos.AuthCandidateRequestDTO;
import com.perrut.gestao_vagas.modules.candidate.dtos.AuthCandidateResponseDTO;
import com.perrut.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import com.perrut.gestao_vagas.modules.company.entities.CompanyEntitity;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    private static String LOGIN_INCORRECT = "Username/Password Incorrect";

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {

        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(LOGIN_INCORRECT);
                });

        if (verifyPasswordMatches(authCandidateRequestDTO.password(), candidate.getPassword())) {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var expiresIn = Instant.now().plus(Duration.ofMinutes(30));
            var token = JWT.create()
                    .withIssuer(candidate.getName())
                    .withExpiresAt(expiresIn)
                    .withSubject(candidate.getId().toString())
                    .withClaim("roles", Arrays.asList("candidate"))
                    .sign(algorithm);

            return AuthCandidateResponseDTO.builder()
                    .acces_token(token)
                    .expires_in(expiresIn.getEpochSecond())
                    .build();

        } else {
            throw new AuthenticationException(LOGIN_INCORRECT);
        }
    }

    private boolean verifyPasswordMatches(String rawPassword, String password) {

        return this.passwordEncoder.matches(rawPassword, password);
    }
}
