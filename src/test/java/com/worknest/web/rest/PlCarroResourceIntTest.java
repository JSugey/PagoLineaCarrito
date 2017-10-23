package com.worknest.web.rest;

import com.worknest.PagolineaApp;

import com.worknest.domain.PlCarro;
import com.worknest.repository.PlCarroRepository;
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
 * Test class for the PlCarroResource REST controller.
 *
 * @see PlCarroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PagolineaApp.class)
public class PlCarroResourceIntTest {

    @Autowired
    private PlCarroRepository plCarroRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlCarroMockMvc;

    private PlCarro plCarro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlCarroResource plCarroResource = new PlCarroResource(plCarroRepository);
        this.restPlCarroMockMvc = MockMvcBuilders.standaloneSetup(plCarroResource)
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
    public static PlCarro createEntity(EntityManager em) {
        PlCarro plCarro = new PlCarro();
        return plCarro;
    }

    @Before
    public void initTest() {
        plCarro = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlCarro() throws Exception {
        int databaseSizeBeforeCreate = plCarroRepository.findAll().size();

        // Create the PlCarro
        restPlCarroMockMvc.perform(post("/api/pl-carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarro)))
            .andExpect(status().isCreated());

        // Validate the PlCarro in the database
        List<PlCarro> plCarroList = plCarroRepository.findAll();
        assertThat(plCarroList).hasSize(databaseSizeBeforeCreate + 1);
        PlCarro testPlCarro = plCarroList.get(plCarroList.size() - 1);
    }

    @Test
    @Transactional
    public void createPlCarroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plCarroRepository.findAll().size();

        // Create the PlCarro with an existing ID
        plCarro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlCarroMockMvc.perform(post("/api/pl-carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlCarro> plCarroList = plCarroRepository.findAll();
        assertThat(plCarroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlCarros() throws Exception {
        // Initialize the database
        plCarroRepository.saveAndFlush(plCarro);

        // Get all the plCarroList
        restPlCarroMockMvc.perform(get("/api/pl-carros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plCarro.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPlCarro() throws Exception {
        // Initialize the database
        plCarroRepository.saveAndFlush(plCarro);

        // Get the plCarro
        restPlCarroMockMvc.perform(get("/api/pl-carros/{id}", plCarro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plCarro.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlCarro() throws Exception {
        // Get the plCarro
        restPlCarroMockMvc.perform(get("/api/pl-carros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlCarro() throws Exception {
        // Initialize the database
        plCarroRepository.saveAndFlush(plCarro);
        int databaseSizeBeforeUpdate = plCarroRepository.findAll().size();

        // Update the plCarro
        PlCarro updatedPlCarro = plCarroRepository.findOne(plCarro.getId());

        restPlCarroMockMvc.perform(put("/api/pl-carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlCarro)))
            .andExpect(status().isOk());

        // Validate the PlCarro in the database
        List<PlCarro> plCarroList = plCarroRepository.findAll();
        assertThat(plCarroList).hasSize(databaseSizeBeforeUpdate);
        PlCarro testPlCarro = plCarroList.get(plCarroList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPlCarro() throws Exception {
        int databaseSizeBeforeUpdate = plCarroRepository.findAll().size();

        // Create the PlCarro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlCarroMockMvc.perform(put("/api/pl-carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plCarro)))
            .andExpect(status().isCreated());

        // Validate the PlCarro in the database
        List<PlCarro> plCarroList = plCarroRepository.findAll();
        assertThat(plCarroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlCarro() throws Exception {
        // Initialize the database
        plCarroRepository.saveAndFlush(plCarro);
        int databaseSizeBeforeDelete = plCarroRepository.findAll().size();

        // Get the plCarro
        restPlCarroMockMvc.perform(delete("/api/pl-carros/{id}", plCarro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlCarro> plCarroList = plCarroRepository.findAll();
        assertThat(plCarroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlCarro.class);
        PlCarro plCarro1 = new PlCarro();
        plCarro1.setId(1L);
        PlCarro plCarro2 = new PlCarro();
        plCarro2.setId(plCarro1.getId());
        assertThat(plCarro1).isEqualTo(plCarro2);
        plCarro2.setId(2L);
        assertThat(plCarro1).isNotEqualTo(plCarro2);
        plCarro1.setId(null);
        assertThat(plCarro1).isNotEqualTo(plCarro2);
    }
}
