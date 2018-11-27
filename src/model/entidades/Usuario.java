package model.entidades;

/**
 *
 * @author Charles
 */
public class Usuario extends Pessoa {
    private int idUsuario;
    private int funcao;

    public int getFuncao() {
        return funcao;
    }

    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
}
