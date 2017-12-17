package com.xunj.util;

import java.awt.Color;   
import java.awt.Font;   
import java.awt.Graphics2D;   
import java.awt.image.BufferedImage;   
import java.util.Random;   
  
import javax.imageio.ImageIO;   
import javax.servlet.ServletException;   
import javax.servlet.ServletOutputStream;   
import javax.servlet.http.HttpServlet;   
import javax.servlet.http.HttpServletRequest;   
import javax.servlet.http.HttpServletResponse;   
import javax.servlet.http.HttpSession;   
  
/**  
 * 生成图片验证码  
 * 
 */  
public class ImgCaptcha extends HttpServlet {   
  
    private static final long serialVersionUID = 1L;   
  
    // 验证码图片的宽度。   
    private int width = 60;   
    // 验证码图片的高度。   
    private int height = 20;   
    // 验证码字符个数   
    private int codeCount = 4;   
  
    private int x = 0;   
    // 字体高度   
    private int fontHeight;   
    private int codeY;   
    
    private int r=0;
    private int g=0;
    private int b=0;
  
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',   
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',   
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };   
  
    /**  
     * 初始化验证图片属性  
     */  
    public void init() throws ServletException {   
        // 从web.xml中获取初始信息   
        // 宽度   
        String strWidth = this.getInitParameter("width");   
        // 高度   
        String strHeight = this.getInitParameter("height");   
        // 字符个数   
        String strCodeCount = this.getInitParameter("codeCount");   
  
        // 将配置的信息转换成数值   
        try {   
            if (strWidth != null && strWidth.length() != 0) {   
                width = Integer.parseInt(strWidth);   
            }   
            if (strHeight != null && strHeight.length() != 0) {   
                height = Integer.parseInt(strHeight);   
            }   
            if (strCodeCount != null && strCodeCount.length() != 0) {   
                codeCount = Integer.parseInt(strCodeCount);   
            }   
        } catch (NumberFormatException e) {   
        }   
  
        x = width / (codeCount +1);   
        fontHeight = height - 2;   
        codeY = height - 4;   
  
    }   
    //定义一个获取随机颜色的方法    
    private Color getRandColor(int fc,int bc)    
    {    
        Random random = new Random();    
        if(fc > 255) fc = 255;    
        if(bc > 255) bc = 255;    
        int r = fc + random.nextInt(bc - fc);   
        this.r=r;
        int g = fc + random.nextInt(bc - fc);    
        this.g=g;
        int b = fc + random.nextInt(bc - fc);    
        this.b=b;
        //得到随机颜色    
        return new Color(r , g , b);    
    }
    //定义获取随机字符串方法    
    private static String getRandomChar()    
    {    
        //生成一个0、1、2的随机数字    
        int rand = (int)Math.round(Math.random() * 2);    
        long itmp = 0;    
        char ctmp = '\u0000';    
        switch (rand)    
        {    
            //生成大写字母    
            case 1:    
                itmp = Math.round(Math.random() * 25 + 65);    
                ctmp = (char)itmp;    
                return String.valueOf(ctmp);    
            //生成小写字母    
            case 2:    
                itmp = Math.round(Math.random() * 25 + 97);    
                ctmp = (char)itmp;    
                return String.valueOf(ctmp);    
            //生成数字    
            default :    
                itmp = Math.round(Math.random() * 9);    
                return  itmp + "";    
        }    
    }
    //重写service方法，生成对客户端的响应
    protected void service(HttpServletRequest req, HttpServletResponse resp)   
            throws ServletException, java.io.IOException {   
  
        HttpSession session = req.getSession();   
        session.removeAttribute("ImgCaptchaCode");
        // 定义图像buffer   
        BufferedImage buffImg = new BufferedImage(width, height,   
                BufferedImage.TYPE_INT_RGB);   
        Graphics2D g = buffImg.createGraphics();   
  
        // 创建一个随机数生成器类   
        Random random = new Random();   
  
        // 将图像填充色   
//        g.setColor(getRandColor(200,250));   
        g.setColor(Color.WHITE);   
        g.fillRect(0, 0, width, height);   
  
        // 创建字体，字体的大小应该根据图片的高度来定。   
        Font font = new Font("Arial Black", Font.PLAIN, fontHeight);   
        // 设置字体。   
        g.setFont(font);   
  
        // 画边框。   
        g.setColor(Color.GRAY);   
        g.drawRect(0, 0, width - 1, height - 1);   
  
        // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。   
        g.setColor(Color.BLACK);   
        for (int i = 0; i < 10; i++) {   
            int x = random.nextInt(width);   
            int y = random.nextInt(height);   
            int xl = random.nextInt(12);   
            int yl = random.nextInt(12);   
            g.drawLine(x, y, x + xl, y + yl);   
        }   
  
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。   
        StringBuffer randomCode = new StringBuffer();   
        int red = 0, green = 0, blue = 0;   
  
        // 随机产生codeCount数字的验证码。   
        for (int i = 0; i < codeCount; i++) {   
            // 得到随机产生的验证码数字。   
//          String strRand = String.valueOf(codeSequence[random.nextInt(36)]);   
            String strRand = getRandomChar();   
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。   
            red = random.nextInt(255);   
            green = random.nextInt(255);   
            blue = random.nextInt(255);   
            
            red=red==this.r?red+1:red;
            red=red>255?red-1:red;
            green=green==this.g?green+1:green;
            green=green>255?green-2:green;
            blue=blue==this.b?blue+1:blue;
            blue=blue>255?blue-3:blue;
            // 用随机产生的颜色将验证码绘制到图像中。   
            g.setColor(new Color(red, green, blue));   
            if(i==0){
            	g.drawString(strRand, 4, codeY);   
            }else{
            	g.drawString(strRand, ((i + 0) * x)+4, codeY);   
            }
  
            // 将产生的四个随机数组合在一起。   
            randomCode.append(strRand);   
        }   
        // 将四位数字的验证码保存到Session中。   
        session.setAttribute("ImgCaptchaCode", randomCode.toString());   
  
        // 禁止图像缓存。   
        resp.setHeader("Pragma", "no-cache");   
        resp.setHeader("Cache-Control", "no-cache");   
        resp.setDateHeader("Expires", 0);   
  
        resp.setContentType("image/jpeg");   
  
        //清空缓存   
        g.dispose();   
           
        // 将图像输出到Servlet输出流中。   
        ServletOutputStream sos = resp.getOutputStream();   
        ImageIO.write(buffImg, "jpeg", sos);   
        sos.close();   
    }
  
  
}  

