package dao;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

/**
 * Created by apple on 2018/3/3.
 */
public class ExeQueueDAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory
        .getLogger(CaseinfoDAO.class);
    // property constants
    public static final String WIR_ID = "wir_id";
    public static final String ORDER = "exe_order";

    public void save(ExeQueue transientInstance) {
        log.debug("saving ExeQueue instance");
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

    public void update(String sql, Object[] params) {
        Query query = getSession().createQuery(sql);
        for (int i=0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        query.executeUpdate();
    }



    public void updateOrder(int newOrder, String wirid) {
        Transaction tran = getSession().beginTransaction();
        try {
            String sql = "update ExeQueue e set e.exe_order = ? where e.wir_id = ?";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, newOrder);
            query.setParameter(1, wirid);
            query.executeUpdate();
        } catch (RuntimeException re) {
            throw re;
        }
        tran.commit();
        getSession().flush();
        getSession().close();
    }

    public void updateWhenDelete(int delOrder) {
        Transaction tran = getSession().beginTransaction();
        try {
            List allNext = findAllNextOrder(delOrder);
            if (!allNext.isEmpty()) {
                for (int i = 0; i < allNext.size(); i++) {
                    String sql = "update ExeQueue e set e.exe_order = ? where e.wir_id = ?";
                    Query query = getSession().createQuery(sql);
                    ExeQueue e = (ExeQueue) allNext.get(i);
                    int newOrder = e.getOrder() - 1;
                    query.setParameter(0, newOrder);
                    query.setParameter(1, e.getWir_id());
                    query.executeUpdate();
                }
            }
        } catch (RuntimeException re){
            throw re;
        }
        tran.commit();
        getSession().flush();
        getSession().close();
    }

    public void delete(ExeQueue persistentInstance) {
        log.debug("deleting ExeQueue instance");
        try {
            int delOrder = persistentInstance.getOrder();
            getSession().delete(persistentInstance);
            updateWhenDelete(delOrder);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ExeQueue findById(java.lang.Integer id) {
        log.debug("getting ExeQueue instance with id: " + id);
        try {
            ExeQueue instance = (ExeQueue) getSession().get("dao.ExeQueue", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ExeQueue instance) {
        log.debug("finding ExeQueue instance by example");
        try {
            List results = getSession().createCriteria("dao.ExeQueue")
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
        log.debug("finding ExeQueue instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from ExeQueue as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByWir_id(Object wir_id) {
        return findByProperty(WIR_ID, wir_id);
    }
    public List findByOrder(Object order) {
        return findByProperty(ORDER, order);
    }

    public List findNextOrder(int order) {
        int nextOrder = order+1;
        return findByOrder(nextOrder);
    }

    public List findPreOrder(int order) {
        int preOrder = order-1;
        return findByOrder(preOrder);
    }

    public List findAllNextOrder(Object value) {
        try {
            String queryString = "from ExeQueue as model where model."
                    + ORDER + "> ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            List l = queryObject.list();
            System.out.println("\n"+l.size()+"\n");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all ExeQueue instances");
        try {
            String queryString = "from ExeQueue";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ExeQueue merge(ExeQueue detachedInstance) {
        log.debug("merging ExeQueue instance");
        try {
            ExeQueue result = (ExeQueue) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ExeQueue instance) {
        log.debug("attaching dirty ExeQueue instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ExeQueue instance) {
        log.debug("attaching clean ExeQueue instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public int getCount() {
        log.debug("get count");
        try {
            String queryString = "from ExeQueue";
            Query queryObject = getSession().createQuery(queryString);
            List<ExeQueue> list = queryObject.list();
            if (list == null) return 0;
            return list.size();
        } catch (RuntimeException re) {
            log.error("get count failed", re);
            throw re;
        }
    }
}
