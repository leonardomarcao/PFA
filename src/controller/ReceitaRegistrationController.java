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
	
	private TipoReceita tipoReceita;

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
		setTipoReceita(getReceita().getTipoReceita());
		setTableViewReceita(_tableViewReceita);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("test");
		if (getReceita() != null) {
			edtValorReceita.setText(String.valueOf((getReceita().getValorReceita())));
			loadCxTipoReceita();
			if (getReceita() != null) setTipoReceitaFromCx(getReceita().getTipoReceita());
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
	
	public TipoReceita getTipoReceita() {
		return tipoReceita;
	}

	public void setTipoReceita(TipoReceita tipoReceita) {
		this.tipoReceita = tipoReceita;
	}

	@FXML
	void addTipoReceita(MouseEvent event) {
		String nomeTipoReceita = inputDialogTipoReceita();
		TipoReceita tipoReceita = new TipoReceita(nomeTipoReceita);
		TipoReceitaDAO tipoReceitaDAO = new TipoReceitaDAO();
		if (!tipoReceitaDAO.verifyExistsTipoReceita(nomeTipoReceita)) {					
			tipoReceitaDAO.saveTipoReceita(tipoReceita);
			if (tipoReceita != null) {
				cxTipoReceita.getItems().clear();
				loadCxTipoReceita();
				setTipoReceitaFromCx(tipoReceita);
			}
		}else
			alertDialog("Aten��o", "Duplicidade de dados!", "J� existe um tipo de receita "
				   	  + "com o mesmo nome cadastrado! Por favor, tente com outro nome.", AlertType.ERROR);
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
			alertDialog("Altera��o de Receita", "A receita foi alterada!", null, AlertType.INFORMATION);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.close();
		}
	}

	private String inputDialogTipoReceita() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Cadastro de Tipo de Receita");
		dialog.setHeaderText("Por favor, informe um nome para o tipo de receita");
		dialog.setContentText("Tipo de receita: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().isEmpty()) {
			return result.get();					
		} else if (result.isPresent() && result.get().isEmpty())
			alertDialog("Cadastro de tipo de receita", "Aten��o!", "Nome do tipo de receita n�o pode ser nulo.",
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
			cxTipoReceita.getSelectionModel().select(tipoReceita.getNomeTipoReceita());
	}

	private void loadCxTipoReceita() {
		List<TipoReceita> listTipoReceita = new TipoReceitaDAO().getAllTipoReceita();
		if (listTipoReceita.isEmpty()) {
			alertDialog("Aten��o!", "N�o h� nenhum tipo de receita cadastrado!", 
					"Por favor, � necess�rio o cadastro de um \n" + "tipo de receita para vincular a receita", AlertType.WARNING);
		} else {
			setMapTipoReceita((listTipoReceita.stream().sorted(Comparator.comparing(TipoReceita::getNomeTipoReceita))
					.collect(Collectors.toMap(TipoReceita::getNomeTipoReceita, Function.identity()))));
			setSetTipoReceita(getMapTipoReceita().keySet());
			setTipoReceita.forEach(allTipoReceita -> cxTipoReceita.getItems().add(allTipoReceita));
			cxTipoReceita.autosize();
		}
	}

	public boolean alertDialogConfirmationTipoReceita() {
		ButtonType buttonYes = new ButtonType("Sim");
		ButtonType buttonNo = new ButtonType("N�o");
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
