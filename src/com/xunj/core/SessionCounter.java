package com.xunj.core;

import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounter extends HttpServlet
  implements HttpSessionListener
{
  private static final long serialVersionUID = 7095461489031304606L;
  private static int activeSessions = 0;

  public void sessionCreated(HttpSessionEvent se)
  {
    activeSessions += 1;
  }

  public void sessionDestroyed(HttpSessionEvent se)
  {
    SessionBean sessionbean = (SessionBean)se.getSession().getAttribute(
      "sessionbean");
    if (sessionbean != null) {
      ServletContext ctx = se.getSession().getServletContext();
      HashMap userMap = (HashMap)ctx.getAttribute("globeUsers");
      userMap.remove(sessionbean.getUserId());
      ctx.setAttribute("globeUsers", userMap);
    }
    activeSessions -= 1;
  }

  public static int getActiveSessions() {
    return activeSessions;
  }
}