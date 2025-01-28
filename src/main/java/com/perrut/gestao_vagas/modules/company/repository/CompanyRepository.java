package com.perrut.gestao_vagas.modules.company.repository;

import com.perrut.gestao_vagas.modules.company.entities.CompanyEntitity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntitity, UUID> {

    Optional<CompanyEntitity> findByUsernameOrEmail(String username, String email);

    Optional<CompanyEntitity> findByUsername(String username);
}
