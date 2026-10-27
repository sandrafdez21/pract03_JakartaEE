package es.unican.ps.uchoteles.entities;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class Hotel {
    
    private int id;

    private String nombre;
    private String direccion;
    private String localidad;

    private Categoria categoria;

    private List<Habitacion> habitaciones;

    private List<Reserva> reservas;

}