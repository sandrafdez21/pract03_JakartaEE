package es.unican.ps.uchoteles.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class DatosPago {

    private String numTarjeta;
    private int cvc;
    private int mesCaducidad;
    private int anhoCaducidad;

    private TipoTarjeta tipo;

}