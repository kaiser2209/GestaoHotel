/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author guard
 */
public class Reserva {
    private int id;
    private Hospede hospede;
    private Apartamento apartamento;
    private LocalDateTime dataReserva;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String observacoes;
    private boolean reservaCancelada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isReservaCancelada() {
        return reservaCancelada;
    }

    public void setReservaCancelada(boolean reservaCancelada) {
        this.reservaCancelada = reservaCancelada;
    }
    
    public String getNumeroApartamento() {
        return this.apartamento.getNumero();
    }
    
    public String getNomeHospede() {
        return this.hospede.getNome();
    }
    
    public String getDataEntradaFormatada() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataEntrada.format(dtf);
    }
}
