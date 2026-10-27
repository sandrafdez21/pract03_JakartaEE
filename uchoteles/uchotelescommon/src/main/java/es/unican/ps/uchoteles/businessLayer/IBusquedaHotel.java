package es.unican.ps.uchoteles.businessLayer;

import java.util.List;

import es.unican.ps.uchoteles.entities.Hotel;

public interface IBusquedaHotel {
    public Hotel buscarHotelNombre(String nombreHotel);
    public List<Hotel> buscarHotelesLocalidad(String localidad);
}
