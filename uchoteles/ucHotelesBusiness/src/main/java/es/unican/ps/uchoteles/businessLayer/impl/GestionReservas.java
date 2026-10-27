package es.unican.ps.uchoteles.businessLayer.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import es.unican.ps.uchoteles.businessLayer.IGestionReservasClienteLocal;
import es.unican.ps.uchoteles.businessLayer.IGestionReservasClienteRemote;
import es.unican.ps.uchoteles.businessLayer.IGestionReservasGerenteLocal;
import es.unican.ps.uchoteles.businessLayer.IGestionReservasGerenteRemote;
import es.unican.ps.uchoteles.daoLayer.IClientesDAO;
import es.unican.ps.uchoteles.daoLayer.IHotelesDAO;
import es.unican.ps.uchoteles.daoLayer.IReservasDAO;
import es.unican.ps.uchoteles.entities.Cliente;
import es.unican.ps.uchoteles.entities.Habitacion;
import es.unican.ps.uchoteles.entities.Hotel;
import es.unican.ps.uchoteles.entities.Reserva;
import es.unican.ps.uchoteles.entities.ReservaTipoHabitacion;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless 
public class GestionReservas implements IGestionReservasClienteLocal, IGestionReservasClienteRemote, IGestionReservasGerenteLocal, IGestionReservasGerenteRemote {

    @EJB
    private IReservasDAO reservasDAO;

    @EJB
    private IClientesDAO clientesDAO;

    @EJB
    private IHotelesDAO hotelesDAO;

    @Override
    public List<Habitacion> consultarDisponibilidad(Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida) {
        
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        List<Reserva> reservas = reservasDAO.reservasPorHotel(hotel);

        for (Habitacion h : hotel.getHabitaciones()) {
            
            int disponibles = h.getDisponibles();

            for (Reserva r : reservas) {

                if (r.getFechaEntrada().isBefore(fechaSalida) && r.getFechaSalida().isAfter(fechaEntrada)) {
                    
                    for (ReservaTipoHabitacion rth : r.getHabitacionesReservadas()) {
                        if (rth.getHabitacion().equals(h)) {
                            disponibles = disponibles - rth.getNumHabitaciones();
                        }
                    }
                }
            }

            if (disponibles > 0) {
                habitacionesDisponibles.add(h);
            }
        }

        if (habitacionesDisponibles.isEmpty()) {
            throw new IllegalArgumentException("No hay disponibilidad, pruebe otras fechas");
        }

        return habitacionesDisponibles;
    }

    @Override
    public Reserva consultarReserva(int id, String dni) {
        Reserva reserva = reservasDAO.reserva(id);

        if (reserva == null) {
            throw new IllegalArgumentException("No existe reserva");
        }
        
        if (!reserva.getCliente().getDni().equals(dni)) {
            throw new IllegalArgumentException("Reserva no accesible");
        }

        return reserva;
    }

    @Override
    public Reserva cancelarReserva(int id, String dni) {
        
        Reserva reserva = consultarReserva(id, dni);

        if (ChronoUnit.DAYS.between(LocalDate.now(), reserva.getFechaEntrada()) < 2) {
            throw new IllegalArgumentException("Límite de cancelación de reserva sobrepasado");
        }

        return reservasDAO.eliminarReserva(reserva);
        
    }

    @Override
    public double bloquearHabitaciones(Reserva reserva) {
        long nochesTotales = ChronoUnit.DAYS.between(reserva.getFechaEntrada(), reserva.getFechaSalida());
        double importeTotal = 0.0;
        
        List<Reserva> reservasHotel = reservasDAO.reservasPorHotel(reserva.getHotel());
        
        for (ReservaTipoHabitacion rth : reserva.getHabitacionesReservadas()) {
            
            Habitacion hab = rth.getHabitacion();
            int cantidadSolicitada = rth.getNumHabitaciones();
            
            int disponiblesReales = hab.getDisponibles();
            
            for (Reserva r : reservasHotel) {
                if (r.getFechaEntrada().isBefore(reserva.getFechaSalida()) && 
                    r.getFechaSalida().isAfter(reserva.getFechaEntrada())) {
                    
                    for (ReservaTipoHabitacion rthExistente : r.getHabitacionesReservadas()) {
                        if (rthExistente.getHabitacion().equals(hab)) {
                            disponiblesReales = disponiblesReales - rthExistente.getNumHabitaciones();
                        }
                    }
                }
            }
            
            if (disponiblesReales < cantidadSolicitada) {
                throw new IllegalArgumentException("Habitación de tipo " + hab.getTipo() + " no disponible");
            }
            
            importeTotal = importeTotal + (hab.getPrecioPorNoche() * cantidadSolicitada * nochesTotales);
        }
        
        // TODO BLOQUEAR HABITACIONES 10 MIN

        reserva.setImporte(importeTotal);
        
        return importeTotal;
    }

    @Override
    public int reservar(Reserva reserva) {
        Cliente cliente = reserva.getCliente();
        
        Cliente clienteExistente = clientesDAO.cliente(cliente.getDni());
        
        if (clienteExistente == null) {
            clientesDAO.crearCliente(cliente);
        } else {
            reserva.setCliente(clienteExistente);
        }
        
        Reserva reservaCreada = reservasDAO.crearReserva(reserva);
        
        return reservaCreada.getId();
    }

    

    @Override
    public List<Reserva> consultarReservasHotel(String nombreHotel) {
        return reservasDAO.reservasPorHotel(hotelesDAO.hotel(nombreHotel));
    }
    
}
