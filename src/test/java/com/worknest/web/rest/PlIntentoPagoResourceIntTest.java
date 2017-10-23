package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlIntentoPago;
import com.worknest.repository.PlIntentoPagoRepository;
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

import com.worknest.domain.enumeration.StatusIntentoPago;
/**
 * Test class for the PlIntentoPagoResource REST controller.
 *
 * @see PlIntentoPagoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlIntentoPagoResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ENVIADO = false;
    private static final Boolean UPDATED_ENVIADO = true;

    private static final StatusIntentoPago DEFAULT_STATUS = StatusIntentoPago.DISPERSA;
    private static final StatusIntentoPago UPDATED_STATUS = StatusIntentoPago.ACEPTADA;

    private static final String DEFAULT_AUTH = "AAAAAAAAAA";
    private static final String UPDATED_AUTH = "BBBBBBBBBB";

    @Autowired
    private PlIntentoPagoRepository plIntentoPagoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlIntentoPagoMockMvc;

    private PlIntentoPago plIntentoPago;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlIntentoPagoResource plIntentoPagoResource = new PlIntentoPagoResource(plIntentoPagoRepository);
        this.restPlIntentoPagoMockMvc = MockMvcBuilders.standaloneSetup(plIntentoPagoResource)
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
    public static PlIntentoPago createEntity(EntityManager em) {
        PlIntentoPago plIntentoPago = new PlIntentoPago()
            .fecha(DEFAULT_FECHA)
            .enviado(DEFAULT_ENVIADO)
            .status(DEFAULT_STATUS)
            .auth(DEFAULT_AUTH);
        return plIntentoPago;
    }

    @Before
    public void initTest() {
        plIntentoPago = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlIntentoPago() throws Exception {
        int databaseSizeBeforeCreate = plIntentoPagoRepository.findAll().size();

        // Create the PlIntentoPago
        restPlIntentoPagoMockMvc.perform(post("/api/pl-intento-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plIntentoPago)))
            .andExpect(status().isCreated());

        // Validate the PlIntentoPago in the database
        List<PlIntentoPago> plIntentoPagoList = plIntentoPagoRepository.findAll();
        assertThat(plIntentoPagoList).hasSize(databaseSizeBeforeCreate + 1);
        PlIntentoPago testPlIntentoPago = plIntentoPagoList.get(plIntentoPagoList.size() - 1);
        assertThat(testPlIntentoPago.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPlIntentoPago.isEnviado()).isEqualTo(DEFAULT_ENVIADO);
        assertThat(testPlIntentoPago.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPlIntentoPago.getAuth()).isEqualTo(DEFAULT_AUTH);
    }

    @Test
    @Transactional
    public void createPlIntentoPagoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plIntentoPagoRepository.findAll().size();

        // Create the PlIntentoPago with an existing ID
        plIntentoPago.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlIntentoPagoMockMvc.perform(post("/api/pl-intento-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plIntentoPago)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlIntentoPago> plIntentoPagoList = plIntentoPagoRepository.findAll();
        assertThat(plIntentoPagoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = plIntentoPagoRepository.findAll().size();
        // set the field null
        plIntentoPago.setFecha(null);

        // Create the PlIntentoPago, which fails.

        restPlIntentoPagoMockMvc.perform(post("/api/pl-intento-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plIntentoPago)))
            .andExpect(status().isBadRequest());

        List<PlIntentoPago> plIntentoPagoList = plIntentoPagoRepository.findAll();
        assertThat(plIntentoPagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlIntentoPagos() throws Exception {
        // Initialize the database
        plIntentoPagoRepository.saveAndFlush(plIntentoPago);

        // Get all the plIntentoPagoList
        restPlIntentoPagoMockMvc.perform(get("/api/pl-intento-pagos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plIntentoPago.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].auth").value(hasItem(DEFAULT_AUTH.toString())));
    }

    @Test
    @Transactional
    public void getPlIntentoPago() throws Exception {
        // Initialize the database
        plIntentoPagoRepository.saveAndFlush(plIntentoPago);

        // Get the plIntentoPago
        restPlIntentoPagoMockMvc.perform(get("/api/pl-intento-pagos/{id}", plIntentoPago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plIntentoPago.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.auth").value(DEFAULT_AUTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlIntentoPago() throws Exception {
        // Get the plIntentoPago
        restPlIntentoPagoMockMvc.perform(get("/api/pl-intento-pagos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlIntentoPago() throws Exception {
        // Initialize the database
        plIntentoPagoRepository.saveAndFlush(plIntentoPago);
        int databaseSizeBeforeUpdate = plIntentoPagoRepository.findAll().size();

        // Update the plIntentoPago
        PlIntentoPago updatedPlIntentoPago = plIntentoPagoRepository.findOne(plIntentoPago.getId());
        updatedPlIntentoPago
            .fecha(UPDATED_FECHA)
            .enviado(UPDATED_ENVIADO)
            .status(UPDATED_STATUS)
            .auth(UPDATED_AUTH);

        restPlIntentoPagoMockMvc.perform(put("/api/pl-intento-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlIntentoPago)))
            .andExpect(status().isOk());

        // Validate the PlIntentoPago in the database
        List<PlIntentoPago> plIntentoPagoList = plIntentoPagoRepository.findAll();
        assertThat(plIntentoPagoList).hasSize(databaseSizeBeforeUpdate);
        PlIntentoPago testPlIntentoPago = plIntentoPagoList.get(plIntentoPagoList.size() - 1);
        assertThat(testPlIntentoPago.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPlIntentoPago.isEnviado()).isEqualTo(UPDATED_ENVIADO);
        assertThat(testPlIntentoPago.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPlIntentoPago.getAuth()).isEqualTo(UPDATED_AUTH);
    }

    @Test
    @Transactional
    public void updateNonExistingPlIntentoPago() throws Exception {
        int databaseSizeBeforeUpdate = plIntentoPagoRepository.findAll().size();

        // Create the PlIntentoPago

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlIntentoPagoMockMvc.perform(put("/api/pl-intento-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plIntentoPago)))
            .andExpect(status().isCreated());

        // Validate the PlIntentoPago in the database
        List<PlIntentoPago> plIntentoPagoList = plIntentoPagoRepository.findAll();
        assertThat(plIntentoPagoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlIntentoPago() throws Exception {
        // Initialize the database
        plIntentoPagoRepository.saveAndFlush(plIntentoPago);
        int databaseSizeBeforeDelete = plIntentoPagoRepository.findAll().size();

        // Get the plIntentoPago
        restPlIntentoPagoMockMvc.perform(delete("/api/pl-intento-pagos/{id}", plIntentoPago.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlIntentoPago> plIntentoPagoList = plIntentoPagoRepository.findAll();
        assertThat(plIntentoPagoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlIntentoPago.class);
        PlIntentoPago plIntentoPago1 = new PlIntentoPago();
        plIntentoPago1.setId(1L);
        PlIntentoPago plIntentoPago2 = new PlIntentoPago();
        plIntentoPago2.setId(plIntentoPago1.getId());
        assertThat(plIntentoPago1).isEqualTo(plIntentoPago2);
        plIntentoPago2.setId(2L);
        assertThat(plIntentoPago1).isNotEqualTo(plIntentoPago2);
        plIntentoPago1.setId(null);
        assertThat(plIntentoPago1).isNotEqualTo(plIntentoPago2);
    }
}
