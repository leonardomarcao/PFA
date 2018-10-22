package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import model.TipoReceita;

public class TipoReceitaDAO {

	private SessionFactory sessionFactory;

	public TipoReceitaDAO() {
		sessionFactory = new BuildSessionFactory().getSessionFactory();
	}

	public List<TipoReceita> getAllTipoReceita() {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			return session.createNamedQuery("TipoReceita.getAllTipoReceita", TipoReceita.class).getResultList();
		} catch (HibernateException he) {
			throw new RuntimeException(he);
		} finally {
			session.close();
		}
	}

	public void saveTipoReceita(Object o) {
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

}
