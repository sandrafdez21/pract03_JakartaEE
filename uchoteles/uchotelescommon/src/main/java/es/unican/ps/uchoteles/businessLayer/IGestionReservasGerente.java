package es.unican.ps.uchoteles.businessLayer;

import java.util.List;

import es.unican.ps.uchoteles.entities.Reserva;

public interface IGestionReservasGerente {
    
    public List<Reserva> consultarReservasHotel(String nombreHotel);

}
