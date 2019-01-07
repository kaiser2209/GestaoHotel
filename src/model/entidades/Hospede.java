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
public class Hospede extends Pessoa {
    private int idHospede;
    private String preferencia;

    public int getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(int idHospede) {
        this.idHospede = idHospede;
    }

    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    @Override
    public boolean equals(Object outro) {
        if (outro instanceof Hospede) {
            return this.getCpf().equals(((Hospede) outro).getCpf());
        }
        
        return false;
    }

    
}
