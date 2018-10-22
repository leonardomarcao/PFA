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
@NamedQuery(name = "Receita.getAllReceita", query = "SELECT r FROM "
		+ "Receita r where r.usuarioReceita = :usuarioReceita ORDER BY dataReceita ASC")

@Entity
@Table(name = "receita")
public class Receita {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_receita")
	private int idReceita;

	@Column(name = "valor_receita")
	private double valorReceita;

	@Column(name = "descricao_receita")
	private String descricaoReceita;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_tipo_receita")
	private TipoReceita tipoReceita;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_usuario")
	private Usuario usuarioReceita;

	@Column(name = "data_receita")
	private Date dataReceita;

	public Receita() {
	}

	// this constructor will be used for series categoryReceita in lineChart
	// do not use for persistent
	public Receita(double _valorReceita, Date dataReceita) {
		setValorReceita(_valorReceita);
		setDataReceita(dataReceita);
	}

	public Receita(double _valorReceita, TipoReceita _tipoReceita, Usuario _usuarioReceita, Date dataReceita) {
		setValorReceita(_valorReceita);
		setTipoReceita(_tipoReceita);
		setUsuarioReceita(_usuarioReceita);
		setDataReceita(dataReceita);
	}

	public Receita(double _valorReceita, TipoReceita _tipoReceita, Usuario _usuarioReceita, Date dataReceita,
			String _descricaoReceita) {
		setValorReceita(_valorReceita);
		setTipoReceita(_tipoReceita);
		setUsuarioReceita(_usuarioReceita);
		setDataReceita(dataReceita);
		setDescricaoReceita(_descricaoReceita);
	}

	public Date getDataReceita() {
		return dataReceita;
	}

	public void setDataReceita(Date dataReceita) {
		this.dataReceita = dataReceita;
	}

	public int getIdReceita() {
		return idReceita;
	}

	public void setIdReceita(int idReceita) {
		this.idReceita = idReceita;
	}

	public double getValorReceita() {
		return valorReceita;
	}

	public void setValorReceita(double valorReceita) {
		this.valorReceita = valorReceita;
	}

	public String getDescricaoReceita() {
		return descricaoReceita;
	}

	public void setDescricaoReceita(String observacaoReceita) {
		this.descricaoReceita = observacaoReceita;
	}

	public TipoReceita getTipoReceita() {
		return tipoReceita;
	}

	public void setTipoReceita(TipoReceita tipoReceita) {
		this.tipoReceita = tipoReceita;
	}

	public Usuario getUsuarioReceita() {
		return usuarioReceita;
	}

	public void setUsuarioReceita(Usuario usuarioReceita) {
		this.usuarioReceita = usuarioReceita;
	}

}