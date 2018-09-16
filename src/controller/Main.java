package controller;

import dao.UsuarioDAO;
import model.Endereco;
import model.Usuario;

public class Main {

	public static void main(String[] args) {
		Endereco endereco = new Endereco("Rua Teste", 683, "14600000", "Baixada");
		Usuario usuario = new Usuario("Leonardo", endereco, "418", "test@test.com", "test123", 4, 1500);
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.insertUsuario(usuario);
	}

}
