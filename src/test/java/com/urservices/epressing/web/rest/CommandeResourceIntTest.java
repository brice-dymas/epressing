package com.urservices.epressing.web.rest;

import com.urservices.epressing.EpressingApp;

import com.urservices.epressing.domain.Commande;
import com.urservices.epressing.repository.CommandeRepository;
import com.urservices.epressing.service.CommandeService;
import com.urservices.epressing.repository.search.CommandeSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static com.urservices.epressing.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommandeResource REST controller.
 *
 * @see CommandeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpressingApp.class)
public class CommandeResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_COMMANDE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_COMMANDE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_FACTURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FACTURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_FACTURATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FACTURATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_CUEILLETTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CUEILLETTE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_LIVRAISON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_LIVRAISON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_NET_A_PAYER = new BigDecimal(1);
    private static final BigDecimal UPDATED_NET_A_PAYER = new BigDecimal(2);

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_CUEILLETTE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_CUEILLETTE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_LIVRAISON = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_LIVRAISON = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_FACTURATION = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_FACTURATION = "BBBBBBBBBB";

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private CommandeSearchRepository commandeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommandeMockMvc;

    private Commande commande;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommandeResource commandeResource = new CommandeResource(commandeService);
        this.restCommandeMockMvc = MockMvcBuilders.standaloneSetup(commandeResource)
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
    public static Commande createEntity(EntityManager em) {
        Commande commande = new Commande()
                .dateCommande(DEFAULT_DATE_COMMANDE)
                .dateFacture(DEFAULT_DATE_FACTURE)
                .dateFacturation(DEFAULT_DATE_FACTURATION)
                .dateCueillette(DEFAULT_DATE_CUEILLETTE)
                .dateLivraison(DEFAULT_DATE_LIVRAISON)
                .netAPayer(DEFAULT_NET_A_PAYER)
                .etat(DEFAULT_ETAT)
                .adresseCueillette(DEFAULT_ADRESSE_CUEILLETTE)
                .adresseLivraison(DEFAULT_ADRESSE_LIVRAISON)
                .adresseFacturation(DEFAULT_ADRESSE_FACTURATION);
        return commande;
    }

    @Before
    public void initTest() {
        commandeSearchRepository.deleteAll();
        commande = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommande() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande

        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isCreated());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate + 1);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testCommande.getDateFacture()).isEqualTo(DEFAULT_DATE_FACTURE);
        assertThat(testCommande.getDateFacturation()).isEqualTo(DEFAULT_DATE_FACTURATION);
        assertThat(testCommande.getDateCueillette()).isEqualTo(DEFAULT_DATE_CUEILLETTE);
        assertThat(testCommande.getDateLivraison()).isEqualTo(DEFAULT_DATE_LIVRAISON);
        assertThat(testCommande.getNetAPayer()).isEqualTo(DEFAULT_NET_A_PAYER);
        assertThat(testCommande.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testCommande.getAdresseCueillette()).isEqualTo(DEFAULT_ADRESSE_CUEILLETTE);
        assertThat(testCommande.getAdresseLivraison()).isEqualTo(DEFAULT_ADRESSE_LIVRAISON);
        assertThat(testCommande.getAdresseFacturation()).isEqualTo(DEFAULT_ADRESSE_FACTURATION);

        // Validate the Commande in Elasticsearch
        Commande commandeEs = commandeSearchRepository.findOne(testCommande.getId());
        assertThat(commandeEs).isEqualToComparingFieldByField(testCommande);
    }

    @Test
    @Transactional
    public void createCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande with an existing ID
        Commande existingCommande = new Commande();
        existingCommande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCommande)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateCueilletteIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeRepository.findAll().size();
        // set the field null
        commande.setDateCueillette(null);

        // Create the Commande, which fails.

        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeRepository.findAll().size();
        // set the field null
        commande.setDateLivraison(null);

        // Create the Commande, which fails.

        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseCueilletteIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeRepository.findAll().size();
        // set the field null
        commande.setAdresseCueillette(null);

        // Create the Commande, which fails.

        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeRepository.findAll().size();
        // set the field null
        commande.setAdresseLivraison(null);

        // Create the Commande, which fails.

        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommandes() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList
        restCommandeMockMvc.perform(get("/api/commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(sameInstant(DEFAULT_DATE_COMMANDE))))
            .andExpect(jsonPath("$.[*].dateFacture").value(hasItem(sameInstant(DEFAULT_DATE_FACTURE))))
            .andExpect(jsonPath("$.[*].dateFacturation").value(hasItem(sameInstant(DEFAULT_DATE_FACTURATION))))
            .andExpect(jsonPath("$.[*].dateCueillette").value(hasItem(sameInstant(DEFAULT_DATE_CUEILLETTE))))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(sameInstant(DEFAULT_DATE_LIVRAISON))))
            .andExpect(jsonPath("$.[*].netAPayer").value(hasItem(DEFAULT_NET_A_PAYER.intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].adresseCueillette").value(hasItem(DEFAULT_ADRESSE_CUEILLETTE.toString())))
            .andExpect(jsonPath("$.[*].adresseLivraison").value(hasItem(DEFAULT_ADRESSE_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].adresseFacturation").value(hasItem(DEFAULT_ADRESSE_FACTURATION.toString())));
    }

    @Test
    @Transactional
    public void getCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commande.getId().intValue()))
            .andExpect(jsonPath("$.dateCommande").value(sameInstant(DEFAULT_DATE_COMMANDE)))
            .andExpect(jsonPath("$.dateFacture").value(sameInstant(DEFAULT_DATE_FACTURE)))
            .andExpect(jsonPath("$.dateFacturation").value(sameInstant(DEFAULT_DATE_FACTURATION)))
            .andExpect(jsonPath("$.dateCueillette").value(sameInstant(DEFAULT_DATE_CUEILLETTE)))
            .andExpect(jsonPath("$.dateLivraison").value(sameInstant(DEFAULT_DATE_LIVRAISON)))
            .andExpect(jsonPath("$.netAPayer").value(DEFAULT_NET_A_PAYER.intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.adresseCueillette").value(DEFAULT_ADRESSE_CUEILLETTE.toString()))
            .andExpect(jsonPath("$.adresseLivraison").value(DEFAULT_ADRESSE_LIVRAISON.toString()))
            .andExpect(jsonPath("$.adresseFacturation").value(DEFAULT_ADRESSE_FACTURATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommande() throws Exception {
        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommande() throws Exception {
        // Initialize the database
        commandeService.save(commande);

        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Update the commande
        Commande updatedCommande = commandeRepository.findOne(commande.getId());
        updatedCommande
                .dateCommande(UPDATED_DATE_COMMANDE)
                .dateFacture(UPDATED_DATE_FACTURE)
                .dateFacturation(UPDATED_DATE_FACTURATION)
                .dateCueillette(UPDATED_DATE_CUEILLETTE)
                .dateLivraison(UPDATED_DATE_LIVRAISON)
                .netAPayer(UPDATED_NET_A_PAYER)
                .etat(UPDATED_ETAT)
                .adresseCueillette(UPDATED_ADRESSE_CUEILLETTE)
                .adresseLivraison(UPDATED_ADRESSE_LIVRAISON)
                .adresseFacturation(UPDATED_ADRESSE_FACTURATION);

        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommande)))
            .andExpect(status().isOk());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testCommande.getDateFacture()).isEqualTo(UPDATED_DATE_FACTURE);
        assertThat(testCommande.getDateFacturation()).isEqualTo(UPDATED_DATE_FACTURATION);
        assertThat(testCommande.getDateCueillette()).isEqualTo(UPDATED_DATE_CUEILLETTE);
        assertThat(testCommande.getDateLivraison()).isEqualTo(UPDATED_DATE_LIVRAISON);
        assertThat(testCommande.getNetAPayer()).isEqualTo(UPDATED_NET_A_PAYER);
        assertThat(testCommande.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testCommande.getAdresseCueillette()).isEqualTo(UPDATED_ADRESSE_CUEILLETTE);
        assertThat(testCommande.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
        assertThat(testCommande.getAdresseFacturation()).isEqualTo(UPDATED_ADRESSE_FACTURATION);

        // Validate the Commande in Elasticsearch
        Commande commandeEs = commandeSearchRepository.findOne(testCommande.getId());
        assertThat(commandeEs).isEqualToComparingFieldByField(testCommande);
    }

    @Test
    @Transactional
    public void updateNonExistingCommande() throws Exception {
        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Create the Commande

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isCreated());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommande() throws Exception {
        // Initialize the database
        commandeService.save(commande);

        int databaseSizeBeforeDelete = commandeRepository.findAll().size();

        // Get the commande
        restCommandeMockMvc.perform(delete("/api/commandes/{id}", commande.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean commandeExistsInEs = commandeSearchRepository.exists(commande.getId());
        assertThat(commandeExistsInEs).isFalse();

        // Validate the database is empty
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCommande() throws Exception {
        // Initialize the database
        commandeService.save(commande);

        // Search the commande
        restCommandeMockMvc.perform(get("/api/_search/commandes?query=id:" + commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(sameInstant(DEFAULT_DATE_COMMANDE))))
            .andExpect(jsonPath("$.[*].dateFacture").value(hasItem(sameInstant(DEFAULT_DATE_FACTURE))))
            .andExpect(jsonPath("$.[*].dateFacturation").value(hasItem(sameInstant(DEFAULT_DATE_FACTURATION))))
            .andExpect(jsonPath("$.[*].dateCueillette").value(hasItem(sameInstant(DEFAULT_DATE_CUEILLETTE))))
            .andExpect(jsonPath("$.[*].dateLivraison").value(hasItem(sameInstant(DEFAULT_DATE_LIVRAISON))))
            .andExpect(jsonPath("$.[*].netAPayer").value(hasItem(DEFAULT_NET_A_PAYER.intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].adresseCueillette").value(hasItem(DEFAULT_ADRESSE_CUEILLETTE.toString())))
            .andExpect(jsonPath("$.[*].adresseLivraison").value(hasItem(DEFAULT_ADRESSE_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].adresseFacturation").value(hasItem(DEFAULT_ADRESSE_FACTURATION.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commande.class);
    }
}
