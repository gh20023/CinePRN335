package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class ProductoBean extends AbstractDataPersistence<Producto> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public ProductoBean() {
        super(Producto.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Producto> findProductosByIdTP(Integer idTipoProducto){
        return em.createQuery("SELECT p FROM Producto p WHERE p.idTipoProducto.idTipoProducto = :idTipoProducto", Producto.class)
                .setParameter("idTipoProducto", idTipoProducto)
                .getResultList();
    }
}
