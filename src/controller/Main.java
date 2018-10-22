package controller;

import java.sql.SQLException;

import org.hibernate.SessionFactory;

import dao.BuildSessionFactory;
import dao.DespesaDAO;
import dao.UsuarioDAO;
import model.Endereco;
import model.Usuario;

public class Main {

	public static void main(String[] args) throws SQLException {
		DespesaDAO despesaDAO = new DespesaDAO();
		// System.out.println(despesaDAO.getAllDespesa());
	}

}
