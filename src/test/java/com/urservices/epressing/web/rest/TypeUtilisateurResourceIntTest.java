package com.urservices.epressing.web.rest;

import com.urservices.epressing.EpressingApp;

import com.urservices.epressing.domain.TypeUtilisateur;
import com.urservices.epressing.repository.TypeUtilisateurRepository;
import com.urservices.epressing.service.TypeUtilisateurService;
import com.urservices.epressing.repository.search.TypeUtilisateurSearchRepository;
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
 * Test class for the TypeUtilisateurResource REST controller.
 *
 * @see TypeUtilisateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpressingApp.class)
public class TypeUtilisateurResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeUtilisateurRepository typeUtilisateurRepository;

    @Autowired
    private TypeUtilisateurService typeUtilisateurService;

    @Autowired
    private TypeUtilisateurSearchRepository typeUtilisateurSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeUtilisateurMockMvc;

    private TypeUtilisateur typeUtilisateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TypeUtilisateurResource typeUtilisateurResource = new TypeUtilisateurResource(typeUtilisateurService);
        this.restTypeUtilisateurMockMvc = MockMvcBuilders.standaloneSetup(typeUtilisateurResource)
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
    public static TypeUtilisateur createEntity(EntityManager em) {
        TypeUtilisateur typeUtilisateur = new TypeUtilisateur()
                .libelle(DEFAULT_LIBELLE);
        return typeUtilisateur;
    }

    @Before
    public void initTest() {
        typeUtilisateurSearchRepository.deleteAll();
        typeUtilisateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeUtilisateur() throws Exception {
        int databaseSizeBeforeCreate = typeUtilisateurRepository.findAll().size();

        // Create the TypeUtilisateur

        restTypeUtilisateurMockMvc.perform(post("/api/type-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeUtilisateur)))
            .andExpect(status().isCreated());

        // Validate the TypeUtilisateur in the database
        List<TypeUtilisateur> typeUtilisateurList = typeUtilisateurRepository.findAll();
        assertThat(typeUtilisateurList).hasSize(databaseSizeBeforeCreate + 1);
        TypeUtilisateur testTypeUtilisateur = typeUtilisateurList.get(typeUtilisateurList.size() - 1);
        assertThat(testTypeUtilisateur.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the TypeUtilisateur in Elasticsearch
        TypeUtilisateur typeUtilisateurEs = typeUtilisateurSearchRepository.findOne(testTypeUtilisateur.getId());
        assertThat(typeUtilisateurEs).isEqualToComparingFieldByField(testTypeUtilisateur);
    }

    @Test
    @Transactional
    public void createTypeUtilisateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeUtilisateurRepository.findAll().size();

        // Create the TypeUtilisateur with an existing ID
        TypeUtilisateur existingTypeUtilisateur = new TypeUtilisateur();
        existingTypeUtilisateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeUtilisateurMockMvc.perform(post("/api/type-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTypeUtilisateur)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TypeUtilisateur> typeUtilisateurList = typeUtilisateurRepository.findAll();
        assertThat(typeUtilisateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeUtilisateurRepository.findAll().size();
        // set the field null
        typeUtilisateur.setLibelle(null);

        // Create the TypeUtilisateur, which fails.

        restTypeUtilisateurMockMvc.perform(post("/api/type-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeUtilisateur)))
            .andExpect(status().isBadRequest());

        List<TypeUtilisateur> typeUtilisateurList = typeUtilisateurRepository.findAll();
        assertThat(typeUtilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeUtilisateurs() throws Exception {
        // Initialize the database
        typeUtilisateurRepository.saveAndFlush(typeUtilisateur);

        // Get all the typeUtilisateurList
        restTypeUtilisateurMockMvc.perform(get("/api/type-utilisateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeUtilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getTypeUtilisateur() throws Exception {
        // Initialize the database
        typeUtilisateurRepository.saveAndFlush(typeUtilisateur);

        // Get the typeUtilisateur
        restTypeUtilisateurMockMvc.perform(get("/api/type-utilisateurs/{id}", typeUtilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeUtilisateur.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeUtilisateur() throws Exception {
        // Get the typeUtilisateur
        restTypeUtilisateurMockMvc.perform(get("/api/type-utilisateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeUtilisateur() throws Exception {
        // Initialize the database
        typeUtilisateurService.save(typeUtilisateur);

        int databaseSizeBeforeUpdate = typeUtilisateurRepository.findAll().size();

        // Update the typeUtilisateur
        TypeUtilisateur updatedTypeUtilisateur = typeUtilisateurRepository.findOne(typeUtilisateur.getId());
        updatedTypeUtilisateur
                .libelle(UPDATED_LIBELLE);

        restTypeUtilisateurMockMvc.perform(put("/api/type-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeUtilisateur)))
            .andExpect(status().isOk());

        // Validate the TypeUtilisateur in the database
        List<TypeUtilisateur> typeUtilisateurList = typeUtilisateurRepository.findAll();
        assertThat(typeUtilisateurList).hasSize(databaseSizeBeforeUpdate);
        TypeUtilisateur testTypeUtilisateur = typeUtilisateurList.get(typeUtilisateurList.size() - 1);
        assertThat(testTypeUtilisateur.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the TypeUtilisateur in Elasticsearch
        TypeUtilisateur typeUtilisateurEs = typeUtilisateurSearchRepository.findOne(testTypeUtilisateur.getId());
        assertThat(typeUtilisateurEs).isEqualToComparingFieldByField(testTypeUtilisateur);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = typeUtilisateurRepository.findAll().size();

        // Create the TypeUtilisateur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeUtilisateurMockMvc.perform(put("/api/type-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeUtilisateur)))
            .andExpect(status().isCreated());

        // Validate the TypeUtilisateur in the database
        List<TypeUtilisateur> typeUtilisateurList = typeUtilisateurRepository.findAll();
        assertThat(typeUtilisateurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeUtilisateur() throws Exception {
        // Initialize the database
        typeUtilisateurService.save(typeUtilisateur);

        int databaseSizeBeforeDelete = typeUtilisateurRepository.findAll().size();

        // Get the typeUtilisateur
        restTypeUtilisateurMockMvc.perform(delete("/api/type-utilisateurs/{id}", typeUtilisateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean typeUtilisateurExistsInEs = typeUtilisateurSearchRepository.exists(typeUtilisateur.getId());
        assertThat(typeUtilisateurExistsInEs).isFalse();

        // Validate the database is empty
        List<TypeUtilisateur> typeUtilisateurList = typeUtilisateurRepository.findAll();
        assertThat(typeUtilisateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTypeUtilisateur() throws Exception {
        // Initialize the database
        typeUtilisateurService.save(typeUtilisateur);

        // Search the typeUtilisateur
        restTypeUtilisateurMockMvc.perform(get("/api/_search/type-utilisateurs?query=id:" + typeUtilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeUtilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeUtilisateur.class);
    }
}
