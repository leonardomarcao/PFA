package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import model.Usuario;

public class AdminPanelController implements Initializable {

	@FXML
	private BorderPane borderPaneMain;
	@FXML
	private Parent temp;
	@FXML
	private HBox btnDashboard;
	@FXML
	private HBox btnReceita;
	@FXML
	private HBox btnDespesa;
	@FXML
	private HBox btnRelatorio;

	private Usuario usuario;

	public AdminPanelController(Usuario _usuario) {
		super();
		setUsuario(_usuario);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DashBoardController dashBoardController = new DashBoardController(getUsuario());
		loadUI("/view/Dashboard", dashBoardController);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// load dash board on mouse click
	@FXML
	void btnDashboardOnMouseClick(MouseEvent event) {
		DashBoardController dashBoardController = new DashBoardController(getUsuario());
		loadUI("/view/Dashboard", dashBoardController);
	}

	// load despesa on mouse click
	@FXML
	void btnDespesaOnMouseClick(MouseEvent event) {
		DespesaController despesaController = new DespesaController(getUsuario());
		loadUI("/view/Despesa", despesaController);
	}

	// load receita on mouse click
	@FXML
	void btnReceitaOnMouseClick(MouseEvent event) {
		ReceitaController receitaController = new ReceitaController(getUsuario());
		loadUI("/view/Receita", receitaController);
	}

	// load user interface with fade transition
	private void loadUI(String ui, Object o) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(ui + ".fxml"));
			loader.setController(o);
			temp = loader.load();
		} catch (IOException e) {
			System.out.println(e);
		}
		FadeTransition ft = new FadeTransition(Duration.millis(300), borderPaneMain.getCenter());
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);
		ft.setOnFinished((ActionEvent e) -> borderPaneMain.setCenter(temp));
		ft.play();
	}

}
