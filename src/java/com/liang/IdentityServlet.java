/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liang;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author liang
 */
public class IdentityServlet extends HttpServlet {
    //随机字符，去掉0，O,1,l难认字符
    protected static final char[] CHARS = {'2','3','4','5','6','7','8','9','A','B',
        'C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V',
        'W','X','Y','Z'};
    

    /**
     * 随机数
     */
    protected static Random random = new Random();
    
    

    /**
     * 生成4位随机数组合 getRandomString
     * @return buffer.toString()
     */
    protected static String getRandomString(){
        StringBuffer buffer;
        buffer = new StringBuffer();
        for(int i=0;i<4;i++){            
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }
    
    /**
     * 生成随机颜色 getRandomColor
     * 
     * @return 
     */
    protected static Color getRandomColor(){
       return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)); 
    }
    
    /**
     *生成某颜色反色 getReverseColor
     * 
     * @param  c
     * @return 
     */
    protected static Color getReverseColor(Color c){
        return new Color(255 - c.getRed(),255 - c.getGreen(), 255 - c.getBlue());
    }
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IdentityServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IdentityServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("image/jpeg");      //设置输出类型，必须
        String randomString = getRandomString();    //生成随机字符串
        //放入session 
        request.getSession(true).setAttribute("randomString", randomString);
        
        int width = 100;
        int height= 30;
        
        Color color = getRandomColor();                 //随机颜色
        Color reverseColor = getReverseColor(color);    //反色，用天前景色
        //创建彩色图片
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();             //获取绘图对象
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));  //设置字体
        g.setColor(color);                              //设置字体颜色
        g.fillRect(0, 0, width, height);                //绘制背景
        g.setColor(reverseColor);                       //设置背景颜色为字体反色
        g.drawString(randomString, 18, 20);             //绘制随机字符
        //生成噪音点
        for(int i =0,n=random.nextInt(100); i<n;i++){
            g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
        }
        
        ServletOutputStream out = response.getOutputStream();
        
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(bi);
        out.flush();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
