package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class FrmMenu implements Serializable {
    @Inject
    FacesContext facesContext;
    DefaultMenuModel model;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        // Registro para comprobar si init se llama
        System.out.println("Inicializando el menú...");

        DefaultSubMenu tipos = DefaultSubMenu.builder()
                .label("Tipos")
                .expanded(true)
                .build();

        DefaultSubMenu cineclasi = DefaultSubMenu.builder()
                .label("Cine")
                .expanded(true)
                .build();

        // Menu items
        DefaultMenuItem PeliculaItem = DefaultMenuItem.builder()
                .value("Pelicula")
                .ajax(true)
                .command("#{frmMenu.navegar('Pelicula.jsf')}") // Pago navigation logic
                .build();

        DefaultMenuItem SucursalItem = DefaultMenuItem.builder()
                .value("Sucursal")
                .ajax(true)
                .command("#{frmMenu.navegar('Sucursal.jsf')}") // Asiento navigation logic
                .build();

        DefaultMenuItem SalaItem = DefaultMenuItem.builder()
                .value("Sala")
                .ajax(true)
                .command("#{frmMenu.navegar('Sala.jsf')}") // Pelicula navigation logic
                .build();

        DefaultMenuItem ReservaItem = DefaultMenuItem.builder()
                .value("Reserva")
                .ajax(true)
                .command("#{frmMenu.navegar('Reserva.jsf')}") // Producto navigation logic
                .build();

        DefaultMenuItem ProductosItem = DefaultMenuItem.builder()
                .value("Comprar Productos")
                .ajax(true)
                .command("#{frmMenu.navegar('Productos.jsf')}")
                .build();

        // Menu items tipos
        DefaultMenuItem pagoTipo = DefaultMenuItem.builder()
                .value("Pago")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoPago.jsf')}") // Pago navigation logic
                .build();

        DefaultMenuItem asientoTipo = DefaultMenuItem.builder()
                .value("Asiento")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoAsiento.jsf')}") // Asiento navigation logic
                .build();

        DefaultMenuItem peliculaTipo = DefaultMenuItem.builder()
                .value("Película")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoPelicula.jsf')}") // Pelicula navigation logic
                .build();

        DefaultMenuItem productoTipo = DefaultMenuItem.builder()
                .value("Producto")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoProducto.jsf')}") // Producto navigation logic
                .build();

        DefaultMenuItem reservaTipo = DefaultMenuItem.builder()
                .value("Reserva")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoReserva.jsf')}") // Reserva navigation logic
                .build();

        DefaultMenuItem salaTipo = DefaultMenuItem.builder()
                .value("Sala")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoSala.jsf')}")
                .build();

        // Añadir al menu principal
        //Tipos
        tipos.getElements().add(pagoTipo);
        tipos.getElements().add(asientoTipo);
        tipos.getElements().add(peliculaTipo);
        tipos.getElements().add(productoTipo);
        tipos.getElements().add(reservaTipo);
        tipos.getElements().add(salaTipo);
        //Items
        cineclasi.getElements().add(PeliculaItem);
        cineclasi.getElements().add(SucursalItem);
        cineclasi.getElements().add(SalaItem);
        cineclasi.getElements().add(ReservaItem);
        cineclasi.getElements().add(ProductosItem);

        model.getElements().add(tipos);
        model.getElements().add(cineclasi);

        // Registro para comprobar la estructura del modelo
        System.out.println("Modelo de menú inicializado: " + model.getElements());
    }

    public void navegar(String formulario) {
        try {
            facesContext.getExternalContext().redirect(formulario);
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo navegar.");
            facesContext.addMessage(null, message);
            e.printStackTrace(); // Para depuración
        }
    }

    public DefaultMenuModel getModel() {
        return model;
    }
}
