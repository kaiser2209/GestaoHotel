/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hospede;

import exceptions.HospedeExistenteException;
import java.util.ArrayList;
import model.entidades.Hospede;
import java.sql.SQLException;
/**
 *
 * @author guard
 */
public class HospedeBO {
    private HospedeDAO dao;
    
    public HospedeBO() {
        dao = new HospedeDAO();
    }
    
    public ArrayList<Hospede> listar() throws SQLException {
        return dao.listar();
    }
    
    public void salvar(Hospede h) throws SQLException, HospedeExistenteException {
        if (dao.buscarPeloCpf(h.getCpf()).getIdHospede() > 0) {
            throw new HospedeExistenteException();
        } else {
            dao.salvar(h);
        }
    }
    
    public void editar(Hospede h) throws SQLException, HospedeExistenteException {
        Hospede aux = dao.buscarPeloCpf(h.getCpf());
        
        if ((aux.getIdHospede() > 0) && (aux.getIdHospede() != h.getIdHospede())) {
            throw new HospedeExistenteException();
        } else {
            dao.editar(h);
        }
    }
    
    public void excluir(Hospede h) throws SQLException {
        dao.excluir(h);
    }
    
    public ArrayList<Hospede> listarPorCpf(String cpf) throws SQLException {
        return dao.filtrarPorCpf(cpf);
    }
    
    public ArrayList<Hospede> listarPorNome(String nome) throws SQLException {
        return dao.filtrarPorNome(nome);
    }
}
