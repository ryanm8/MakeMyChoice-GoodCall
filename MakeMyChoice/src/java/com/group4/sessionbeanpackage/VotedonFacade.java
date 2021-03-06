/*
 * Created by Brian Green on 2014.11.18  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.group4.sessionbeanpackage;

import com.group4.entitypackage.Votedon;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian
 */
@Stateless
public class VotedonFacade extends AbstractFacade<Votedon> {
    @PersistenceContext(unitName = "MakeMyChoicePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotedonFacade() {
        super(Votedon.class);
    }
    
}
