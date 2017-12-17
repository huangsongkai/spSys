package com.xunj.core;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public class ParaUnit<T>
{
  private T ob;
  private String key;
  private String type;
  private List<ParaUnit> puList;
  private Object[] obj;
  private DetachedCriteria detachedCriteria;
  public static final String NULL = "null";
  public static final String NOT_NULL = "notNull";
  public static final String NE = "ne";
  public static final String EQ = "eq";
  public static final String GT = "gt";
  public static final String LT = "lt";
  public static final String GE = "ge";
  public static final String LE = "le";
  public static final String LIKE = "like";
  public static final String RIGHT_LIKE = "rightLike";
  public static final String LEFT_LIKE = "leftLike";
  public static final String IN = "in";
  public static final String NOT_IN = "notIn";
  public static final String ORDER = "order";
  public static final String DESC = "desc";
  public static final String ASC = "asc";
  public static final String OR = "or";
  public static final String AVG = "avg";
  public static final String COUNT = "count";
  public static final String MAX = "max";
  public static final String MIN = "min";
  public static final String DISTINCT = "distinct";
  public static final String CUSTOM = "custom";

  public ParaUnit(String key, T ob, String type)
  {
    this.key = key;
    this.ob = ob;
    this.type = type;
  }

  public ParaUnit(String key, Object[] obj, String type)
  {
    this.key = key;
    this.obj = obj;
    this.type = type;
  }

  public ParaUnit(String key, List<ParaUnit> puList, String type)
  {
    this.key = key;
    this.puList = puList;
    this.type = type;
  }

  public ParaUnit(String key, DetachedCriteria detachedCriteria, String type)
  {
    this.key = key;
    this.detachedCriteria = detachedCriteria;
    this.type = type;
  }

  public String getKey()
  {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public T getValue()
  {
    return this.ob;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public static String IntType() {
    return "int";
  }

  public static String DoubleType() {
    return "double";
  }

  public static String StringType() {
    return "string";
  }

  public static String LongType() {
    return "long";
  }

  public List getPuList() {
    return this.puList; }

  public Object[] getObj() {
    return this.obj;
  }

  public DetachedCriteria getDetachedCriteria() {
    return this.detachedCriteria;
  }

  public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
    this.detachedCriteria = detachedCriteria;
  }
}