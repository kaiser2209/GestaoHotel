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
public class PagamentoHospedagem {
    private int id;
    private Hospedagem hospedagem;
    private float totalValorDiaria;
    private float totalDesconto;
    private float totalAcrescimo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hospedagem getHospedagem() {
        return hospedagem;
    }

    public void setHospedagem(Hospedagem hospedagem) {
        this.hospedagem = hospedagem;
    }

    public float getTotalValorDiaria() {
        return totalValorDiaria;
    }

    public void setTotalValorDiaria(float totalValorDiaria) {
        this.totalValorDiaria = totalValorDiaria;
    }

    public float getTotalDesconto() {
        return totalDesconto;
    }

    public void setTotalDesconto(float totalDesconto) {
        this.totalDesconto = totalDesconto;
    }

    public float getTotalAcrescimo() {
        return totalAcrescimo;
    }

    public void setTotalAcrescimo(float totalAcrescimo) {
        this.totalAcrescimo = totalAcrescimo;
    }
    
    
}
