package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//Using @NamedQuery to get all data of Tipo Despesa
@NamedQuery(name = "TipoReceita.getAllTipoReceita", query = "SELECT tr FROM TipoReceita tr")
@NamedQuery(name = "TipoReceita.verifyExistsTipoReceita", 
			query = "FROM TipoReceita WHERE nomeTipoReceita=:nomeTipoReceita")
@Entity
@Table(name = "tipo_receita")
public class TipoReceita {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_tipo_receita")
	private int idtipoReceita;

	@Column(name = "nome_tipo_receita")
	private String nomeTipoReceita;

	public TipoReceita() {
	}

	public TipoReceita(String _nomeTipoReceita) {
		setNomeTipoReceita(_nomeTipoReceita);
	}

	public int getIdtipoReceita() {
		return idtipoReceita;
	}

	public void setIdtipoReceita(int idtipoReceita) {
		this.idtipoReceita = idtipoReceita;
	}

	public String getNomeTipoReceita() {
		return nomeTipoReceita;
	}

	public void setNomeTipoReceita(String nomeTipoReceita) {
		this.nomeTipoReceita = nomeTipoReceita;
	}

}
