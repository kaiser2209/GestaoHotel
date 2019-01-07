package model.entidades;

import java.text.DecimalFormat;

/**
 *
 * @author Charles
 */
public class Apartamento {
    private int id;
    private String numero;
    private int ramal;
    private CategoriaApartamento categoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public CategoriaApartamento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaApartamento categoria) {
        this.categoria = categoria;
    }

    public int getRamal() {
        return ramal;
    }

    public void setRamal(int ramal) {
        this.ramal = ramal;
    }
    
    public String getDiaria() {
        DecimalFormat df = new DecimalFormat("R$ #0.00");
        return df.format(this.categoria.getValorDiaria());
    }
    
    public String getNomeCategoria() {
        return this.categoria.getNomeCategoria();
    }
    
    @Override
    public boolean equals(Object outro) {
        if (outro instanceof Apartamento) {
            return this.id == ((Apartamento) outro).getId();
        }
        
        return false;
    }
}
