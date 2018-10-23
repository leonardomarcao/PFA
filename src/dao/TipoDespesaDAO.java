package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.TipoDespesa;
import model.TipoReceita;

public class TipoDespesaDAO {

	private SessionFactory sessionFactory;

	public TipoDespesaDAO() {
		sessionFactory = new BuildSessionFactory().getSessionFactory();
	}

	public List<TipoDespesa> getAllTipoDespesa() {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			return session.createNamedQuery("TipoDespesa.getAllTipoDespesa", TipoDespesa.class).getResultList();
		} catch (HibernateException he) {
			throw new RuntimeException(he);
		} finally {
			session.close();
		}
	}
	
	public boolean verifyExistsTipoDespesa(String _nomeTipoDespesa) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			TipoDespesa tipoDespesa = session.createNamedQuery("TipoDespesa.verifyExistsTipoDespesa", TipoDespesa.class)
					.setParameter("nomeTipoDespesa", _nomeTipoDespesa)
					.uniqueResult();
			if (tipoDespesa != null)
				return true;
			else
				return false;
		} catch (HibernateException he) {			
			return false;
		} finally {
			session.close();
		}		
	}

	public void saveTipoDespesa(Object o) {
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
