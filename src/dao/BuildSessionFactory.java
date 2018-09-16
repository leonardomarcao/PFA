package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Despesa;
import model.Endereco;
import model.Receita;
import model.TipoDespesa;
import model.TipoReceita;
import model.Usuario;

public class BuildSessionFactory {

	private static SessionFactory sessionFactory;

	// method buildSessionFactory
	// using Configuration for configure sessionFactory and return the same
	private SessionFactory buildSessionFactory() {

		Configuration configuration = new Configuration();

		sessionFactory = configuration.configure("hibernate.cfg.xml").addAnnotatedClass(Usuario.class)
				.addAnnotatedClass(Endereco.class).addAnnotatedClass(Despesa.class).addAnnotatedClass(Receita.class)
				.addAnnotatedClass(TipoDespesa.class).addAnnotatedClass(TipoReceita.class).buildSessionFactory();

		return sessionFactory;
	}

	// method return sessionFactory
	// verify if sessionFactory its null for initialization new sessionFactory
	// using method buildSessionFactory
	public SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}
}
