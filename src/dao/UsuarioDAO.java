package dao;


import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Endereco;
import model.Usuario;

public class UsuarioDAO {
	
	private static SessionFactory sessionFactory;	
	
	public UsuarioDAO() {
		sessionFactory = new BuildSessionFactory().getSessionFactory();		
	}
	
	public void insertUsuario(Usuario usuario) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();			
			session.save(usuario);
			session.getTransaction().commit();
		}finally {
			session.close();	
			sessionFactory.close();
		}
		
	}
	
	public boolean signIn(Usuario usuarioLogin) {
		Session session = sessionFactory.getCurrentSession();
		try {	
			session.getTransaction().begin();

			Usuario usuarioValidator = (Usuario) session.createNamedQuery("Usuario.getUserLogin", Usuario.class)
		    		.setParameter("nomeUsuario", usuarioLogin.getNomeUsuario())
		    		.setParameter("senhaUsuario", usuarioLogin.getSenhaUsuario())
		    		.getSingleResult();
		    	    
			if (usuarioValidator != null) return true;
			return false;
		}catch (NoResultException nre) {
			return false;
		}
		finally {
			session.close();
		}
	}

}
