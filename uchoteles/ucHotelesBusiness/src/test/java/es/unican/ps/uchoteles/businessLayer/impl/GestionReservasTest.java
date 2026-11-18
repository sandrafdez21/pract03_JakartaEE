package es.unican.ps.uchoteles.businessLayer.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.unican.ps.uchoteles.businessLayer.IGestionReservasCliente.FechasNoValidasException;
import es.unican.ps.uchoteles.daoLayer.DataAccessException;
import es.unican.ps.uchoteles.daoLayer.IReservasDAO;
import es.unican.ps.uchoteles.entities.Categoria;
import es.unican.ps.uchoteles.entities.Habitacion;
import es.unican.ps.uchoteles.entities.Hotel;
import es.unican.ps.uchoteles.entities.Reserva;
import es.unican.ps.uchoteles.entities.ReservaTipoHabitacion;
import es.unican.ps.uchoteles.entities.TipoHabitacion;

public class GestionReservasTest {
    
    @Mock
    private IReservasDAO reservasDAO;

    private GestionReservas sut;

    private Hotel hotel1;
    private List<Habitacion> habitaciones;

    private Habitacion habitacionIndividual;
    private Habitacion habitacionDoble;


    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        sut = new GestionReservas(reservasDAO);

        hotel1 = new Hotel("hotel1", "dir", "Santander", Categoria._4_STARS);

        habitaciones = new ArrayList<>();
        habitacionDoble = new Habitacion(100, 5, TipoHabitacion.DOBLE);
        habitacionIndividual = new Habitacion(50, 2, TipoHabitacion.INDIVIDUAL);
        habitaciones.add(habitacionDoble);
        habitaciones.add(habitacionIndividual);

        hotel1.setHabitaciones(habitaciones);
    }

    /**
     * UGS.1a: Consulta con disponibilidad parcial
     */
    @Test
    public void testConsultarDisponibilidadParcial() throws FechasNoValidasException, DataAccessException {
        LocalDate fechaEntrada = LocalDate.of(2026, 11, 22);
        LocalDate fechaSalida = LocalDate.of(2026, 11, 25);

        // Creo una reserva con los datos necesarios solo
        Reserva reserva1 = new Reserva();
        reserva1.setFechaEntrada(LocalDate.of(2026, 11, 20));
        reserva1.setFechaSalida(LocalDate.of(2026, 11, 24));
        List<ReservaTipoHabitacion> reservasTipo= new ArrayList<>();
        reservasTipo.add(new ReservaTipoHabitacion(2, habitacionDoble));
        reservasTipo.add(new ReservaTipoHabitacion(2, habitacionIndividual));
        reserva1.setHabitacionesReservadas(reservasTipo);

        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva1);

        // Preparo el mock
        when(reservasDAO.reservasPorHotel(hotel1)).thenReturn(reservas);

        List<Habitacion> habitaciones = sut.consultarDisponibilidad(hotel1, fechaEntrada, fechaSalida);

        assertEquals(1, habitaciones.size());
        assertEquals(TipoHabitacion.DOBLE, habitaciones.get(0).getTipo());
        assertEquals(3, habitaciones.get(0).getDisponibles());

        verify(reservasDAO).reservasPorHotel(hotel1);
    }

    /**
     * UGS.1b: Consulta con disponibilidad total (reservas existentes fuera del rango de fechas)
     */
    @Test
    public void testConsultarDisponibilidadTotal() throws FechasNoValidasException, DataAccessException {
        LocalDate fechaEntrada = LocalDate.of(2026, 11, 22);
        LocalDate fechaSalida = LocalDate.of(2026, 11, 25);

        // Creo una reserva con los datos necesarios solo
        Reserva reserva1 = new Reserva();
        reserva1.setFechaEntrada(LocalDate.of(2026, 11, 25));
        reserva1.setFechaSalida(LocalDate.of(2026, 11, 28));
        List<ReservaTipoHabitacion> reservasTipo= new ArrayList<>();
        reservasTipo.add(new ReservaTipoHabitacion(5, habitacionDoble));
        reservasTipo.add(new ReservaTipoHabitacion(2, habitacionIndividual));
        reserva1.setHabitacionesReservadas(reservasTipo);

        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva1);

        // Preparo el mock
        when(reservasDAO.reservasPorHotel(hotel1)).thenReturn(reservas);

        List<Habitacion> habitaciones = sut.consultarDisponibilidad(hotel1, fechaEntrada, fechaSalida);

        assertEquals(2, habitaciones.size());
        assertEquals(TipoHabitacion.DOBLE, habitaciones.get(0).getTipo());
        assertEquals(5, habitaciones.get(0).getDisponibles());
        assertEquals(TipoHabitacion.INDIVIDUAL, habitaciones.get(1).getTipo());
        assertEquals(2, habitaciones.get(1).getDisponibles());

        verify(reservasDAO).reservasPorHotel(hotel1);
    }

    /**
     * UGS.1c: Sin disponibilidad para las fechas indicadas
     */
    @Test
    public void testConsultarDisponibilidadSinDisponibilidad() throws FechasNoValidasException, DataAccessException {
        LocalDate fechaEntrada = LocalDate.of(2026, 11, 22);
        LocalDate fechaSalida = LocalDate.of(2026, 11, 25);

        // Creo una reserva con los datos necesarios solo
        Reserva reserva1 = new Reserva();
        reserva1.setFechaEntrada(LocalDate.of(2026, 11, 21));
        reserva1.setFechaSalida(LocalDate.of(2026, 11, 26));
        List<ReservaTipoHabitacion> reservasTipo= new ArrayList<>();
        reservasTipo.add(new ReservaTipoHabitacion(5, habitacionDoble));
        reservasTipo.add(new ReservaTipoHabitacion(2, habitacionIndividual));
        reserva1.setHabitacionesReservadas(reservasTipo);

        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva1);

        // Preparo el mock
        when(reservasDAO.reservasPorHotel(hotel1)).thenReturn(reservas);

        List<Habitacion> habitaciones = sut.consultarDisponibilidad(hotel1, fechaEntrada, fechaSalida);

        assertEquals(0, habitaciones.size());

        verify(reservasDAO).reservasPorHotel(hotel1);
    }

    /**
     * UGS.1d: Consulta con rango de fechas inválido
     * @throws DataAccessException 
     */
    @Test
    public void testConsultarDisponibilidadFechasInvalidas() throws DataAccessException {
        LocalDate fechaEntrada = LocalDate.of(2026, 11, 25);
        LocalDate fechaSalida = LocalDate.of(2026, 11, 22);

        assertThrows(FechasNoValidasException.class, () -> {
            sut.consultarDisponibilidad(hotel1, fechaEntrada, fechaSalida);
        });

        verify(reservasDAO, times(0)).reservasPorHotel(hotel1);
    }

    /**
     * UGS.1e: Error en acceso a datos en el DAO
     */
    @Test
    public void testConsultarDisponibilidadErrorAccesoDatos() throws DataAccessException {
        LocalDate fechaEntrada = LocalDate.of(2026, 11, 22);
        LocalDate fechaSalida = LocalDate.of(2026, 11, 25);

        when(reservasDAO.reservasPorHotel(hotel1)).thenThrow(new DataAccessException());

        assertThrows(DataAccessException.class, () -> {
            sut.consultarDisponibilidad(hotel1, fechaEntrada, fechaSalida);
        });
    }

}
