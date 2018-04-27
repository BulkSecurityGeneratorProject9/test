package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A AgentMaster.
 */
@Table(name = "agentMaster")
public class AgentMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private Integer agentNumber;

    private String agentName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getAgentNumber() {
        return agentNumber;
    }

    public AgentMaster agentNumber(Integer agentNumber) {
        this.agentNumber = agentNumber;
        return this;
    }

    public void setAgentNumber(Integer agentNumber) {
        this.agentNumber = agentNumber;
    }

    public String getAgentName() {
        return agentName;
    }

    public AgentMaster agentName(String agentName) {
        this.agentName = agentName;
        return this;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AgentMaster agentMaster = (AgentMaster) o;
        if (agentMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agentMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgentMaster{" +
            "id=" + getId() +
            ", agentNumber=" + getAgentNumber() +
            ", agentName='" + getAgentName() + "'" +
            "}";
    }
}
