package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tipo_receita")
public class TipoReceita {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_tipo_receita")
	private int idtipoReceita;
	
	@Column(name="nome_tipo_receita")
	private String nomeTipoReceita;
		
	public TipoReceita() {}
	
	public TipoReceita(String _nomeTipoReceita) {		
		setNomeTipoReceita(_nomeTipoReceita);
	}

	public int getIdtipoReceita() {
		return idtipoReceita;
	}
	
	public void setIdtipoReceita(int idtipoReceita) {
		this.idtipoReceita = idtipoReceita;
	}
	
	public String getTipoReceita() {
		return nomeTipoReceita;
	}
	
	public void setNomeTipoReceita(String nomeTipoReceita) {
		this.nomeTipoReceita = nomeTipoReceita;
	}
	

}
