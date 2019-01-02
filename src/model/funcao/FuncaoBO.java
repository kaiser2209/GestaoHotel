/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.funcao;

import exceptions.FuncaoExistenteException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.entidades.Funcao;

/**
 *
 * @author guard
 */
public class FuncaoBO {
    private FuncaoDAO dao;
    public FuncaoBO() {
        dao = new FuncaoDAO();
    }
    
    public void salvar(Funcao f) throws SQLException, FuncaoExistenteException {
        if ((dao.buscarPeloNome(f.getNomeFuncao()) == null)) { 
            dao.salvar(f);
        } else {
            throw new FuncaoExistenteException();
        }
    }
    
    public void editar(Funcao f) throws SQLException, FuncaoExistenteException {
        Funcao aux = dao.buscarPeloNome(f.getNomeFuncao());
        if ((aux == null) || aux.getId() == f.getId()) {
            dao.editar(f);
        } else {
            throw new FuncaoExistenteException();
        }
    }
    
    public void excluir(Funcao f) throws SQLException {
        dao.excluir(f);
    }
    
    public ArrayList<Funcao> listar() throws SQLException {
        return dao.listar();
    }
}
