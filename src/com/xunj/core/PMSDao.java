package com.xunj.core;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.xunj.po.SysLoginfo;
import com.xunj.tag.TagUtil;

/**
 * ����Dao����������Dao����
 * 
 * @author  
 * 
 */
public class PMSDao {

	private SessionFactory factory;

	private HibernateTemplate htemplate;

	private static final Log log = LogFactory.getLog(CoreDao.class);
	
	private boolean recordLog = true;

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
		htemplate = new HibernateTemplate(factory);
	}

	public Session getSession() {
		return factory.getCurrentSession();
	}

	public HibernateTemplate getHtemplate() {
		return htemplate;
	}

	/**
	 * ���浥һVO����
	 * 
	 * @param CorePo
	 * @return �������������ݵ�����ֵ
	 */
	public String save(CorePo po) throws Exception {
		log.info("saving " + po.getClass().getName() + " instance");
		String test = htemplate.save(po).toString();
		if(recordLog)
			this.saveLog(po, "����",null);
		return test;
	}
	/**
	 * �޸Ļ�������
	 * @param CorePo
	 * @return 
	 */
	public String saveOrUpdate(CorePo po) throws Exception {
		log.info("saving " + po.getClass().getName() + " instance");
		htemplate.saveOrUpdate(po);
		if(recordLog)
			this.saveLog(po, "����", null);
		
		return "true";
	}

	/**
	 * ������������
	 * 
	 * @param collection
	 */
	public void saveOrUpdateAll(List collection) {
		htemplate.saveOrUpdateAll(collection);
	}
	
	public void saveAll(final List collection) { 
		htemplate.execute(new HibernateCallback() { 
			public Object doInHibernate(Session session) throws HibernateException, SQLException {                
				for (int i = 0; i < collection.size(); i++) { 
					session.save(collection.get(i)); 
					if (i % 20 == 0) { 
						session.flush(); 
						session.clear(); 
					} 
				}                
				return null; 
			} 
		}); 
	}

	/**
	 * ���ݴ����hql����ѯ��������
	 * 
	 * @param hql
	 * @return
	 */
	public List findAll(String hql) throws Exception {
		log.debug("the query hql is : " + hql);
		List list = htemplate.find(hql);
		if(recordLog)
			saveLog(null,"��ѯ",hql);
		return list;
	}

	/**
	 * ���ݴ����hql����ѯ��������
	 * 
	 * @param hql
	 * @return
	 */
	public List findAll(String hql, List<ParaUnit> lists) throws Exception {
		Query query = htemplate.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		if (lists != null) {
			for (ParaUnit pu : lists) {
				if (pu.getType().equals("string"))
					query.setString(pu.getKey(), (String) pu.getValue());
				else if (pu.getType().equals("int"))
					query.setInteger(pu.getKey(), Integer.parseInt((String) pu
							.getValue()));
				else if (pu.getType().equals("double"))
					query.setDouble(pu.getKey(), Double.parseDouble((String) pu
							.getValue()));
			}
		}
		if(recordLog)
			saveLog(null,"��ѯ",hql);
		List list = query.list();
		return list;
	}

	/**
	 * ���ݴ����pojo��ѯ��������ͬ��Ϊ�յ�������Ϊ��ѯ����
	 * ***************ע�⣺pojo��id��Ϊ��IDҲ�����ٲ�ѯ�����г���*************
	 * 
	 * @param po
	 * @return
	 */
	public List findByExample(CorePo po) {
		log.debug("finding " + po.getClass().getName()
						+ " instance by example");
		List results = htemplate.findByExample(po);
		if(recordLog)
			saveLog(po,"��ѯ","");
		return results;
	}

	/**
	 * ���µ�һDTO����
	 * 
	 * @param transientInstance
	 */
	public boolean update(CorePo po) throws Exception {
		// log.debug("saving " + po.getClass().getName() + " instance");
		boolean test = false;
		htemplate.update(po);
		if(recordLog)
			saveLog(po, "����", null);
		test = true;
		return test;
	}

	/**
	 * ִ����������
	 * 
	 * @param hql
	 * @return
	 */
	public int bulkUpdate(String hql) throws Exception {
		log.debug("the query hql is : " + hql);
		int result = htemplate.bulkUpdate(hql);
		if(recordLog)
			saveLog(null,"����",hql);
		return result;
	}

	/**
	 * ��ѯ,��ҳ
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByPage(final String hql, final int rowCount,
			final List<ParaUnit> lists) throws Exception {
		return htemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				HttpServletRequest request = ServletActionContext.getRequest();
				ServletContext ctx = ServletActionContext.getServletContext();

				// �����ҳ����
				// ʹ�ñ��������ز�ѯ��Ҫ����Ծ���ļ�¼����ÿҳ��ʾ��¼�� ***,***
				String num[] = TagUtil.initSplitPage(ctx, rowCount, request,-1).split(",");
				int skipRow = Integer.parseInt(num[0]);
				int pageSize =  Integer.parseInt(num[1]);
				Query query = s.createQuery(hql);
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
					saveLog(null,"��ѯ",hql);
				return list;
			}
		});
	}
	/**
	 * hql��ҳ��ѯ������ָ����ÿҳ�������в�ѯ
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findCustomRowByPage(final String hql, final int rowCount,
			final List<ParaUnit> lists,final int pageSize) throws Exception {
		return htemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
			SQLException {
				HttpServletRequest request = ServletActionContext.getRequest();
				ServletContext ctx = ServletActionContext.getServletContext();
				
				// �����ҳ����
				// ʹ�ñ��������ز�ѯ��Ҫ����Ծ���ļ�¼����ÿҳ��ʾ��¼�� ***,***
				String num[] = TagUtil.initSplitPage(ctx, rowCount, request,pageSize).split(",");
				int skipRow = Integer.parseInt(num[0]);
				int pageSize =  Integer.parseInt(num[1]);
				Query query = s.createQuery(hql);
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
					saveLog(null,"��ѯ",hql);
				return list;
			}
		});
	}

	/**
	 * ��ѯ,��ҳ
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByPage(final String hql, final int pageSize,
			final int startRow) throws Exception {
		return htemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Query query = s.createQuery(hql);
				query.setFirstResult(startRow);
				query.setMaxResults(pageSize);
				List list = query.list();
				if(recordLog)
					saveLog(null,"��ѯ",hql);
				return list;
			}
		});
	}

	/**
	 * ��ѯ����,��ҳ
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public int findRowCount(String hql, List<ParaUnit> lists) throws Exception {

		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		if (lists != null) {
			for (ParaUnit pu : lists) {
				if (pu.getType().equals("string"))
					query.setString(pu.getKey(), (String) pu.getValue());
				else if (pu.getType().equals("int"))
					query.setInteger(pu.getKey(), Integer.parseInt((String) pu
							.getValue()));
				else if (pu.getType().equals("long"))
					query.setLong(pu.getKey(), Long.parseLong((String) pu
							.getValue()));
				else if (pu.getType().equals("double"))
					query.setDouble(pu.getKey(), Double.parseDouble((String) pu
							.getValue()));
			}
		}
		List resultlist = query.list();
		return Integer.parseInt(resultlist.get(0).toString());

	}

	/**
	 * ��ѯ����,��ҳ
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public int findRowCount(String hql) throws Exception {

		List resultlist = htemplate.find(hql);
		return Integer.parseInt(resultlist.get(0).toString());

	}

	/**
	 * ��ѯ����,��ҳ
	 * 
	 * key: 1.���ֶ� 2.���������.���ֶ� value: null.�� notNull.�����ڿ� ne ������ eq.���� ge.���ڵ���
	 * le.С�ڵ��� gt.���� lt.С�� like.�м�ģ�� leftLike ��ģ�� rightLike ��ģ�� in ��ʲôʲô�� notIn
	 * ����ʲôʲô�� order ����
	 * 
	 * paraUnit : setKey( ���ֶ� ��: name) ,setValue( ����ֵ ��: ���� ) ,setType ( ��ѯ��������
	 * ��:eq )
	 * 
	 * paraUnit : setKey( ����) ,setPuList(�߼��������б� �� list.add(new
	 * ParaUnit(�ֶ�����:name,ֵ��:����,��ѯ������: like)) ) ,setType ( ��ѯ�������� ��:or )
	 * ParaUnit<String>(����:��"table"���Ǹ�����"",
	 * ,�ָ��ֶ����ֶ�,#�ָ��ֶ��������"registerId#registerId,registerType#registerType",����Ϊ�Զ���ͶӰ
	 * ParaUnit.CUSTOM);�ֶ�ͶӰ
	 * 
	 * @param src
	 * @param lists
	 * @param pageSize
	 * @param startRow
	 * @return
	 * @throws Exception
	 */
	public List criteriaByPage(Class clazz, List<ParaUnit> lists)
			throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext ctx = ServletActionContext.getServletContext();
		Criteria criteria = getCriteria(clazz, lists, false);
		int rowCount = ((Integer) criteria
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		// �����ҳ����
		// ʹ�ñ��������ز�ѯ��Ҫ����Ծ���ļ�¼����ÿҳ��ʾ��¼�� ***,***
		String num = TagUtil.initSplitPage(ctx, rowCount, request,-1);
		criteria.setProjection(null);
		return getCriteria(clazz, lists, true).setFirstResult(
				Integer.parseInt(num.split(",")[0])).setMaxResults(
				Integer.parseInt(num.split(",")[1])).list();
	}
	/**
	 * ����ָ����¼���ݲ�ѯ���з�ҳ
	 */
	public List criteriaCustomRowByPage(Class clazz, List<ParaUnit> lists,int rows)
	throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext ctx = ServletActionContext.getServletContext();
		Criteria criteria = getCriteria(clazz, lists, false);
		int rowCount = ((Integer) criteria
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		// �����ҳ����
		// ʹ�ñ��������ز�ѯ��Ҫ����Ծ���ļ�¼����ÿҳ��ʾ��¼�� ***,***
		String num = TagUtil.initSplitPage(ctx, rowCount, request,rows);
		criteria.setProjection(null);
		return getCriteria(clazz, lists, true).setFirstResult(
				Integer.parseInt(num.split(",")[0])).setMaxResults(
						Integer.parseInt(num.split(",")[1])).list();
	}

	/**
	 * ��ѯָ���������ݣ������з�ҳ��ʼ��
	 * @param clazz
	 * @param lists
	 * @param rowNumber
	 * @return
	 * @throws Exception
	 */
	public List criteriaCustomRow(Class clazz, List<ParaUnit> lists,int rowNumber)
	throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext ctx = ServletActionContext.getServletContext();
		Criteria criteria = getCriteria(clazz, lists, false);
		int rowCount = ((Integer) criteria
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		// �����ҳ����
		// ʹ�ñ��������ز�ѯ��Ҫ����Ծ���ļ�¼����ÿҳ��ʾ��¼�� ***,***
//		String num = TagUtil.initSplitPage(ctx, rowCount, request,-1);
		criteria.setProjection(null);
		return getCriteria(clazz, lists, true).setFirstResult(0).setMaxResults(rowNumber).list();
}
	
	/**
	 * ��ͨ��ѯ
	 * 
	 * key: 1.���ֶ� 2.���������.���ֶ� value: null.�� notNull.�����ڿ� ne ������ eq.���� ge.���ڵ���
	 * le.С�ڵ��� gt.���� lt.С�� like.�м�ģ�� leftLike ��ģ�� rightLike ��ģ�� in ��ʲôʲô�� notIn
	 * ����ʲôʲô�� order ����
	 * 
	 * paraUnit : setKey( ���ֶ� ��: name) ,setValue( ����ֵ ��: ���� ) ,setType ( ��ѯ��������
	 * ��:eq )
	 * 
	 * paraUnit : setKey( ����) ,setPuList(�߼��������б� �� list.add(new
	 * ParaUnit(�ֶ�����:name,ֵ��:����,��ѯ������: like)) ) ,setType ( ��ѯ�������� ��:or )
	 * ParaUnit<String>(����:��"table"���Ǹ�����"",
	 * ,�ָ��ֶ����ֶ�,#�ָ��ֶ��������"registerId#registerId,registerType#registerType",����Ϊ�Զ���ͶӰ
	 * ParaUnit.CUSTOM);�ֶ�ͶӰ
	 * 
	 * @param src
	 * @param lists
	 * @param pageSize
	 * @param startRow
	 * @return
	 * @throws Exception
	 */
	public List criteriaQuery(Class clazz, List<ParaUnit> lists)
			throws Exception {
		return getCriteria(clazz, lists, true).list();

	}

	/**
	 * ���ض����ֶ� ��ѯ
	 * 
	 * key: 1.���ֶ� 2.���������.���ֶ� value: null.�� notNull.�����ڿ� ne ������ eq.���� ge.���ڵ���
	 * le.С�ڵ��� gt.���� lt.С�� like.�м�ģ�� leftLike ��ģ�� rightLike ��ģ�� in ��ʲôʲô�� notIn
	 * ����ʲôʲô�� order ����
	 * 
	 * paraUnit : setKey( ���ֶ� ��: name) ,setValue( ����ֵ ��: ���� ) ,setType ( ��ѯ��������
	 * ��:eq )
	 * 
	 * paraUnit : setKey( ����) ,setPuList(�߼��������б� �� list.add(new
	 * ParaUnit(�ֶ�����:name,ֵ��:����,��ѯ������: like)) ) ,setType ( ��ѯ�������� ��:or )
	 * ParaUnit<String>(����:��"table"���Ǹ�����"",
	 * ,�ָ��ֶ����ֶ�,#�ָ��ֶ��������"registerId#registerId,registerType#registerType",����Ϊ�Զ���ͶӰ
	 * ParaUnit.CUSTOM);�ֶ�ͶӰ
	 * 
	 * @param src
	 * @param lists
	 * @param pageSize
	 * @param startRow
	 * @return
	 * @throws Exception
	 */
	public List criteriaCustom(Class clazz, List<ParaUnit> lists)
			throws Exception {

		return getCriteria(clazz, lists, true).setResultTransformer(
				AliasToEntityMapResultTransformer.INSTANCE).list();

	}

	/**
	 * 
	 * ��ѯ ���� ���� Criteria key: 1.���ֶ� 2.���������.���ֶ� value: null.�� notNull.�����ڿ� ne
	 * ������ eq.���� ge.���ڵ��� le.С�ڵ��� gt.���� lt.С�� like.�м�ģ�� leftLike ��ģ�� rightLike
	 * ��ģ�� in ��ʲôʲô�� notIn ����ʲôʲô�� order ���� or �߼���
	 * 
	 * paraUnit : setKey( ���ֶ� ��: name) ,setValue( ����ֵ ��: ���� ) ,setType ( ��ѯ��������
	 * ��:eq )
	 * 
	 * paraUnit : setKey( ����) ,setPuList(�߼��������б� �� list.add(new
	 * ParaUnit(�ֶ�����:name,ֵ��:����,��ѯ������: like)) ) ,setType ( ��ѯ�������� ��:or )
	 * ParaUnit<String>(����:��"table"���Ǹ�����"",
	 * ,�ָ��ֶ����ֶ�,#�ָ��ֶ��������"registerId#registerId,registerType#registerType",����Ϊ�Զ���ͶӰ
	 * ParaUnit.CUSTOM);�ֶ�ͶӰ
	 * 
	 * @param src
	 * @param lists
	 * @param pageSize
	 * @param startRow
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Criteria getCriteria(Class clazz, List<ParaUnit> lists,
			Boolean isOrder) throws Exception {
		Criteria criteria = getSession().createCriteria(clazz);
		// ��Ų�ѯ��������к�
		int i = 1;
		// ��Ų�ѯ�������Ա�𼶵�Ԫ��

		Map<String, String> names = new TreeMap<String, String>();
		// ��Ų�ѯ�����
		Object[] str = new Object[32];
		// str�д�Ų�ѯ������ԵĶ�Ӧ��ţ��ϼ����ƣ������ƣ�����
		names.put("0", "0,table,table,-1");
		// �������ѯ�����
		str[0] = criteria;
		// ����ַ���ר��
		String temp[] = null;
		String temp2[] = null;
		// ����ֶ�ר��
		String value = "";
		// ���ͶӰ�ֶ�
		ProjectionList projectionList = Projections.projectionList();

		// �������Ϊ���򲻴���
		if (lists != null) {
			for (ParaUnit pu : lists) {
				// ��ʼ�� Ϊ1���� add Ϊ0����or
				int tempNum = 1;
				if (pu.getType().trim().equals(ParaUnit.OR)
						|| pu.getType().trim().equals(ParaUnit.CUSTOM)) {
					if (StringUtils.isNotBlank(pu.getKey())) {
						tempNum = 0;
					}
				}
				// �ϼ������
				String ptable = "";
				// �ϼ�������
				Criteria pcriteria = (Criteria) str[0];
				temp = Common.getStringTokenizer(pu.getKey(), ".");
				if (temp.length > 1 || tempNum == 0) {
					for (int num = 0; num < temp.length - tempNum; num++) {
						// ��ʶ
						Boolean type = true;
						if (num == 0) {
							// ��ȡ����
							ptable = "table";
						} else {
							// ��ȡ��һ��
							ptable = temp[num - 1];
						}
						if (names != null) {
							// ѭ���Ƿ����Ѵ��ڵĲ�ѯ�����
							for (int nums = 0; nums < names.size(); nums++) {
								// names: str�д�Ų�ѯ������ԵĶ�Ӧ��ţ��ϼ����ƣ������ƣ�����
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
						// ���Ϊ��
						if (type) {
							// ��ȡ��ǰ��ѯ���ʵ��
							pcriteria = pcriteria.createCriteria(temp[num],
									temp[num]);
							// str�д�Ų�ѯ������ԵĶ�Ӧ��ţ��ϼ����ƣ������ƣ�����
							names.put(i + "", i + "," + ptable + ","
									+ temp[num] + "," + num + "");
							// ��Ų�ѯ�����
							str[i] = pcriteria;
							// ��ѯ��������к� ��1
							i++;
						}
					}
					// ��ȡ�ֶ���
					value = temp[temp.length - 1];
					// ��ȡ��ǰ��ѯ��ʵ��
					criteria = pcriteria;
				} else {
					// ��ȡ�ֶ���
					value = pu.getKey();
					// ��ȡ����ѯ��ʵ��
					criteria = (Criteria) str[0];
				}

				// �����ѯ����Ϊ�߼���
				if (pu.getType().trim().equals(ParaUnit.OR)) {
					// ���߼������.ʵ�廯
					Object tempObj = Restrictions.disjunction();
					// ѭ���߼����ѯ����
					for (ParaUnit pus : (List<ParaUnit>) pu.getPuList()) {
						// ��ȡ�߼����ѯ����
						((Disjunction) tempObj).add(getCriterion(pus.getType(),
								pus.getKey(), pus.getValue(), pus.getObj(), pus.getDetachedCriteria()));
					}
					// �����߼�������
					criteria.add((Disjunction) tempObj);

					// ���ΪͶӰ
				} else if (pu.getType().trim().equals(ParaUnit.CUSTOM)) {

					// ,�ָ��ֶ����ֶ�,#�ָ��ֶ������
					String[] tempCustom = Common.getStringTokenizer(pu
							.getValue().toString(), ",");

					for (int numCustom = 0; numCustom < tempCustom.length; numCustom++) {
						String[] tempTwoCustom = Common.getStringTokenizer(
								tempCustom[numCustom], "#");
						projectionList.add(Projections
								.property(tempTwoCustom[0]), tempTwoCustom[1]);
					}

					// �����ѯ����Ϊ����
				} else if (pu.getType().trim().equals(ParaUnit.ORDER)) {
					if (isOrder) {
						if (pu.getValue().equals(ParaUnit.ASC)) {
							criteria.addOrder(Order.asc(value));
						} else if (pu.getValue().equals(ParaUnit.DESC)) {
							criteria.addOrder(Order.desc(value));
						}
					}

				} else {
					// �����߼�������
					criteria.add(getCriterion(pu.getType(), value, pu
							.getValue(), pu.getObj(),pu.getDetachedCriteria()));
				}
			}
		}

		// �����ͶӰ������
		if (projectionList.getLength() > 0) {
			((Criteria) str[0]).setProjection(projectionList);
		}

		return ((Criteria) str[0]);
	}

	/**
	 * ��ȡ��ѯ����
	 * 
	 * @param type
	 *            ��ѯ����
	 * @param key
	 *            ��po�ֶ�
	 * @param value
	 *            ��ѯ�Ƚ�ֵ
	 * @return
	 */
	public Criterion getCriterion(String type, String key, Object value,
			Object[] obj, DetachedCriteria detachedCriteria) {
		Criterion criterion = null;
		// �����Ƿ�Ϊ����null
		if (type.trim().equals(ParaUnit.NULL)) {

			criterion = Restrictions.isNull(key);

		}
		// �����Ƿ�Ϊ������null
		if (type.trim().equals(ParaUnit.NOT_NULL)) {

			criterion = Restrictions.isNotNull(key);
		}
		// �����Ƿ�Ϊ������
		if (type.trim().equals(ParaUnit.NE)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).ne(detachedCriteria);
			else
				criterion = Restrictions.not(Restrictions.eq(key, value));

		}
		// �����Ƿ�Ϊ����
		if (type.trim().equals(ParaUnit.EQ)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).eq(detachedCriteria);
			else
				criterion = Restrictions.eq(key, value);

		}
		// �����Ƿ�Ϊ���ڵ���
		if (type.trim().equals(ParaUnit.GE)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).ge(detachedCriteria);
			else
				criterion = Restrictions.ge(key, value);

		}
		// �����Ƿ�ΪС�ڵ���
		if (type.trim().equals(ParaUnit.LE)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).le(detachedCriteria);
			else
				criterion = Restrictions.le(key, value);

		}
		// �����Ƿ�Ϊ����
		if (type.trim().equals(ParaUnit.GT)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).gt(detachedCriteria);
			else
				criterion = Restrictions.gt(key, value);

		}
		// �����Ƿ�ΪС��
		if (type.trim().equals(ParaUnit.LT)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).lt(detachedCriteria);
			else
				criterion = Restrictions.lt(key, value);

		}
		// �����Ƿ�Ϊ�м�ģ����ѯ
		if (type.trim().equals(ParaUnit.LIKE)) {

			criterion = Restrictions.like(key, value.toString(),
					MatchMode.ANYWHERE);
		}

		// �����Ƿ�Ϊ��ģ����ѯ
		if (type.trim().equals(ParaUnit.RIGHT_LIKE)) {

			criterion = Restrictions.like(key, value.toString(), MatchMode.END);
		}

		// �����Ƿ�Ϊ��ģ����ѯ
		if (type.trim().equals(ParaUnit.LEFT_LIKE)) {

			criterion = Restrictions.like(key, value.toString(),
					MatchMode.START);
		}

		// �����Ƿ�Ϊ��ʲôʲô��
		if (type.trim().equals(ParaUnit.IN)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).in(detachedCriteria);
			else
				criterion = Restrictions.in(key, obj);
		}

		// �����Ƿ�Ϊ����ʲôʲô��
		if (type.trim().equals(ParaUnit.NOT_IN)) {
			if(detachedCriteria!=null)
				criterion = Property.forName(key).notIn(detachedCriteria);
			else
				criterion = Restrictions.not(Restrictions.in(key, obj));

		}
		return criterion;
	}
	/**
	 * ��ȡ�Ӳ�ѯ����,propertyName�Ӳ�ѯ����������ֶΣ�clazz�ӱ����ͣ�
	 * lists����list��sqlFunctName�����������ֶ�ʹ�õ�sql������ֵ��Դ��ParaUnit
	 * @param clazz
	 * @param lists
	 * @param isOrder
	 * @return �������������ѯ���Ӳ�ѯ���󣬵��ô��Ӳ�ѯ������ʹ�÷�ʽΪ
	 * createCriteria(����.class).add(Property.forName("���������ж��ֶ�").Sql����(���������ض���) 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public DetachedCriteria getDetachedCriteria(String propertyName, Class clazz, List<ParaUnit> lists ,String SqlFuncName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(clazz);
		detachedCriteria.setProjection(this.getProjection(propertyName, SqlFuncName));
		// ��Ų�ѯ��������к�
		int i = 1;
		// ��Ų�ѯ�������Ա�𼶵�Ԫ��

		Map<String, String> names = new TreeMap<String, String>();
		// ��Ų�ѯ�����
		Object[] str = new Object[32];
		// str�д�Ų�ѯ������ԵĶ�Ӧ��ţ��ϼ����ƣ������ƣ�����
		names.put("0", "0,table,table,-1");
		// �������ѯ�����
		str[0] = detachedCriteria;
		// ����ַ���ר��
		String temp[] = null;
		String temp2[] = null;
		// ����ֶ�ר��
		String value = "";
		// ���ͶӰ�ֶ�
		ProjectionList projectionList = Projections.projectionList();

		// �������Ϊ���򲻴���
		if (lists != null) {
			for (ParaUnit pu : lists) {
				// ��ʼ�� Ϊ1���� add Ϊ0����or
				int tempNum = 1;
				if (pu.getType().trim().equals(ParaUnit.OR)
						|| pu.getType().trim().equals(ParaUnit.CUSTOM)) {
					if (StringUtils.isNotBlank(pu.getKey())) {
						tempNum = 0;
					}
				}
				// �ϼ������
				String ptable = "";
				// �ϼ�������
				DetachedCriteria pcriteria = (DetachedCriteria) str[0];
				temp = Common.getStringTokenizer(pu.getKey(), ".");
				if (temp.length > 1 || tempNum == 0) {
					for (int num = 0; num < temp.length - tempNum; num++) {
						// ��ʶ
						Boolean type = true;
						if (num == 0) {
							// ��ȡ����
							ptable = "table";
						} else {
							// ��ȡ��һ��
							ptable = temp[num - 1];
						}
						if (names != null) {
							// ѭ���Ƿ����Ѵ��ڵĲ�ѯ�����
							for (int nums = 0; nums < names.size(); nums++) {
								// names: str�д�Ų�ѯ������ԵĶ�Ӧ��ţ��ϼ����ƣ������ƣ�����
								temp2 = Common.getStringTokenizer(names
										.get(nums + ""), ",");
								if (temp2[1].trim().equals(ptable)
										&& temp2[2].trim().equals(temp[num])
										&& temp2[3].trim().equals(num + "")) {

									pcriteria = (DetachedCriteria) str[Integer
											.parseInt(temp2[0])];
									type = false;
									break;
								}
							}

						}
						// ���Ϊ��
						if (type) {
							// ��ȡ��ǰ��ѯ���ʵ��
							pcriteria = pcriteria.createCriteria(temp[num],
									temp[num]);
							// str�д�Ų�ѯ������ԵĶ�Ӧ��ţ��ϼ����ƣ������ƣ�����
							names.put(i + "", i + "," + ptable + ","
									+ temp[num] + "," + num + "");
							// ��Ų�ѯ�����
							str[i] = pcriteria;
							// ��ѯ��������к� ��1
							i++;
						}
					}
					// ��ȡ�ֶ���
					value = temp[temp.length - 1];
					// ��ȡ��ǰ��ѯ��ʵ��
					detachedCriteria = pcriteria;
				} else {
					// ��ȡ�ֶ���
					value = pu.getKey();
					// ��ȡ����ѯ��ʵ��
					detachedCriteria = (DetachedCriteria) str[0];
				}

				// �����ѯ����Ϊ�߼���
				if (pu.getType().trim().equals(ParaUnit.OR)) {
					// ���߼������.ʵ�廯
					Object tempObj = Restrictions.disjunction();
					// ѭ���߼����ѯ����
					for (ParaUnit pus : (List<ParaUnit>) pu.getPuList()) {
						// ��ȡ�߼����ѯ����
						((Disjunction) tempObj).add(getCriterion(pus.getType(),
								pus.getKey(), pus.getValue(), pu.getObj(), pu.getDetachedCriteria()));
					}
					// �����߼�������
					detachedCriteria.add((Disjunction) tempObj);

					// ���ΪͶӰ
				} else if (pu.getType().trim().equals(ParaUnit.CUSTOM)) {

					// ,�ָ��ֶ����ֶ�,#�ָ��ֶ������
					String[] tempCustom = Common.getStringTokenizer(pu
							.getValue().toString(), ",");

					for (int numCustom = 0; numCustom < tempCustom.length; numCustom++) {
						String[] tempTwoCustom = Common.getStringTokenizer(
								tempCustom[numCustom], "#");
						projectionList.add(Projections
								.property(tempTwoCustom[0]), tempTwoCustom[1]);
					}

					// �����ѯ����Ϊ����
				} else if (pu.getType().trim().equals(ParaUnit.ORDER)) {
					if (pu.getValue().equals(ParaUnit.ASC)) {
						detachedCriteria.addOrder(Order.asc(value));
					} else if (pu.getValue().equals(ParaUnit.DESC)) {
						detachedCriteria.addOrder(Order.desc(value));
					}

				} else {
					// �����߼�������
					detachedCriteria.add(getCriterion(pu.getType(), value, pu
							.getValue(), pu.getObj(), pu.getDetachedCriteria()));
				}
			}
		}

		// �����ͶӰ������
		if (projectionList.getLength() > 0) {
			((Criteria) str[0]).setProjection(projectionList);
		}

		return ((DetachedCriteria) str[0]);
	}
	/**
	 * ��ȡ�Ӳ�ѯͶӰ
	 * @param propertyName
	 * @param SqlFuncName
	 * @return
	 */
	private Projection getProjection(String propertyName,String SqlFuncName)
	{
		if(SqlFuncName==null)
			return Property.forName(propertyName);
		else
		{
			if(SqlFuncName.equals(ParaUnit.AVG))
				return Property.forName(propertyName).avg();
			else if(SqlFuncName.equals(ParaUnit.COUNT))
				return Property.forName(propertyName).count();
			else if(SqlFuncName.equals(ParaUnit.MAX))
				return Property.forName(propertyName).max();
			else if(SqlFuncName.equals(ParaUnit.MIN))
				return Property.forName(propertyName).min();
			else
				return Property.forName(propertyName);
		}
	}
	/**
	 * ��ѯ��һ����
	 * 
	 * @param id
	 *            ����ID
	 * @return obj �����صĶ���
	 */
	public Object findOne(Class clazz, Object id) {
		Object obj = htemplate.get(clazz, (Serializable) id);
		if(recordLog)
			saveLog(obj,"��ѯ����",null);
		return obj;
	}

	public void delete(CorePo po) {
		if(recordLog)
			saveLog(po,"ɾ��",null);
		htemplate.delete(po);
	}


	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeQuery(final String queryString) {
		log.info(queryString);
		List<Map<String, Object>> dataList;
		dataList = (List<Map<String, Object>>) htemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
						Connection con = session.connection();
						Statement stm = con.createStatement();
						try {
							ResultSet rs = stm.executeQuery(queryString);
							ResultSetMetaData rt = rs.getMetaData();
							Map<String, Object> col;
							while (rs.next()) {
								col = new HashMap<String, Object>();
								for (int i = 1; i <= rt.getColumnCount(); i++) {
									col.put(rt.getColumnLabel(i).toUpperCase(),
											rs.getObject(i));
								}
								dataList.add(col);
							}
						} catch (SQLException e) {
							throw e;
						} finally {
							stm.close();
							// con.close();
						}
						return dataList;
					}

				});
		if(recordLog)
			saveLog(null,"����",queryString);
		return dataList;
	}
	/**
	 * ��־������
	 * obj���������Զ���־��ϵĶ�����logText��ͬʱʹ��
	 * action������������
	 * logText������־��ʾ�ı����ݣ���obj��ͬʱʹ��
	 */
	public void saveLog(Object obj, String action,String logText) {
		TableLog tlog = new TableLog();
		if(tlog.getLogSwitch())
		{
//			Loginfo vo = tlog.organizLog(obj, action,logText);
//			htemplate.save(vo);
		}
	}

	public boolean isRecordLog() {
		return recordLog;
	}

	public void setRecordLog(boolean recordLog) {
		this.recordLog = recordLog;
	}
}
