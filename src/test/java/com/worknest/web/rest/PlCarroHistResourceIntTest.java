package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlCarroHist;
import com.worknest.repository.PlCarroHistRepository;
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
 * Test class for the PlCarroHistResource REST controller.
 *
 * @see PlCarroHistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlCarroHistResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA_ENVIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ENVIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIA = "BBBBBBBBBB";

    @Autowired
    private PlCarroHistRepository plCarroHistRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlCarroHistMockMvc;

    private PlCarroHist plCarroHist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlCarroHistResource plCarroHistResource = new PlCarroHistResource(plCarroHistRepository);
        this.restPlCarroHistMockMvc = MockMvcBuilders.standaloneSetup(plCarroHistResource)
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
    public static PlCarroHist createEntity(EntityManager em) {
        PlCarroHist plCarroHist = new PlCarroHist()
            .fechaEnvio(DEFAULT_FECHA_ENVIO)
            .referencia(DEFAULT_REFERENCIA);
        return plCarroHist;
    }

    @Before
    public void initTest() {
        plCarroHist = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlCarroHist() throws Exception {
        int databaseSizeBeforeCreate = plCarroHistRepository.findAll().size();

        // Create the PlCarroHist
        restPlCarroHistMockMvc.perform(post("/api/pl-carro-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroHist)))
            .andExpect(status().isCreated());

        // Validate the PlCarroHist in the database
        List<PlCarroHist> plCarroHistList = plCarroHistRepository.findAll();
        assertThat(plCarroHistList).hasSize(databaseSizeBeforeCreate + 1);
        PlCarroHist testPlCarroHist = plCarroHistList.get(plCarroHistList.size() - 1);
        assertThat(testPlCarroHist.getFechaEnvio()).isEqualTo(DEFAULT_FECHA_ENVIO);
        assertThat(testPlCarroHist.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
    }

    @Test
    @Transactional
    public void createPlCarroHistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plCarroHistRepository.findAll().size();

        // Create the PlCarroHist with an existing ID
        plCarroHist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlCarroHistMockMvc.perform(post("/api/pl-carro-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroHist)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlCarroHist> plCarroHistList = plCarroHistRepository.findAll();
        assertThat(plCarroHistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaEnvioIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroHistRepository.findAll().size();
        // set the field null
        plCarroHist.setFechaEnvio(null);

        // Create the PlCarroHist, which fails.

        restPlCarroHistMockMvc.perform(post("/api/pl-carro-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroHist)))
            .andExpect(status().isBadRequest());

        List<PlCarroHist> plCarroHistList = plCarroHistRepository.findAll();
        assertThat(plCarroHistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroHistRepository.findAll().size();
        // set the field null
        plCarroHist.setReferencia(null);

        // Create the PlCarroHist, which fails.

        restPlCarroHistMockMvc.perform(post("/api/pl-carro-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroHist)))
            .andExpect(status().isBadRequest());

        List<PlCarroHist> plCarroHistList = plCarroHistRepository.findAll();
        assertThat(plCarroHistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlCarroHists() throws Exception {
        // Initialize the database
        plCarroHistRepository.saveAndFlush(plCarroHist);

        // Get all the plCarroHistList
        restPlCarroHistMockMvc.perform(get("/api/pl-carro-hists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plCarroHist.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaEnvio").value(hasItem(sameInstant(DEFAULT_FECHA_ENVIO))))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA.toString())));
    }

    @Test
    @Transactional
    public void getPlCarroHist() throws Exception {
        // Initialize the database
        plCarroHistRepository.saveAndFlush(plCarroHist);

        // Get the plCarroHist
        restPlCarroHistMockMvc.perform(get("/api/pl-carro-hists/{id}", plCarroHist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plCarroHist.getId().intValue()))
            .andExpect(jsonPath("$.fechaEnvio").value(sameInstant(DEFAULT_FECHA_ENVIO)))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlCarroHist() throws Exception {
        // Get the plCarroHist
        restPlCarroHistMockMvc.perform(get("/api/pl-carro-hists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlCarroHist() throws Exception {
        // Initialize the database
        plCarroHistRepository.saveAndFlush(plCarroHist);
        int databaseSizeBeforeUpdate = plCarroHistRepository.findAll().size();

        // Update the plCarroHist
        PlCarroHist updatedPlCarroHist = plCarroHistRepository.findOne(plCarroHist.getId());
        updatedPlCarroHist
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .referencia(UPDATED_REFERENCIA);

        restPlCarroHistMockMvc.perform(put("/api/pl-carro-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlCarroHist)))
            .andExpect(status().isOk());

        // Validate the PlCarroHist in the database
        List<PlCarroHist> plCarroHistList = plCarroHistRepository.findAll();
        assertThat(plCarroHistList).hasSize(databaseSizeBeforeUpdate);
        PlCarroHist testPlCarroHist = plCarroHistList.get(plCarroHistList.size() - 1);
        assertThat(testPlCarroHist.getFechaEnvio()).isEqualTo(UPDATED_FECHA_ENVIO);
        assertThat(testPlCarroHist.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingPlCarroHist() throws Exception {
        int databaseSizeBeforeUpdate = plCarroHistRepository.findAll().size();

        // Create the PlCarroHist

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlCarroHistMockMvc.perform(put("/api/pl-carro-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroHist)))
            .andExpect(status().isCreated());

        // Validate the PlCarroHist in the database
        List<PlCarroHist> plCarroHistList = plCarroHistRepository.findAll();
        assertThat(plCarroHistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlCarroHist() throws Exception {
        // Initialize the database
        plCarroHistRepository.saveAndFlush(plCarroHist);
        int databaseSizeBeforeDelete = plCarroHistRepository.findAll().size();

        // Get the plCarroHist
        restPlCarroHistMockMvc.perform(delete("/api/pl-carro-hists/{id}", plCarroHist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlCarroHist> plCarroHistList = plCarroHistRepository.findAll();
        assertThat(plCarroHistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlCarroHist.class);
        PlCarroHist plCarroHist1 = new PlCarroHist();
        plCarroHist1.setId(1L);
        PlCarroHist plCarroHist2 = new PlCarroHist();
        plCarroHist2.setId(plCarroHist1.getId());
        assertThat(plCarroHist1).isEqualTo(plCarroHist2);
        plCarroHist2.setId(2L);
        assertThat(plCarroHist1).isNotEqualTo(plCarroHist2);
        plCarroHist1.setId(null);
        assertThat(plCarroHist1).isNotEqualTo(plCarroHist2);
    }
}
