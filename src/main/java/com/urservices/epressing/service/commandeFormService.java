/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.urservices.epressing.service;

import com.urservices.epressing.domain.Commande;
import com.urservices.epressing.domain.CommandeForm;

/**
 *
 * @author sando
 */
public interface commandeFormService
{

    CommandeForm save(CommandeForm commandeForm);

    CommandeForm findAll(Commande commande);

    void delete(Long id);
}
