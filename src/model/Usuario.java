package model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")

//Using @NamedQuery to get user 
@NamedQuery(name = "Usuario.getUserLogin", query = "from "
		+ "Usuario where emailUsuario=:emailUsuario and senhaUsuario=:senhaUsuario")

//Using @NamedQuery to get duplicate user
//this will used for verify if exists user already registrated
@NamedQuery(name = "Usuario.getUserDuplicate", query = "from "
		+ "Usuario where emailUsuario=:emailUsuario")

public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private int idUsuario;

	@Column(name = "nome_usuario")
	private String nomeUsuario;

	@Embedded
	private Endereco enderecoUsuario;

	@Column(name = "cpf_usuario")
	private String CPFUsuario;

	@Column(name = "email_usuario")
	private String emailUsuario;

	@Column(name = "senha_usuario")
	private String senhaUsuario;

	@Column(name = "dia_util_salario")
	private int diaUtilSalario;

	@Column(name = "saldo_usuario")
	private double saldoUsuario;

	public Usuario() {
	}

	public Usuario(String _emailUsuario, String _senhaUsuario) {
		setEmailUsuario(_emailUsuario);
		setSenhaUsuario(_senhaUsuario);
	}

	public Usuario(String _nomeUsuario, Endereco _enderecoUsuario, String _CPFUsuario, String _emailUsuario,
			String _senhaUsuario, int _diaUtilSalario, double _saldoUsuario) {
		setNomeUsuario(_nomeUsuario);
		setEnderecoUsuario(_enderecoUsuario);
		setCPFUsuario(_CPFUsuario);
		setEmailUsuario(_emailUsuario);
		setSenhaUsuario(_senhaUsuario);
		setDiaUtilSalario(_diaUtilSalario);
		setSaldoUsuario(_saldoUsuario);
	}

	// getters and setters
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Endereco getEnderecoUsuario() {
		return enderecoUsuario;
	}

	public void setEnderecoUsuario(Endereco enderecoUsuario) {
		this.enderecoUsuario = enderecoUsuario;
	}

	public String getCPFUsuario() {
		return CPFUsuario;
	}

	public void setCPFUsuario(String cPFUsuario) {
		CPFUsuario = cPFUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public int getDiaUtilSalario() {
		return diaUtilSalario;
	}

	public void setDiaUtilSalario(int diaUtilSalario) {
		this.diaUtilSalario = diaUtilSalario;
	}

	public double getSaldoUsuario() {
		return saldoUsuario;
	}

	public void setSaldoUsuario(double saldoUsuario) {
		this.saldoUsuario = saldoUsuario;
	}

}
