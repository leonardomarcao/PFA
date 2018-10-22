package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.DespesaDAO;
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
import model.Despesa;
import model.Usuario;

public class DespesaController implements Initializable {

	@FXML
	private AnchorPane anchorPaneDespesa;

	@FXML
	private StackPane stackPaneDespesa;

	@FXML
	private BorderPane borderPageDespesa;

	@FXML
	private MenuItem menuItemInserir;

	@FXML
	private Parent temp;

	@FXML
	private TableColumn<Despesa, Integer> idDespesaCol;

	@FXML
	private TableColumn<Despesa, String> tipoDespesaCol;

	@FXML
	private TableColumn<Despesa, String> descricaoDespesaCol;

	@FXML
	private TableColumn<Despesa, Date> dataDespesaCol;

	@FXML
	private TableColumn<Despesa, Double> valorDespesaCol;

	@FXML
	private TableView<Despesa> tableViewDespesa;

	private Usuario usuario;

	public DespesaController(Usuario _usuario) {
		super();
		setUsuario(_usuario);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idDespesaCol.setCellValueFactory(new PropertyValueFactory<>("idDespesa"));
		// using lambda for return SimplesObjectProperty.
		// this get object of column and populate with attribute
		tipoDespesaCol.setCellValueFactory(Despesa -> {
			SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
			property.setValue(Despesa.getValue().getTipoDespesa().getNomeTipoDespesa());
			return property;
		});
		descricaoDespesaCol.setCellValueFactory(new PropertyValueFactory<>("descricaoDespesa"));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		dataDespesaCol.setCellValueFactory(Despesa -> {
			SimpleObjectProperty property = new SimpleObjectProperty();
			property.setValue(format.format((Despesa.getValue().getDataDespesa())));
			return property;
		});
		valorDespesaCol.setCellValueFactory(Despesa -> {
			SimpleObjectProperty property = new SimpleObjectProperty();
			property.setValue(String.format("R$ %.2f", (Despesa.getValue().getValorDespesa())));
			return property;
		});
		getTableViewDespesa().getItems().addAll(loadTableView());
		getTableViewDespesa().sort();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TableView<Despesa> getTableViewDespesa() {
		return tableViewDespesa;
	}

	public ObservableList<Despesa> loadTableView() {
		List<Despesa> listDespesa = new DespesaDAO().getAllDespesa(getUsuario());
		return FXCollections.observableList(listDespesa);
	}

	@FXML
	void menuItemInserirOnAction(ActionEvent event) {
		DespesaRegistrationController despesaRegistrationController = new DespesaRegistrationController(getUsuario(),
				getTableViewDespesa());
		loadUI("/view/DespesaRegistration", despesaRegistrationController);
	}

	@FXML
	void menuItemAlterarOnAction(ActionEvent event) {
		if (getTableViewDespesa().getSelectionModel().getSelectedItem() != null) {
			DespesaRegistrationController despesaRegistrationController = new DespesaRegistrationController(
					getUsuario(), getTableViewDespesa().getSelectionModel().getSelectedItem(), getTableViewDespesa());
			loadUI("/view/DespesaRegistration", despesaRegistrationController);
		} else {
			alertDialog("Alteração de despesa", "Atenção", "Por favor, selecione uma despesa para poder alterá-la!",
					AlertType.WARNING);
		}
	}

	@FXML
	void menuItemRemoveOnAction(ActionEvent event) {
		if (getTableViewDespesa().getSelectionModel().getSelectedItem() != null
				&& alertDialogConfirmationDeleteTipoDespesa() == true) {
			Despesa despesa = tableViewDespesa.getSelectionModel().getSelectedItem();
			DespesaDAO despesaDAO = new DespesaDAO();
			despesaDAO.deleteDespesa(despesa);
			tableViewDespesa.getItems().remove(despesa);
		} else if (getTableViewDespesa().getSelectionModel().getSelectedItem() == null) {
			alertDialog("Removação de despesa", "Atenção", "Por favor, selecione uma despesa para poder removê-la!",
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

	public boolean alertDialogConfirmationDeleteTipoDespesa() {
		ButtonType buttonYes = new ButtonType("Sim");
		ButtonType buttonNo = new ButtonType("Não");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Confirmação de Deleção");
		alert.setHeaderText("Confirma a deleção?");
		alert.setContentText("Confirma a deleção do item ID Nº: "
				+ String.valueOf(getTableViewDespesa().getSelectionModel().getSelectedItem().getIdDespesa()) + "?");
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
		stage.setTitle("PFA - CADASTRO DE DESPESA");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

}
