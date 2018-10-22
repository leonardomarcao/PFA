package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.ReceitaDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Receita;
import model.Usuario;

public class ReceitaController implements Initializable {

	@FXML
	private AnchorPane anchorPaneReceita;

	@FXML
	private StackPane stackPaneReceita;

	@FXML
	private BorderPane borderPageReceita;

	@FXML
	private MenuItem menuItemInserir;

	@FXML
	private Parent temp;

	@FXML
	private TableColumn<Receita, Integer> idReceitaCol;

	@FXML
	private TableColumn<Receita, String> tipoReceitaCol;

	@FXML
	private TableColumn<Receita, String> descricaoReceitaCol;

	@FXML
	private TableColumn<Receita, Date> dataReceitaCol;

	@FXML
	private TableColumn<Receita, Double> valorReceitaCol;

	@FXML
	private TableView<Receita> tableViewReceita;

	private Usuario usuario;

	public ReceitaController(Usuario _usuario) {
		super();
		setUsuario(_usuario);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idReceitaCol.setCellValueFactory(new PropertyValueFactory<>("idReceita"));
		// using lambda for return SimplesObjectProperty.
		// this get object of column and populate with attribute
		tipoReceitaCol.setCellValueFactory(Receita -> {
			SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
			property.setValue(Receita.getValue().getTipoReceita().getNomeTipoReceita());
			return property;
		});
		descricaoReceitaCol.setCellValueFactory(new PropertyValueFactory<>("descricaoReceita"));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		dataReceitaCol.setCellValueFactory(Receita -> {
			SimpleObjectProperty property = new SimpleObjectProperty();
			property.setValue(format.format((Receita.getValue().getDataReceita())));
			return property;
		});
		valorReceitaCol.setCellValueFactory(Receita -> {
			SimpleObjectProperty property = new SimpleObjectProperty();
			property.setValue(String.format("R$ %.2f", (Receita.getValue().getValorReceita())));
			return property;
		});
		getTableViewReceita().getItems().addAll(loadTableView());
		getTableViewReceita().sort();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TableView<Receita> getTableViewReceita() {
		return tableViewReceita;
	}

	public ObservableList<Receita> loadTableView() {
		List<Receita> listReceita = new ReceitaDAO().getAllReceita(getUsuario());
		return FXCollections.observableList(listReceita);
	}

	@FXML
	void menuItemInserirOnAction(ActionEvent event) {
		ReceitaRegistrationController receitaRegistrationController = new ReceitaRegistrationController(getUsuario(),
				getTableViewReceita());
		loadUI("/view/ReceitaRegistration", receitaRegistrationController);
	}

	@FXML
	void menuItemAlterarOnAction(ActionEvent event) {
		if (getTableViewReceita().getSelectionModel().getSelectedItem() != null) {
			ReceitaRegistrationController receitaRegistrationController = new ReceitaRegistrationController(
					getUsuario(), getTableViewReceita().getSelectionModel().getSelectedItem(), getTableViewReceita());
			loadUI("/view/ReceitaRegistration", receitaRegistrationController);
		} else {
			alertDialog("Alteração de receita", "Atenção", "Por favor, selecione uma receita para poder alterá-la!",
					AlertType.WARNING);
		}
	}

	@FXML
	void menuItemRemoveOnAction(ActionEvent event) {
		if (getTableViewReceita().getSelectionModel().getSelectedItem() != null
				&& alertDialogConfirmationDeleteTipoReceita() == true) {
			Receita receita = tableViewReceita.getSelectionModel().getSelectedItem();
			ReceitaDAO receitaDAO = new ReceitaDAO();
			receitaDAO.deleteReceita(receita);
			tableViewReceita.getItems().remove(receita);
		} else if (getTableViewReceita().getSelectionModel().getSelectedItem() == null) {
			alertDialog("Removação de receita", "Atenção", "Por favor, selecione uma receita para poder removê-la!",
					AlertType.WARNING);
		}
	}

	private void alertDialog(String title, String header, String content, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public boolean alertDialogConfirmationDeleteTipoReceita() {
		ButtonType buttonYes = new ButtonType("Sim");
		ButtonType buttonNo = new ButtonType("Não");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Confirmação de Deleção");
		alert.setHeaderText("Confirma a deleção?");
		alert.setContentText("Confirma a deleção do item ID Nº: "
				+ String.valueOf(getTableViewReceita().getSelectionModel().getSelectedItem().getIdReceita()) + "?");
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonYes) {
			return true;
		} else if (result.get() == buttonNo) {
			alert.close();
		}
		return false;
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
		Stage stage = new Stage();
		Scene scene = new Scene(temp);

		stage.setScene(scene);
		stage.resizableProperty().set(false);
		stage.initStyle(StageStyle.DECORATED);
		stage.setTitle("PFA - CADASTRO DE RECEITAS");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

}
