package moviereview.dao.impl;

import moviereview.dao.CriteriaClause;
import moviereview.dao.DataHelper;
import moviereview.dao.QueryMethod;
import moviereview.util.ResultMessage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilverNarcissus on 16/11/12.<br>
 * DataHelper which use hibernate frame<br>
 * Pass test on 16/11/18<br>
 * Done on 16/11/18
 */

@Repository
public class HibernateHelper<T> implements DataHelper<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    private Class<T> type;


    public HibernateHelper() {
        //Configuration configuration = new Configuration();
        //sessionFactory = configuration.configure().buildSessionFactory();
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
        //this.sessionFactory= sessionFactory;

    }

    public void init(Class<T> type){
        this.type = type;
        session = sessionFactory.openSession();
    }
    /**
     * 初始化Session
     */
    private void setUpSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }


    @Override
    public ResultMessage save(Object o) {
        try {
            setUpSession();
            session.save(type.getName(), o);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage save(List<T> poList) {
        try {
            setUpSession();
            for (Object o : poList) {
                session.save(type.getName(), o);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }


    @Override
    public ResultMessage update(Object o) {
        try {
            setUpSession();
            session.update(type.getName(), o);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }


    @Override
    public ResultMessage delete(String key, String ID) {
        try {
            Object o = exactlyQuery(key, ID);
            if (o == null) {
                return ResultMessage.NOT_EXIST;
            }
            setUpSession();
            session.delete(type.getName(), o);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }


    @Override
    public T exactlyQuery(String field, Object value) {
        ArrayList<T> array = fullMatchQuery(field, value);
        return array.size() == 0 ? null : fullMatchQuery(field, value).get(0);
    }


    @Override
    public ArrayList<T> fullMatchQuery(String field, Object value) {
        try {
            Criteria criteria = SetUpCriteria();
            criteria.add(Restrictions.eq(field, value));
            @SuppressWarnings("unchecked")
            ArrayList<T> result = (ArrayList<T>) criteria.list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return new ArrayList<>();
        }
    }


    @Override
    public ArrayList<T> prefixMatchQuery(String field, String value) {
        value = value + "%";
        return likePatternQuery(field, value);
    }


    @Override
    public ArrayList<T> suffixMatchQuery(String field, String value) {
        value = "%" + value;
        return likePatternQuery(field, value);
    }


    @Override
    public ArrayList<T> fuzzyMatchQuery(String field, String value) {
        value = "%" + value + "%";
        return likePatternQuery(field, value);
    }


    @Override
    public ArrayList<T> rangeQuery(String field, Object min, Object max) {
        try {
            Criteria criteria = SetUpCriteria();
            criteria.add(Restrictions.between(field, min, max));
            ArrayList<T> arrayList = (ArrayList<T>) criteria.list();
            session.close();
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return new ArrayList<T>();
        }
    }

    /**
     * 生成一个新的匹配标准
     *
     * @return 新的匹配标准
     * @throws ClassNotFoundException 如果没有指定type，则抛出该异常
     */
    private Criteria SetUpCriteria() throws ClassNotFoundException {
        setUpSession();
        return session.createCriteria(type);
    }

    /**
     * 利用模糊查找返回符合条件的PO列表
     *
     * @param field 查询域
     * @param value 域值
     * @return PO列表
     */
    private ArrayList<T> likePatternQuery(String field, String value) {
        try {
            Criteria criteria = SetUpCriteria();
            criteria.add(Restrictions.like(field, value));
            ArrayList<T> arrayList = (ArrayList<T>) criteria.list();
            session.close();
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return new ArrayList<T>();
        }
    }

    @Override
    public ArrayList<T> multiCriteriaQuery(ArrayList<CriteriaClause> criteriaClauses) {
        //建立查询标准
        Criteria criteria = null;
        try {
            criteria = SetUpCriteria();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        for (CriteriaClause criteriaClause : criteriaClauses) {
            //完全匹配查询
            if (criteriaClause.getQueryMethod().equals(QueryMethod.Full)) {
                criteria.add(Restrictions.eq(criteriaClause.getField(), criteriaClause.getValue()));
                continue;
            }
            //范围查询
            if (criteriaClause.getQueryMethod().equals(QueryMethod.Range)) {
                criteria.add(Restrictions.between(criteriaClause.getField(), criteriaClause.getValue(), criteriaClause.getAnotherValue()));
            }
            //like样查询
            else {
                criteria.add(Restrictions.like(criteriaClause.getField(), criteriaClause.getKeyWord()));
            }
        }
        //进行查询,返回结果
        ArrayList<T> arrayList = (ArrayList<T>) criteria.list();
        session.close();
        return arrayList;
    }
}
