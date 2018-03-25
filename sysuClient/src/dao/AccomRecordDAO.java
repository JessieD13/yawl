package dao;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by apple on 2018/3/19.
 */
public class AccomRecordDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory
            .getLogger(AccomRecordDAO.class);
    // property constants
    public static final String USER_ID = "userId";
    public static final String SPEC_NAME = "specName";
    public static final String TASK_NAME = "taskName";
    public static final String AVER_EXE_TIME = "averageExeTime";
    public static final String COMPLETED_TIMES = "completedTimes";

    public void save(AccomRecord transientInstance) {
        log.debug("saving AccomRecord instance");
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
        log.debug("deleting AccomRecord instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AccomRecord findById(java.lang.Integer id) {
        log.debug("getting AccomRecord instance with id: " + id);
        try {
            AccomRecord instance = (AccomRecord) getSession().get(
                    "dao.AccomRecord", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AccomRecord instance) {
        log.debug("finding AccomRecord instance by example");
        try {
            List results = getSession().createCriteria("dao.AccomRecord")
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
        log.debug("finding AccomRecord instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from AccomRecord as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByUserId(Object userid) {
        return findByProperty(USER_ID, userid);
    }

    public List findBySpecName(Object spec) {
        return findByProperty(SPEC_NAME, spec);
    }

    public List findByTaskName(Object task) {
        return findByProperty(TASK_NAME, task);
    }

    public List findBySpecAndTask(Object spec, Object task) {
        try {
            String queryString = "from AccomRecord as model where model."
                    + SPEC_NAME + "= ? and model." + TASK_NAME + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, spec);
            queryObject.setParameter(1, task);
            return queryObject.list();
        }catch (RuntimeException re) {
            log.error("find by spec and task failed", re);
            throw re;
        }
    }

    public List findRecord(Object userid, Object spec, Object task) {
        try {
            String queryString = "from AccomRecord as model where model."
                    + USER_ID + " = ? and model." + SPEC_NAME + " = ? and model."
                    + TASK_NAME + " = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, userid);
            queryObject.setParameter(1, spec);
            queryObject.setParameter(2, task);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find record failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all AccomRecord instances");
        try {
            String queryString = "from AccomRecord";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AccomRecord merge(AccomRecord detachedInstance) {
        log.debug("merging AccomRecord instance");
        try {
            AccomRecord result = (AccomRecord) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AccomRecord instance) {
        log.debug("attaching dirty AccomRecord instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AccomRecord instance) {
        log.debug("attaching clean AccomRecord instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void updateAverExeTime(String userid, String spec, String task, String time, Integer times) {
        Transaction tran = getSession().beginTransaction();
        try {
            String sql = "update AccomRecord ar set ar." + AVER_EXE_TIME + "= ? , ar."+
                    COMPLETED_TIMES + "= ? where ar."
                    + USER_ID + "= ? and ar." + SPEC_NAME + "= ? and  ar."
                    + TASK_NAME + "= ?";
            Query queryObject = getSession().createQuery(sql);
            queryObject.setParameter(0, time);
            queryObject.setParameter(1, times);
            queryObject.setParameter(2, userid);
            queryObject.setParameter(3, spec);
            queryObject.setParameter(4, task);
            queryObject.executeUpdate();
        } catch (RuntimeException re) {
            throw re;
        }
        tran.commit();
        getSession().flush();
        getSession().close();
    }
}
