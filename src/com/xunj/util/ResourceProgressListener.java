package com.xunj.util;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;


public class ResourceProgressListener implements ProgressListener {

    private HttpSession session;

 

    public ResourceProgressListener(HttpServletRequest request) {
//    	System.out.println("--------------------------------------------ResourceProgressListener:");
       session = request.getSession();
       session.removeAttribute("progressStatus");
    }

 

    public void update(long readedBytes, long totalBytes, int currentItem) {

       
       double  readedByte =0;//已传
       double  totalByte =0; //总共
       double present=0;
       
       readedByte = readedBytes!=0?Double.parseDouble(readedBytes+""):0;
       totalByte = totalBytes!=0?Double.parseDouble(totalBytes+""):0;
      
       present = readedByte/totalByte*100;
       String status = present+","+readedByte/1024+","+totalByte/1024;
       session.setAttribute("status", status);

    }

}

