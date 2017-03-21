package com.urservices.epressing.web.rest;

import com.urservices.epressing.EpressingApp;

import com.urservices.epressing.domain.Tarif;
import com.urservices.epressing.repository.TarifRepository;
import com.urservices.epressing.service.TarifService;
import com.urservices.epressing.repository.search.TarifSearchRepository;
import com.urservices.epressing.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TarifResource REST controller.
 *
 * @see TarifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpressingApp.class)
public class TarifResourceIntTest {

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);

    @Autowired
    private TarifRepository tarifRepository;

    @Autowired
    private TarifService tarifService;

    @Autowired
    private TarifSearchRepository tarifSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTarifMockMvc;

    private Tarif tarif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TarifResource tarifResource = new TarifResource(tarifService);
        this.restTarifMockMvc = MockMvcBuilders.standaloneSetup(tarifResource)
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
    public static Tarif createEntity(EntityManager em) {
        Tarif tarif = new Tarif()
                .montant(DEFAULT_MONTANT);
        return tarif;
    }

    @Before
    public void initTest() {
        tarifSearchRepository.deleteAll();
        tarif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarif() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarif)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate + 1);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getMontant()).isEqualTo(DEFAULT_MONTANT);

        // Validate the Tarif in Elasticsearch
        Tarif tarifEs = tarifSearchRepository.findOne(testTarif.getId());
        assertThat(tarifEs).isEqualToComparingFieldByField(testTarif);
    }

    @Test
    @Transactional
    public void createTarifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif with an existing ID
        Tarif existingTarif = new Tarif();
        existingTarif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTarif)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setMontant(null);

        // Create the Tarif, which fails.

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarif)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarifs() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList
        restTarifMockMvc.perform(get("/api/tarifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.intValue())));
    }

    @Test
    @Transactional
    public void getTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", tarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarif.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTarif() throws Exception {
        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarif() throws Exception {
        // Initialize the database
        tarifService.save(tarif);

        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Update the tarif
        Tarif updatedTarif = tarifRepository.findOne(tarif.getId());
        updatedTarif
                .montant(UPDATED_MONTANT);

        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarif)))
            .andExpect(status().isOk());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getMontant()).isEqualTo(UPDATED_MONTANT);

        // Validate the Tarif in Elasticsearch
        Tarif tarifEs = tarifSearchRepository.findOne(testTarif.getId());
        assertThat(tarifEs).isEqualToComparingFieldByField(testTarif);
    }

    @Test
    @Transactional
    public void updateNonExistingTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Create the Tarif

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarif)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTarif() throws Exception {
        // Initialize the database
        tarifService.save(tarif);

        int databaseSizeBeforeDelete = tarifRepository.findAll().size();

        // Get the tarif
        restTarifMockMvc.perform(delete("/api/tarifs/{id}", tarif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tarifExistsInEs = tarifSearchRepository.exists(tarif.getId());
        assertThat(tarifExistsInEs).isFalse();

        // Validate the database is empty
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTarif() throws Exception {
        // Initialize the database
        tarifService.save(tarif);

        // Search the tarif
        restTarifMockMvc.perform(get("/api/_search/tarifs?query=id:" + tarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.intValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarif.class);
    }
}
