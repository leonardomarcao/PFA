package controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import dao.DespesaDAO;
import dao.TipoDespesaDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Despesa;
import model.TipoDespesa;
import model.Usuario;

public class DespesaRegistrationController implements Initializable {

	private Despesa despesa;

	private Usuario usuario;

	@FXML
	private JFXTextField edtValorDespesa;

	private Map<String, TipoDespesa> mapTipoDespesa;
	private Set<String> setTipoDespesa;
	@FXML
	private JFXComboBox<String> cxTipoDespesa;

	@FXML
	private JFXTextField edtDescricaoDespesa;

	@FXML
	private JFXDatePicker edtDataDespesa;

	@FXML
	private TableView<Despesa> tableViewDespesa;

	// this constructor will be used for save the Despesa
	public DespesaRegistrationController(Usuario _usuario, TableView<Despesa> _tableViewDespesa) {
		super();
		setUsuario(_usuario);
		setTableViewDespesa(_tableViewDespesa);
	}

	// this constructor will be used for alter the Despesa
	public DespesaRegistrationController(Usuario _usuario, Despesa _despesa, TableView<Despesa> _tableViewDespesa) {
		super();
		setUsuario(_usuario);
		setDespesa(_despesa);
		setTableViewDespesa(_tableViewDespesa);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (getDespesa() != null) {
			edtValorDespesa.setText(String.valueOf((getDespesa().getValorDespesa())));
			loadCxTipoDespesa();
			setTipoDespesaFromCx(null);
			edtDescricaoDespesa.setText(getDespesa().getDescricaoDespesa());
			edtDataDespesa
					.setValue(getDespesa().getDataDespesa().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
	}

	public TableView<Despesa> getTableViewDespesa() {
		return tableViewDespesa;
	}

	public void setTableViewDespesa(TableView<Despesa> tableViewDespesa) {
		this.tableViewDespesa = tableViewDespesa;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Map<String, TipoDespesa> getMapTipoDespesa() {
		return mapTipoDespesa;
	}

	public void setMapTipoDespesa(Map<String, TipoDespesa> mapTipoDespesa) {
		this.mapTipoDespesa = mapTipoDespesa;
	}

	public Set<String> getSetTipoDespesa() {
		return setTipoDespesa;
	}

	public void setSetTipoDespesa(Set<String> setTipoDespesa) {
		this.setTipoDespesa = setTipoDespesa;
	}

	@FXML
	void addTipoDespesa(MouseEvent event) {
		TipoDespesa tipoDespesa = inputDialogTipoDespesa();
		if (tipoDespesa != null) {
			cxTipoDespesa.getItems().clear();
			loadCxTipoDespesa();
			setTipoDespesaFromCx(tipoDespesa);
		} else {
			cxTipoDespesa.getItems().clear();
			loadCxTipoDespesa();
			setTipoDespesaFromCx(null);
		}
	}

	@FXML
	void saveDespesa(MouseEvent event) {
		if (getDespesa() == null) {
			Despesa despesa = new Despesa((Double.valueOf(edtValorDespesa.getText())), (getTipoDespesaFromCx()),
					(getUsuario()),
					(Date.from((edtDataDespesa.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))),
					edtDescricaoDespesa.getText());
			DespesaDAO despesaDAO = new DespesaDAO();
			despesaDAO.saveDespesa(despesa);
			getTableViewDespesa().getItems().add(despesa);
			if (alertDialogConfirmationTipoDespesa() == true) {
				clearControls();
				edtValorDespesa.requestFocus();
			} else {
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.close();
			}
		} else {
			int pos = getTableViewDespesa().getSelectionModel().getSelectedIndex();
			getTableViewDespesa().getItems().remove(getDespesa());
			getDespesa().setValorDespesa((Double.valueOf(edtValorDespesa.getText())));
			getDespesa().setTipoDespesa((getTipoDespesaFromCx()));
			getDespesa().setDescricaoDespesa(edtDescricaoDespesa.getText());
			getDespesa().setDataDespesa(
					(Date.from((edtDataDespesa.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))));
			DespesaDAO despesaDAO = new DespesaDAO();
			despesaDAO.mergeDespesa(getDespesa());
			getTableViewDespesa().getItems().add(pos, getDespesa());
			getTableViewDespesa().getSelectionModel().clearAndSelect(pos);
			getTableViewDespesa().refresh();
			alertDialog("Alteração de Despesa", "A despesa foi alterada!", null, AlertType.INFORMATION);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.close();
		}
	}

	private TipoDespesa inputDialogTipoDespesa() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Cadastro de Tipo de Despesa");
		dialog.setHeaderText("Por favor, informe um nome para o tipo de despesa");
		dialog.setContentText("Tipo de despesa: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().isEmpty()) {
			TipoDespesa tipoDespesa = new TipoDespesa(result.get());
			TipoDespesaDAO tipoDespesaDAO = new TipoDespesaDAO();
			tipoDespesaDAO.saveTipoDespesa(tipoDespesa);
			return tipoDespesa;
		} else if (result.isPresent() && result.get().isEmpty())
			alertDialog("Cadastro de tipo de despesa", "Atenção!", "Nome do tipo de despesa não pode ser nulo.",
					AlertType.ERROR);
		return null;
	}

	@FXML
	void backDespesa(MouseEvent event) {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}

	@FXML
	void onMouseClickedCxTipoDespesa(MouseEvent event) {
		if (mapTipoDespesa == null)
			loadCxTipoDespesa();
	}

	private TipoDespesa getTipoDespesaFromCx() {
		String key = cxTipoDespesa.getSelectionModel().getSelectedItem();
		return mapTipoDespesa.get(key);
	}

	private void setTipoDespesaFromCx(TipoDespesa tipoDespesa) {
		if (tipoDespesa == null)
			cxTipoDespesa.getSelectionModel().select(getDespesa().getTipoDespesa().getNomeTipoDespesa());
		else
			cxTipoDespesa.getSelectionModel().select(tipoDespesa.getNomeTipoDespesa());
	}

	private void loadCxTipoDespesa() {
		List<TipoDespesa> listTipoDespesa = new TipoDespesaDAO().getAllTipoDespesa();
		if (listTipoDespesa.isEmpty()) {
			alertDialogTipoDespesaEmpty();
		} else {
			setMapTipoDespesa((listTipoDespesa.stream().sorted(Comparator.comparing(TipoDespesa::getNomeTipoDespesa))
					.collect(Collectors.toMap(TipoDespesa::getNomeTipoDespesa, Function.identity()))));
			setSetTipoDespesa(getMapTipoDespesa().keySet());
			setTipoDespesa.forEach(allTipoDespesa -> cxTipoDespesa.getItems().add(allTipoDespesa));
		}
	}

	private void alertDialogTipoDespesaEmpty() {
		ButtonType buttonYes = new ButtonType("Sim");
		ButtonType buttonNo = new ButtonType("Não");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Tipo de Despesa");
		alert.setHeaderText("Não há nenhum tipo de despesa cadastrado!");
		alert.setContentText("Por favor, é necessário o cadastro de um \n" + "tipo de despesa para vincular a despesa");
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonYes) {
			inputDialogTipoDespesa();
		} else if (result.get() == buttonNo) {
			alert.close();
		}
	}

	public boolean alertDialogConfirmationTipoDespesa() {
		ButtonType buttonYes = new ButtonType("Sim");
		ButtonType buttonNo = new ButtonType("Não");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Cadastro salvo!");
		alert.setHeaderText("Cadastro salvo com sucesso!");
		alert.setContentText("Deseja inserir um novo cadastro?");
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonYes) {
			return true;
		} else if (result.get() == buttonNo) {
			alert.close();
		}
		return false;
	}

	private void alertDialog(String title, String header, String content, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private void clearControls() {
		edtValorDespesa.clear();
		cxTipoDespesa.getSelectionModel().select(null);
		edtDataDespesa.setValue(null);
		edtDescricaoDespesa.clear();
	}

}
