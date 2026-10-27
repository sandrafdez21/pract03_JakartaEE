package es.unican.ps.uchoteles.businessLayer.impl;

import java.util.List;

import es.unican.ps.uchoteles.businessLayer.IBusquedaHotelLocal;
import es.unican.ps.uchoteles.businessLayer.IBusquedaHotelRemote;
import es.unican.ps.uchoteles.businessLayer.IGestionHotelLocal;
import es.unican.ps.uchoteles.businessLayer.IGestionHotelRemote;
import es.unican.ps.uchoteles.daoLayer.IHotelesDAO;
import es.unican.ps.uchoteles.entities.Habitacion;
import es.unican.ps.uchoteles.entities.Hotel;
import es.unican.ps.uchoteles.entities.TipoHabitacion;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class GestionHotel implements IBusquedaHotelLocal, IBusquedaHotelRemote, IGestionHotelLocal, IGestionHotelRemote {

    @EJB
    private IHotelesDAO hotelesDAO;

    @Override
    public Boolean anhadirTipoHabitacion(String nombreHotel, Habitacion habitacion) {
        
        Hotel hotel = buscarHotelNombre(nombreHotel);
        hotel.getHabitaciones().add(habitacion);

        return hotelesDAO.modificarHotel(hotel);
        
    }

    @Override
    public Hotel modificarHabitaciones(String nombreHotel, List<Habitacion> habitaciones) {
        Hotel hotel = buscarHotelNombre(nombreHotel);
        
        hotel.setHabitaciones(habitaciones);
        
        hotelesDAO.modificarHotel(hotel);
        
        return hotel;
    }

    @Override
    public Boolean eliminarTipoHabitacion(String nombreHotel, TipoHabitacion tipoHabitacion) {
        Hotel hotel = buscarHotelNombre(nombreHotel);
        
        Habitacion habitacionAEliminar = null;
        
        for (Habitacion hab : hotel.getHabitaciones()) {
            if (hab.getTipo() == tipoHabitacion) {
                habitacionAEliminar = hab;
                break;
            }
        }

        if (habitacionAEliminar == null) {
            return false;
        }
        
        hotel.getHabitaciones().remove(habitacionAEliminar);
        
        hotelesDAO.modificarHotel(hotel);
        
        return true; 
    }

    @Override
    public Hotel buscarHotelNombre(String nombreHotel) {
        if (nombreHotel == null || nombreHotel.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del hotel no puede estar vacío");
        }

        return hotelesDAO.hotel(nombreHotel);
    }

    @Override
    public List<Hotel> buscarHotelesLocalidad(String localidad) {
        if (localidad == null || localidad.trim().isEmpty()) {
        throw new IllegalArgumentException("La localidad no puede estar vacía");
    }

    return hotelesDAO.hotelesPorLocalidad(localidad);
    }
    
}
