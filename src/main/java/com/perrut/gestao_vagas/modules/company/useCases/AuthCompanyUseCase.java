package com.perrut.gestao_vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.perrut.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import com.perrut.gestao_vagas.modules.company.dtos.AuthCompanyResponseDTO;
import com.perrut.gestao_vagas.modules.company.entities.CompanyEntitity;
import com.perrut.gestao_vagas.modules.company.repository.CompanyRepository;
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
public class AuthCompanyUseCase {

    private static String LOGIN_INCORRECT = "Username/Password Incorrect";

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws Exception {

        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException(LOGIN_INCORRECT);
        });

        if (verifyPasswordMatches(authCompanyDTO.getPassword(), company.getPassword())) {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var expiresIn = Instant.now().plus(Duration.ofHours(1));
            var token = JWT.create().withIssuer(company.getName())
                    .withExpiresAt(expiresIn)
                    .withClaim("roles", Arrays.asList("COMPANY"))
                    .withSubject(company.getUuid().toString())
                    .sign(algorithm);

            return AuthCompanyResponseDTO.builder()
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
