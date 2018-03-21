package dao;

import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void save(Application transientInstance) {
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


}
