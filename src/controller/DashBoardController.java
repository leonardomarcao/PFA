package controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import dao.DespesaDAO;
import dao.ReceitaDAO;
import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Despesa;
import model.Receita;
import model.Usuario;

public class DashBoardController implements Initializable {

	@FXML
	private Label lblSaldoAtual;

	@FXML
	private Label lblMonthAmountDespesa;

	@FXML
	private Label lblMonthAmountReceita;

	@FXML
	private Label lblWelcome;

	@FXML
	private Label lblPeriodoDespesa;

	@FXML
	private Label lblPeriodoReceita;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	@FXML
	private LineChart<String, Number> lineChart;

	private Usuario usuario;

	private static DateTimeFormatter formatter;

	public DashBoardController(Usuario _usuario) {
		super();
		setUsuario(_usuario);
		setFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setWelcomeMessage();
		setSaldoAtual();
		setMouthAmountDespesa();
		setMouthAmountReceita();
		loadLineChart();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DateTimeFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	private void setSaldoAtual() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		setUsuario(usuarioDAO.updateUsuario(getUsuario()));
		lblSaldoAtual.setText(String.format("R$ %.2f", ((getUsuario().getSaldoUsuario()))));
	}

	// setMonthAmountDespesa
	private void setMouthAmountDespesa() {
		LocalDate now = LocalDate.now();
		LocalDate thirty = now.minusDays(30);
		List<Despesa> listDespesa = new DespesaDAO().getAllDespesa(getUsuario());
		Double totalDespesaUltimosTrintaDias = 0.0;
		for (Despesa despesa : listDespesa)
			// considerar data de início
			if (despesa.getDataDespesa().after(Date.valueOf(thirty.plusDays(-1))))
				totalDespesaUltimosTrintaDias += despesa.getValorDespesa();
		lblMonthAmountDespesa.setText(String.valueOf(String.format("R$ %.2f", (totalDespesaUltimosTrintaDias))));
		lblPeriodoDespesa.setText(thirty.format(formatter) + " à " + now.format(formatter));
		listDespesa.clear();
	}

	// setMonthAmountReceita
	private void setMouthAmountReceita() {
		LocalDate now = LocalDate.now();
		LocalDate thirty = now.minusDays(30);
		List<Receita> listReceita = new ReceitaDAO().getAllReceita(getUsuario());
		Double totalReceitaUltimosTrintaDias = 0.0;
		for (Receita receita : listReceita)
			// considerar data de início
			if (receita.getDataReceita().after(Date.valueOf(thirty.plusDays(-1))))
				totalReceitaUltimosTrintaDias += receita.getValorReceita();
		lblMonthAmountReceita.setText(String.valueOf(String.format("R$ %.2f", (totalReceitaUltimosTrintaDias))));
		lblPeriodoReceita.setText(thirty.format(formatter) + " à " + now.format(formatter));
	}

	private void setWelcomeMessage() {
		lblWelcome.setText("Bem vindo, " + getUsuario().getNomeUsuario() + ".");
	}

	// categoryDespesa
	@SuppressWarnings("rawtypes")
	private XYChart.Series categoryDespesa() {
		// seriesAmountMonthDespesa
		XYChart.Series<String, Double> seriesMonthAmountDespesa = new XYChart.Series<String, Double>();

		LocalDate now = LocalDate.now();
		LocalDate thirty = now.minusDays(30);

		List<Despesa> listDespesa = new DespesaDAO().getAllDespesa(getUsuario());

		Stream<Despesa> streamThirtyDaysDespesa = listDespesa.stream()
				.filter(listDespesaAmountMonth -> listDespesaAmountMonth.getDataDespesa()
						.after(Date.valueOf(thirty.plusDays(-1)))
						&& listDespesaAmountMonth.getDataDespesa().before((Date.valueOf(now.plusDays(1)))));

		seriesMonthAmountDespesa.setName("Despesa");
		streamThirtyDaysDespesa.forEach(despesa -> seriesMonthAmountDespesa.getData().add(
				new XYChart.Data<String, Double>(String.valueOf(despesa.getDataDespesa()), despesa.getValorDespesa())));

		return seriesMonthAmountDespesa;
	}

	// categoryReceita
	@SuppressWarnings("rawtypes")
	private XYChart.Series categoryReceita() {
		// seriesAmountMonthReceita
		XYChart.Series<String, Double> seriesMonthAmountReceita = new XYChart.Series<String, Double>();

		LocalDate now = LocalDate.now();
		LocalDate thirty = now.minusDays(30);

		List<Receita> listReceita = new ReceitaDAO().getAllReceita(getUsuario());

		Stream<Receita> streamThirtyDaysReceita = listReceita.stream()
				.filter(listReceitaAmountMonth -> listReceitaAmountMonth.getDataReceita()
						.after(Date.valueOf(thirty.plusDays(-1)))
						&& listReceitaAmountMonth.getDataReceita().before((Date.valueOf(now.plusDays(1)))));

		seriesMonthAmountReceita.setName("Receita");
		streamThirtyDaysReceita.forEach(receita -> seriesMonthAmountReceita.getData().add(
				new XYChart.Data<String, Double>(String.valueOf(receita.getDataReceita()), receita.getValorReceita())));

		return seriesMonthAmountReceita;
	}

	// load lineChart using series
	private void loadLineChart() {
		lineChart.getData().addAll(categoryDespesa(), categoryReceita());
	}

}
