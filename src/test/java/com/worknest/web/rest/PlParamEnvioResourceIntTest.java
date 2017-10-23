package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlParamEnvio;
import com.worknest.repository.PlParamEnvioRepository;
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

/**
 * Test class for the PlParamEnvioResource REST controller.
 *
 * @see PlParamEnvioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlParamEnvioResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private PlParamEnvioRepository plParamEnvioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlParamEnvioMockMvc;

    private PlParamEnvio plParamEnvio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlParamEnvioResource plParamEnvioResource = new PlParamEnvioResource(plParamEnvioRepository);
        this.restPlParamEnvioMockMvc = MockMvcBuilders.standaloneSetup(plParamEnvioResource)
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
    public static PlParamEnvio createEntity(EntityManager em) {
        PlParamEnvio plParamEnvio = new PlParamEnvio()
            .valor(DEFAULT_VALOR);
        return plParamEnvio;
    }

    @Before
    public void initTest() {
        plParamEnvio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlParamEnvio() throws Exception {
        int databaseSizeBeforeCreate = plParamEnvioRepository.findAll().size();

        // Create the PlParamEnvio
        restPlParamEnvioMockMvc.perform(post("/api/pl-param-envios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamEnvio)))
            .andExpect(status().isCreated());

        // Validate the PlParamEnvio in the database
        List<PlParamEnvio> plParamEnvioList = plParamEnvioRepository.findAll();
        assertThat(plParamEnvioList).hasSize(databaseSizeBeforeCreate + 1);
        PlParamEnvio testPlParamEnvio = plParamEnvioList.get(plParamEnvioList.size() - 1);
        assertThat(testPlParamEnvio.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createPlParamEnvioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plParamEnvioRepository.findAll().size();

        // Create the PlParamEnvio with an existing ID
        plParamEnvio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlParamEnvioMockMvc.perform(post("/api/pl-param-envios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamEnvio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlParamEnvio> plParamEnvioList = plParamEnvioRepository.findAll();
        assertThat(plParamEnvioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = plParamEnvioRepository.findAll().size();
        // set the field null
        plParamEnvio.setValor(null);

        // Create the PlParamEnvio, which fails.

        restPlParamEnvioMockMvc.perform(post("/api/pl-param-envios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamEnvio)))
            .andExpect(status().isBadRequest());

        List<PlParamEnvio> plParamEnvioList = plParamEnvioRepository.findAll();
        assertThat(plParamEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlParamEnvios() throws Exception {
        // Initialize the database
        plParamEnvioRepository.saveAndFlush(plParamEnvio);

        // Get all the plParamEnvioList
        restPlParamEnvioMockMvc.perform(get("/api/pl-param-envios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plParamEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }

    @Test
    @Transactional
    public void getPlParamEnvio() throws Exception {
        // Initialize the database
        plParamEnvioRepository.saveAndFlush(plParamEnvio);

        // Get the plParamEnvio
        restPlParamEnvioMockMvc.perform(get("/api/pl-param-envios/{id}", plParamEnvio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plParamEnvio.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlParamEnvio() throws Exception {
        // Get the plParamEnvio
        restPlParamEnvioMockMvc.perform(get("/api/pl-param-envios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlParamEnvio() throws Exception {
        // Initialize the database
        plParamEnvioRepository.saveAndFlush(plParamEnvio);
        int databaseSizeBeforeUpdate = plParamEnvioRepository.findAll().size();

        // Update the plParamEnvio
        PlParamEnvio updatedPlParamEnvio = plParamEnvioRepository.findOne(plParamEnvio.getId());
        updatedPlParamEnvio
            .valor(UPDATED_VALOR);

        restPlParamEnvioMockMvc.perform(put("/api/pl-param-envios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlParamEnvio)))
            .andExpect(status().isOk());

        // Validate the PlParamEnvio in the database
        List<PlParamEnvio> plParamEnvioList = plParamEnvioRepository.findAll();
        assertThat(plParamEnvioList).hasSize(databaseSizeBeforeUpdate);
        PlParamEnvio testPlParamEnvio = plParamEnvioList.get(plParamEnvioList.size() - 1);
        assertThat(testPlParamEnvio.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPlParamEnvio() throws Exception {
        int databaseSizeBeforeUpdate = plParamEnvioRepository.findAll().size();

        // Create the PlParamEnvio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlParamEnvioMockMvc.perform(put("/api/pl-param-envios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamEnvio)))
            .andExpect(status().isCreated());

        // Validate the PlParamEnvio in the database
        List<PlParamEnvio> plParamEnvioList = plParamEnvioRepository.findAll();
        assertThat(plParamEnvioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlParamEnvio() throws Exception {
        // Initialize the database
        plParamEnvioRepository.saveAndFlush(plParamEnvio);
        int databaseSizeBeforeDelete = plParamEnvioRepository.findAll().size();

        // Get the plParamEnvio
        restPlParamEnvioMockMvc.perform(delete("/api/pl-param-envios/{id}", plParamEnvio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlParamEnvio> plParamEnvioList = plParamEnvioRepository.findAll();
        assertThat(plParamEnvioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlParamEnvio.class);
        PlParamEnvio plParamEnvio1 = new PlParamEnvio();
        plParamEnvio1.setId(1L);
        PlParamEnvio plParamEnvio2 = new PlParamEnvio();
        plParamEnvio2.setId(plParamEnvio1.getId());
        assertThat(plParamEnvio1).isEqualTo(plParamEnvio2);
        plParamEnvio2.setId(2L);
        assertThat(plParamEnvio1).isNotEqualTo(plParamEnvio2);
        plParamEnvio1.setId(null);
        assertThat(plParamEnvio1).isNotEqualTo(plParamEnvio2);
    }
}
