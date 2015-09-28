package com.as400samplecode;
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.google.gson.Gson;
import com.google.gson.JsonObject;
 
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public Login() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        String loginData = request.getParameter("loginData");
        System.out.println("loginData = "+loginData);
        if(loginData!=null)
        {
	        Gson gson = new Gson();
	        UserInfo userInfo = gson.fromJson(loginData, UserInfo.class);
	        String userId = userInfo.getUserId();
	        String password = userInfo.getPassword();
	 
	        PrintWriter out = response.getWriter();
	        response.setContentType("text/html");
	        response.setHeader("Cache-control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Expires", "-1");
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST,GET");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        response.setHeader("Access-Control-Max-Age", "86400");
	 
	        JsonObject myObj = new JsonObject();
	 
	        //nothing was sent   
	        if(userId == null || password == null){
	            myObj.addProperty("success", false);
	            myObj.addProperty("message", "Please send userId and Password!");
	        }
	        else {
	            if(userId.trim().equals("ajax") && password.trim().equals("request")){
	                myObj.addProperty("success", true);
	                myObj.addProperty("message", "Welcome to as400sampecode.blogspot.com");
	            }
	            else {
	                myObj.addProperty("success", false);
	                myObj.addProperty("message", "Looks like you forgot your login infomartion");
	            }
	        }
	        
	        out.println(myObj.toString());
	        out.close();
        }
    }
}