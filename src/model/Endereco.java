package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
	@Column(name = "rua_endereco")
	private String rua;

	@Column(name = "numero_endereco")
	private int numero;

	@Column(name = "cep_endereco")
	private String CEP;

	@Column(name = "bairro_endereco")
	private String bairro;

	@Column(name = "complemento_endereco")
	private String complemento;

	public Endereco() {
	}

	public Endereco(String _rua, int _numero, String _CEP, String _bairro) {
		setRua(_rua);
		setNumero(_numero);
		setCEP(_CEP);
		setBairro(_bairro);
	}

	// getters and setters
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String CEP) {
		this.CEP = CEP;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
