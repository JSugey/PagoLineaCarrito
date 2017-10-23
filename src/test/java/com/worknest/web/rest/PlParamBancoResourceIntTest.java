package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlParamBanco;
import com.worknest.repository.PlParamBancoRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.worknest.domain.enumeration.TipoParamBanco;
/**
 * Test class for the PlParamBancoResource REST controller.
 *
 * @see PlParamBancoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlParamBancoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final TipoParamBanco DEFAULT_TIPO = TipoParamBanco.IN;
    private static final TipoParamBanco UPDATED_TIPO = TipoParamBanco.OUT;

    private static final String DEFAULT_EN_USO = "AAAAAAAAAA";
    private static final String UPDATED_EN_USO = "BBBBBBBBBB";

    @Autowired
    private PlParamBancoRepository plParamBancoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlParamBancoMockMvc;

    private PlParamBanco plParamBanco;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlParamBancoResource plParamBancoResource = new PlParamBancoResource(plParamBancoRepository);
        this.restPlParamBancoMockMvc = MockMvcBuilders.standaloneSetup(plParamBancoResource)
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
    public static PlParamBanco createEntity(EntityManager em) {
        PlParamBanco plParamBanco = new PlParamBanco()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .enUso(DEFAULT_EN_USO);
        return plParamBanco;
    }

    @Before
    public void initTest() {
        plParamBanco = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlParamBanco() throws Exception {
        int databaseSizeBeforeCreate = plParamBancoRepository.findAll().size();

        // Create the PlParamBanco
        restPlParamBancoMockMvc.perform(post("/api/pl-param-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamBanco)))
            .andExpect(status().isCreated());

        // Validate the PlParamBanco in the database
        List<PlParamBanco> plParamBancoList = plParamBancoRepository.findAll();
        assertThat(plParamBancoList).hasSize(databaseSizeBeforeCreate + 1);
        PlParamBanco testPlParamBanco = plParamBancoList.get(plParamBancoList.size() - 1);
        assertThat(testPlParamBanco.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPlParamBanco.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPlParamBanco.getEnUso()).isEqualTo(DEFAULT_EN_USO);
    }

    @Test
    @Transactional
    public void createPlParamBancoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plParamBancoRepository.findAll().size();

        // Create the PlParamBanco with an existing ID
        plParamBanco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlParamBancoMockMvc.perform(post("/api/pl-param-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamBanco)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlParamBanco> plParamBancoList = plParamBancoRepository.findAll();
        assertThat(plParamBancoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = plParamBancoRepository.findAll().size();
        // set the field null
        plParamBanco.setNombre(null);

        // Create the PlParamBanco, which fails.

        restPlParamBancoMockMvc.perform(post("/api/pl-param-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamBanco)))
            .andExpect(status().isBadRequest());

        List<PlParamBanco> plParamBancoList = plParamBancoRepository.findAll();
        assertThat(plParamBancoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = plParamBancoRepository.findAll().size();
        // set the field null
        plParamBanco.setTipo(null);

        // Create the PlParamBanco, which fails.

        restPlParamBancoMockMvc.perform(post("/api/pl-param-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamBanco)))
            .andExpect(status().isBadRequest());

        List<PlParamBanco> plParamBancoList = plParamBancoRepository.findAll();
        assertThat(plParamBancoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlParamBancos() throws Exception {
        // Initialize the database
        plParamBancoRepository.saveAndFlush(plParamBanco);

        // Get all the plParamBancoList
        restPlParamBancoMockMvc.perform(get("/api/pl-param-bancos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plParamBanco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].enUso").value(hasItem(DEFAULT_EN_USO.toString())));
    }

    @Test
    @Transactional
    public void getPlParamBanco() throws Exception {
        // Initialize the database
        plParamBancoRepository.saveAndFlush(plParamBanco);

        // Get the plParamBanco
        restPlParamBancoMockMvc.perform(get("/api/pl-param-bancos/{id}", plParamBanco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plParamBanco.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.enUso").value(DEFAULT_EN_USO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlParamBanco() throws Exception {
        // Get the plParamBanco
        restPlParamBancoMockMvc.perform(get("/api/pl-param-bancos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlParamBanco() throws Exception {
        // Initialize the database
        plParamBancoRepository.saveAndFlush(plParamBanco);
        int databaseSizeBeforeUpdate = plParamBancoRepository.findAll().size();

        // Update the plParamBanco
        PlParamBanco updatedPlParamBanco = plParamBancoRepository.findOne(plParamBanco.getId());
        updatedPlParamBanco
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .enUso(UPDATED_EN_USO);

        restPlParamBancoMockMvc.perform(put("/api/pl-param-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlParamBanco)))
            .andExpect(status().isOk());

        // Validate the PlParamBanco in the database
        List<PlParamBanco> plParamBancoList = plParamBancoRepository.findAll();
        assertThat(plParamBancoList).hasSize(databaseSizeBeforeUpdate);
        PlParamBanco testPlParamBanco = plParamBancoList.get(plParamBancoList.size() - 1);
        assertThat(testPlParamBanco.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPlParamBanco.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPlParamBanco.getEnUso()).isEqualTo(UPDATED_EN_USO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlParamBanco() throws Exception {
        int databaseSizeBeforeUpdate = plParamBancoRepository.findAll().size();

        // Create the PlParamBanco

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlParamBancoMockMvc.perform(put("/api/pl-param-bancos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamBanco)))
            .andExpect(status().isCreated());

        // Validate the PlParamBanco in the database
        List<PlParamBanco> plParamBancoList = plParamBancoRepository.findAll();
        assertThat(plParamBancoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlParamBanco() throws Exception {
        // Initialize the database
        plParamBancoRepository.saveAndFlush(plParamBanco);
        int databaseSizeBeforeDelete = plParamBancoRepository.findAll().size();

        // Get the plParamBanco
        restPlParamBancoMockMvc.perform(delete("/api/pl-param-bancos/{id}", plParamBanco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlParamBanco> plParamBancoList = plParamBancoRepository.findAll();
        assertThat(plParamBancoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlParamBanco.class);
        PlParamBanco plParamBanco1 = new PlParamBanco();
        plParamBanco1.setId(1L);
        PlParamBanco plParamBanco2 = new PlParamBanco();
        plParamBanco2.setId(plParamBanco1.getId());
        assertThat(plParamBanco1).isEqualTo(plParamBanco2);
        plParamBanco2.setId(2L);
        assertThat(plParamBanco1).isNotEqualTo(plParamBanco2);
        plParamBanco1.setId(null);
        assertThat(plParamBanco1).isNotEqualTo(plParamBanco2);
    }
}
