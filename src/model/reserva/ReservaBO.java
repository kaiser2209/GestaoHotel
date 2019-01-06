/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.reserva;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.entidades.Apartamento;
import model.entidades.Reserva;

/**
 *
 * @author guard
 */
public class ReservaBO {
    private ReservaDAO dao;
        
    public ReservaBO() {
        dao = new ReservaDAO();
    }
    
    public void salvar(Reserva reserva) throws SQLException {
        dao.salvar(reserva);
    }
    
    public void editar(Reserva reserva) throws SQLException {
        dao.editar(reserva);
    }
    
    public void excluir(Reserva reserva) throws SQLException {
        dao.excluir(reserva);
    }
    
    public ArrayList<Reserva> listar() throws SQLException {
        return dao.listar();
    }
    
    public Reserva buscaReserva(Apartamento a, LocalDate dataEntrada,
            LocalDate dataSaida) throws SQLException {
        return dao.buscarReserva(a, dataEntrada, dataSaida);
    }
    
    public ArrayList<Apartamento> buscarQuartosDisponiveis(LocalDate dataEntrada,
            LocalDate dataSaida) throws SQLException {
        return dao.buscarQuartosDisponiveis(dataEntrada, dataSaida);
    }
}
