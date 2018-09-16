package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Endereco;
import model.Usuario;

public class SignUpController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField jTextFieldNomeUsuario;

	@FXML
	private JFXTextField jTextFieldCPFUsuario;

	@FXML
	private JFXTextField jTextFieldEmailUsuario;

	@FXML
	private JFXPasswordField jTextFieldSenhaUsuario;

	@FXML
	private JFXPasswordField jTextFielSenhaVerifyUsuario;
	
	@FXML
	private JFXTextField jTextFieldDiaUtilSalarioUsuario;

	@FXML
	private JFXTextField jTextFieldSaldoInicialUsuario;

	@FXML
	private JFXTextField jTextFieldRuaEndereco;

	@FXML
	private JFXTextField jTextFieldNumeroEndereco;

	@FXML
	private JFXTextField jTextFieldBairroEndereco;

	@FXML
	private JFXTextField jTextFieldCEPEndereco;

	@FXML
	private AnchorPane frmSignUp;
	
	@FXML
	private Label lblErrorSignUp;

	// back to sign-in screen
	@FXML
	void backLogin(MouseEvent event) throws IOException {
		// set new stage
		Parent login = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene loginScene = new Scene(login);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setTitle("PFA - Login");
		
		window.setScene(loginScene);
		window.show();
	}

	// insert data of new user
	@FXML
	void signUp(ActionEvent event) throws IOException {
		UsuarioDAO usuarioDAOTemp = new UsuarioDAO();
		//verify required fields
		 if (validateFields()){
			Endereco enderecoUsuario = new Endereco(jTextFieldRuaEndereco.getText(),
					Integer.parseInt((jTextFieldNumeroEndereco.getText())), jTextFieldCEPEndereco.getText(),
					jTextFieldBairroEndereco.getText());
			Usuario usuario = new Usuario(jTextFieldNomeUsuario.getText(), enderecoUsuario, jTextFieldCPFUsuario.getText(),
					jTextFieldEmailUsuario.getText(), jTextFieldSenhaUsuario.getText(),
					Integer.parseInt((jTextFieldDiaUtilSalarioUsuario.getText())),
					Double.parseDouble((jTextFieldSaldoInicialUsuario.getText())));
			//verify duplicate user
			if (usuarioDAOTemp.verifyUsuarioDuplicator(usuario)) {
				lblErrorSignUp.setText("E-mail de usuário já cadastrado");
			}else
				try {
					UsuarioDAO usuarioDAO = new UsuarioDAO();
					usuarioDAO.insertUsuario(usuario);
				} finally {
					// change to login screen
					Parent login = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
					Scene loginScene = new Scene(login);
		
					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setTitle("PFA - Login");
		
					window.setScene(loginScene);
					window.show();
				}
			}
		 }

	@FXML
	void initialize() {
		
	}

	//validator sign-up
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RequiredFieldValidator validatorInputRequired = new RequiredFieldValidator();
		validatorInputRequired.setMessage("Campo obrigatório");
		
		NumberValidator validatorNumber = new NumberValidator();
		validatorNumber.setMessage("Somente números!");
		
		//required field validator add for user register
		jTextFieldNomeUsuario.getValidators().add(validatorInputRequired);
		jTextFieldCPFUsuario.getValidators().add(validatorInputRequired);
		jTextFieldCPFUsuario.getValidators().add(validatorNumber);
		jTextFieldEmailUsuario.getValidators().add(validatorInputRequired);
		jTextFieldSenhaUsuario.getValidators().add(validatorInputRequired);
		jTextFielSenhaVerifyUsuario.getValidators().add(validatorInputRequired);
		jTextFieldDiaUtilSalarioUsuario.getValidators().add(validatorInputRequired);
		jTextFieldDiaUtilSalarioUsuario.getValidators().add(validatorNumber);
		jTextFieldSaldoInicialUsuario.getValidators().add(validatorInputRequired);
		jTextFieldSaldoInicialUsuario.getValidators().add(validatorNumber);
		
		//required field validator add for address register
		jTextFieldRuaEndereco.getValidators().add(validatorInputRequired);
		jTextFieldNumeroEndereco.getValidators().add(validatorInputRequired);
		jTextFieldNumeroEndereco.getValidators().add(validatorNumber);
		jTextFieldBairroEndereco.getValidators().add(validatorInputRequired);
		jTextFieldCEPEndereco.getValidators().add(validatorInputRequired);
		jTextFieldCEPEndereco.getValidators().add(validatorNumber);
		
		//set focusedproperty listener for required field validator		
		jTextFieldNomeUsuario.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldNomeUsuario.validate();
			}
		});
		jTextFieldCPFUsuario.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldCPFUsuario.validate();
			}
		});
		jTextFieldEmailUsuario.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldEmailUsuario.validate();
			}
		});
		jTextFieldSenhaUsuario.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldSenhaUsuario.validate();
			}
		});
		jTextFielSenhaVerifyUsuario.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFielSenhaVerifyUsuario.validate();
			}
		});
		jTextFieldDiaUtilSalarioUsuario.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldDiaUtilSalarioUsuario.validate();
			}
		});
		jTextFieldSaldoInicialUsuario.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldSaldoInicialUsuario.validate();
			}
		});
		jTextFieldRuaEndereco.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldRuaEndereco.validate();
			}
		});
		jTextFieldNumeroEndereco.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldNumeroEndereco.validate();
			}
		});
		jTextFieldBairroEndereco.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldBairroEndereco.validate();
			}
		});
		jTextFieldCEPEndereco.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextFieldCEPEndereco.validate();
			}
		});
	}
	
	//validate fields
	private Boolean validateFields() {
		if (jTextFieldNomeUsuario.validate()&&jTextFieldCPFUsuario.validate()&&
				jTextFieldEmailUsuario.validate()&&jTextFieldSenhaUsuario.validate()&&
				jTextFielSenhaVerifyUsuario.validate()&&jTextFieldDiaUtilSalarioUsuario.validate()&&
				jTextFieldSaldoInicialUsuario.validate()&&jTextFieldRuaEndereco.validate()&&
				jTextFieldNumeroEndereco.validate()&&jTextFieldBairroEndereco.validate()&&
				jTextFieldCEPEndereco.validate()) 
			 return true;
		else return false;
	}
}
	
