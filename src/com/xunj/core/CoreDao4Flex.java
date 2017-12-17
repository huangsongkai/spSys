package com.xunj.core;

import com.xunj.tag.TagUtil;
import com.xunj.util.Common;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class CoreDao4Flex
{
  private SessionFactory factory;
  private HibernateTemplate htemplate;
  private static final Log log = LogFactory.getLog(CoreDao4Flex.class);

  public SessionFactory getFactory() {
    return this.factory;
  }

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
    this.htemplate = new HibernateTemplate(factory);
  }

  public Session getSession() {
    return this.factory.getCurrentSession();
  }

  public HibernateTemplate getHtemplate() {
    return this.htemplate;
  }

  public List criteriaByPage(Class clazz, List<ParaUnit> lists, int pageNumber, int pageSize)
    throws Exception
  {
    Criteria criteria = getCriteria(clazz, lists, Boolean.valueOf(false));
    int rowCount = ((Integer)criteria
      .setProjection(Projections.rowCount()).uniqueResult())
      .intValue();
    List resultList = new ArrayList();

    String num = TagUtil.initSplitPage4Flex(rowCount, pageNumber, pageSize);
    resultList.add(Integer.valueOf(rowCount));
    resultList.add(Integer.valueOf(Integer.parseInt(num.split(",")[2])));
    criteria.setProjection(null);
    resultList.add(
      getCriteria(clazz, lists, Boolean.valueOf(true)).setFirstResult(Integer.parseInt(num.split(",")[0]))
      .setMaxResults(Integer.parseInt(num.split(",")[1])).list());
    return resultList;
  }

  public List criteriaQuery(Class clazz, List<ParaUnit> lists)
    throws Exception
  {
    return getCriteria(clazz, lists, Boolean.valueOf(true)).list();
  }

  public Criteria getCriteria(Class clazz, List<ParaUnit> lists, Boolean isOrder)
    throws Exception
  {
    Criteria criteria = getSession().createCriteria(clazz);

    int i = 1;

    Map names = new TreeMap();

    Object[] str = new Object[32];

    names.put("0", "0,table,table,-1");

    str[0] = criteria;

    String[] temp = (String[])null;
    String[] temp2 = (String[])null;

    String value = "";

    ProjectionList projectionList = Projections.projectionList();

    if (lists != null) {
      for (ParaUnit pu : lists)
      {
        int tempNum = 1;
        if ((((pu.getType().trim().equals("or")) || 
          (pu.getType().trim().equals("custom")))) && 
          (StringUtils.isNotBlank(pu.getKey()))) {
          tempNum = 0;
        }

        String ptable = "";

        Criteria pcriteria = (Criteria)str[0];
        temp = Common.getStringTokenizer(pu.getKey(), ".");
        int nums;
        if ((temp.length > 1) || (tempNum == 0)) {
          for (int num = 0; num < temp.length - tempNum; ++num)
          {
            Boolean type = Boolean.valueOf(true);
            if (num == 0)
            {
              ptable = "table";
            }
            else {
              ptable = temp[(num - 1)];
            }
            if (names != null)
            {
              for (nums = 0; nums < names.size(); ++nums)
              {
                temp2 = Common.getStringTokenizer(
                  (String)names
                  .get(nums), ",");
                if ((!(temp2[1].trim().equals(ptable))) || 
                  (!(temp2[2].trim().equals(temp[num]))) || 
                  (!(temp2[3].trim().equals(num))))
                  continue;
                pcriteria = (Criteria)str[
                  Integer.parseInt(temp2[0])];
                type = Boolean.valueOf(false);
                break;
              }

            }

            if (!(type.booleanValue()))
              continue;
            pcriteria = pcriteria.createCriteria(temp[num], 
              temp[num]);

            names.put(i, i + "," + ptable + "," + 
              temp[num] + "," + num);

            str[i] = pcriteria;

            ++i;
          }

          value = temp[(temp.length - 1)];

          criteria = pcriteria;
        }
        else {
          value = pu.getKey();

          criteria = (Criteria)str[0];
        }

        if (pu.getType().trim().equals("or"))
        {
          Object tempObj = Restrictions.disjunction();
          List<ParaUnit> pulist = pu.getPuList();
          for (ParaUnit pus : pulist)
          {
            ((Disjunction)tempObj).add(
              getCriterion(pus.getType(), 
              pus.getKey(), pus.getValue(), pu.getObj()));
          }

          criteria.add((Disjunction)tempObj);
        }
        else if (pu.getType().trim().equals("custom"))
        {
          String[] tempCustom = Common.getStringTokenizer(pu
            .getValue().toString(), ",");

          for (int numCustom = 0; numCustom < tempCustom.length; ++numCustom) {
            String[] tempTwoCustom = Common.getStringTokenizer(
              tempCustom[numCustom], "#");
            projectionList.add(
              Projections.property(tempTwoCustom[0]), tempTwoCustom[1]);
          }

        }
        else if (pu.getType().trim().equals("order")) {
          if (isOrder.booleanValue()) {
            if (pu.getValue().equals("asc"))
              criteria.addOrder(Order.asc(value));
            else if (pu.getValue().equals("desc")) {
              criteria.addOrder(Order.desc(value));
            }
          }
        }
        else
        {
          criteria.add(
            getCriterion(pu.getType(), value, 
            pu.getValue(), pu.getObj()));
        }
      }

    }

    if (projectionList.getLength() > 0) {
      ((Criteria)str[0]).setProjection(projectionList);
    }

    return ((Criteria)str[0]);
  }

  public Criterion getCriterion(String type, String key, Object value, Object[] obj)
  {
    Criterion criterion = null;

    if (type.trim().equals("null"))
    {
      criterion = Restrictions.isNull(key);
    }

    if (type.trim().equals("notNull"))
    {
      criterion = Restrictions.isNotNull(key);
    }

    if (type.trim().equals("ne"))
    {
      criterion = Restrictions.not(Restrictions.eq(key, value));
    }

    if (type.trim().equals("eq"))
    {
      criterion = Restrictions.eq(key, value);
    }

    if (type.trim().equals("ge"))
    {
      criterion = Restrictions.ge(key, value);
    }

    if (type.trim().equals("le"))
    {
      criterion = Restrictions.le(key, value);
    }

    if (type.trim().equals("gt"))
    {
      criterion = Restrictions.gt(key, value);
    }

    if (type.trim().equals("lt"))
    {
      criterion = Restrictions.lt(key, value);
    }

    if (type.trim().equals("like"))
    {
      criterion = Restrictions.like(key, value.toString(), 
        MatchMode.ANYWHERE);
    }

    if (type.trim().equals("rightLike"))
    {
      criterion = Restrictions.like(key, value.toString(), MatchMode.END);
    }

    if (type.trim().equals("leftLike"))
    {
      criterion = Restrictions.like(key, value.toString(), 
        MatchMode.START);
    }

    if (type.trim().equals("in"))
    {
      criterion = Restrictions.in(key, obj);
    }

    if (type.trim().equals("notIn"))
    {
      criterion = Restrictions.not(Restrictions.in(key, obj));
    }

    return criterion;
  }
}