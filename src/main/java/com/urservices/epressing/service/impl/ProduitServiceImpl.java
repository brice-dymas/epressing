package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.ProduitService;
import com.urservices.epressing.service.TarifService;
import com.urservices.epressing.service.dto.ProduitTarifDTO;
import com.urservices.epressing.domain.Produit;
import com.urservices.epressing.domain.Tarif;
import com.urservices.epressing.repository.ProduitRepository;
import com.urservices.epressing.repository.TarifRepository;
import com.urservices.epressing.repository.search.ProduitSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Produit.
 */
@Service
@Transactional
public class ProduitServiceImpl implements ProduitService{

    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);
    
    private final ProduitRepository produitRepository;

    private final TarifRepository tarifRepository;

    private final ProduitSearchRepository produitSearchRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository, ProduitSearchRepository produitSearchRepository, TarifRepository tarifRepository) {
        this.produitRepository = produitRepository;
        this.produitSearchRepository = produitSearchRepository;
        this.tarifRepository = tarifRepository;
    }

    /**
     * Save a produit.
     *
     * @param produit the entity to save
     * @return the persisted entity
     */
    @Override
    public Produit save(Produit produit) {
        log.debug("Request to save Produit : {}", produit);
        Produit result = produitRepository.save(produit);
        produitSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the produits.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produit> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        Page<Produit> result = produitRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get all the produits with their Tarif
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProduitTarifDTO> findAllWithTarif(Pageable pageable){
        log.debug("Request to get all Produits with their Tarif");
        List<Produit> produits = produitRepository.findAll();     
        log.debug("ProduitServiceImpl on a trouvé {} produits ", produits.size()); 
        List<ProduitTarifDTO> produitTarifDTOs = new ArrayList<>();
        for(Produit prod : produits){
            ProduitTarifDTO dto = new ProduitTarifDTO();
            List<Tarif> tarifs = new ArrayList<>();
            log.debug("ProduitServiceImpl produit trouvé: {} pour ID {} ", prod.getLibelle(), prod.getId());
            tarifs = tarifRepository.findByProductID(prod.getId());
            log.debug("ProduitServiceImpl le produit trouvé: {} contient: {}  tarifs", prod.getLibelle(), tarifs.size());
            dto.setProduit(prod);
            dto.setTarifs(tarifs);
            produitTarifDTOs.add(dto);
        }
        long total = produitTarifDTOs.size();
        return new PageImpl<ProduitTarifDTO>(produitTarifDTOs, pageable, total);
    }
    /**
     *  Get one produit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Produit findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        Produit produit = produitRepository.findOne(id);
        return produit;
    }

    /**
     *  Delete the  produit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.delete(id);
        produitSearchRepository.delete(id);
    }

    /**
     * Search for the produit corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produit> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Produits for query {}", query);
        Page<Produit> result = produitSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
