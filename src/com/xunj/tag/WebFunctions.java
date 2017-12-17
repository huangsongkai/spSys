package com.xunj.tag;

import com.xunj.core.MD5;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebFunctions
{
  public static String getVerifyKey(String sessionId, String funcId)
  {
    MD5 md5 = new MD5();
    return md5.toMD5(sessionId + "_X-J_" + funcId);
  }

  public static String getDateTime(String pattern)
  {
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat(pattern);
    String dateStr = df.format(date);
    return dateStr;
  }

  public static boolean isNew(Date date) {
    Date now = new Date();
    if (date != null)
    {
      Long l1 = Long.valueOf(date.getTime());
      Long lnow = Long.valueOf(now.getTime());
      Long l = Long.valueOf((lnow.longValue() - l1.longValue()) / 86400000L);
      if (l.longValue() <= 3L)
        return true;
    }
    return false;
  }

  public static String encodingString(String str) {
    if (str != null) {
      try
      {
        System.out.println(str);
        str = new String(str.getBytes("utf-8"), "iso-8859-1");
        System.out.println(str);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return str;
  }
}