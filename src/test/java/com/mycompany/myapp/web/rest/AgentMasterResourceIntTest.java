package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.AgentMaster;
import com.mycompany.myapp.repository.AgentMasterRepository;
import com.mycompany.myapp.service.AgentMasterService;
import com.mycompany.myapp.service.dto.AgentMasterDTO;
import com.mycompany.myapp.service.mapper.AgentMasterMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgentMasterResource REST controller.
 *
 * @see AgentMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class AgentMasterResourceIntTest extends AbstractCassandraTest {

    private static final Integer DEFAULT_AGENT_NUMBER = 1;
    private static final Integer UPDATED_AGENT_NUMBER = 2;

    private static final String DEFAULT_AGENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_NAME = "BBBBBBBBBB";

    @Autowired
    private AgentMasterRepository agentMasterRepository;

    @Autowired
    private AgentMasterMapper agentMasterMapper;

    @Autowired
    private AgentMasterService agentMasterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAgentMasterMockMvc;

    private AgentMaster agentMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgentMasterResource agentMasterResource = new AgentMasterResource(agentMasterService);
        this.restAgentMasterMockMvc = MockMvcBuilders.standaloneSetup(agentMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgentMaster createEntity() {
        AgentMaster agentMaster = new AgentMaster()
            .agentNumber(DEFAULT_AGENT_NUMBER)
            .agentName(DEFAULT_AGENT_NAME);
        return agentMaster;
    }

    @Before
    public void initTest() {
        agentMasterRepository.deleteAll();
        agentMaster = createEntity();
    }

    @Test
    public void createAgentMaster() throws Exception {
        int databaseSizeBeforeCreate = agentMasterRepository.findAll().size();

        // Create the AgentMaster
        AgentMasterDTO agentMasterDTO = agentMasterMapper.toDto(agentMaster);
        restAgentMasterMockMvc.perform(post("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the AgentMaster in the database
        List<AgentMaster> agentMasterList = agentMasterRepository.findAll();
        assertThat(agentMasterList).hasSize(databaseSizeBeforeCreate + 1);
        AgentMaster testAgentMaster = agentMasterList.get(agentMasterList.size() - 1);
        assertThat(testAgentMaster.getAgentNumber()).isEqualTo(DEFAULT_AGENT_NUMBER);
        assertThat(testAgentMaster.getAgentName()).isEqualTo(DEFAULT_AGENT_NAME);
    }

    @Test
    public void createAgentMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentMasterRepository.findAll().size();

        // Create the AgentMaster with an existing ID
        agentMaster.setId(UUID.randomUUID());
        AgentMasterDTO agentMasterDTO = agentMasterMapper.toDto(agentMaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentMasterMockMvc.perform(post("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgentMaster in the database
        List<AgentMaster> agentMasterList = agentMasterRepository.findAll();
        assertThat(agentMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAgentMasters() throws Exception {
        // Initialize the database
        agentMasterRepository.save(agentMaster);

        // Get all the agentMasterList
        restAgentMasterMockMvc.perform(get("/api/agent-masters"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agentMaster.getId().toString())))
            .andExpect(jsonPath("$.[*].agentNumber").value(hasItem(DEFAULT_AGENT_NUMBER)))
            .andExpect(jsonPath("$.[*].agentName").value(hasItem(DEFAULT_AGENT_NAME.toString())));
    }

    @Test
    public void getAgentMaster() throws Exception {
        // Initialize the database
        agentMasterRepository.save(agentMaster);

        // Get the agentMaster
        restAgentMasterMockMvc.perform(get("/api/agent-masters/{id}", agentMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agentMaster.getId().toString()))
            .andExpect(jsonPath("$.agentNumber").value(DEFAULT_AGENT_NUMBER))
            .andExpect(jsonPath("$.agentName").value(DEFAULT_AGENT_NAME.toString()));
    }

    @Test
    public void getNonExistingAgentMaster() throws Exception {
        // Get the agentMaster
        restAgentMasterMockMvc.perform(get("/api/agent-masters/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAgentMaster() throws Exception {
        // Initialize the database
        agentMasterRepository.save(agentMaster);
        int databaseSizeBeforeUpdate = agentMasterRepository.findAll().size();

        // Update the agentMaster
        AgentMaster updatedAgentMaster = agentMasterRepository.findOne(agentMaster.getId());
        updatedAgentMaster
            .agentNumber(UPDATED_AGENT_NUMBER)
            .agentName(UPDATED_AGENT_NAME);
        AgentMasterDTO agentMasterDTO = agentMasterMapper.toDto(updatedAgentMaster);

        restAgentMasterMockMvc.perform(put("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentMasterDTO)))
            .andExpect(status().isOk());

        // Validate the AgentMaster in the database
        List<AgentMaster> agentMasterList = agentMasterRepository.findAll();
        assertThat(agentMasterList).hasSize(databaseSizeBeforeUpdate);
        AgentMaster testAgentMaster = agentMasterList.get(agentMasterList.size() - 1);
        assertThat(testAgentMaster.getAgentNumber()).isEqualTo(UPDATED_AGENT_NUMBER);
        assertThat(testAgentMaster.getAgentName()).isEqualTo(UPDATED_AGENT_NAME);
    }

    @Test
    public void updateNonExistingAgentMaster() throws Exception {
        int databaseSizeBeforeUpdate = agentMasterRepository.findAll().size();

        // Create the AgentMaster
        AgentMasterDTO agentMasterDTO = agentMasterMapper.toDto(agentMaster);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgentMasterMockMvc.perform(put("/api/agent-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the AgentMaster in the database
        List<AgentMaster> agentMasterList = agentMasterRepository.findAll();
        assertThat(agentMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAgentMaster() throws Exception {
        // Initialize the database
        agentMasterRepository.save(agentMaster);
        int databaseSizeBeforeDelete = agentMasterRepository.findAll().size();

        // Get the agentMaster
        restAgentMasterMockMvc.perform(delete("/api/agent-masters/{id}", agentMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AgentMaster> agentMasterList = agentMasterRepository.findAll();
        assertThat(agentMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentMaster.class);
        AgentMaster agentMaster1 = new AgentMaster();
        agentMaster1.setId(UUID.randomUUID());
        AgentMaster agentMaster2 = new AgentMaster();
        agentMaster2.setId(agentMaster1.getId());
        assertThat(agentMaster1).isEqualTo(agentMaster2);
        agentMaster2.setId(UUID.randomUUID());
        assertThat(agentMaster1).isNotEqualTo(agentMaster2);
        agentMaster1.setId(null);
        assertThat(agentMaster1).isNotEqualTo(agentMaster2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentMasterDTO.class);
        AgentMasterDTO agentMasterDTO1 = new AgentMasterDTO();
        agentMasterDTO1.setId(UUID.randomUUID());
        AgentMasterDTO agentMasterDTO2 = new AgentMasterDTO();
        assertThat(agentMasterDTO1).isNotEqualTo(agentMasterDTO2);
        agentMasterDTO2.setId(agentMasterDTO1.getId());
        assertThat(agentMasterDTO1).isEqualTo(agentMasterDTO2);
        agentMasterDTO2.setId(UUID.randomUUID());
        assertThat(agentMasterDTO1).isNotEqualTo(agentMasterDTO2);
        agentMasterDTO1.setId(null);
        assertThat(agentMasterDTO1).isNotEqualTo(agentMasterDTO2);
    }
}
