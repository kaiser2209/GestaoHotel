/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import model.entidades.Funcao;
import model.entidades.Usuario;

/**
 *
 * @author guard
 */
public class UsuarioBO {
    UsuarioDAO dao;
    
    public UsuarioBO() {
        dao = new UsuarioDAO();
    }
    
    public void salvar(Usuario u) throws SQLException {
        dao.salvar(u);
    }
    
    public ArrayList<Funcao> listarFuncaoCrescente() throws SQLException {
        return dao.listarFuncaoCrescente();
    }
}
