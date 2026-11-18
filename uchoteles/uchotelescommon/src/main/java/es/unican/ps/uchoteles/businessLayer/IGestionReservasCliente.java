package es.unican.ps.uchoteles.businessLayer;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.uchoteles.daoLayer.DataAccessException;
import es.unican.ps.uchoteles.entities.Habitacion;
import es.unican.ps.uchoteles.entities.Hotel;
import es.unican.ps.uchoteles.entities.Reserva;

public interface IGestionReservasCliente {

    
    @SuppressWarnings("serial")
	public static class FechasNoValidasException extends Exception {
	}

    public List<Habitacion> consultarDisponibilidad(Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida) throws DataAccessException, FechasNoValidasException;
    public Reserva consultarReserva(int id, String dni);
    public Reserva cancelarReserva(int id, String dni);
    public int reservar(Reserva reserva);
    public double bloquearHabitaciones(Reserva reserva) throws DataAccessException;
}