/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entidades;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author guard
 */
public class Hospedagem {
    private int id;
    private ArrayList<Hospede> hospedes;
    private Apartamento apartamento;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String procedencia;
    private String destino;
    private float diaria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Hospede> getHospedes() {
        return hospedes;
    }

    public void setHospedes(ArrayList<Hospede> hospedes) {
        this.hospedes = hospedes;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    } 

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    public String getNomeHospede() {
        return this.hospedes.get(0).getNome();
    }
    
    public String getNumeroApartamento() {
        return this.apartamento.getNumero();
    }
    
    public String getDataEntradaFormatada() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataEntrada.format(dtf);
    }

    public float getDiaria() {
        return diaria;
    }

    public void setDiaria(float diaria) {
        this.diaria = diaria;
    }
    
    public String getDiariaFormatada() {
        DecimalFormat df = new DecimalFormat("R$ #0.00");
        return df.format(this.diaria);
    }
    
    public String getNomeCategoria() {
        return this.apartamento.getNomeCategoria();
    }
    
    public String getSituacao() {
        if (this.dataSaida == null) {
            return "Ocupado";
        } else {
            return "Livre";
        }
    }
}
