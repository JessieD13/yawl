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
 * Application entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see dao.Application
 * @author MyEclipse Persistence Tools
 */

public class ApplicationDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ApplicationDAO.class);
	// property constants
	public static final String APP_NAME = "appName";
	public static final String APP_TYPE = "appType";
	public static final String APP_URL = "appUrl";
	public static final String APP_DESC = "appDesc";

	public void save(Application transientInstance) {
		log.debug("saving Application instance");
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

	public void delete(Application persistentInstance) {
		log.debug("deleting Application instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Application findById(java.lang.Integer id) {
		log.debug("getting Application instance with id: " + id);
		try {
			Application instance = (Application) getSession().get(
					"dao.Application", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Application instance) {
		log.debug("finding Application instance by example");
		try {
			List results = getSession().createCriteria("dao.Application")
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
		log.debug("finding Application instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Application as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAppName(Object appName) {
		return findByProperty(APP_NAME, appName);
	}

	public List findByAppType(Object appType) {
		return findByProperty(APP_TYPE, appType);
	}

	public List findByAppUrl(Object appUrl) {
		return findByProperty(APP_URL, appUrl);
	}

	public List findByAppDesc(Object appDesc) {
		return findByProperty(APP_DESC, appDesc);
	}

	public List findAll() {
		log.debug("finding all Application instances");
		try {
			String queryString = "from Application";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Application merge(Application detachedInstance) {
		log.debug("merging Application instance");
		try {
			Application result = (Application) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Application instance) {
		log.debug("attaching dirty Application instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Application instance) {
		log.debug("attaching clean Application instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}