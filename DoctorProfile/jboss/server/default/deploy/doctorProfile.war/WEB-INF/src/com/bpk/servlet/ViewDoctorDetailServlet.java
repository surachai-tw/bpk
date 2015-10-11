package com.bpk.servlet;

import javax.servlet.http.* ;
import javax.servlet.* ;
import java.util.* ;
import java.net.* ;
import java.io.* ;
import java.awt.Image ;
import java.awt.image.* ;
import javax.imageio.* ;
import javax.swing.ImageIcon ;
import java.awt.* ;
import java.util.ArrayList ;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2558</p>
 * <p>Company: 
 * @author Surachai Torwong 
 * @version 1.0
 */

public class ViewDoctorDetailServlet extends HttpServlet
{

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        try
        {
            response.setContentType("text/plain") ;
            PrintWriter toOutput = response.getWriter() ;
            toOutput.println("TEST") ;
            toOutput.close() ;
        }
        catch (Exception ex)
        {
            ex.printStackTrace() ;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/plain") ;
        PrintWriter out = response.getWriter() ;
        String error = "Error: this servlet does not support the GET method!" ;
        out.println(error) ;
        System.out.println(error) ;
        out.close() ;
    }


}