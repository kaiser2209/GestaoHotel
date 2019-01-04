/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apartamento;

import exceptions.ApartamentoExistenteException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.entidades.Apartamento;
import model.entidades.CategoriaApartamento;

/**
 *
 * @author guard
 */
public class ApartamentoBO {
    ApartamentoDAO dao;
    
    public ApartamentoBO() {
        dao = new ApartamentoDAO();
    }
    
    public void salvar(Apartamento a) throws SQLException,
            ApartamentoExistenteException {
        if(dao.buscarPeloNumero(a.getNumero()) == null) {
            dao.salvar(a);
        } else {
            throw new ApartamentoExistenteException();
        }
    }
    
    public void editar(Apartamento a) throws SQLException,
            ApartamentoExistenteException {
        Apartamento aux = dao.buscarPeloNumero(a.getNumero());
        if (aux == null || aux.getId() == a.getId()) {
            dao.editar(a);
        } else {
            throw new ApartamentoExistenteException();
        }
    }
    
    public void excluir(Apartamento a) throws SQLException {
        dao.excluir(a);
    }
    
    public ArrayList<Apartamento> listar() throws SQLException {
        return dao.listar();
    }
    
    public ArrayList<Apartamento> filtrarPeloNumero(String numero) throws SQLException {
        return dao.filtrarPeloNumero(numero);
    }
    
    public ObservableList<CategoriaApartamento> listarCategoriaCrescente() throws
            SQLException {
        return dao.listarCategoriaCrescente();
    }
    
    public Apartamento buscarPeloNumero(String numero) throws SQLException {
        return dao.buscarPeloNumero(numero);
    }
}
