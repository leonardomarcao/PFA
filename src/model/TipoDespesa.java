package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_despesa")
public class TipoDespesa {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_tipo_despesa")
	private int idTipoDespesa;

	@Column(name = "nome_tipo_despesa")
	private String nomeTipoDespesa;

	public TipoDespesa() {
	}

	public TipoDespesa(String _nomeTipoDespesa) {
		setNomeTipoDespesa(_nomeTipoDespesa);
	}

	public int getIdTipoDespesa() {
		return idTipoDespesa;
	}

	public void setIdTipoDespesa(int idTipoDespesa) {
		this.idTipoDespesa = idTipoDespesa;
	}

	public String getNomeTipoDespesa() {
		return nomeTipoDespesa;
	}

	public void setNomeTipoDespesa(String nomeTipoDespesa) {
		this.nomeTipoDespesa = nomeTipoDespesa;
	}

}
