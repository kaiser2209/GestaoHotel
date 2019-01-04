package model.entidades;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import util.Mensagem;

/**
 *
 * @author Charles
 */
public class CategoriaApartamento {
    private int id;
    private String codigo;
    private String nomeCategoria;
    private String descricao;
    private float valorDiaria;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(float valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFormattedValorDiaria() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(getValorDiaria());
    }
    
    public void setValorDiaria(String valor) throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        this.valorDiaria = nf.parse(valor).floatValue();
    }
    
    @Override
    public String toString() {
        return this.codigo + " - " + this.nomeCategoria;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof CategoriaApartamento) {
            return this.id == ((CategoriaApartamento) o).getId();
        }
        
        return false;
    }
}
