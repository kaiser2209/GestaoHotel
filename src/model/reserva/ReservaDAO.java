/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.reserva;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.apartamento.ApartamentoDAO;
import model.conexao.ConnectionFactory;
import model.entidades.Apartamento;
import model.entidades.Reserva;
import model.hospede.HospedeDAO;

/**
 *
 * @author guard
 */
public class ReservaDAO {
    private ApartamentoDAO aDao;
    private HospedeDAO hDao;
    
    public ReservaDAO() {
        aDao = new ApartamentoDAO();
        hDao = new HospedeDAO();
    }
    
    public void salvar(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reserva (id_quarto, id_cliente, data_entrada, "
                + "data_saida, observacoes, data_reserva, reserva_cancelada) "
                + "VALUE (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, reserva.getApartamento().getId());
        stmt.setInt(2, reserva.getHospede().getIdHospede());
        stmt.setString(3, reserva.getDataEntrada().toString());
        stmt.setString(4, reserva.getDataSaida().toString());
        stmt.setString(5, reserva.getObservacoes());
        stmt.setString(6, reserva.getDataReserva().toString());
        stmt.setBoolean(7, reserva.isReservaCancelada());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void editar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reserva set id_quarto = ?, id_cliente = ?, "
                + "data_entrada = ?, data_saida = ?, observacoes = ?, "
                + "data_reserva = ?, reserva_cancelada = ? where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, reserva.getApartamento().getId());
        stmt.setInt(2, reserva.getHospede().getIdHospede());
        stmt.setString(3, reserva.getDataEntrada().toString());
        stmt.setString(4, reserva.getDataSaida().toString());
        stmt.setString(5, reserva.getObservacoes());
        stmt.setString(6, reserva.getDataReserva().toString());
        stmt.setBoolean(7, reserva.isReservaCancelada());
        stmt.setInt(8, reserva.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void excluir(Reserva reserva) throws SQLException {
        String sql = "DELETE From reserva where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, reserva.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public ArrayList<Reserva> listar() throws SQLException {
        String sql = "SELECT * From reserva";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        
        ArrayList<Reserva> lista = new ArrayList<>();
        while(rs.next()) {
            Reserva r = new Reserva();
            r.setId(rs.getInt("id"));
            r.setHospede(hDao.buscarPeloId(rs.getInt("id_cliente")));
            r.setApartamento(aDao.buscarPeloId(rs.getInt("id_quarto")));
            r.setDataEntrada(LocalDate.parse(rs.getString("data_entrada")));
            r.setDataSaida(LocalDate.parse(rs.getString("data_saida")));
            r.setObservacoes(rs.getString("observacoes"));
            r.setDataReserva(LocalDateTime.parse(rs.getString("data_reserva")));
            r.setReservaCancelada(rs.getBoolean("reserva_cancelada"));
            
            lista.add(r);
        }
        
        rs.close();
        stmt.close();
        
        return lista;
    }
    
    public Reserva buscarReserva(Apartamento a, LocalDate dataEntrada,
            LocalDate dataSaida) throws SQLException {
        String sql = "SELECT * From reserva where id_quarto = ? and ("
                + "(data_entrada between ? and ?) or (data_saida between ?"
                + "and ?) or (? between data_entrada and data_saida))";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, a.getId());
        stmt.setString(2, dataEntrada.toString());
        stmt.setString(3, dataSaida.toString());
        stmt.setString(4, dataEntrada.toString());
        stmt.setString(5, dataSaida.toString());
        stmt.setString(6, dataEntrada.toString());
        
        ResultSet rs = stmt.executeQuery();
        
        Reserva r = null;
        if (rs.next()) {
            r = new Reserva();
            r.setId(rs.getInt("id"));
            r.setHospede(hDao.buscarPeloId(rs.getInt("id_cliente")));
            r.setApartamento(aDao.buscarPeloId(rs.getInt("id_quarto")));
            r.setDataEntrada(LocalDate.parse(rs.getString("data_entrada")));
            r.setDataSaida(LocalDate.parse(rs.getString("data_saida")));
            r.setObservacoes(rs.getString("observacoes"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            r.setDataReserva(LocalDateTime.parse(rs.getTimestamp("data_reserva").toString(), formatter));
            r.setReservaCancelada(rs.getBoolean("reserva_cancelada"));
        }
        
        rs.close();
        stmt.close();
        
        return r;
    }
    
    public ArrayList<Apartamento> buscarQuartosDisponiveis(LocalDate dataEntrada,
            LocalDate dataSaida) throws SQLException {
        ArrayList<Apartamento> quartos = aDao.listar();
        ArrayList<Apartamento> quartosDisponiveis = new ArrayList<>();
        
        for(Apartamento a : quartos) {
            Reserva r = buscarReserva(a, dataEntrada, dataSaida);
            if (r == null) {
                quartosDisponiveis.add(a);
            }
        }
        
        return quartosDisponiveis;
    }
} 
