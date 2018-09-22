package dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Usuario;

public class UsuarioDAO {

	private static SessionFactory sessionFactory;

	//constructor for get session factory hibernate
	public UsuarioDAO() {
		sessionFactory = new BuildSessionFactory().getSessionFactory();
	}

	public void insertUsuario(Usuario usuario) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.save(usuario);
			session.getTransaction().commit();
		} finally {
			session.close();
		}

	}

	//call "get user" login from model user
	public boolean signIn(Usuario usuarioLogin) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			Usuario usuarioValidator = session.createNamedQuery("Usuario.getUserLogin", Usuario.class)
					.setParameter("emailUsuario", usuarioLogin.getEmailUsuario())
					.setParameter("senhaUsuario", usuarioLogin.getSenhaUsuario())
					.getSingleResult();
			if (usuarioValidator != null)
				return true;
			return false;
		} catch (NoResultException nre) {
			return false;
		} finally {
			session.close();
		}
	}
	
	//call "get user duplicate" from model user
	public boolean verifyUsuarioDuplicator(Usuario usuarioSignUp) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			Usuario usuarioValidator = session.createNamedQuery("Usuario.getUserDuplicate", Usuario.class)
					.setParameter("emailUsuario", usuarioSignUp.getEmailUsuario()).getSingleResult();

			if (usuarioValidator != null)
				return true;
			return false;
		} catch (NoResultException nre) {
			return false;
		} finally {
			session.close();
		}
	}

}
