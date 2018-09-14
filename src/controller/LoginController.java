package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Usuario;

public class LoginController implements Initializable{

	@FXML private JFXTextField jTextUsername;
	@FXML private JFXPasswordField jTextPassword;

    @FXML
    void signIn(ActionEvent event) {
    	Usuario usuarioLogin;
    	UsuarioDAO usuarioDAO;
    		try {
    			usuarioLogin = new Usuario(jTextUsername.getText(), jTextPassword.getText());
    			usuarioDAO = new UsuarioDAO();
    			if (usuarioDAO.signIn(usuarioLogin)) {
    				System.out.println("Bem vindo "+usuarioLogin.getNomeUsuario());
    			}else System.out.println("Usuário não encontrado");   			
    		}catch(Exception e) {
    			System.out.println(e);
    		}
    }
	
	@FXML void signUp(ActionEvent ev) {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
