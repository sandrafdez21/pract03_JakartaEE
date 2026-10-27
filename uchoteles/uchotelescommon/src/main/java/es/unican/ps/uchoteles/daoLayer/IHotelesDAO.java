package es.unican.ps.uchoteles.daoLayer;

import java.util.List;

import es.unican.ps.uchoteles.entities.Hotel;

public interface IHotelesDAO {
    public Hotel creaHotel(Hotel hotel);
    public Hotel hotel(String nombre);
    public List<Hotel> hotelesPorLocalidad(String Localidad);
    public List<Hotel> hoteles();
    public Boolean modificarHotel(Hotel hotel);
    public Hotel eliminarHotel(Hotel hotel);
}
