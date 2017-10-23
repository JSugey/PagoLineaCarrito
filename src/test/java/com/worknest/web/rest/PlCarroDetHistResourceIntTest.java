package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlCarroDetHist;
import com.worknest.repository.PlCarroDetHistRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlCarroDetHistResource REST controller.
 *
 * @see PlCarroDetHistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlCarroDetHistResourceIntTest {

    private static final String DEFAULT_CONCEPTO = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VIGENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_IMPORTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPORTE = new BigDecimal(2);

    private static final String DEFAULT_LLAVE = "AAAAAAAAAA";
    private static final String UPDATED_LLAVE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENERO_US = false;
    private static final Boolean UPDATED_GENERO_US = true;

    @Autowired
    private PlCarroDetHistRepository plCarroDetHistRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlCarroDetHistMockMvc;

    private PlCarroDetHist plCarroDetHist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlCarroDetHistResource plCarroDetHistResource = new PlCarroDetHistResource(plCarroDetHistRepository);
        this.restPlCarroDetHistMockMvc = MockMvcBuilders.standaloneSetup(plCarroDetHistResource)
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
    public static PlCarroDetHist createEntity(EntityManager em) {
        PlCarroDetHist plCarroDetHist = new PlCarroDetHist()
            .concepto(DEFAULT_CONCEPTO)
            .fechaVigencia(DEFAULT_FECHA_VIGENCIA)
            .importe(DEFAULT_IMPORTE)
            .llave(DEFAULT_LLAVE)
            .generoUs(DEFAULT_GENERO_US);
        return plCarroDetHist;
    }

    @Before
    public void initTest() {
        plCarroDetHist = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlCarroDetHist() throws Exception {
        int databaseSizeBeforeCreate = plCarroDetHistRepository.findAll().size();

        // Create the PlCarroDetHist
        restPlCarroDetHistMockMvc.perform(post("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDetHist)))
            .andExpect(status().isCreated());

        // Validate the PlCarroDetHist in the database
        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeCreate + 1);
        PlCarroDetHist testPlCarroDetHist = plCarroDetHistList.get(plCarroDetHistList.size() - 1);
        assertThat(testPlCarroDetHist.getConcepto()).isEqualTo(DEFAULT_CONCEPTO);
        assertThat(testPlCarroDetHist.getFechaVigencia()).isEqualTo(DEFAULT_FECHA_VIGENCIA);
        assertThat(testPlCarroDetHist.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testPlCarroDetHist.getLlave()).isEqualTo(DEFAULT_LLAVE);
        assertThat(testPlCarroDetHist.isGeneroUs()).isEqualTo(DEFAULT_GENERO_US);
    }

    @Test
    @Transactional
    public void createPlCarroDetHistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plCarroDetHistRepository.findAll().size();

        // Create the PlCarroDetHist with an existing ID
        plCarroDetHist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlCarroDetHistMockMvc.perform(post("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDetHist)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaVigenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetHistRepository.findAll().size();
        // set the field null
        plCarroDetHist.setFechaVigencia(null);

        // Create the PlCarroDetHist, which fails.

        restPlCarroDetHistMockMvc.perform(post("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDetHist)))
            .andExpect(status().isBadRequest());

        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImporteIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetHistRepository.findAll().size();
        // set the field null
        plCarroDetHist.setImporte(null);

        // Create the PlCarroDetHist, which fails.

        restPlCarroDetHistMockMvc.perform(post("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDetHist)))
            .andExpect(status().isBadRequest());

        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLlaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetHistRepository.findAll().size();
        // set the field null
        plCarroDetHist.setLlave(null);

        // Create the PlCarroDetHist, which fails.

        restPlCarroDetHistMockMvc.perform(post("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDetHist)))
            .andExpect(status().isBadRequest());

        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneroUsIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetHistRepository.findAll().size();
        // set the field null
        plCarroDetHist.setGeneroUs(null);

        // Create the PlCarroDetHist, which fails.

        restPlCarroDetHistMockMvc.perform(post("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDetHist)))
            .andExpect(status().isBadRequest());

        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlCarroDetHists() throws Exception {
        // Initialize the database
        plCarroDetHistRepository.saveAndFlush(plCarroDetHist);

        // Get all the plCarroDetHistList
        restPlCarroDetHistMockMvc.perform(get("/api/pl-carro-det-hists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plCarroDetHist.getId().intValue())))
            .andExpect(jsonPath("$.[*].concepto").value(hasItem(DEFAULT_CONCEPTO.toString())))
            .andExpect(jsonPath("$.[*].fechaVigencia").value(hasItem(DEFAULT_FECHA_VIGENCIA.toString())))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.intValue())))
            .andExpect(jsonPath("$.[*].llave").value(hasItem(DEFAULT_LLAVE.toString())))
            .andExpect(jsonPath("$.[*].generoUs").value(hasItem(DEFAULT_GENERO_US.booleanValue())));
    }

    @Test
    @Transactional
    public void getPlCarroDetHist() throws Exception {
        // Initialize the database
        plCarroDetHistRepository.saveAndFlush(plCarroDetHist);

        // Get the plCarroDetHist
        restPlCarroDetHistMockMvc.perform(get("/api/pl-carro-det-hists/{id}", plCarroDetHist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plCarroDetHist.getId().intValue()))
            .andExpect(jsonPath("$.concepto").value(DEFAULT_CONCEPTO.toString()))
            .andExpect(jsonPath("$.fechaVigencia").value(DEFAULT_FECHA_VIGENCIA.toString()))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.intValue()))
            .andExpect(jsonPath("$.llave").value(DEFAULT_LLAVE.toString()))
            .andExpect(jsonPath("$.generoUs").value(DEFAULT_GENERO_US.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlCarroDetHist() throws Exception {
        // Get the plCarroDetHist
        restPlCarroDetHistMockMvc.perform(get("/api/pl-carro-det-hists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlCarroDetHist() throws Exception {
        // Initialize the database
        plCarroDetHistRepository.saveAndFlush(plCarroDetHist);
        int databaseSizeBeforeUpdate = plCarroDetHistRepository.findAll().size();

        // Update the plCarroDetHist
        PlCarroDetHist updatedPlCarroDetHist = plCarroDetHistRepository.findOne(plCarroDetHist.getId());
        updatedPlCarroDetHist
            .concepto(UPDATED_CONCEPTO)
            .fechaVigencia(UPDATED_FECHA_VIGENCIA)
            .importe(UPDATED_IMPORTE)
            .llave(UPDATED_LLAVE)
            .generoUs(UPDATED_GENERO_US);

        restPlCarroDetHistMockMvc.perform(put("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlCarroDetHist)))
            .andExpect(status().isOk());

        // Validate the PlCarroDetHist in the database
        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeUpdate);
        PlCarroDetHist testPlCarroDetHist = plCarroDetHistList.get(plCarroDetHistList.size() - 1);
        assertThat(testPlCarroDetHist.getConcepto()).isEqualTo(UPDATED_CONCEPTO);
        assertThat(testPlCarroDetHist.getFechaVigencia()).isEqualTo(UPDATED_FECHA_VIGENCIA);
        assertThat(testPlCarroDetHist.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testPlCarroDetHist.getLlave()).isEqualTo(UPDATED_LLAVE);
        assertThat(testPlCarroDetHist.isGeneroUs()).isEqualTo(UPDATED_GENERO_US);
    }

    @Test
    @Transactional
    public void updateNonExistingPlCarroDetHist() throws Exception {
        int databaseSizeBeforeUpdate = plCarroDetHistRepository.findAll().size();

        // Create the PlCarroDetHist

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlCarroDetHistMockMvc.perform(put("/api/pl-carro-det-hists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDetHist)))
            .andExpect(status().isCreated());

        // Validate the PlCarroDetHist in the database
        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlCarroDetHist() throws Exception {
        // Initialize the database
        plCarroDetHistRepository.saveAndFlush(plCarroDetHist);
        int databaseSizeBeforeDelete = plCarroDetHistRepository.findAll().size();

        // Get the plCarroDetHist
        restPlCarroDetHistMockMvc.perform(delete("/api/pl-carro-det-hists/{id}", plCarroDetHist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlCarroDetHist> plCarroDetHistList = plCarroDetHistRepository.findAll();
        assertThat(plCarroDetHistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlCarroDetHist.class);
        PlCarroDetHist plCarroDetHist1 = new PlCarroDetHist();
        plCarroDetHist1.setId(1L);
        PlCarroDetHist plCarroDetHist2 = new PlCarroDetHist();
        plCarroDetHist2.setId(plCarroDetHist1.getId());
        assertThat(plCarroDetHist1).isEqualTo(plCarroDetHist2);
        plCarroDetHist2.setId(2L);
        assertThat(plCarroDetHist1).isNotEqualTo(plCarroDetHist2);
        plCarroDetHist1.setId(null);
        assertThat(plCarroDetHist1).isNotEqualTo(plCarroDetHist2);
    }
}
