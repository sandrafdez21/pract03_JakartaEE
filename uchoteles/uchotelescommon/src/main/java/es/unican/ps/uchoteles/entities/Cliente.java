package es.unican.ps.uchoteles.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class Cliente {

    private String dni;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;

}