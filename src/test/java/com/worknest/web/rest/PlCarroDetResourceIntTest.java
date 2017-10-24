package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlCarroDet;
import com.worknest.repository.PlCarroDetRepository;
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
 * Test class for the PlCarroDetResource REST controller.
 *
 * @see PlCarroDetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlCarroDetResourceIntTest {

    private static final Long DEFAULT_ID_LIQUIDACION = 1L;
    private static final Long UPDATED_ID_LIQUIDACION = 2L;

    private static final LocalDate DEFAULT_FECHA_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VIGENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_IMPORTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPORTE = new BigDecimal(2);

    private static final String DEFAULT_LLAVE = "AAAAAAAAAA";
    private static final String UPDATED_LLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_CONCEPTO = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENERO_US = false;
    private static final Boolean UPDATED_GENERO_US = true;

    @Autowired
    private PlCarroDetRepository plCarroDetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlCarroDetMockMvc;

    private PlCarroDet plCarroDet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlCarroDetResource plCarroDetResource = new PlCarroDetResource(plCarroDetRepository);
        this.restPlCarroDetMockMvc = MockMvcBuilders.standaloneSetup(plCarroDetResource)
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
    public static PlCarroDet createEntity(EntityManager em) {
        PlCarroDet plCarroDet = new PlCarroDet()
            .idLiquidacion(DEFAULT_ID_LIQUIDACION)
            .fechaVigencia(DEFAULT_FECHA_VIGENCIA)
            .importe(DEFAULT_IMPORTE)
            .llave(DEFAULT_LLAVE)
            .concepto(DEFAULT_CONCEPTO)
            .generoUs(DEFAULT_GENERO_US);
        return plCarroDet;
    }

    @Before
    public void initTest() {
        plCarroDet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlCarroDet() throws Exception {
        int databaseSizeBeforeCreate = plCarroDetRepository.findAll().size();

        // Create the PlCarroDet
        restPlCarroDetMockMvc.perform(post("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isCreated());

        // Validate the PlCarroDet in the database
        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeCreate + 1);
        PlCarroDet testPlCarroDet = plCarroDetList.get(plCarroDetList.size() - 1);
        assertThat(testPlCarroDet.getIdLiquidacion()).isEqualTo(DEFAULT_ID_LIQUIDACION);
        assertThat(testPlCarroDet.getFechaVigencia()).isEqualTo(DEFAULT_FECHA_VIGENCIA);
        assertThat(testPlCarroDet.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testPlCarroDet.getLlave()).isEqualTo(DEFAULT_LLAVE);
        assertThat(testPlCarroDet.getConcepto()).isEqualTo(DEFAULT_CONCEPTO);
        assertThat(testPlCarroDet.isGeneroUs()).isEqualTo(DEFAULT_GENERO_US);
    }

    @Test
    @Transactional
    public void createPlCarroDetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plCarroDetRepository.findAll().size();

        // Create the PlCarroDet with an existing ID
        plCarroDet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlCarroDetMockMvc.perform(post("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdLiquidacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetRepository.findAll().size();
        // set the field null
        plCarroDet.setIdLiquidacion(null);

        // Create the PlCarroDet, which fails.

        restPlCarroDetMockMvc.perform(post("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isBadRequest());

        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaVigenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetRepository.findAll().size();
        // set the field null
        plCarroDet.setFechaVigencia(null);

        // Create the PlCarroDet, which fails.

        restPlCarroDetMockMvc.perform(post("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isBadRequest());

        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImporteIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetRepository.findAll().size();
        // set the field null
        plCarroDet.setImporte(null);

        // Create the PlCarroDet, which fails.

        restPlCarroDetMockMvc.perform(post("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isBadRequest());

        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLlaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetRepository.findAll().size();
        // set the field null
        plCarroDet.setLlave(null);

        // Create the PlCarroDet, which fails.

        restPlCarroDetMockMvc.perform(post("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isBadRequest());

        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneroUsIsRequired() throws Exception {
        int databaseSizeBeforeTest = plCarroDetRepository.findAll().size();
        // set the field null
        plCarroDet.setGeneroUs(null);

        // Create the PlCarroDet, which fails.

        restPlCarroDetMockMvc.perform(post("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isBadRequest());

        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlCarroDets() throws Exception {
        // Initialize the database
        plCarroDetRepository.saveAndFlush(plCarroDet);

        // Get all the plCarroDetList
        restPlCarroDetMockMvc.perform(get("/api/pl-carro-dets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plCarroDet.getId().intValue())))
            .andExpect(jsonPath("$.[*].idLiquidacion").value(hasItem(DEFAULT_ID_LIQUIDACION.intValue())))
            .andExpect(jsonPath("$.[*].fechaVigencia").value(hasItem(DEFAULT_FECHA_VIGENCIA.toString())))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.intValue())))
            .andExpect(jsonPath("$.[*].llave").value(hasItem(DEFAULT_LLAVE.toString())))
            .andExpect(jsonPath("$.[*].concepto").value(hasItem(DEFAULT_CONCEPTO.toString())))
            .andExpect(jsonPath("$.[*].generoUs").value(hasItem(DEFAULT_GENERO_US.booleanValue())));
    }

    @Test
    @Transactional
    public void getPlCarroDet() throws Exception {
        // Initialize the database
        plCarroDetRepository.saveAndFlush(plCarroDet);

        // Get the plCarroDet
        restPlCarroDetMockMvc.perform(get("/api/pl-carro-dets/{id}", plCarroDet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plCarroDet.getId().intValue()))
            .andExpect(jsonPath("$.idLiquidacion").value(DEFAULT_ID_LIQUIDACION.intValue()))
            .andExpect(jsonPath("$.fechaVigencia").value(DEFAULT_FECHA_VIGENCIA.toString()))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.intValue()))
            .andExpect(jsonPath("$.llave").value(DEFAULT_LLAVE.toString()))
            .andExpect(jsonPath("$.concepto").value(DEFAULT_CONCEPTO.toString()))
            .andExpect(jsonPath("$.generoUs").value(DEFAULT_GENERO_US.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlCarroDet() throws Exception {
        // Get the plCarroDet
        restPlCarroDetMockMvc.perform(get("/api/pl-carro-dets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlCarroDet() throws Exception {
        // Initialize the database
        plCarroDetRepository.saveAndFlush(plCarroDet);
        int databaseSizeBeforeUpdate = plCarroDetRepository.findAll().size();

        // Update the plCarroDet
        PlCarroDet updatedPlCarroDet = plCarroDetRepository.findOne(plCarroDet.getId());
        updatedPlCarroDet
            .idLiquidacion(UPDATED_ID_LIQUIDACION)
            .fechaVigencia(UPDATED_FECHA_VIGENCIA)
            .importe(UPDATED_IMPORTE)
            .llave(UPDATED_LLAVE)
            .concepto(UPDATED_CONCEPTO)
            .generoUs(UPDATED_GENERO_US);

        restPlCarroDetMockMvc.perform(put("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlCarroDet)))
            .andExpect(status().isOk());

        // Validate the PlCarroDet in the database
        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeUpdate);
        PlCarroDet testPlCarroDet = plCarroDetList.get(plCarroDetList.size() - 1);
        assertThat(testPlCarroDet.getIdLiquidacion()).isEqualTo(UPDATED_ID_LIQUIDACION);
        assertThat(testPlCarroDet.getFechaVigencia()).isEqualTo(UPDATED_FECHA_VIGENCIA);
        assertThat(testPlCarroDet.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testPlCarroDet.getLlave()).isEqualTo(UPDATED_LLAVE);
        assertThat(testPlCarroDet.getConcepto()).isEqualTo(UPDATED_CONCEPTO);
        assertThat(testPlCarroDet.isGeneroUs()).isEqualTo(UPDATED_GENERO_US);
    }

    @Test
    @Transactional
    public void updateNonExistingPlCarroDet() throws Exception {
        int databaseSizeBeforeUpdate = plCarroDetRepository.findAll().size();

        // Create the PlCarroDet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlCarroDetMockMvc.perform(put("/api/pl-carro-dets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarroDet)))
            .andExpect(status().isCreated());

        // Validate the PlCarroDet in the database
        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlCarroDet() throws Exception {
        // Initialize the database
        plCarroDetRepository.saveAndFlush(plCarroDet);
        int databaseSizeBeforeDelete = plCarroDetRepository.findAll().size();

        // Get the plCarroDet
        restPlCarroDetMockMvc.perform(delete("/api/pl-carro-dets/{id}", plCarroDet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlCarroDet> plCarroDetList = plCarroDetRepository.findAll();
        assertThat(plCarroDetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlCarroDet.class);
        PlCarroDet plCarroDet1 = new PlCarroDet();
        plCarroDet1.setId(1L);
        PlCarroDet plCarroDet2 = new PlCarroDet();
        plCarroDet2.setId(plCarroDet1.getId());
        assertThat(plCarroDet1).isEqualTo(plCarroDet2);
        plCarroDet2.setId(2L);
        assertThat(plCarroDet1).isNotEqualTo(plCarroDet2);
        plCarroDet1.setId(null);
        assertThat(plCarroDet1).isNotEqualTo(plCarroDet2);
    }
}
