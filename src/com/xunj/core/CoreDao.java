package com.xunj.core;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.xunj.action.common.BusinessException;
import com.xunj.po.SysLoginfo;
import com.xunj.tag.TagUtil;
import com.xunj.util.Common;

public class CoreDao
{
  //private static MemCachedClient mcc;
  private SessionFactory factory;
  private HibernateTemplate htemplate;
  private static final Log log = LogFactory.getLog(CoreDao.class);
  
  private boolean recordLog = true;

  public SessionFactory getFactory() {
    return this.factory;
  }

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
    this.htemplate = new HibernateTemplate(factory);
  }

  public org.hibernate.Session getSession() {
    return this.factory.getCurrentSession();
  }

  public HibernateTemplate getHtemplate() {
    return this.htemplate;
  }

  public String save(CorePo po)
    throws BusinessException
  {
	try{
	  String test = this.htemplate.save(po).toString();
	  return test;
	}catch(Exception e){
		throw new BusinessException(e);
	}
  }
  

  public String saveOrUpdate(CorePo po)
    throws BusinessException
  {
	try{
	    log.info("saving " + po.getClass().getName() + " instance");
	    this.htemplate.saveOrUpdate(po);
	    if (this.recordLog) {
	      saveLog(po, "保存或更新", null);
	    }
	    return "true";
    }catch(Exception e){
		throw new BusinessException(e);
	}
  }

  public void saveOrUpdateAll(List collection) throws BusinessException
  {
	try{
	    this.htemplate.saveOrUpdateAll(collection);
	    if (this.recordLog)
	      saveLog(collection, "保存或更新", null);
	}catch(Exception e){
		throw new BusinessException(e);
	}
  }

  public void saveAll(final List collection) throws BusinessException
  {
	  try{
	      htemplate.execute(new HibernateCallback() {
	      public Object doInHibernate(org.hibernate.Session session) throws HibernateException, SQLException {
	        for (int i = 0; i < collection.size(); ++i) {
	          session.save(collection.get(i));
	          if (i % 20 == 0) {
	            session.flush();
	            session.clear();
	          }
	        }
	        if (CoreDao.this.recordLog)
	          CoreDao.this.saveLog(collection, "批量保存", null);
	        return null;
	      }
	    });
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

  public void updateAll(final List collection)throws BusinessException
  {
	  try{
	    this.htemplate.execute(new HibernateCallback() {
	      public Object doInHibernate(org.hibernate.Session session) throws HibernateException, SQLException {
	        for (int i = 0; i < collection.size(); ++i) {
	          session.update(collection.get(i));
	          if (i % 20 == 0) {
	            session.flush();
	            session.clear();
	          }
	        }
	        if (CoreDao.this.recordLog)
	          CoreDao.this.saveLog(collection, "批量更新", null);
	        return null;
	      }
	    });
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

	public List findAll(String hql) throws BusinessException {
		try{
			log.debug("the query hql is : " + hql);
			List list = htemplate.find(hql);
			if(recordLog)
				saveLog(null,"查询",hql);
			return list;
		 }catch(Exception e){
				throw new BusinessException(e);
		 }
	}

  public List findAll(String hql, List<ParaUnit> lists)
    throws BusinessException
  {
	  try{
		    Query query = this.htemplate.getSessionFactory().getCurrentSession()
		      .createQuery(hql);
		    if (lists != null) {
		      for (ParaUnit pu : lists) {
		        if (pu.getType().equals("string"))
		          query.setString(pu.getKey(), (String)pu.getValue());
		        else if (pu.getType().equals("int"))
		          query.setInteger(pu.getKey(), Integer.parseInt(
		            (String)pu.getValue()));
		        else if (pu.getType().equals("double")) {
		          query.setDouble(pu.getKey(), Double.parseDouble(
		            (String)pu.getValue()));
		        }
		      }
		    }
		
		    List list = query.list();
		    return list;
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

  public List findByExample(CorePo po)  throws BusinessException
  {
	  try{
	    log.debug("finding " + po.getClass().getName() + 
	      " instance by example");
	    List results = this.htemplate.findByExample(po);
	
	    return results;
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

	/**
	 * ```````````````````````````````````
	 * @param hql
	 * @return
	 */
	public Long findCount(String hql)  throws BusinessException{
		try{
			log.debug("the query first hql is : " + hql);
			List list = htemplate.find(hql);
			if(recordLog)
				saveLog(null,"",hql);
			if(list!=null && list.size()>0){
				return (Long)list.get(0);
			}else{
				return Long.valueOf(0);
			}
		  }catch(Exception e){
				throw new BusinessException(e);
		  }

	}
	
	/**
	 * ```````````````````````````````````
	 * @param hql
	 * @return
	 */
	public Object findFirst(String hql) throws BusinessException{
		try{
			log.debug("the query first hql is : " + hql);
			List list = htemplate.find(hql);
			if(recordLog)
				saveLog(null,"",hql);
			if(list!=null &&  list.size()>0){
				try {
					return list.get(0);
				} catch (Exception e) {
					return null;
				}
			}else{
				return null;
			}
		  }catch(Exception e){
				throw new BusinessException(e);
		  }

	}
  
  public boolean update(CorePo po)
    throws BusinessException
  {
	  try{
		    boolean test = false;
		    this.htemplate.update(po);
		    if (this.recordLog)
		      saveLog(po, "更新", null);
		    test = true;
		    return test;
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

  public int bulkUpdate(String hql)
    throws BusinessException
  {
	  try{
		    log.debug("the query hql is : " + hql);
		    int result = this.htemplate.bulkUpdate(hql);
		    if (this.recordLog)
		      saveLog(null, "批量hql操作", hql);
		    return result;
	  }catch(Exception e){
			throw new BusinessException(e);
	  }

  }

  public List findByPage(final String hql)  throws BusinessException {
		return findByPage(hql,null);
	}
	public List findByPage(final String hql, 
			final List<ParaUnit> lists) throws BusinessException {
		try{
			return htemplate.executeFind(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException,
						SQLException {
					HttpServletRequest request = ServletActionContext.getRequest();
					ServletContext ctx = ServletActionContext.getServletContext();
	
					// 处理分页数据
					// 使用本方法返回查询需要的跳跃过的记录数与每页显示记录数 ***,***
	//				String[] hqlSplit = hql.split("from");
					String hqlAfter = StringUtils.substringAfter(hql, "from");
					Query query = s.createQuery("select count(*) from"+hqlAfter);
				
					int rowCount=Integer.valueOf(query.list().get(0).toString());
					
					String num[] = TagUtil.initSplitPage(ctx, rowCount, request,-1).split(",");
					
				    request.setAttribute("totalCount", rowCount);
				    
					int skipRow = Integer.parseInt(num[0]);
					int pageSize =  Integer.parseInt(num[1]);
					query = s.createQuery(hql);
					query.setFirstResult(skipRow);
					query.setMaxResults(pageSize);
	
					if (lists != null) {
						for (ParaUnit pu : lists) {
							if (pu.getType().equals("string"))
								query.setString(pu.getKey(), (String) pu
												.getValue());
							else if (pu.getType().equals("int"))
								query.setInteger(pu.getKey(), Integer
										.parseInt((String) pu.getValue()));
							else if (pu.getType().equals("long"))
								query.setLong(pu.getKey(), Long.parseLong((String) pu
										.getValue()));
							else if (pu.getType().equals("double"))
								query.setDouble(pu.getKey(), Double
										.parseDouble((String) pu.getValue()));
						}
					}
	
					List list = query.list();
					if(recordLog)
						saveLog(null,"查询",hql);
					return list;
				}
			});
		  }catch(Exception e){
				throw new BusinessException(e);
		  }
	}

  public int findRowCount(String hql, List<ParaUnit> lists)
    throws BusinessException
  {
	  try{
	    org.hibernate.Session session = this.factory.getCurrentSession();
	    Query query = session.createQuery(hql);
	    if (lists != null) {
	      for (ParaUnit pu : lists) {
	        if (pu.getType().equals("string"))
	          query.setString(pu.getKey(), (String)pu.getValue());
	        else if (pu.getType().equals("int"))
	          query.setInteger(pu.getKey(), Integer.parseInt(
	            (String)pu.getValue()));
	        else if (pu.getType().equals("long"))
	          query.setLong(pu.getKey(), Long.parseLong(
	            (String)pu.getValue()));
	        else if (pu.getType().equals("double"))
	          query.setDouble(pu.getKey(), Double.parseDouble(
	            (String)pu.getValue()));
	      }
	    }
	    List resultlist = query.list();
	    return Integer.parseInt(resultlist.get(0).toString());
	  }catch(Exception e){
			throw new BusinessException(e);
	  }

  }

  public int findRowCount(String hql)
    throws BusinessException
  {
	  try{
	    List resultlist = this.htemplate.find(hql);
	    return Integer.parseInt(resultlist.get(0).toString());
	  }catch(Exception e){
			throw new BusinessException(e);
	  }

  }

  public List criteriaByPage(Class clazz, List<ParaUnit> lists)
    throws BusinessException
  {
	  try{
	    HttpServletRequest request = ServletActionContext.getRequest();
	    ServletContext ctx = ServletActionContext.getServletContext();
	    Criteria criteria = getCriteria(clazz, lists, Boolean.valueOf(false));
	    int rowCount = ((Integer)criteria
	      .setProjection(Projections.rowCount()).uniqueResult())
	      .intValue();
	    if (lists!=null) {
	    	 for (int i = 0; i < lists.size(); i++) {
	    		 ParaUnit paraUnit =lists.get(i);
	    			System.out.println("paraUnit:======================"+paraUnit.getValue());
	    		}
		}
	   
	    String num = TagUtil.initSplitPage(ctx, rowCount, request, -1);
	    request.setAttribute("totalCount", rowCount);
	    criteria.setProjection(null);
	    return getCriteria(clazz, lists, Boolean.valueOf(true)).setFirstResult(
	      Integer.parseInt(num.split(",")[0])).setMaxResults(
	      Integer.parseInt(num.split(",")[1])).list();
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }
  public List criteriaAll(Class clazz, List<ParaUnit> lists)
  throws BusinessException
{
	  try{
	  HttpServletRequest request = ServletActionContext.getRequest();
	  ServletContext ctx = ServletActionContext.getServletContext();
	  Criteria criteria = getCriteria(clazz, lists, Boolean.valueOf(false));
	//  int rowCount = ((Integer)criteria
	//    .setProjection(Projections.rowCount()).uniqueResult())
	//    .intValue();
	  if (lists!=null) {
	  	 for (int i = 0; i < lists.size(); i++) {
	  		 ParaUnit paraUnit =lists.get(i);
	  			System.out.println("paraUnit:======================"+paraUnit.getValue());
	  		}
		}
	//  request.setAttribute("totalCount", rowCount);
	  criteria.setProjection(null);
	  return getCriteria(clazz, lists, Boolean.valueOf(true)).list();
	  }catch(Exception e){
			throw new BusinessException(e);
	  }

}
  public List criteriaCustomRowByPage(Class clazz, List<ParaUnit> lists, int rows)
    throws BusinessException
  {
	  try{
	    HttpServletRequest request = ServletActionContext.getRequest();
	    ServletContext ctx = ServletActionContext.getServletContext();
	    Criteria criteria = getCriteria(clazz, lists, Boolean.valueOf(false));
	    int rowCount = ((Integer)criteria
	      .setProjection(Projections.rowCount()).uniqueResult())
	      .intValue();
	
	    String num = TagUtil.initSplitPage(ctx, rowCount, request, rows);
	    criteria.setProjection(null);
	    return getCriteria(clazz, lists, Boolean.valueOf(true)).setFirstResult(
	      Integer.parseInt(num.split(",")[0])).setMaxResults(
	      Integer.parseInt(num.split(",")[1])).list();
	  }catch(Exception e){
			throw new BusinessException(e);
	  }

  }

  public List criteriaCustomRow(Class clazz, List<ParaUnit> lists, int rowNumber)
    throws BusinessException
  {
	  try{
	    HttpServletRequest request = ServletActionContext.getRequest();
	    ServletContext ctx = ServletActionContext.getServletContext();
	    Criteria criteria = getCriteria(clazz, lists, Boolean.valueOf(false));
	    int rowCount = ((Integer)criteria
	      .setProjection(Projections.rowCount()).uniqueResult())
	      .intValue();
	
	    String num = TagUtil.initSplitPage(ctx, rowCount, request, -1);
	    criteria.setProjection(null);
	    return getCriteria(clazz, lists, Boolean.valueOf(true)).setFirstResult(0).setMaxResults(rowNumber).list();
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
 }

  public List criteriaQuery(Class clazz, List<ParaUnit> lists)
    throws HibernateException, Exception
  {
    return getCriteria(clazz, lists, Boolean.valueOf(true)).list();
  }

  public List criteriaCustom(Class clazz, List<ParaUnit> lists)
    throws HibernateException, Exception
  {
    return getCriteria(clazz, lists, Boolean.valueOf(true)).setResultTransformer(
      AliasToEntityMapResultTransformer.INSTANCE).list();
    
  }
    
  public Criteria getCriteria(Class clazz, List<ParaUnit> lists,
			Boolean isOrder) throws Exception {   
		Criteria criteria = getSession().createCriteria(clazz);
		// 存放查询结果集序列号
		int i = 1;
		// 存放查询结果集相对表别级等元素  
		Map<String, String> names = new TreeMap<String, String>();
		// 存放查询结果集
		Object[] str = new Object[32];
		// str中存放查询结果集对的对应序号，上级表别称，本表别称，级数
		names.put("0", "0,table,table,-1");
		// 放入根查询结果集
		str[0] = criteria;
		// 拆分字符号专用
		String temp[] = null;
		String temp2[] = null;
		// 存表字段专用
		String value = "";
		// 存放投影字段
		ProjectionList projectionList = Projections.projectionList();

		// 如果条件为空则不处理
		if (lists != null) {
			for (ParaUnit pu : lists) {
				// 初始化 为1则是 and 为0则是or
				int tempNum = 1;
				if (pu.getType().trim().equals(ParaUnit.OR)//逻辑或条件
						|| pu.getType().trim().equals(ParaUnit.CUSTOM)) {// 自定义返回字段
					if (StringUtils.isNotBlank(pu.getKey())) {
						tempNum = 0;
					}
				}
				// 上级表别名
				String ptable = "";
				// 上级表结果集
				Criteria pcriteria = (Criteria) str[0];
				temp = Common.getStringTokenizer(pu.getKey(), ".");//根据.分成字符串数组
				if (temp.length > 1 || tempNum == 0) {
					for (int num = 0; num < temp.length - tempNum; num++) {
						// 标识
						Boolean type = true;
						if (num == 0) {
							// 获取顶级
							ptable = "table";
						} else {
							// 获取上一级
							ptable = temp[num - 1];
						}
						if (names != null) {
							// 循环是否是已存在的查询结果集
							for (int nums = 0; nums < names.size(); nums++) {
								// names: str中存放查询结果集对的对应序号，上级表别称，本表别称，级数
								temp2 = Common.getStringTokenizer(names
										.get(nums + ""), ",");
								if (temp2[1].trim().equals(ptable)
										&& temp2[2].trim().equals(temp[num])
										&& temp2[3].trim().equals(num + "")) {

									pcriteria = (Criteria) str[Integer
											.parseInt(temp2[0])];
									type = false;
									break;
								}
							}

						}
						// 如果为真
						if (type) {
							// 获取当前查询表的实例
							pcriteria = pcriteria.createCriteria(temp[num],
									temp[num]);
							// str中存放查询结果集对的对应序号，上级表别称，本表别称，级数
							names.put(i + "", i + "," + ptable + ","
									+ temp[num] + "," + num + "");
							// 存放查询结果集
							str[i] = pcriteria;
							// 查询结果级序列号 增1
							i++;
						}
					}
					// 获取字段名
					value = temp[temp.length - 1];
					// 获取当前查询表实例
					criteria = pcriteria;
				} else {
					// 获取字段名
					value = pu.getKey();
					// 获取根查询表实例
					criteria = (Criteria) str[0];
				}

				// 如果查询类型为逻辑或
				if (pu.getType().trim().equals(ParaUnit.OR)) {
					// 将逻辑或语句.实体化
					Object tempObj = Restrictions.disjunction();
					// 循环逻辑或查询条件
					for (ParaUnit pus : (List<ParaUnit>) pu.getPuList()) {
						// 获取逻辑或查询条件
						((Disjunction) tempObj).add(getCriterion(pus.getType(),
								pus.getKey(), pus.getValue(), pus.getObj(), pus.getDetachedCriteria()));
					}
					// 置入逻辑或条件
					criteria.add((Disjunction) tempObj);

					// 如果为投影
				} else if (pu.getType().trim().equals(ParaUnit.CUSTOM)) {

					// ,分割字段与字段,#分割字段与别名
					String[] tempCustom = Common.getStringTokenizer(pu
							.getValue().toString(), ",");

					for (int numCustom = 0; numCustom < tempCustom.length; numCustom++) {
						String[] tempTwoCustom = Common.getStringTokenizer(
								tempCustom[numCustom], "#");
						projectionList.add(Projections
								.property(tempTwoCustom[0]), tempTwoCustom[1]);
					}

					// 如果查询类型为排序
				} else if (pu.getType().trim().equals(ParaUnit.ORDER)) {
					if (isOrder) {
						if (pu.getValue().equals(ParaUnit.ASC)) {
							criteria.addOrder(Order.asc(value));
						} else if (pu.getValue().equals(ParaUnit.DESC)) {
							criteria.addOrder(Order.desc(value));
						}
					}

				} else {
					// 置入逻辑与条件
					criteria.add(getCriterion(pu.getType(), value, pu
							.getValue(), pu.getObj(),pu.getDetachedCriteria()));
				}
			}  
		}

		// 如果有投影则置入
		if (projectionList.getLength() > 0) {
			((Criteria) str[0]).setProjection(projectionList);
		}

		return ((Criteria) str[0]);
	}

  public Criterion getCriterion(String type, String key, Object value, Object[] obj, DetachedCriteria detachedCriteria)throws BusinessException
  {
	  try{
	    Criterion criterion = null;
	
	    if (type.trim().equals("null"))
	    {
	      criterion = Restrictions.isNull(key);
	    }
	
	    if (type.trim().equals("notNull"))
	    {
	      criterion = Restrictions.isNotNull(key);
	    }
	
	    if (type.trim().equals("ne")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).ne(detachedCriteria);
	      else {
	        criterion = Restrictions.not(Restrictions.eq(key, value));
	      }
	    }
	
	    if (type.trim().equals("eq")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).eq(detachedCriteria);
	      else {
	        criterion = Restrictions.eq(key, value);
	      }
	    }
	
	    if (type.trim().equals("ge")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).ge(detachedCriteria);
	      else {
	        criterion = Restrictions.ge(key, value);
	      }
	    }
	
	    if (type.trim().equals("le")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).le(detachedCriteria);
	      else {
	        criterion = Restrictions.le(key, value);
	      }
	    }
	
	    if (type.trim().equals("gt")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).gt(detachedCriteria);
	      else {
	        criterion = Restrictions.gt(key, value);
	      }
	    }
	
	    if (type.trim().equals("lt")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).lt(detachedCriteria);
	      else {
	        criterion = Restrictions.lt(key, value);
	      }
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
	
	    if (type.trim().equals("in")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).in(detachedCriteria);
	      else {
	        criterion = Restrictions.in(key, obj);
	      }
	    }
	
	    if (type.trim().equals("notIn")) {
	      if (detachedCriteria != null)
	        criterion = Property.forName(key).notIn(detachedCriteria);
	      else {
	        criterion = Restrictions.not(Restrictions.in(key, obj));
	      }
	    }
	    return criterion;
	  }catch(Exception e){
			throw new BusinessException(e);
	  }

  }

  public DetachedCriteria getDetachedCriteria(String propertyName, Class clazz, List<ParaUnit> lists, String SqlFuncName)throws BusinessException
  {
	  try{
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(clazz);
		    detachedCriteria.setProjection(getProjection(propertyName, SqlFuncName));
		
		    int i = 1;
		
		    Map names = new TreeMap();
		
		    Object[] str = new Object[32];
		
		    names.put("0", "0,table,table,-1");
		
		    str[0] = detachedCriteria;
		
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
		
		        DetachedCriteria pcriteria = (DetachedCriteria)str[0];
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
		                pcriteria = (DetachedCriteria)str[
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
		
		          detachedCriteria = pcriteria;
		        }
		        else {
		          value = pu.getKey();
		
		          detachedCriteria = (DetachedCriteria)str[0];
		        }
		
		        if (pu.getType().trim().equals("or"))
		        {
		          Object tempObj = Restrictions.disjunction();
		          List<ParaUnit> puList = pu.getPuList();
		          for (ParaUnit pus : puList)
		          {
		            ((Disjunction)tempObj).add(
		              getCriterion(pus.getType(), 
		              pus.getKey(), pus.getValue(), pu.getObj(), pu.getDetachedCriteria()));
		          }
		
		          detachedCriteria.add((Disjunction)tempObj);
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
		          if (pu.getValue().equals("asc"))
		            detachedCriteria.addOrder(Order.asc(value));
		          else if (pu.getValue().equals("desc")) {
		            detachedCriteria.addOrder(Order.desc(value));
		          }
		        }
		        else
		        {
		          detachedCriteria.add(
		            getCriterion(pu.getType(), value, 
		            pu.getValue(), pu.getObj(), pu.getDetachedCriteria()));
		        }
		      }
		
		    }
		
		    if (projectionList.getLength() > 0) {
		      ((Criteria)str[0]).setProjection(projectionList);
		    }
		
		    return ((DetachedCriteria)str[0]);
	  }catch(Exception e){
			throw new BusinessException(e);
	  }

  }

  private Projection getProjection(String propertyName, String SqlFuncName)throws BusinessException
  {
	  try{
	    if (SqlFuncName == null) {
	      return Property.forName(propertyName);
	    }
	
	    if (SqlFuncName.equals("avg"))
	      return Property.forName(propertyName).avg();
	    if (SqlFuncName.equals("count"))
	      return Property.forName(propertyName).count();
	    if (SqlFuncName.equals("max"))
	      return Property.forName(propertyName).max();
	    if (SqlFuncName.equals("min")) {
	      return Property.forName(propertyName).min();
	    }
	    return Property.forName(propertyName);
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

  public Object findOne(Class clazz, Object id)throws BusinessException
  {
	  try{
	    Object obj = this.htemplate.get(clazz, (Serializable)id);
	    return obj;
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

  public void delete(CorePo po)throws BusinessException {
	  try{
	    if (this.recordLog)
	      saveLog(po, "删除", null);
	    this.htemplate.delete(po);
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

  public List<Map<String, Object>> executeQuery(final String queryString)throws BusinessException 
  {
	  try{
	    log.info(queryString);
	
	    List dataList = (List)this.htemplate
	      .execute(new HibernateCallback()
	    {
	      public Object doInHibernate(org.hibernate.Session session) throws HibernateException, SQLException {
	        List dataList = new ArrayList();
	        Connection con = session.connection();
	        Statement stm = con.createStatement();
	        try {
	          ResultSet rs = stm.executeQuery(queryString);
	          ResultSetMetaData rt = rs.getMetaData();
	
	          while (rs.next()) {
	            Map col = new HashMap();
	            for (int i = 1; i <= rt.getColumnCount(); ++i) {
	              col.put(rt.getColumnLabel(i).toUpperCase(), 
	                rs.getObject(i));
	            }
	            dataList.add(col);
	          }
	        } catch (SQLException e) {
	          throw e;
	        } finally {
	          stm.close();
	        }
	
	        return dataList;
	      }
	    });
	    if (this.recordLog)
	      saveLog(null, "其它", queryString);
	    return dataList;
	  }catch(Exception e){
			throw new BusinessException(e);
	  }
  }

  public void saveLog(Object obj, String action, String logText)
  {
	  log.info(logText);
    TableLog tlog = new TableLog();
    if (!(tlog.getLogSwitch()))
      return;
    SysLoginfo vo = tlog.organizLog(obj, action, logText);
    vo.setState("A");
    this.htemplate.save(vo);
  }

  public boolean isRecordLog()
  {
    return this.recordLog;
  }

  public void setRecordLog(boolean recordLog) {
    this.recordLog = recordLog;
  }

	/**
	 * 查询表中列的最大值+1
	 * @param tableName
	 * @param columnName
	 * @return
	 * @throws BusinessException
	 */
	public String getNewTableId(String tableName,String columnName,String where,int length) throws BusinessException {
		try{
			String returnValue = "1";
			List maxlist = this.findAll("select max("+columnName+") as "+columnName +"  from "+tableName+" "+where );
			for (Iterator it = maxlist.iterator(); it.hasNext();) {
				Object value = it.next();
				if (value != null)
					returnValue=(String)value;
			}
			returnValue=Common.createSequence(returnValue, "", length);
			
			return returnValue;
		 }catch(Exception e){
				throw new BusinessException(e);
		  }
	}
	
	
}