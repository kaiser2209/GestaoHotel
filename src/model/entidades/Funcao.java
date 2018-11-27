/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entidades;

/**
 *
 * @author guard
 */
public class Funcao {
    private int id;
    private String nomeFuncao;
    private String descricaoFuncao;
    private short nivelAcesso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeFuncao() {
        return nomeFuncao;
    }

    public void setNomeFuncao(String nomeFuncao) {
        this.nomeFuncao = nomeFuncao;
    }

    public String getDescricaoFuncao() {
        return descricaoFuncao;
    }

    public void setDescricaoFuncao(String descricaoFuncao) {
        this.descricaoFuncao = descricaoFuncao;
    }

    public short getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(short nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
    
    
}
