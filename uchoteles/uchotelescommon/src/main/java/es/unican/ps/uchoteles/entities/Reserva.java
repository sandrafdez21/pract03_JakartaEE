package es.unican.ps.uchoteles.entities;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class Reserva {
    private int id;

    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    private double importe;

    private Hotel hotel;

    private Cliente cliente;

    private DatosPago datosPago;

    private List<ReservaTipoHabitacion> habitacionesReservadas;
}