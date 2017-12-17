package com.xunj.tag;

import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class BaseHandlerTag extends BodyTagSupport
{

  /** @deprecated */
  protected static final Locale defaultLocale = Locale.getDefault();

  protected String accesskey = null;

  protected String tabindex = null;

  protected boolean indexed = false;

  private String onclick = null;

  private String ondblclick = null;

  private String onmouseover = null;

  private String onmouseout = null;

  private String onmousemove = null;

  private String onmousedown = null;

  private String onmouseup = null;

  private String onkeydown = null;

  private String onkeyup = null;

  private String onkeypress = null;

  private String onselect = null;

  private String onchange = null;

  private String onblur = null;

  private String onfocus = null;

  private boolean disabled = false;

  private boolean readonly = false;

  private String style = null;

  private String styleClass = null;

  private String styleId = null;

  private String alt = null;

  private String altKey = null;

  private String bundle = null;

  private String title = null;

  private String titleKey = null;

  public void setAccesskey(String accessKey)
  {
    this.accesskey = accessKey;
  }

  public String getAccesskey()
  {
    return this.accesskey;
  }

  public void setTabindex(String tabIndex)
  {
    this.tabindex = tabIndex;
  }

  public String getTabindex()
  {
    return this.tabindex;
  }

  public void setIndexed(boolean indexed)
  {
    this.indexed = indexed;
  }

  public boolean getIndexed()
  {
    return this.indexed;
  }

  public void setOnclick(String onClick)
  {
    this.onclick = onClick;
  }

  public String getOnclick()
  {
    return this.onclick;
  }

  public void setOndblclick(String onDblClick)
  {
    this.ondblclick = onDblClick;
  }

  public String getOndblclick()
  {
    return this.ondblclick;
  }

  public void setOnmousedown(String onMouseDown)
  {
    this.onmousedown = onMouseDown;
  }

  public String getOnmousedown()
  {
    return this.onmousedown;
  }

  public void setOnmouseup(String onMouseUp)
  {
    this.onmouseup = onMouseUp;
  }

  public String getOnmouseup()
  {
    return this.onmouseup;
  }

  public void setOnmousemove(String onMouseMove)
  {
    this.onmousemove = onMouseMove;
  }

  public String getOnmousemove()
  {
    return this.onmousemove;
  }

  public void setOnmouseover(String onMouseOver)
  {
    this.onmouseover = onMouseOver;
  }

  public String getOnmouseover()
  {
    return this.onmouseover;
  }

  public void setOnmouseout(String onMouseOut)
  {
    this.onmouseout = onMouseOut;
  }

  public String getOnmouseout()
  {
    return this.onmouseout;
  }

  public void setOnkeydown(String onKeyDown)
  {
    this.onkeydown = onKeyDown;
  }

  public String getOnkeydown()
  {
    return this.onkeydown;
  }

  public void setOnkeyup(String onKeyUp)
  {
    this.onkeyup = onKeyUp;
  }

  public String getOnkeyup()
  {
    return this.onkeyup;
  }

  public void setOnkeypress(String onKeyPress)
  {
    this.onkeypress = onKeyPress;
  }

  public String getOnkeypress()
  {
    return this.onkeypress;
  }

  public void setOnchange(String onChange)
  {
    this.onchange = onChange;
  }

  public String getOnchange()
  {
    return this.onchange;
  }

  public void setOnselect(String onSelect)
  {
    this.onselect = onSelect;
  }

  public String getOnselect()
  {
    return this.onselect;
  }

  public void setOnblur(String onBlur)
  {
    this.onblur = onBlur;
  }

  public String getOnblur()
  {
    return this.onblur;
  }

  public void setOnfocus(String onFocus)
  {
    this.onfocus = onFocus;
  }

  public String getOnfocus()
  {
    return this.onfocus;
  }

  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }

  public boolean getDisabled()
  {
    return this.disabled;
  }

  public void setReadonly(boolean readonly)
  {
    this.readonly = readonly;
  }

  public boolean getReadonly()
  {
    return this.readonly;
  }

  public void setStyle(String style)
  {
    this.style = style;
  }

  public String getStyle()
  {
    return this.style;
  }

  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }

  public String getStyleClass()
  {
    return this.styleClass;
  }

  public void setStyleId(String styleId)
  {
    this.styleId = styleId;
  }

  public String getStyleId()
  {
    return this.styleId;
  }

  public String getAlt()
  {
    return this.alt;
  }

  public void setAlt(String alt)
  {
    this.alt = alt;
  }

  public String getAltKey()
  {
    return this.altKey;
  }

  public void setAltKey(String altKey)
  {
    this.altKey = altKey;
  }

  public String getBundle()
  {
    return this.bundle;
  }

  public void setBundle(String bundle)
  {
    this.bundle = bundle;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getTitleKey()
  {
    return this.titleKey;
  }

  public void setTitleKey(String titleKey)
  {
    this.titleKey = titleKey;
  }

  public void release()
  {
    super.release();
    this.accesskey = null;
    this.alt = null;
    this.altKey = null;
    this.bundle = null;
    this.indexed = false;
    this.onclick = null;
    this.ondblclick = null;
    this.onmouseover = null;
    this.onmouseout = null;
    this.onmousemove = null;
    this.onmousedown = null;
    this.onmouseup = null;
    this.onkeydown = null;
    this.onkeyup = null;
    this.onkeypress = null;
    this.onselect = null;
    this.onchange = null;
    this.onblur = null;
    this.onfocus = null;
    this.disabled = false;
    this.readonly = false;
    this.style = null;
    this.styleClass = null;
    this.styleId = null;
    this.tabindex = null;
    this.title = null;
    this.titleKey = null;
  }

  protected String prepareStyles()
    throws JspException
  {
    StringBuffer styles = new StringBuffer();
    if (getStyle() != null) {
      styles.append(" style=\"");
      styles.append(getStyle());
      styles.append("\"");
    }
    if (getStyleClass() != null) {
      styles.append(" class=\"");
      styles.append(getStyleClass());
      styles.append("\"");
    }
    if (getStyleId() != null) {
      styles.append(" id=\"");
      styles.append(getStyleId());
      styles.append("\"");
    }
    if (getTitle() != null) {
      styles.append(" title=\"");
      styles.append(getTitle());
      styles.append("\"");
    }
    if (getAlt() != null) {
      styles.append(" alt=\"");
      styles.append(getAlt());
      styles.append("\"");
    }
    return styles.toString();
  }

  protected String prepareEventHandlers()
  {
    StringBuffer handlers = new StringBuffer();
    prepareMouseEvents(handlers);
    prepareKeyEvents(handlers);
    prepareTextEvents(handlers);
    prepareFocusEvents(handlers);
    return handlers.toString();
  }

  protected void prepareMouseEvents(StringBuffer handlers)
  {
    if (getOnclick() != null) {
      handlers.append(" onclick=\"");
      handlers.append(getOnclick());
      handlers.append("\"");
    }

    if (getOndblclick() != null) {
      handlers.append(" ondblclick=\"");
      handlers.append(getOndblclick());
      handlers.append("\"");
    }

    if (getOnmouseover() != null) {
      handlers.append(" onmouseover=\"");
      handlers.append(getOnmouseover());
      handlers.append("\"");
    }

    if (getOnmouseout() != null) {
      handlers.append(" onmouseout=\"");
      handlers.append(getOnmouseout());
      handlers.append("\"");
    }

    if (getOnmousemove() != null) {
      handlers.append(" onmousemove=\"");
      handlers.append(getOnmousemove());
      handlers.append("\"");
    }

    if (getOnmousedown() != null) {
      handlers.append(" onmousedown=\"");
      handlers.append(getOnmousedown());
      handlers.append("\"");
    }

    if (getOnmouseup() != null) {
      handlers.append(" onmouseup=\"");
      handlers.append(getOnmouseup());
      handlers.append("\"");
    }
  }

  protected void prepareKeyEvents(StringBuffer handlers)
  {
    if (getOnkeydown() != null) {
      handlers.append(" onkeydown=\"");
      handlers.append(getOnkeydown());
      handlers.append("\"");
    }

    if (getOnkeyup() != null) {
      handlers.append(" onkeyup=\"");
      handlers.append(getOnkeyup());
      handlers.append("\"");
    }

    if (getOnkeypress() != null) {
      handlers.append(" onkeypress=\"");
      handlers.append(getOnkeypress());
      handlers.append("\"");
    }
  }

  protected void prepareTextEvents(StringBuffer handlers)
  {
    if (getOnselect() != null) {
      handlers.append(" onselect=\"");
      handlers.append(getOnselect());
      handlers.append("\"");
    }

    if (getOnchange() != null) {
      handlers.append(" onchange=\"");
      handlers.append(getOnchange());
      handlers.append("\"");
    }
  }

  protected void prepareFocusEvents(StringBuffer handlers)
  {
    if (getOnblur() != null) {
      handlers.append(" onblur=\"");
      handlers.append(getOnblur());
      handlers.append("\"");
    }

    if (getOnfocus() != null) {
      handlers.append(" onfocus=\"");
      handlers.append(getOnfocus());
      handlers.append("\"");
    }

    if (getDisabled()) {
      handlers.append(" disabled=\"disabled\"");
    }

    if (getReadonly())
      handlers.append(" readonly=\"readonly\"");
  }

  protected void prepareAttribute(StringBuffer handlers, String name, Object value)
  {
    if (value != null) {
      handlers.append(" ");
      handlers.append(name);
      handlers.append("=\"");
      handlers.append(value);
      handlers.append("\"");
    }
  }

  protected boolean isXhtml()
  {
    return false;
  }

  protected String getElementClose()
  {
    return ((isXhtml()) ? " />" : ">");
  }
}