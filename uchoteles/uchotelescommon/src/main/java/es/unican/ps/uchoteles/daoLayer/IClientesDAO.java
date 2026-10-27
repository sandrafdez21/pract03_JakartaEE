package es.unican.ps.uchoteles.daoLayer;

import java.util.List;

import es.unican.ps.uchoteles.entities.Cliente;

public interface IClientesDAO {
    public Cliente crearCliente(Cliente cliente);
    public Cliente cliente(String dni);
    public List<Cliente> clientes();
    public Boolean modificarCliente(Cliente cliente);
    public Cliente eliminarCliente(Cliente cliente);
}
