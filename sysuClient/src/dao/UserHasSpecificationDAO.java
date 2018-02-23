package dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserHasSpecification entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see dao.UserHasSpecification
 * @author MyEclipse Persistence Tools
 */

public class UserHasSpecificationDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserHasSpecificationDAO.class);

	Session session = HibernateSessionFactory.getSession(); 
	// property constants

	public void save(UserHasSpecification transientInstance) {
		log.debug("saving UserHasSpecification instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserHasSpecification persistentInstance) {
		log.debug("deleting UserHasSpecification instance");
		try {
	        session.beginTransaction();
	        UserHasSpecification uhs = (UserHasSpecification)session.load(UserHasSpecification.class, persistentInstance.getId()); 
	        session.delete(uhs);
	        session.getTransaction().commit();    //同时更新数据库
			// getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserHasSpecification findById(dao.UserHasSpecificationId id) {
		log.debug("getting UserHasSpecification instance with id: " + id);
		try {
			UserHasSpecification instance = (UserHasSpecification) getSession()
					.get("dao.UserHasSpecification", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserHasSpecification instance) {
		log.debug("finding UserHasSpecification instance by example");
		try {
			List results = getSession()
					.createCriteria("dao.UserHasSpecification")
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
		log.debug("finding UserHasSpecification instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from UserHasSpecification as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all UserHasSpecification instances");
		try {
			String queryString = "from UserHasSpecification";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserHasSpecification merge(UserHasSpecification detachedInstance) {
		log.debug("merging UserHasSpecification instance");
		try {
			UserHasSpecification result = (UserHasSpecification) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserHasSpecification instance) {
		log.debug("attaching dirty UserHasSpecification instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserHasSpecification instance) {
		log.debug("attaching clean UserHasSpecification instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}