/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hospedagem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.apartamento.ApartamentoDAO;
import model.conexao.ConnectionFactory;
import model.entidades.Apartamento;
import model.entidades.Hospedagem;
import model.entidades.Hospede;
import model.hospede.HospedeDAO;

/**
 *
 * @author guard
 */
public class HospedagemDAO {
    private ApartamentoDAO aDao;
    private HospedeDAO hDao;
    
    public HospedagemDAO() {
        aDao = new ApartamentoDAO();
        hDao = new HospedeDAO();
    }
    
    public void salvar(Hospedagem h) throws SQLException {
        String sql = "INSERT INTO hospedagem (id_quarto, data_entrada, "
                + "procedencia, destino, valor_diaria) VALUE (?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, h.getApartamento().getId());
        stmt.setString(2, h.getDataEntrada().toString());
        stmt.setString(3, h.getProcedencia());
        stmt.setString(4, h.getDestino());
        stmt.setFloat(5, h.getDiaria());
        
        stmt.executeUpdate();
        
        stmt.close();
        
        salvarHospedes(ultimoId(), h.getHospedes());
    }
    
    public void salvarHospedes(int id, ArrayList<Hospede> hospedes) throws SQLException {
        String sql = "INSERT INTO cliente_hospedagem (id_hospedagem, id_cliente) "
                + "VALUE (?, ?)";
        
        for (Hospede h : hospedes) {
            PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, h.getIdHospede());
            
            stmt.executeUpdate();
            
            stmt.close();
        }
        
    }
    
    public int ultimoId() throws SQLException {
        String sql = "SELECT id From hospedagem Order By id Desc Limit 1";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        
        rs.close();
        stmt.close();
        
        return id;
    }
    
    public void checkout(Hospedagem h) throws SQLException {
        String sql = "UPDATE hospedagem set data_saida = ? where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, h.getDataSaida().toString());
        stmt.setInt(2, h.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public ArrayList<Hospedagem> listar(LocalDate hoje) throws SQLException {
        String sql = "SELECT * From hospedagem where data_saida IS null AND "
                + "data_entrada < ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, hoje.toString());
        
        ArrayList<Hospedagem> lista = new ArrayList<>();
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            Hospedagem h = new Hospedagem();
            h.setId(rs.getInt("id"));
            h.setApartamento(aDao.buscarPeloId(rs.getInt("id_quarto")));
            h.setDataEntrada(LocalDate.parse(rs.getString("data_entrada")));
            h.setProcedencia(rs.getString("procedencia"));
            h.setDestino(rs.getString("destino"));
            h.setHospedes(listarHospedes(h.getId()));
            h.setDiaria(rs.getFloat("valor_diaria"));
            
            lista.add(h);
        }
        
        rs.close();
        stmt.close();
        
        return lista;
    }
    
    public ArrayList<Hospede> listarHospedes(int id) throws SQLException {
        String sql = "SELECT * From cliente_hospedagem where id_hospedagem = ? "
                + "Order By id";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, id);
        
        ArrayList<Hospede> lista = new ArrayList<>();
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            Hospede h;
            h = hDao.buscarPeloId(rs.getInt("id_cliente"));
            
            lista.add(h);
        }
        
        rs.close();
        stmt.close();
        
        return lista;
    }
    
    public Hospedagem buscarHospedagem(Apartamento a, LocalDate dataEntrada)
            throws SQLException {
        
        String sql = "SELECT * From hospedagem where id_quarto = ? and "
                + "data_saida is null and data_entrada <= ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, a.getId());
        stmt.setString(2, dataEntrada.toString());
        
        ResultSet rs = stmt.executeQuery();
        
        Hospedagem h = null;
        if (rs.next()) {
            h = new Hospedagem();
            h.setId(rs.getInt("id"));
            h.setApartamento(aDao.buscarPeloId(rs.getInt("id_quarto")));
            h.setDataEntrada(LocalDate.parse(rs.getString("data_entrada")));
            h.setProcedencia(rs.getString("procedencia"));
            h.setDestino(rs.getString("destino"));
            h.setDiaria(rs.getFloat("valor_diaria"));
        }
        
        rs.close();
        stmt.close();
        
        return h;
    }
    
    public ArrayList<Apartamento> buscarQuartosDisponiveis(LocalDate dataEntrada)
            throws SQLException {
        ArrayList<Apartamento> quartos = aDao.listar();
        ArrayList<Apartamento> quartosDisponiveis = new ArrayList<>();
        
        for(Apartamento a : quartos) {
            Hospedagem r = buscarHospedagem(a, dataEntrada);
            if (r == null) {
                quartosDisponiveis.add(a);
            }
        }
        
        return quartosDisponiveis;
    }

    
}
