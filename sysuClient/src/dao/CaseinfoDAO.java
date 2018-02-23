package dao;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Caseinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see dao.Caseinfo
 * @author MyEclipse Persistence Tools
 */
public class CaseinfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CaseinfoDAO.class);
	// property constants
	public static final String CASEID = "caseid";
	public static final String PRIORITY = "priority";
	public static final String DIFFICULTY = "difficulty";
	public static final String CLIENT_LEVEL = "clientLevel";

	public void save(Caseinfo transientInstance) {
		log.debug("saving Caseinfo instance");
		Transaction tran=getSession().beginTransaction();
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		tran.commit();
        getSession().flush(); 
        getSession().close();
	}

	public void delete(Caseinfo persistentInstance) {
		log.debug("deleting Caseinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Caseinfo findById(java.lang.Integer id) {
		log.debug("getting Caseinfo instance with id: " + id);
		try {
			Caseinfo instance = (Caseinfo) getSession().get("dao.Caseinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Caseinfo instance) {
		log.debug("finding Caseinfo instance by example");
		try {
			List results = getSession().createCriteria("dao.Caseinfo")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Caseinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Caseinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCaseid(Object caseid) {
		return findByProperty(CASEID, caseid);
	}

	public List findByPriority(Object priority) {
		return findByProperty(PRIORITY, priority);
	}

	public List findByDifficulty(Object difficulty) {
		return findByProperty(DIFFICULTY, difficulty);
	}

	public List findByClientLevel(Object clientLevel) {
		return findByProperty(CLIENT_LEVEL, clientLevel);
	}

	public List findAll() {
		log.debug("finding all Caseinfo instances");
		try {
			String queryString = "from Caseinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Caseinfo merge(Caseinfo detachedInstance) {
		log.debug("merging Caseinfo instance");
		try {
			Caseinfo result = (Caseinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Caseinfo instance) {
		log.debug("attaching dirty Caseinfo instance");
		Transaction tran=getSession().beginTransaction();
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
		tran.commit();
        getSession().flush(); 
        getSession().close();
	}

	public void attachClean(Caseinfo instance) {
		log.debug("attaching clean Caseinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}