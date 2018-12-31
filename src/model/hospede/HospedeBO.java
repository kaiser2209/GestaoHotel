/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hospede;

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
}
