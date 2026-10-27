package es.unican.ps.uchoteles.businessLayer;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.uchoteles.entities.Habitacion;
import es.unican.ps.uchoteles.entities.Hotel;
import es.unican.ps.uchoteles.entities.Reserva;

public interface IGestionReservasCliente {
    public List<Habitacion> consultarDisponibilidad(Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida);
    public Reserva consultarReserva(int id, String dni);
    public Reserva cancelarReserva(int id, String dni);
    public int reservar(Reserva reserva);
    public double bloquearHabitaciones(Reserva reserva);
}