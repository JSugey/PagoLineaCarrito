package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlParamRespuesta;
import com.worknest.repository.PlParamRespuestaRepository;
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
 * Test class for the PlParamRespuestaResource REST controller.
 *
 * @see PlParamRespuestaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlParamRespuestaResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private PlParamRespuestaRepository plParamRespuestaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlParamRespuestaMockMvc;

    private PlParamRespuesta plParamRespuesta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlParamRespuestaResource plParamRespuestaResource = new PlParamRespuestaResource(plParamRespuestaRepository);
        this.restPlParamRespuestaMockMvc = MockMvcBuilders.standaloneSetup(plParamRespuestaResource)
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
    public static PlParamRespuesta createEntity(EntityManager em) {
        PlParamRespuesta plParamRespuesta = new PlParamRespuesta()
            .valor(DEFAULT_VALOR);
        return plParamRespuesta;
    }

    @Before
    public void initTest() {
        plParamRespuesta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlParamRespuesta() throws Exception {
        int databaseSizeBeforeCreate = plParamRespuestaRepository.findAll().size();

        // Create the PlParamRespuesta
        restPlParamRespuestaMockMvc.perform(post("/api/pl-param-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamRespuesta)))
            .andExpect(status().isCreated());

        // Validate the PlParamRespuesta in the database
        List<PlParamRespuesta> plParamRespuestaList = plParamRespuestaRepository.findAll();
        assertThat(plParamRespuestaList).hasSize(databaseSizeBeforeCreate + 1);
        PlParamRespuesta testPlParamRespuesta = plParamRespuestaList.get(plParamRespuestaList.size() - 1);
        assertThat(testPlParamRespuesta.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createPlParamRespuestaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plParamRespuestaRepository.findAll().size();

        // Create the PlParamRespuesta with an existing ID
        plParamRespuesta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlParamRespuestaMockMvc.perform(post("/api/pl-param-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamRespuesta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlParamRespuesta> plParamRespuestaList = plParamRespuestaRepository.findAll();
        assertThat(plParamRespuestaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = plParamRespuestaRepository.findAll().size();
        // set the field null
        plParamRespuesta.setValor(null);

        // Create the PlParamRespuesta, which fails.

        restPlParamRespuestaMockMvc.perform(post("/api/pl-param-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamRespuesta)))
            .andExpect(status().isBadRequest());

        List<PlParamRespuesta> plParamRespuestaList = plParamRespuestaRepository.findAll();
        assertThat(plParamRespuestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlParamRespuestas() throws Exception {
        // Initialize the database
        plParamRespuestaRepository.saveAndFlush(plParamRespuesta);

        // Get all the plParamRespuestaList
        restPlParamRespuestaMockMvc.perform(get("/api/pl-param-respuestas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plParamRespuesta.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }

    @Test
    @Transactional
    public void getPlParamRespuesta() throws Exception {
        // Initialize the database
        plParamRespuestaRepository.saveAndFlush(plParamRespuesta);

        // Get the plParamRespuesta
        restPlParamRespuestaMockMvc.perform(get("/api/pl-param-respuestas/{id}", plParamRespuesta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plParamRespuesta.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlParamRespuesta() throws Exception {
        // Get the plParamRespuesta
        restPlParamRespuestaMockMvc.perform(get("/api/pl-param-respuestas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlParamRespuesta() throws Exception {
        // Initialize the database
        plParamRespuestaRepository.saveAndFlush(plParamRespuesta);
        int databaseSizeBeforeUpdate = plParamRespuestaRepository.findAll().size();

        // Update the plParamRespuesta
        PlParamRespuesta updatedPlParamRespuesta = plParamRespuestaRepository.findOne(plParamRespuesta.getId());
        updatedPlParamRespuesta
            .valor(UPDATED_VALOR);

        restPlParamRespuestaMockMvc.perform(put("/api/pl-param-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlParamRespuesta)))
            .andExpect(status().isOk());

        // Validate the PlParamRespuesta in the database
        List<PlParamRespuesta> plParamRespuestaList = plParamRespuestaRepository.findAll();
        assertThat(plParamRespuestaList).hasSize(databaseSizeBeforeUpdate);
        PlParamRespuesta testPlParamRespuesta = plParamRespuestaList.get(plParamRespuestaList.size() - 1);
        assertThat(testPlParamRespuesta.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPlParamRespuesta() throws Exception {
        int databaseSizeBeforeUpdate = plParamRespuestaRepository.findAll().size();

        // Create the PlParamRespuesta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlParamRespuestaMockMvc.perform(put("/api/pl-param-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plParamRespuesta)))
            .andExpect(status().isCreated());

        // Validate the PlParamRespuesta in the database
        List<PlParamRespuesta> plParamRespuestaList = plParamRespuestaRepository.findAll();
        assertThat(plParamRespuestaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlParamRespuesta() throws Exception {
        // Initialize the database
        plParamRespuestaRepository.saveAndFlush(plParamRespuesta);
        int databaseSizeBeforeDelete = plParamRespuestaRepository.findAll().size();

        // Get the plParamRespuesta
        restPlParamRespuestaMockMvc.perform(delete("/api/pl-param-respuestas/{id}", plParamRespuesta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlParamRespuesta> plParamRespuestaList = plParamRespuestaRepository.findAll();
        assertThat(plParamRespuestaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlParamRespuesta.class);
        PlParamRespuesta plParamRespuesta1 = new PlParamRespuesta();
        plParamRespuesta1.setId(1L);
        PlParamRespuesta plParamRespuesta2 = new PlParamRespuesta();
        plParamRespuesta2.setId(plParamRespuesta1.getId());
        assertThat(plParamRespuesta1).isEqualTo(plParamRespuesta2);
        plParamRespuesta2.setId(2L);
        assertThat(plParamRespuesta1).isNotEqualTo(plParamRespuesta2);
        plParamRespuesta1.setId(null);
        assertThat(plParamRespuesta1).isNotEqualTo(plParamRespuesta2);
    }
}
