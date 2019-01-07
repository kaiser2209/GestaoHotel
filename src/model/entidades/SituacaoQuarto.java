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
public class SituacaoQuarto {
    private Apartamento apartamento;
    private String situacao;

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    public String getNumeroApartamento() {
        return this.apartamento.getNumero();
    }
    
    public String getNomeCategoria() {
        return this.apartamento.getNomeCategoria();
    }
}
