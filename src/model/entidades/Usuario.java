package model.entidades;

/**
 *
 * @author Charles
 */
public class Usuario extends Pessoa {
    private int funcao;

    public int getFuncao() {
        return funcao;
    }

    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }
}
