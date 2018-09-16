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
import javax.persistence.Table;

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

	public Receita(double _valorReceita, String _observacaoReceita, TipoReceita _tipoReceita, Usuario _usuarioReceita,
			Date _dataReceita) {
		setValorReceita(_valorReceita);
		setObservacaoReceita(_observacaoReceita);
		setTipoReceita(_tipoReceita);
		setUsuarioReceita(_usuarioReceita);
		setDataReceita(_dataReceita);
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

	public String getObservacaoReceita() {
		return descricaoReceita;
	}

	public void setObservacaoReceita(String observacaoReceita) {
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