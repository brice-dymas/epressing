package com.urservices.epressing.web.rest;

import com.urservices.epressing.EpressingApp;

import com.urservices.epressing.domain.CarteBancaire;
import com.urservices.epressing.repository.CarteBancaireRepository;
import com.urservices.epressing.service.CarteBancaireService;
import com.urservices.epressing.repository.search.CarteBancaireSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.urservices.epressing.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarteBancaireResource REST controller.
 *
 * @see CarteBancaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpressingApp.class)
public class CarteBancaireResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_HVC = "AAAAAAAAAA";
    private static final String UPDATED_CODE_HVC = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_EXPIRATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_EXPIRATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CarteBancaireRepository carteBancaireRepository;

    @Autowired
    private CarteBancaireService carteBancaireService;

    @Autowired
    private CarteBancaireSearchRepository carteBancaireSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarteBancaireMockMvc;

    private CarteBancaire carteBancaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarteBancaireResource carteBancaireResource = new CarteBancaireResource(carteBancaireService);
        this.restCarteBancaireMockMvc = MockMvcBuilders.standaloneSetup(carteBancaireResource)
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
    public static CarteBancaire createEntity(EntityManager em) {
        CarteBancaire carteBancaire = new CarteBancaire()
                .numero(DEFAULT_NUMERO)
                .codeHVC(DEFAULT_CODE_HVC)
                .dateExpiration(DEFAULT_DATE_EXPIRATION);
        return carteBancaire;
    }

    @Before
    public void initTest() {
        carteBancaireSearchRepository.deleteAll();
        carteBancaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarteBancaire() throws Exception {
        int databaseSizeBeforeCreate = carteBancaireRepository.findAll().size();

        // Create the CarteBancaire

        restCarteBancaireMockMvc.perform(post("/api/carte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteBancaire)))
            .andExpect(status().isCreated());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeCreate + 1);
        CarteBancaire testCarteBancaire = carteBancaireList.get(carteBancaireList.size() - 1);
        assertThat(testCarteBancaire.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCarteBancaire.getCodeHVC()).isEqualTo(DEFAULT_CODE_HVC);
        assertThat(testCarteBancaire.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);

        // Validate the CarteBancaire in Elasticsearch
        CarteBancaire carteBancaireEs = carteBancaireSearchRepository.findOne(testCarteBancaire.getId());
        assertThat(carteBancaireEs).isEqualToComparingFieldByField(testCarteBancaire);
    }

    @Test
    @Transactional
    public void createCarteBancaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carteBancaireRepository.findAll().size();

        // Create the CarteBancaire with an existing ID
        CarteBancaire existingCarteBancaire = new CarteBancaire();
        existingCarteBancaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteBancaireMockMvc.perform(post("/api/carte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCarteBancaire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteBancaireRepository.findAll().size();
        // set the field null
        carteBancaire.setNumero(null);

        // Create the CarteBancaire, which fails.

        restCarteBancaireMockMvc.perform(post("/api/carte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteBancaire)))
            .andExpect(status().isBadRequest());

        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeHVCIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteBancaireRepository.findAll().size();
        // set the field null
        carteBancaire.setCodeHVC(null);

        // Create the CarteBancaire, which fails.

        restCarteBancaireMockMvc.perform(post("/api/carte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteBancaire)))
            .andExpect(status().isBadRequest());

        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateExpirationIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteBancaireRepository.findAll().size();
        // set the field null
        carteBancaire.setDateExpiration(null);

        // Create the CarteBancaire, which fails.

        restCarteBancaireMockMvc.perform(post("/api/carte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteBancaire)))
            .andExpect(status().isBadRequest());

        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarteBancaires() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        // Get all the carteBancaireList
        restCarteBancaireMockMvc.perform(get("/api/carte-bancaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteBancaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].codeHVC").value(hasItem(DEFAULT_CODE_HVC.toString())))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(sameInstant(DEFAULT_DATE_EXPIRATION))));
    }

    @Test
    @Transactional
    public void getCarteBancaire() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        // Get the carteBancaire
        restCarteBancaireMockMvc.perform(get("/api/carte-bancaires/{id}", carteBancaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carteBancaire.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.codeHVC").value(DEFAULT_CODE_HVC.toString()))
            .andExpect(jsonPath("$.dateExpiration").value(sameInstant(DEFAULT_DATE_EXPIRATION)));
    }

    @Test
    @Transactional
    public void getNonExistingCarteBancaire() throws Exception {
        // Get the carteBancaire
        restCarteBancaireMockMvc.perform(get("/api/carte-bancaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarteBancaire() throws Exception {
        // Initialize the database
        carteBancaireService.save(carteBancaire);

        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();

        // Update the carteBancaire
        CarteBancaire updatedCarteBancaire = carteBancaireRepository.findOne(carteBancaire.getId());
        updatedCarteBancaire
                .numero(UPDATED_NUMERO)
                .codeHVC(UPDATED_CODE_HVC)
                .dateExpiration(UPDATED_DATE_EXPIRATION);

        restCarteBancaireMockMvc.perform(put("/api/carte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarteBancaire)))
            .andExpect(status().isOk());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
        CarteBancaire testCarteBancaire = carteBancaireList.get(carteBancaireList.size() - 1);
        assertThat(testCarteBancaire.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCarteBancaire.getCodeHVC()).isEqualTo(UPDATED_CODE_HVC);
        assertThat(testCarteBancaire.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);

        // Validate the CarteBancaire in Elasticsearch
        CarteBancaire carteBancaireEs = carteBancaireSearchRepository.findOne(testCarteBancaire.getId());
        assertThat(carteBancaireEs).isEqualToComparingFieldByField(testCarteBancaire);
    }

    @Test
    @Transactional
    public void updateNonExistingCarteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();

        // Create the CarteBancaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarteBancaireMockMvc.perform(put("/api/carte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteBancaire)))
            .andExpect(status().isCreated());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarteBancaire() throws Exception {
        // Initialize the database
        carteBancaireService.save(carteBancaire);

        int databaseSizeBeforeDelete = carteBancaireRepository.findAll().size();

        // Get the carteBancaire
        restCarteBancaireMockMvc.perform(delete("/api/carte-bancaires/{id}", carteBancaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean carteBancaireExistsInEs = carteBancaireSearchRepository.exists(carteBancaire.getId());
        assertThat(carteBancaireExistsInEs).isFalse();

        // Validate the database is empty
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCarteBancaire() throws Exception {
        // Initialize the database
        carteBancaireService.save(carteBancaire);

        // Search the carteBancaire
        restCarteBancaireMockMvc.perform(get("/api/_search/carte-bancaires?query=id:" + carteBancaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteBancaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].codeHVC").value(hasItem(DEFAULT_CODE_HVC.toString())))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(sameInstant(DEFAULT_DATE_EXPIRATION))));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteBancaire.class);
    }
}
