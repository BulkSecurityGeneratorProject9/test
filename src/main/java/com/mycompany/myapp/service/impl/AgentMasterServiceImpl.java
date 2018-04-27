package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AgentMasterService;
import com.mycompany.myapp.domain.AgentMaster;
import com.mycompany.myapp.repository.AgentMasterRepository;
import com.mycompany.myapp.service.dto.AgentMasterDTO;
import com.mycompany.myapp.service.mapper.AgentMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AgentMaster.
 */
@Service
public class AgentMasterServiceImpl implements AgentMasterService {

    private final Logger log = LoggerFactory.getLogger(AgentMasterServiceImpl.class);

    private final AgentMasterRepository agentMasterRepository;

    private final AgentMasterMapper agentMasterMapper;

    public AgentMasterServiceImpl(AgentMasterRepository agentMasterRepository, AgentMasterMapper agentMasterMapper) {
        this.agentMasterRepository = agentMasterRepository;
        this.agentMasterMapper = agentMasterMapper;
    }

    /**
     * Save a agentMaster.
     *
     * @param agentMasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgentMasterDTO save(AgentMasterDTO agentMasterDTO) {
        log.debug("Request to save AgentMaster : {}", agentMasterDTO);
        AgentMaster agentMaster = agentMasterMapper.toEntity(agentMasterDTO);
        agentMaster = agentMasterRepository.save(agentMaster);
        return agentMasterMapper.toDto(agentMaster);
    }

    /**
     * Get all the agentMasters.
     *
     * @return the list of entities
     */
    @Override
    public List<AgentMasterDTO> findAll() {
        log.debug("Request to get all AgentMasters");
        return agentMasterRepository.findAll().stream()
            .map(agentMasterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one agentMaster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public AgentMasterDTO findOne(String id) {
        log.debug("Request to get AgentMaster : {}", id);
        AgentMaster agentMaster = agentMasterRepository.findOne(UUID.fromString(id));
        return agentMasterMapper.toDto(agentMaster);
    }

    /**
     * Delete the agentMaster by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AgentMaster : {}", id);
        agentMasterRepository.delete(UUID.fromString(id));
    }
}
