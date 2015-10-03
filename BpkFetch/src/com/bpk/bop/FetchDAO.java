package com.bpk.bop;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author SURACHAI.TO
 */
public class FetchDAO
{
    public int execute(String sqlFetch)
    {
        Connection conn = null;
        Statement stmt = null;
        int resultRec = 0;
        try
        {
            conn = DAOFactory.getConnection();
            stmt = conn.createStatement();

            // System.out.println(sqlFetch);
            resultRec = stmt.executeUpdate(sqlFetch);

            stmt.close();
            conn.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            stmt = null;
            conn = null;
        }
        return resultRec;
    }
}
