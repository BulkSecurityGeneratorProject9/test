package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AgentMasterDTO;
import java.util.List;

/**
 * Service Interface for managing AgentMaster.
 */
public interface AgentMasterService {

    /**
     * Save a agentMaster.
     *
     * @param agentMasterDTO the entity to save
     * @return the persisted entity
     */
    AgentMasterDTO save(AgentMasterDTO agentMasterDTO);

    /**
     * Get all the agentMasters.
     *
     * @return the list of entities
     */
    List<AgentMasterDTO> findAll();

    /**
     * Get the "id" agentMaster.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AgentMasterDTO findOne(String id);

    /**
     * Delete the "id" agentMaster.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
