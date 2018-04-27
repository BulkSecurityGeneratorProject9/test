package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AgentMaster;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the AgentMaster entity.
 */
@Repository
public class AgentMasterRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<AgentMaster> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public AgentMasterRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(AgentMaster.class);
        this.findAllStmt = session.prepare("SELECT * FROM agentMaster");
        this.truncateStmt = session.prepare("TRUNCATE agentMaster");
    }

    public List<AgentMaster> findAll() {
        List<AgentMaster> agentMastersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                AgentMaster agentMaster = new AgentMaster();
                agentMaster.setId(row.getUUID("id"));
                agentMaster.setAgentNumber(row.getInt("agentNumber"));
                agentMaster.setAgentName(row.getString("agentName"));
                return agentMaster;
            }
        ).forEach(agentMastersList::add);
        return agentMastersList;
    }

    public AgentMaster findOne(UUID id) {
        return mapper.get(id);
    }

    public AgentMaster save(AgentMaster agentMaster) {
        if (agentMaster.getId() == null) {
            agentMaster.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<AgentMaster>> violations = validator.validate(agentMaster);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(agentMaster);
        return agentMaster;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
