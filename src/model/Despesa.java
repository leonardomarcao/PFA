package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//Using @NamedQuery to get all data of Despesa by user
@NamedQuery(name = "Despesa.getAllDespesa", query = "SELECT d FROM "
		+ "Despesa d WHERE d.usuarioDespesa = :usuarioDespesa ORDER BY dataDespesa ASC")

@Entity
@Table(name = "despesa")
public class Despesa {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_despesa")
	private int idDespesa;

	@Column(name = "valor_despesa")
	private double valorDespesa;

	@Column(name = "descricao_despesa")
	private String descricaoDespesa;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_tipo_despesa")
	private TipoDespesa tipoDespesa;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_usuario")
	private Usuario usuarioDespesa;

	@Column(name = "data_despesa")
	private Date dataDespesa;

	public Despesa() {
	}

	// this constructor will be used for series categoryDespesa in lineChart
	// do not use for persistent
	public Despesa(double _valorDespesa, Date dataDespesa) {
		setValorDespesa(_valorDespesa);
		setDataDespesa(dataDespesa);
	}

	public Despesa(double _valorDespesa, TipoDespesa _tipoDespesa, Usuario _usuarioDespesa, Date dataDespesa) {
		setValorDespesa(_valorDespesa);
		setTipoDespesa(_tipoDespesa);
		setUsuarioDespesa(_usuarioDespesa);
		setDataDespesa(dataDespesa);
	}

	public Despesa(double _valorDespesa, TipoDespesa _tipoDespesa, Usuario _usuarioDespesa, Date dataDespesa,
			String _descricaoDespesa) {
		setValorDespesa(_valorDespesa);
		setTipoDespesa(_tipoDespesa);
		setUsuarioDespesa(_usuarioDespesa);
		setDataDespesa(dataDespesa);
		setDescricaoDespesa(_descricaoDespesa);
	}

	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public Date getDataDespesa() {
		return dataDespesa;
	}

	public void setDataDespesa(Date dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public int getIdDespesa() {
		return idDespesa;
	}

	public void setIdDespesa(int idDespesa) {
		this.idDespesa = idDespesa;
	}

	public double getValorDespesa() {
		return valorDespesa;
	}

	public void setValorDespesa(double valorDespesa) {
		this.valorDespesa = valorDespesa;
	}

	public String getDescricaoDespesa() {
		return descricaoDespesa;
	}

	public void setDescricaoDespesa(String descricaoDespesa) {
		this.descricaoDespesa = descricaoDespesa;
	}

	public Usuario getUsuarioDespesa() {
		return usuarioDespesa;
	}

	public void setUsuarioDespesa(Usuario usuarioDespesa) {
		this.usuarioDespesa = usuarioDespesa;
	}

}