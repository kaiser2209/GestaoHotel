package model.entidades;

/**
 *
 * @author Charles
 */
public class Usuario extends Pessoa {
    private int idUsuario;
    private Funcao funcao;

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
}
