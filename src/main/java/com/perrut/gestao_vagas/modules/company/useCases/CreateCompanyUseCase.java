package com.perrut.gestao_vagas.modules.company.useCases;

import com.perrut.gestao_vagas.exceptions.UserFoundException;
import com.perrut.gestao_vagas.modules.company.entities.CompanyEntitity;
import com.perrut.gestao_vagas.modules.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public CompanyEntitity execute(CompanyEntitity companyEntitity) {

        this.companyRepository
                .findByUsernameOrEmail(companyEntitity.getUsername(), companyEntitity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        companyEntitity.setPassword(passwordEncoder.encode(companyEntitity.getPassword()));
        companyEntitity = companyRepository.save(companyEntitity);

        return companyEntitity;
    }
}
