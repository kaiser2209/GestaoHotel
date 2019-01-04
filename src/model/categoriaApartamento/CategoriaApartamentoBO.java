/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.categoriaApartamento;

import exceptions.CategoriaApartamentoExistenteException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.entidades.CategoriaApartamento;

/**
 *
 * @author guard
 */
public class CategoriaApartamentoBO {
    private CategoriaApartamentoDAO dao;
    
    public CategoriaApartamentoBO() {
        dao = new CategoriaApartamentoDAO();
    }
    
    public void salvar(CategoriaApartamento c) throws SQLException, CategoriaApartamentoExistenteException {
        if (dao.buscarPeloCodigo(c.getCodigo()) == null) {
            dao.salvar(c);
        } else {
            throw new CategoriaApartamentoExistenteException();
        }
    }
    
    public void editar(CategoriaApartamento c) throws SQLException,
            CategoriaApartamentoExistenteException {
        CategoriaApartamento aux = dao.buscarPeloCodigo(c.getCodigo());
        
        if (aux == null || aux.getId() == c.getId()) {
            dao.editar(c);
        } else {
            throw new CategoriaApartamentoExistenteException();
        }
    }
    
    public void excluir(CategoriaApartamento c) throws SQLException {
        dao.excluir(c);
    }
    
    public ArrayList<CategoriaApartamento> listar() throws SQLException {
        return dao.listar();
    }
            
} 
