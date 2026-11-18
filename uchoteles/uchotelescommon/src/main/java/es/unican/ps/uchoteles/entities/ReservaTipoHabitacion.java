package es.unican.ps.uchoteles.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class ReservaTipoHabitacion {

    private int numHabitaciones;

    private Habitacion habitacion;

    public ReservaTipoHabitacion (int numHabitaciones, Habitacion habitacion) {
        this.numHabitaciones = numHabitaciones;
        this.habitacion = habitacion;
    }
    
}