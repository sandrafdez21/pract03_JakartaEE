package es.unican.ps.uchoteles.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class Habitacion {
    
    private int id;

    private double precioPorNoche;
    private int disponibles;

    private TipoHabitacion tipo;

    public Habitacion (double precioPorNoche, int disponibles, TipoHabitacion tipo) {
        this.precioPorNoche = precioPorNoche;
        this.disponibles = disponibles;
        this.tipo = tipo;
    }

}