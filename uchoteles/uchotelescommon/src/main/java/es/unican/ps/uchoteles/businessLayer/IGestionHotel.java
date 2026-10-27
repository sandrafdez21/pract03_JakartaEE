package es.unican.ps.uchoteles.businessLayer;

import java.util.List;

import es.unican.ps.uchoteles.entities.Habitacion;
import es.unican.ps.uchoteles.entities.Hotel;
import es.unican.ps.uchoteles.entities.TipoHabitacion;

public interface IGestionHotel {
    public Boolean anhadirTipoHabitacion(String nombreHotel, Habitacion habitacion);
    public Hotel modificarHabitaciones(String nombreHotel, List<Habitacion> habitaciones);
    public Boolean eliminarTipoHabitacion(String nombreHotel, TipoHabitacion tipoHabitacion);
}
