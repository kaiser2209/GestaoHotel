/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hospedagem;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.entidades.Apartamento;
import model.entidades.Hospedagem;

/**
 *
 * @author guard
 */
public class HospedagemBO {
    private HospedagemDAO dao;
    
    public HospedagemBO() {
        dao = new HospedagemDAO();
    }
    
    public void salvar(Hospedagem h) throws SQLException {
        dao.salvar(h);
    }
    
    public void checkout(Hospedagem h) throws SQLException {
        dao.checkout(h);
    }
    
    public ArrayList<Apartamento> buscarQuartosDisponiveis(LocalDate dataEntrada)
            throws SQLException {
        return dao.buscarQuartosDisponiveis(dataEntrada);
    }
}
