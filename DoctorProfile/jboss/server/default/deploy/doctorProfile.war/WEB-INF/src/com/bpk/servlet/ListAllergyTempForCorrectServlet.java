package com.bpk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.bpk.dao.AllergyDAO;
import com.bpk.dto.BpkAllergyTempVO;
import com.bpk.dto.ParameterFlag;
import com.bpk.dto.ResultFlag;

@SuppressWarnings("serial")
public class ListAllergyTempForCorrectServlet extends HttpServlet {
	
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        doGet(request, response);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	// response.setContentType("text/javascript; charset=TIS-620");
        response.setContentType("application/json; charset=TIS-620");
        PrintWriter out = response.getWriter() ;

        AllergyDAO aDAO = new AllergyDAO();
		HashMap param = new HashMap();
        String limit = request.getParameter("limit");
        if(limit==null)
        	limit = "1000";
        param.put(ParameterFlag.LIMIT, limit);
        
        HashMap result = aDAO.listAllergyTempForCorrect(param);
        
        if(ResultFlag.STATUS_SUCCESS.equals(result.get(ResultFlag.STATUS)))
        {
        	List listBpkAllergyTempVO = (List)result.get(ResultFlag.RESULT_DATA);
        	
        	StringBuilder json = new StringBuilder("{\"success\":true,\"ListBpkAllergyTemp\":[");
        	int sizei = listBpkAllergyTempVO.size();
        	for(int i=0; i<sizei; i++)
        	{
        		BpkAllergyTempVO tmpBpkAllergyTempVO = (BpkAllergyTempVO)listBpkAllergyTempVO.get(i);
        		
        		json.append(tmpBpkAllergyTempVO.toJSON());
        		
        		if(i+1<sizei)
        		{
        			json.append(",");
        		}
        	}
        	json.append("],\"totalCount\":").append(sizei).append("}");
        	String text = (json.toString().replaceAll(System.getProperty("line.separator"), " ").replaceAll("\t",  " ").replaceAll("\n",  " ").replaceAll("\r",  " "));
        	// BpkUtility.printDebug(this, text);
        	out.print(text);
        	out.close();
        }
        
        /*
        out.print('[');
        out.print(" {");
        out.print("\"clinic\": \"");
        
		out.print("Medicine");
        
        out.print("\",");
        out.print("\"speciality\": \"");
        
        out.print("Medicine children");
        
        out.print("\",");
        out.print("\"doctorname\": \"");
        
        out.print("Dr.Pana");
        
        out.print("\" }");
        out.print(']');
        out.close() ;
        */
        
        System.out.println("REQUEST TO "+this.getClass().getSimpleName()+" SUCCESS");
    }

}
