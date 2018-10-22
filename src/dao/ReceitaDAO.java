package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Despesa;
import model.Receita;
import model.Usuario;

public class ReceitaDAO {

	private SessionFactory sessionFactory;

	public ReceitaDAO() {
		sessionFactory = new BuildSessionFactory().getSessionFactory();
	}

	// return all despesa by user logged
	public List<Receita> getAllReceita(Usuario _usuario) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			return session.createNamedQuery("Receita.getAllReceita", Receita.class)
					.setParameter("usuarioReceita", _usuario).getResultList();
		} catch (HibernateException he) {
			throw new RuntimeException(he);
		} finally {
			session.close();
		}
	}

	// save Receita
	public void saveReceita(Object o) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.save(o);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			throw new RuntimeException(he);
		} finally {
			session.close();
		}
	}

	// merge Receita
	public void mergeReceita(Object o) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.merge(o);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			throw new RuntimeException(he);
		} finally {
			session.close();
		}
	}

	// delete Receita
	public void deleteReceita(Object o) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.delete(o);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			throw new RuntimeException(he);
		} finally {
			session.close();
		}
	}

}
