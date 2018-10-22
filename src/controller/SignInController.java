package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
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
import javafx.stage.Stage;
import model.Usuario;

public class SignInController implements Initializable {

	@FXML
	private JFXTextField jTextUsername;
	@FXML
	private JFXPasswordField jTextPassword;
	@FXML
	private Label lblMessageUserNotFound;
	@FXML
	private JFXSpinner spinnerLoading;

	// sign-in
	@FXML
	void signIn(ActionEvent event) {
		Usuario usuarioLogin = null;
		if (jTextUsername.validate() && jTextPassword.validate()) {
			try {
				usuarioLogin = new UsuarioDAO().signIn(jTextUsername.getText(), jTextPassword.getText());
				if (usuarioLogin != null) {
					System.out.println("Usuário na tela de login: " + usuarioLogin.getCPFUsuario());
					AdminPanelController adminPanelController = new AdminPanelController(usuarioLogin);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminPanel.fxml"));
					loader.setController(adminPanelController);
					// set new stage - admin panel
					Parent signUp = (Parent) loader.load();

					Scene signUpScene = new Scene(signUp);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setTitle("PFA - ADMIN PANEL");

					window.setScene(signUpScene);
					window.centerOnScreen();
					window.setMaximized(true);
					window.show();
				} else
					lblMessageUserNotFound.setVisible(true);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	// sign-up
	@FXML
	void signUp(MouseEvent event) throws IOException {
		// visible false lblMessageUserNotFound
		lblMessageUserNotFound.setVisible(false);
		// set new stage
		Parent signUp = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
		Scene signUpScene = new Scene(signUp);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setTitle("PFA - CADASTRO DE USUÁRIO");

		window.setScene(signUpScene);
		window.show();
	}

	// validator fields for login
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		RequiredFieldValidator validatorInputRequired = new RequiredFieldValidator();
		validatorInputRequired.setMessage("Campo obrigatório");
		// jTextUsername RequiredFieldValidator
		jTextUsername.getValidators().add(validatorInputRequired);
		jTextUsername.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextUsername.validate();
			}
		});
		jTextPassword.getValidators().add(validatorInputRequired);
		jTextPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				jTextPassword.validate();
			}
		});
	}

}
