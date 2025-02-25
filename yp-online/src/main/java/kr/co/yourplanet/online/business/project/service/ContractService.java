package kr.co.yourplanet.online.business.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.entity.project.ProjectContract;
import kr.co.yourplanet.online.business.project.repository.ProjectContractRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ProjectContractRepository projectContractRepository;

    public Optional<ProjectContract> getByProjectId(Long projectId) {
        return projectContractRepository.findByProjectId(projectId);
    }

    public ProjectContract save(ProjectContract contract) {
        return projectContractRepository.save(contract);
    }
}
