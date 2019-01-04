package model.entidades;

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
    
    public float getDiaria() {
        return this.categoria.getValorDiaria();
    }
    
    public String getNomeCategoria() {
        return this.categoria.getNomeCategoria();
    }
}
