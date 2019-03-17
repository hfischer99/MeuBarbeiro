package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexaoHSQLDB.CNXHSQLDB;
import entidade.Agenda;
import entidade.Pessoa;

public class agendaDAO {
	private final String SQL_INSERE_PESSOA = "INSERT INTO TB_Agendamento(nome,descricao,cor,km) VALUES (?,?,?,?);";
	private final String SQL_ALTERA_CARRO = "UPDATE carro SET nome=?, descricao=?, cor=?, km=? WHERE ID=?;";
	private final String SQL_EXCLUI_CARRO = "DELETE FROM carro WHERE ID=?";
	private final String SQL_SELECIONA_AGENDA = "SELECT * FROM TB_Agendamento";

	public void inserirCarro(Pessoa umCarro) {
		try (	Connection conn = new CNXHSQLDB().conectar(); 
				PreparedStatement pst = conn.prepareStatement(SQL_INSERE_PESSOA);) {
			
			pst.setString(1, umCarro.getNome());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao executar o Statment " + e.toString());
		}
	}
	
	public ArrayList<Agenda> listarTodosCliente() {
		ArrayList<Agenda> listaDeClientes = new ArrayList<Agenda>();

		Agenda umaAgenda;
		try (	Connection conn = new CNXHSQLDB().conectar();
				PreparedStatement pst = conn.prepareStatement(SQL_SELECIONA_AGENDA);
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				umaAgenda = new Agenda();
				umaAgenda.setId(rs.getInt("ID"));
				umaAgenda.setCliente(rs.getString("cliente"));
				umaAgenda.setBarbeiro(rs.getString("barbeiro"));
				umaAgenda.setStatus(rs.getString("status"));
				umaAgenda.setServico(rs.getString("servico"));
				umaAgenda.setHorario(rs.getString("horario"));
				umaAgenda.setData(rs.getString("data"));
				
				
				listaDeClientes.add(umaAgenda);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao executar o Statement" + e.toString());
		}

		return listaDeClientes;
	}

	public void updateAgenda(Pessoa umaPessoa) {
		try (	Connection conn = new CNXHSQLDB().conectar(); 
				PreparedStatement pst = conn.prepareStatement(SQL_ALTERA_CARRO);) {
			pst.setString(1, umaPessoa.getNome());
			pst.setInt(5, umaPessoa.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao executar o Statment " + e.toString());
		}
	}

	public void excluiAgendamento(Agenda selecionada) {
		try (Connection conn = new CNXHSQLDB().conectar(); 
				PreparedStatement pst = conn.prepareStatement(SQL_EXCLUI_CARRO);) {
			pst.setInt(1, selecionada.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao executar o Statment " + e.toString());
		}
	}

}
