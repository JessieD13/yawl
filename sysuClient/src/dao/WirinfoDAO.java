package dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Wirinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see dao.Wirinfo
 * @author MyEclipse Persistence Tools
 */

public class WirinfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(WirinfoDAO.class);
	// property constants
	public static final String ITEMID = "itemid";
	public static final String LOCATION = "location";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String REGION = "region";
	public static final String DELAY_FACTOR = "delayFactor";
	public static final String APPOINTMENT_TIME = "appointmentTime";
	public static final String CONSUMING = "consuming";

	public void save(Wirinfo transientInstance) {
		log.debug("saving Wirinfo instance");
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

	public void delete(Wirinfo persistentInstance) {
		log.debug("deleting Wirinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Wirinfo findById(java.lang.Integer id) {
		log.debug("getting Wirinfo instance with id: " + id);
		try {
			Wirinfo instance = (Wirinfo) getSession().get("dao.Wirinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Wirinfo instance) {
		log.debug("finding Wirinfo instance by example");
		try {
			List results = getSession().createCriteria("dao.Wirinfo")
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
		log.debug("finding Wirinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Wirinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemid(Object itemid) {
		return findByProperty(ITEMID, itemid);
	}

	public List findByLocation(Object location) {
		return findByProperty(LOCATION, location);
	}

	public List findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}

	public List findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	public List findByRegion(Object region) {
		return findByProperty(REGION, region);
	}

	public List findByDelayFactor(Object delayFactor) {
		return findByProperty(DELAY_FACTOR, delayFactor);
	}

	public List findByAppointmentTime(Object appointmentTime) {
		return findByProperty(APPOINTMENT_TIME, appointmentTime);
	}

	public List findByConsuming(Object consuming) {
		return findByProperty(CONSUMING, consuming);
	}

	public List findAll() {
		log.debug("finding all Wirinfo instances");
		try {
			String queryString = "from Wirinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Wirinfo merge(Wirinfo detachedInstance) {
		log.debug("merging Wirinfo instance");
		try {
			Wirinfo result = (Wirinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Wirinfo instance) {
		log.debug("attaching dirty Wirinfo instance");
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

	public void attachClean(Wirinfo instance) {
		log.debug("attaching clean Wirinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}