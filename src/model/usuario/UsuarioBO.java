/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.usuario;

import exceptions.UsuarioExistenteException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.entidades.Funcao;
import model.entidades.Usuario;
import model.funcao.FuncaoDAO;
import util.Mensagem;

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
    
    public ObservableList<Funcao> listarFuncaoCrescente() throws SQLException {
        return dao.listarFuncaoCrescente();
    }
    
    public void excluir(Usuario u) throws SQLException {
        dao.excluir(u);
    }
    
    public ArrayList<Usuario> listar() throws SQLException {
        return dao.listar();
    }
    
    public void editar(Usuario u) throws SQLException, UsuarioExistenteException {
        Usuario aux = dao.buscarPeloCpf(u.getCpf());
        
        if (aux == null || aux.getIdUsuario() == u.getIdUsuario()) {
            dao.editar(u);
        } else {
            throw new UsuarioExistenteException();
        }
    }
    
    public ArrayList<Usuario> filtrarPeloCpf(String cpf) throws SQLException {
        return dao.filtrarPeloCpf(cpf);
    }
    
    public ArrayList<Usuario> filtrarPeloNome(String nome) throws SQLException {
        return dao.filtrarPorNome(nome);
    }
    
    public Usuario buscarPeloCpf(String cpf) throws SQLException {
        return dao.buscarPeloCpf(cpf);
    }
    
    public void salvarSenha(Usuario u, String senha) throws SQLException {
        dao.salvarSenha(u, senha);
    }
    
    public Usuario login(String cpf, String senha) throws SQLException {
        return dao.login(cpf, senha);
    }
}
