package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import model.Despesa;
import model.Usuario;

public class DespesaDAO {

	private static SessionFactory sessionFactory;

	public DespesaDAO() {
		sessionFactory = new BuildSessionFactory().getSessionFactory();
	}

	// return all despesa by user logged
	public List<Despesa> getAllDespesa(Usuario _usuario) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			return session.createNamedQuery("Despesa.getAllDespesa", Despesa.class)
					.setParameter("usuarioDespesa", _usuario).getResultList();
		} catch (HibernateException he) {
			throw new RuntimeException(he);
		} finally {
			session.close();
		}
	}

	// save Despesa
	public void saveDespesa(Object o) {
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

	// merge Despesa
	public void mergeDespesa(Object o) {
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

	// delete Despesa
	public void deleteDespesa(Object o) {
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
