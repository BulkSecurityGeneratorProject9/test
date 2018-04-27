package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AgentMasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AgentMaster and its DTO AgentMasterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgentMasterMapper extends EntityMapper<AgentMasterDTO, AgentMaster> {


}
