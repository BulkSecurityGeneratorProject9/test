package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.AgentMasterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.AgentMasterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing AgentMaster.
 */
@RestController
@RequestMapping("/api")
public class AgentMasterResource {

    private final Logger log = LoggerFactory.getLogger(AgentMasterResource.class);

    private static final String ENTITY_NAME = "agentMaster";

    private final AgentMasterService agentMasterService;

    public AgentMasterResource(AgentMasterService agentMasterService) {
        this.agentMasterService = agentMasterService;
    }

    /**
     * POST  /agent-masters : Create a new agentMaster.
     *
     * @param agentMasterDTO the agentMasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agentMasterDTO, or with status 400 (Bad Request) if the agentMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agent-masters")
    @Timed
    public ResponseEntity<AgentMasterDTO> createAgentMaster(@RequestBody AgentMasterDTO agentMasterDTO) throws URISyntaxException {
        log.debug("REST request to save AgentMaster : {}", agentMasterDTO);
        if (agentMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new agentMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }

            int agentnumber = 0;
            List<AgentMasterDTO> agentMasterDTOList = agentMasterService.findAll();
            agentMasterDTO.setAgentNumber(agentMasterDTOList.size()+1);


        AgentMasterDTO result = agentMasterService.save(agentMasterDTO);
        return ResponseEntity.created(new URI("/api/agent-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agent-masters : Updates an existing agentMaster.
     *
     * @param agentMasterDTO the agentMasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agentMasterDTO,
     * or with status 400 (Bad Request) if the agentMasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the agentMasterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agent-masters")
    @Timed
    public ResponseEntity<AgentMasterDTO> updateAgentMaster(@RequestBody AgentMasterDTO agentMasterDTO) throws URISyntaxException {
        log.debug("REST request to update AgentMaster : {}", agentMasterDTO);
        if (agentMasterDTO.getId() == null) {
            return createAgentMaster(agentMasterDTO);
        }
        AgentMasterDTO result = agentMasterService.save(agentMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agentMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agent-masters : get all the agentMasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agentMasters in body
     */
    @GetMapping("/agent-masters")
    @Timed
    public List<AgentMasterDTO> getAllAgentMasters() {
        log.debug("REST request to get all AgentMasters");
        return agentMasterService.findAll();
        }

    /**
     * GET  /agent-masters/:id : get the "id" agentMaster.
     *
     * @param id the id of the agentMasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agentMasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agent-masters/{id}")
    @Timed
    public ResponseEntity<AgentMasterDTO> getAgentMaster(@PathVariable String id) {
        log.debug("REST request to get AgentMaster : {}", id);
        AgentMasterDTO agentMasterDTO = agentMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agentMasterDTO));
    }

    /**
     * DELETE  /agent-masters/:id : delete the "id" agentMaster.
     *
     * @param id the id of the agentMasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agent-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgentMaster(@PathVariable String id) {
        log.debug("REST request to delete AgentMaster : {}", id);
        agentMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
