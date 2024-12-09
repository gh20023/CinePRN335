package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class SalaCaracteristicaBean extends AbstractDataPersistence<SalaCaracteristica> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public SalaCaracteristicaBean() {
        super(SalaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<SalaCaracteristica> findByIdSala(final Integer idSala, int first, int last) {
        try{
            TypedQuery<SalaCaracteristica> q = em.createNamedQuery("SalaCaracteristica.findByIdSala", SalaCaracteristica.class);
            q.setParameter("idSalaCaracteristica", idSala);
            q.setFirstResult(first);
            q.setMaxResults(last);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public int countSala(final Integer idSala) {
        try {
            Long Longvar = Long.valueOf(idSala);
            TypedQuery<Long> q = em.createNamedQuery("SalaCaracteristica.countByIdPelicula", Long.class);
            q.setParameter("idSala", Longvar);
            return q.getSingleResult().intValue();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
}
