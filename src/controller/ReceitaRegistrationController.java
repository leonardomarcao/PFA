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

import dao.ReceitaDAO;
import dao.TipoReceitaDAO;
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
import model.Receita;
import model.TipoReceita;
import model.Usuario;

public class ReceitaRegistrationController implements Initializable {

	private Receita receita;

	private Usuario usuario;

	@FXML
	private JFXTextField edtValorReceita;

	private Map<String, TipoReceita> mapTipoReceita;
	private Set<String> setTipoReceita;
	@FXML
	private JFXComboBox<String> cxTipoReceita;

	@FXML
	private JFXTextField edtDescricaoReceita;

	@FXML
	private JFXDatePicker edtDataReceita;

	@FXML
	private TableView<Receita> tableViewReceita;

	// this constructor will be used for save the Receita
	public ReceitaRegistrationController(Usuario _usuario, TableView<Receita> _tableViewReceita) {
		super();
		setUsuario(_usuario);
		setTableViewReceita(_tableViewReceita);
	}

	// this constructor will be used for alter the Receita
	public ReceitaRegistrationController(Usuario _usuario, Receita _receita, TableView<Receita> _tableViewReceita) {
		super();
		setUsuario(_usuario);
		setReceita(_receita);
		setTableViewReceita(_tableViewReceita);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (getReceita() != null) {
			edtValorReceita.setText(String.valueOf((getReceita().getValorReceita())));
			loadCxTipoReceita();
			setTipoReceitaFromCx(null);
			edtDescricaoReceita.setText(getReceita().getDescricaoReceita());
			edtDataReceita
					.setValue(getReceita().getDataReceita().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
	}

	public TableView<Receita> getTableViewReceita() {
		return tableViewReceita;
	}

	public void setTableViewReceita(TableView<Receita> tableViewReceita) {
		this.tableViewReceita = tableViewReceita;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Map<String, TipoReceita> getMapTipoReceita() {
		return mapTipoReceita;
	}

	public void setMapTipoReceita(Map<String, TipoReceita> mapTipoReceita) {
		this.mapTipoReceita = mapTipoReceita;
	}

	public Set<String> getSetTipoReceita() {
		return setTipoReceita;
	}

	public void setSetTipoReceita(Set<String> setTipoReceita) {
		this.setTipoReceita = setTipoReceita;
	}

	@FXML
	void addTipoReceita(MouseEvent event) {
		TipoReceita tipoReceita = inputDialogTipoReceita();
		if (tipoReceita != null) {
			cxTipoReceita.getItems().clear();
			loadCxTipoReceita();
			setTipoReceitaFromCx(tipoReceita);
		} else {
			cxTipoReceita.getItems().clear();
			loadCxTipoReceita();
			setTipoReceitaFromCx(null);
		}
	}

	@FXML
	void saveReceita(MouseEvent event) {
		if (getReceita() == null) {
			Receita receita = new Receita((Double.valueOf(edtValorReceita.getText())), (getTipoReceitaFromCx()),
					(getUsuario()),
					(Date.from((edtDataReceita.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))),
					edtDescricaoReceita.getText());
			ReceitaDAO receitaDAO = new ReceitaDAO();
			receitaDAO.saveReceita(receita);
			getTableViewReceita().getItems().add(receita);
			if (alertDialogConfirmationTipoReceita() == true) {
				clearControls();
				edtValorReceita.requestFocus();
			} else {
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.close();
			}
		} else {
			int pos = getTableViewReceita().getSelectionModel().getSelectedIndex();
			getTableViewReceita().getItems().remove(getReceita());
			getReceita().setValorReceita((Double.valueOf(edtValorReceita.getText())));
			getReceita().setTipoReceita((getTipoReceitaFromCx()));
			getReceita().setDescricaoReceita(edtDescricaoReceita.getText());
			getReceita().setDataReceita(
					(Date.from((edtDataReceita.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))));
			ReceitaDAO receitaDAO = new ReceitaDAO();
			receitaDAO.mergeReceita(getReceita());
			getTableViewReceita().getItems().add(pos, getReceita());
			getTableViewReceita().getSelectionModel().clearAndSelect(pos);
			getTableViewReceita().refresh();
			alertDialog("Alteração de Receita", "A receita foi alterada!", null, AlertType.INFORMATION);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.close();
		}
	}

	private TipoReceita inputDialogTipoReceita() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Cadastro de Tipo de Receita");
		dialog.setHeaderText("Por favor, informe um nome para o tipo de receita");
		dialog.setContentText("Tipo de receita: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().isEmpty()) {
			TipoReceita tipoReceita = new TipoReceita(result.get());
			TipoReceitaDAO tipoReceitaDAO = new TipoReceitaDAO();
			tipoReceitaDAO.saveTipoReceita(tipoReceita);
			return tipoReceita;
		} else if (result.isPresent() && result.get().isEmpty())
			alertDialog("Cadastro de tipo de receita", "Atenção!", "Nome do tipo de receita não pode ser nulo.",
					AlertType.ERROR);
		return null;
	}

	@FXML
	void backReceita(MouseEvent event) {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}

	@FXML
	void onMouseClickedCxTipoReceita(MouseEvent event) {
		if (mapTipoReceita == null)
			loadCxTipoReceita();
	}

	private TipoReceita getTipoReceitaFromCx() {
		String key = cxTipoReceita.getSelectionModel().getSelectedItem();
		return mapTipoReceita.get(key);
	}

	private void setTipoReceitaFromCx(TipoReceita tipoReceita) {
		if (tipoReceita == null)
			cxTipoReceita.getSelectionModel().select(getReceita().getTipoReceita().getNomeTipoReceita());
		else
			cxTipoReceita.getSelectionModel().select(tipoReceita.getNomeTipoReceita());
	}

	private void loadCxTipoReceita() {
		List<TipoReceita> listTipoReceita = new TipoReceitaDAO().getAllTipoReceita();
		if (listTipoReceita.isEmpty()) {
			alertDialogTipoReceitaEmpty();
		} else {
			setMapTipoReceita((listTipoReceita.stream().sorted(Comparator.comparing(TipoReceita::getNomeTipoReceita))
					.collect(Collectors.toMap(TipoReceita::getNomeTipoReceita, Function.identity()))));
			setSetTipoReceita(getMapTipoReceita().keySet());
			setTipoReceita.forEach(allTipoReceita -> cxTipoReceita.getItems().add(allTipoReceita));
		}
	}

	private void alertDialogTipoReceitaEmpty() {
		ButtonType buttonYes = new ButtonType("Sim");
		ButtonType buttonNo = new ButtonType("Não");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Tipo de Receita");
		alert.setHeaderText("Não há nenhum tipo de receita cadastrado!");
		alert.setContentText("Por favor, é necessário o cadastro de um \n" + "tipo de receita para vincular a receita");
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonYes) {
			inputDialogTipoReceita();
		} else if (result.get() == buttonNo) {
			alert.close();
		}
	}

	public boolean alertDialogConfirmationTipoReceita() {
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
		edtValorReceita.clear();
		cxTipoReceita.getSelectionModel().select(null);
		edtDataReceita.setValue(null);
		edtDescricaoReceita.clear();
	}

}
