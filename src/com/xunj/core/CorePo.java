package com.xunj.core;

import java.io.Serializable;

public class CorePo
  implements Serializable
{
  private static final long serialVersionUID = 7285510749223830611L;
  protected String tableId;

  public String getTableId()
  {
    return this.tableId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }
}