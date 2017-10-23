package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlRespuestaBanco;
import com.worknest.repository.PlRespuestaBancoRepository;
import com.worknest.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.worknest.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlRespuestaBancoResource REST controller.
 *
 * @see PlRespuestaBancoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlRespuestaBancoResourceIntTest {

    private static final Boolean DEFAULT_EXITOSO = false;
    private static final Boolean UPDATED_EXITOSO = true;

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PlRespuestaBancoRepository plRespuestaBancoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlRespuestaBancoMockMvc;

    private PlRespuestaBanco plRespuestaBanco;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlRespuestaBancoResource plRespuestaBancoResource = new PlRespuestaBancoResource(plRespuestaBancoRepository);
        this.restPlRespuestaBancoMockMvc = MockMvcBuilders.standaloneSetup(plRespuestaBancoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlRespuestaBanco createEntity(EntityManager em) {
        PlRespuestaBanco plRespuestaBanco = new PlRespuestaBanco()
            .exitoso(DEFAULT_EXITOSO)
            .fecha(DEFAULT_FECHA);
        return plRespuestaBanco;
    }

    @Before
    public void initTest() {
        plRespuestaBanco = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlRespuestaBanco() throws Exception {
        int databaseSizeBeforeCreate = plRespuestaBancoRepository.findAll().size();

        // Create the PlRespuestaBanco
        restPlRespuestaBancoMockMvc.perform(post("/api/pl-respuesta-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plRespuestaBanco)))
            .andExpect(status().isCreated());

        // Validate the PlRespuestaBanco in the database
        List<PlRespuestaBanco> plRespuestaBancoList = plRespuestaBancoRepository.findAll();
        assertThat(plRespuestaBancoList).hasSize(databaseSizeBeforeCreate + 1);
        PlRespuestaBanco testPlRespuestaBanco = plRespuestaBancoList.get(plRespuestaBancoList.size() - 1);
        assertThat(testPlRespuestaBanco.isExitoso()).isEqualTo(DEFAULT_EXITOSO);
        assertThat(testPlRespuestaBanco.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createPlRespuestaBancoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plRespuestaBancoRepository.findAll().size();

        // Create the PlRespuestaBanco with an existing ID
        plRespuestaBanco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlRespuestaBancoMockMvc.perform(post("/api/pl-respuesta-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plRespuestaBanco)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlRespuestaBanco> plRespuestaBancoList = plRespuestaBancoRepository.findAll();
        assertThat(plRespuestaBancoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkExitosoIsRequired() throws Exception {
        int databaseSizeBeforeTest = plRespuestaBancoRepository.findAll().size();
        // set the field null
        plRespuestaBanco.setExitoso(null);

        // Create the PlRespuestaBanco, which fails.

        restPlRespuestaBancoMockMvc.perform(post("/api/pl-respuesta-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plRespuestaBanco)))
            .andExpect(status().isBadRequest());

        List<PlRespuestaBanco> plRespuestaBancoList = plRespuestaBancoRepository.findAll();
        assertThat(plRespuestaBancoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = plRespuestaBancoRepository.findAll().size();
        // set the field null
        plRespuestaBanco.setFecha(null);

        // Create the PlRespuestaBanco, which fails.

        restPlRespuestaBancoMockMvc.perform(post("/api/pl-respuesta-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plRespuestaBanco)))
            .andExpect(status().isBadRequest());

        List<PlRespuestaBanco> plRespuestaBancoList = plRespuestaBancoRepository.findAll();
        assertThat(plRespuestaBancoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlRespuestaBancos() throws Exception {
        // Initialize the database
        plRespuestaBancoRepository.saveAndFlush(plRespuestaBanco);

        // Get all the plRespuestaBancoList
        restPlRespuestaBancoMockMvc.perform(get("/api/pl-respuesta-bancos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plRespuestaBanco.getId().intValue())))
            .andExpect(jsonPath("$.[*].exitoso").value(hasItem(DEFAULT_EXITOSO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))));
    }

    @Test
    @Transactional
    public void getPlRespuestaBanco() throws Exception {
        // Initialize the database
        plRespuestaBancoRepository.saveAndFlush(plRespuestaBanco);

        // Get the plRespuestaBanco
        restPlRespuestaBancoMockMvc.perform(get("/api/pl-respuesta-bancos/{id}", plRespuestaBanco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plRespuestaBanco.getId().intValue()))
            .andExpect(jsonPath("$.exitoso").value(DEFAULT_EXITOSO.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)));
    }

    @Test
    @Transactional
    public void getNonExistingPlRespuestaBanco() throws Exception {
        // Get the plRespuestaBanco
        restPlRespuestaBancoMockMvc.perform(get("/api/pl-respuesta-bancos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlRespuestaBanco() throws Exception {
        // Initialize the database
        plRespuestaBancoRepository.saveAndFlush(plRespuestaBanco);
        int databaseSizeBeforeUpdate = plRespuestaBancoRepository.findAll().size();

        // Update the plRespuestaBanco
        PlRespuestaBanco updatedPlRespuestaBanco = plRespuestaBancoRepository.findOne(plRespuestaBanco.getId());
        updatedPlRespuestaBanco
            .exitoso(UPDATED_EXITOSO)
            .fecha(UPDATED_FECHA);

        restPlRespuestaBancoMockMvc.perform(put("/api/pl-respuesta-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlRespuestaBanco)))
            .andExpect(status().isOk());

        // Validate the PlRespuestaBanco in the database
        List<PlRespuestaBanco> plRespuestaBancoList = plRespuestaBancoRepository.findAll();
        assertThat(plRespuestaBancoList).hasSize(databaseSizeBeforeUpdate);
        PlRespuestaBanco testPlRespuestaBanco = plRespuestaBancoList.get(plRespuestaBancoList.size() - 1);
        assertThat(testPlRespuestaBanco.isExitoso()).isEqualTo(UPDATED_EXITOSO);
        assertThat(testPlRespuestaBanco.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingPlRespuestaBanco() throws Exception {
        int databaseSizeBeforeUpdate = plRespuestaBancoRepository.findAll().size();

        // Create the PlRespuestaBanco

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlRespuestaBancoMockMvc.perform(put("/api/pl-respuesta-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plRespuestaBanco)))
            .andExpect(status().isCreated());

        // Validate the PlRespuestaBanco in the database
        List<PlRespuestaBanco> plRespuestaBancoList = plRespuestaBancoRepository.findAll();
        assertThat(plRespuestaBancoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlRespuestaBanco() throws Exception {
        // Initialize the database
        plRespuestaBancoRepository.saveAndFlush(plRespuestaBanco);
        int databaseSizeBeforeDelete = plRespuestaBancoRepository.findAll().size();

        // Get the plRespuestaBanco
        restPlRespuestaBancoMockMvc.perform(delete("/api/pl-respuesta-bancos/{id}", plRespuestaBanco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlRespuestaBanco> plRespuestaBancoList = plRespuestaBancoRepository.findAll();
        assertThat(plRespuestaBancoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlRespuestaBanco.class);
        PlRespuestaBanco plRespuestaBanco1 = new PlRespuestaBanco();
        plRespuestaBanco1.setId(1L);
        PlRespuestaBanco plRespuestaBanco2 = new PlRespuestaBanco();
        plRespuestaBanco2.setId(plRespuestaBanco1.getId());
        assertThat(plRespuestaBanco1).isEqualTo(plRespuestaBanco2);
        plRespuestaBanco2.setId(2L);
        assertThat(plRespuestaBanco1).isNotEqualTo(plRespuestaBanco2);
        plRespuestaBanco1.setId(null);
        assertThat(plRespuestaBanco1).isNotEqualTo(plRespuestaBanco2);
    }
}
