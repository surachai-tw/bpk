package com.iMed.iMedApp.iMedInterfaceFXApp.ibatis;

import java.io.Reader;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.common.resources.*;

public class FXSqlMapConfig
{
  private static final SqlMapClient sqlMap;

  static
  {
    try
    {
      String resource = "com/iMed/iMedApp/iMedInterfaceFXApp/ibatis/InterfaceFXSqlMapConfig.xml";
      Reader reader = Resources.getResourceAsReader(resource);
      sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
    }
    catch(Exception e)
    {
      // If you get an error at this point, it doesn’t matter what it was. It is going to be
      // unrecoverable and we will want the app to blow up hard so we are aware of the
      // problem. You should always log such errors and re-throw them in such a way that
      // you can be made immediately aware of the problem.
      e.printStackTrace();
      throw new RuntimeException("Error initializing SqlMapConfig class.Cause : " + e);
    }
  }

  public static SqlMapClient getSqlMapInstance()
  {
    return sqlMap;
  }

  private static SqlMapClient initSqlMapInstance(String resource)
  {
    SqlMapClient customSqlMap = null;
    try
    {
      Reader reader = Resources.getResourceAsReader(resource);
      customSqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("Error initalizing ReportSqlMapConfig class.Cause : " + e);
    }
    return customSqlMap;
  }
}