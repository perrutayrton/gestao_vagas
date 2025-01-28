package com.perrut.gestao_vagas.modules.company.controllers;

import com.perrut.gestao_vagas.modules.company.dtos.CreateJobDTO;
import com.perrut.gestao_vagas.modules.company.entities.JobEntity;
import com.perrut.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {

        var jobEntity = JobEntity.builder()
                .companyId(UUID.fromString(request.getAttribute("company_id").toString()))
                .benefits(createJobDTO.getBenefits())
                .description(createJobDTO.getDescription())
                .level(createJobDTO.getLevel())
                .build();

        return this.createJobUseCase.execute(jobEntity);
    }
}
