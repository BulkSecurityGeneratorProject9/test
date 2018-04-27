package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the AgentMaster entity.
 */
public class AgentMasterDTO implements Serializable {

    private UUID id;

    private Integer agentNumber;

    private String agentName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(Integer agentNumber) {
        this.agentNumber = agentNumber;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentMasterDTO agentMasterDTO = (AgentMasterDTO) o;
        if(agentMasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agentMasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgentMasterDTO{" +
            "id=" + getId() +
            ", agentNumber=" + getAgentNumber() +
            ", agentName='" + getAgentName() + "'" +
            "}";
    }
}
