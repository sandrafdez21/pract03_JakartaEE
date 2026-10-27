package es.unican.ps.uchoteles.daoLayer;

import java.util.List;

import es.unican.ps.uchoteles.entities.Hotel;
import es.unican.ps.uchoteles.entities.Reserva;

public interface IReservasDAO {
    public Reserva crearReserva(Reserva reserva);
    public Reserva reserva(int id);
    public List<Reserva> reservas();
    public Boolean modificarReserva(Reserva reserva);
    public Reserva eliminarReserva(Reserva reserva);
    public List<Reserva> reservasPorHotel(Hotel hotel);
}
