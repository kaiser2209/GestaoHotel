/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.conexao.ConnectionFactory;
import model.entidades.Funcao;
import model.entidades.Usuario;
import util.Mensagem;

/**
 *
 * @author guard
 */
public class UsuarioDAO {
    public void salvar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, end_rua, end_numero, "
                + "end_bairro, end_cidade, end_estado, end_cep, end_pais, "
                + "data_nascimento, rg, cpf, email, telefone, celular, "
                + "data_cadastro, funcao, end_complemento) VALUE "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, u.getNome());
        stmt.setString(2, u.getEndRua());
        stmt.setString(3, u.getEndNumero());
        stmt.setString(4, u.getEndBairro());
        stmt.setString(5, u.getEndCidade());
        stmt.setString(6, u.getEndEstado());
        stmt.setString(7, u.getEndCep());
        stmt.setString(8, u.getEndPais());
        stmt.setString(9, u.getDataNascimento().toString());
        stmt.setString(10, u.getRg());
        stmt.setString(11, u.getCpf());
        stmt.setString(12, u.getEmail());
        stmt.setString(13, u.getTelefone());
        stmt.setString(14, u.getCelular());
        stmt.setString(15, u.getDataCadastro().toString());
        stmt.setInt(16, u.getFuncao().getId());
        stmt.setString(17, u.getEndComplemento());
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void editar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios set nome = ?, end_rua = ?, end_numero = ?, "
                + "end_complemento = ?, end_bairro = ?, end_cidade = ?, "
                + "end_estado = ?, end_cep = ?, end_pais = ?, "
                + "data_nascimento = ?, rg = ?, cpf = ?, email = ?, "
                + "telefone = ?, celular = ?, funcao = ? "
                + "where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, u.getNome());
        stmt.setString(2, u.getEndRua());
        stmt.setString(3, u.getEndNumero());
        stmt.setString(4, u.getEndComplemento());
        stmt.setString(5, u.getEndBairro());
        stmt.setString(6, u.getEndCidade());
        stmt.setString(7, u.getEndEstado());
        stmt.setString(8, u.getEndCep());
        stmt.setString(9, u.getEndPais());
        stmt.setString(10, u.getDataNascimento().toString());
        stmt.setString(11, u.getRg());
        stmt.setString(12, u.getCpf());
        stmt.setString(13, u.getEmail());
        stmt.setString(14, u.getTelefone());
        stmt.setString(15, u.getCelular());
        stmt.setInt(16, u.getFuncao().getId());
        stmt.setInt(17, u.getIdUsuario());
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public void excluir(Usuario u) throws SQLException {
        String sql = "DELETE From usuarios where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    public ArrayList<Usuario> listar() throws SQLException {
        String sql = "SELECT * From usuarios";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet resultado = stmt.executeQuery();
        
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while(resultado.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(resultado.getInt("id"));
            u.setNome(resultado.getString("nome"));
            u.setRg(resultado.getString("rg"));
            u.setCpf(resultado.getString("cpf"));
            u.setEndRua(resultado.getString("end_rua"));
            u.setEndNumero(resultado.getString("end_numero"));
            u.setEndBairro(resultado.getString("end_bairro"));
            u.setEndComplemento(resultado.getString("end_complemento"));
            u.setEndCidade(resultado.getString("end_cidade"));
            u.setEndCep(resultado.getString("end_cep"));
            u.setEndEstado(resultado.getString("end_estado"));
            u.setEndPais(resultado.getString("end_pais"));
            u.setTelefone(resultado.getString("telefone"));
            u.setCelular(resultado.getString("celular"));
            u.setEmail(resultado.getString("email"));
            u.setFuncao(buscaFuncaoPorId(resultado.getInt("funcao")));
            u.setDataNascimento(LocalDate.parse(resultado.getString("data_nascimento")));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            u.setDataCadastro(LocalDateTime.parse(resultado.getTimestamp("data_cadastro").toString(), formatter));
            
            usuarios.add(u);
        }
        
        return usuarios;
    }
    
    public Funcao buscaFuncaoPorId(int id) throws SQLException {
        String sql = "SELECT * From funcoes where id = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        Funcao f = new Funcao();
        
        if (rs.next()) {
            f.setId(rs.getInt("id"));
            f.setNomeFuncao(rs.getString("nome_funcao"));
            f.setDescricaoFuncao(rs.getString("descricao"));
            f.setNivelAcesso(rs.getShort("nivel_acesso"));
        }
        
        rs.close();
        stmt.close();
        
        return f;
    }
    
    public ArrayList<Usuario> filtrar(String valor, String sql) throws SQLException {
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, "%" + valor + "%");
        
        ResultSet rs = stmt.executeQuery();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while(rs.next()) {
            Usuario u = new Usuario();
            u.setNome(rs.getString("nome"));
            u.setRg(rs.getString("rg"));
            u.setCpf(rs.getString("cpf"));
            u.setEndRua(rs.getString("end_rua"));
            u.setEndNumero(rs.getString("end_numero"));
            u.setEndBairro(rs.getString("end_bairro"));
            u.setEndComplemento(rs.getString("end_complemento"));
            u.setEndCidade(rs.getString("end_cidade"));
            u.setEndCep(rs.getString("end_cep"));
            u.setEndEstado(rs.getString("end_estado"));
            u.setEndPais(rs.getString("end_pais"));
            u.setTelefone(rs.getString("telefone"));
            u.setCelular(rs.getString("celular"));
            u.setEmail(rs.getString("email"));
            u.setFuncao(buscaFuncaoPorId(rs.getInt("funcao")));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            u.setDataCadastro(LocalDateTime.parse(rs.getTimestamp("data_cadastro").toString(), formatter));
            
            usuarios.add(u);
        }
        
        rs.close();
        stmt.close();
        
        return usuarios;
    }
    
    public ArrayList<Usuario> filtrarPorNome(String nome) throws SQLException {
        String sql = "SELECT * From usuarios where nome LIKE ?";
        return filtrar(nome, sql);
    }
    
    public Usuario buscarPeloCpf(String cpf) throws SQLException {
        String sql = "SELECT * From usuarios where cpf = ?";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        stmt.setString(1, cpf);
        
        ResultSet rs = stmt.executeQuery();
        Usuario u = null;
        if (rs.next()) {
            u = new Usuario();
            u.setIdUsuario(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setRg(rs.getString("rg"));
            u.setCpf(rs.getString("cpf"));
            u.setEndRua(rs.getString("end_rua"));
            u.setEndNumero(rs.getString("end_numero"));
            u.setEndBairro(rs.getString("end_bairro"));
            u.setEndComplemento(rs.getString("end_complemento"));
            u.setEndCidade(rs.getString("end_cidade"));
            u.setEndCep(rs.getString("end_cep"));
            u.setEndEstado(rs.getString("end_estado"));
            u.setEndPais(rs.getString("end_pais"));
            u.setTelefone(rs.getString("telefone"));
            u.setCelular(rs.getString("celular"));
            u.setEmail(rs.getString("email"));
            u.setFuncao(buscaFuncaoPorId(rs.getInt("funcao")));
            u.setDataNascimento(LocalDate.parse(rs.getString("data_nascimento")));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            u.setDataCadastro(LocalDateTime.parse(rs.getTimestamp("data_cadastro").toString(), formatter));
        }
        
        rs.close();
        stmt.close();
        
        return u;
    }
    
    public ArrayList<Usuario> filtrarPeloCpf(String cpf) throws SQLException {
        ArrayList<Usuario> lista = new ArrayList<>();
        Usuario u = buscarPeloCpf(cpf);
        if (u != null) {
            lista.add(buscarPeloCpf(cpf));
        }
        return lista;
    }
    
    public ObservableList<Funcao> listarFuncaoCrescente() throws SQLException {
        String sql = "SELECT * From funcoes Order By nome_funcao";
        
        PreparedStatement stmt = ConnectionFactory.prepararSQL(sql);
        
        ResultSet rs = stmt.executeQuery();
        ObservableList<Funcao> funcoes = FXCollections.observableArrayList();
        while (rs.next()) {
            Funcao f = new Funcao();
            f.setId(rs.getInt("id"));
            f.setNomeFuncao(rs.getString("nome_funcao"));
            f.setDescricaoFuncao(rs.getString("descricao"));
            f.setNivelAcesso(rs.getShort("nivel_acesso"));
            funcoes.add(f);
        }
        
        rs.close();
        stmt.close();
        
        return funcoes;
    }
}
