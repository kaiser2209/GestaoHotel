package model.entidades;

/**
 *
 * @author Charles
 */
public class Apartamento {
    private int id;
    private int numero;
    private CategoriaApartamento categoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public CategoriaApartamento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaApartamento categoria) {
        this.categoria = categoria;
    }
}
