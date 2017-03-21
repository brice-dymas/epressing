package com.urservices.epressing.web.rest;

import com.urservices.epressing.EpressingApp;

import com.urservices.epressing.domain.Caracteristique;
import com.urservices.epressing.repository.CaracteristiqueRepository;
import com.urservices.epressing.service.CaracteristiqueService;
import com.urservices.epressing.repository.search.CaracteristiqueSearchRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CaracteristiqueResource REST controller.
 *
 * @see CaracteristiqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpressingApp.class)
public class CaracteristiqueResourceIntTest {

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Autowired
    private CaracteristiqueService caracteristiqueService;

    @Autowired
    private CaracteristiqueSearchRepository caracteristiqueSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCaracteristiqueMockMvc;

    private Caracteristique caracteristique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CaracteristiqueResource caracteristiqueResource = new CaracteristiqueResource(caracteristiqueService);
        this.restCaracteristiqueMockMvc = MockMvcBuilders.standaloneSetup(caracteristiqueResource)
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
    public static Caracteristique createEntity(EntityManager em) {
        Caracteristique caracteristique = new Caracteristique()
                .couleur(DEFAULT_COULEUR)
                .marque(DEFAULT_MARQUE)
                .libelle(DEFAULT_LIBELLE);
        return caracteristique;
    }

    @Before
    public void initTest() {
        caracteristiqueSearchRepository.deleteAll();
        caracteristique = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaracteristique() throws Exception {
        int databaseSizeBeforeCreate = caracteristiqueRepository.findAll().size();

        // Create the Caracteristique

        restCaracteristiqueMockMvc.perform(post("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristique)))
            .andExpect(status().isCreated());

        // Validate the Caracteristique in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Caracteristique testCaracteristique = caracteristiqueList.get(caracteristiqueList.size() - 1);
        assertThat(testCaracteristique.getCouleur()).isEqualTo(DEFAULT_COULEUR);
        assertThat(testCaracteristique.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testCaracteristique.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the Caracteristique in Elasticsearch
        Caracteristique caracteristiqueEs = caracteristiqueSearchRepository.findOne(testCaracteristique.getId());
        assertThat(caracteristiqueEs).isEqualToComparingFieldByField(testCaracteristique);
    }

    @Test
    @Transactional
    public void createCaracteristiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caracteristiqueRepository.findAll().size();

        // Create the Caracteristique with an existing ID
        Caracteristique existingCaracteristique = new Caracteristique();
        existingCaracteristique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristiqueMockMvc.perform(post("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCaracteristique)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCouleurIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristiqueRepository.findAll().size();
        // set the field null
        caracteristique.setCouleur(null);

        // Create the Caracteristique, which fails.

        restCaracteristiqueMockMvc.perform(post("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristique)))
            .andExpect(status().isBadRequest());

        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristiqueRepository.findAll().size();
        // set the field null
        caracteristique.setMarque(null);

        // Create the Caracteristique, which fails.

        restCaracteristiqueMockMvc.perform(post("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristique)))
            .andExpect(status().isBadRequest());

        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCaracteristiques() throws Exception {
        // Initialize the database
        caracteristiqueRepository.saveAndFlush(caracteristique);

        // Get all the caracteristiqueList
        restCaracteristiqueMockMvc.perform(get("/api/caracteristiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristique.getId().intValue())))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR.toString())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getCaracteristique() throws Exception {
        // Initialize the database
        caracteristiqueRepository.saveAndFlush(caracteristique);

        // Get the caracteristique
        restCaracteristiqueMockMvc.perform(get("/api/caracteristiques/{id}", caracteristique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristique.getId().intValue()))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR.toString()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaracteristique() throws Exception {
        // Get the caracteristique
        restCaracteristiqueMockMvc.perform(get("/api/caracteristiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaracteristique() throws Exception {
        // Initialize the database
        caracteristiqueService.save(caracteristique);

        int databaseSizeBeforeUpdate = caracteristiqueRepository.findAll().size();

        // Update the caracteristique
        Caracteristique updatedCaracteristique = caracteristiqueRepository.findOne(caracteristique.getId());
        updatedCaracteristique
                .couleur(UPDATED_COULEUR)
                .marque(UPDATED_MARQUE)
                .libelle(UPDATED_LIBELLE);

        restCaracteristiqueMockMvc.perform(put("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaracteristique)))
            .andExpect(status().isOk());

        // Validate the Caracteristique in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeUpdate);
        Caracteristique testCaracteristique = caracteristiqueList.get(caracteristiqueList.size() - 1);
        assertThat(testCaracteristique.getCouleur()).isEqualTo(UPDATED_COULEUR);
        assertThat(testCaracteristique.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testCaracteristique.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the Caracteristique in Elasticsearch
        Caracteristique caracteristiqueEs = caracteristiqueSearchRepository.findOne(testCaracteristique.getId());
        assertThat(caracteristiqueEs).isEqualToComparingFieldByField(testCaracteristique);
    }

    @Test
    @Transactional
    public void updateNonExistingCaracteristique() throws Exception {
        int databaseSizeBeforeUpdate = caracteristiqueRepository.findAll().size();

        // Create the Caracteristique

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCaracteristiqueMockMvc.perform(put("/api/caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristique)))
            .andExpect(status().isCreated());

        // Validate the Caracteristique in the database
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCaracteristique() throws Exception {
        // Initialize the database
        caracteristiqueService.save(caracteristique);

        int databaseSizeBeforeDelete = caracteristiqueRepository.findAll().size();

        // Get the caracteristique
        restCaracteristiqueMockMvc.perform(delete("/api/caracteristiques/{id}", caracteristique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean caracteristiqueExistsInEs = caracteristiqueSearchRepository.exists(caracteristique.getId());
        assertThat(caracteristiqueExistsInEs).isFalse();

        // Validate the database is empty
        List<Caracteristique> caracteristiqueList = caracteristiqueRepository.findAll();
        assertThat(caracteristiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCaracteristique() throws Exception {
        // Initialize the database
        caracteristiqueService.save(caracteristique);

        // Search the caracteristique
        restCaracteristiqueMockMvc.perform(get("/api/_search/caracteristiques?query=id:" + caracteristique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristique.getId().intValue())))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR.toString())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caracteristique.class);
    }
}
