package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.sound.midi.Soundbank;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Usuario;

public class UsuarioDAO {

	private static SessionFactory sessionFactory;

	// constructor for get session factory hibernate
	public UsuarioDAO() {
		sessionFactory = new BuildSessionFactory().getSessionFactory();
	}

	// insert user
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

	// call "get user" login from model user
	public Usuario signIn(String _email, String _password) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Usuario usuarioValidator = session.createNamedQuery("Usuario.getUserLogin", Usuario.class)
					.setParameter("emailUsuario", _email).setParameter("senhaUsuario", _password).getSingleResult();
			return usuarioValidator;
		} catch (NoResultException nre) {
			return null;
		} finally {
			session.close();
		}

	}

	// call "get user duplicate" from model user
	public boolean verifyUsuarioDuplicator(Usuario usuarioSignUp) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Usuario usuarioValidator = session.createNamedQuery("Usuario.getUserDuplicate", Usuario.class)
					.setParameter("emailUsuario", usuarioSignUp.getEmailUsuario()).getSingleResult();
			if (usuarioValidator != null) {
				return true;
			} else
				return false;
		} catch (NoResultException nre) {
			return false;
		} finally {
			session.close();
		}
	}

	// call "get user" login from model user
	public Usuario updateUsuario(Usuario user) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.refresh(user);
			return user;
		} catch (NoResultException nre) {
			return null;
		} finally {
			session.close();
		}

	}
}
