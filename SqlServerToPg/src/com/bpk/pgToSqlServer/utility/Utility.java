package com.bpk.pgToSqlServer.utility;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author surachai.tw
 */
public class Utility
{

    /**
     * ����Ѻ��èѴ�ٻẺ����Ţ����繨ӹǹ��� 2 ��ѡ 㹡óշ����
     * ��ѡ��������� 0 ��˹��
     */
    private static java.text.NumberFormat nfInt2 = java.text.NumberFormat.getInstance();
    /**
     * ����Ѻ��èѴ�ٻẺ����Ţ����繨ӹǹ��� 3 ��ѡ 㹡óշ����
     * ��ѡ��������� 0 ��˹��
     */
    private static java.text.NumberFormat nfInt3 = java.text.NumberFormat.getInstance();
    /**
     * ����Ѻ��èѴ�ٻẺ����Ţ����繨ӹǹ��� 4 ��ѡ 㹡óշ����
     * ��ѡ��������� 0 ��˹��
     */
    private static NumberFormat nfInt4 = NumberFormat.getInstance();
    private static String LAST_CURRENT_TIME = "";
    private static int SEQ_NUM = 1;
    /** ����Ѻ�红����Ţͧ Error Log */
    private static StringBuilder logError = new StringBuilder();

    static
    {
        nfInt2.setGroupingUsed(false);
        nfInt2.setMaximumIntegerDigits(2);
        nfInt2.setMinimumIntegerDigits(2);

        nfInt3.setGroupingUsed(false);
        nfInt3.setMaximumIntegerDigits(3);
        nfInt3.setMinimumIntegerDigits(3);

        nfInt4.setGroupingUsed(false);
        nfInt4.setMaximumIntegerDigits(4);
        nfInt4.setMinimumIntegerDigits(4);
    }

    /** �����һѨ�غѹ��Ẻ String �ٻẺ �� �.�. 4 ��ѡ:��͹:�ѹ */
    public static String getCurrentDate()
    {
        try
        {
            Calendar cal = Calendar.getInstance(new Locale("en", "US"));
            return (new StringBuffer(String.valueOf(cal.get(Calendar.YEAR)))).append("-").append(nfInt2.format(cal.get(Calendar.MONTH) + 1)).append("-").append(nfInt2.format(cal.get(Calendar.DATE))).toString();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    /** �����һѨ�غѹ��Ẻ String �ٻẺ �������:�ҷ�:�Թҷ� */
    public static String getCurrentTime()
    {
        try
        {
            Calendar cal = Calendar.getInstance(new Locale("en", "US"));
            return (new StringBuffer(nfInt2.format(cal.get(Calendar.HOUR_OF_DAY)))).append(":").append(nfInt2.format(cal.get(Calendar.MINUTE))).append(":").append(nfInt2.format(cal.get(Calendar.SECOND))).toString();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getStringVO(String str)
    {
        if (str == null || "".equals(str) || "null".equals(str))
        {
            return "";
        } else
        {
            return str;
        }
    }

    public static String getActiveVO(String str)
    {
        if (str == null || (!"0".equals(str) && !"1".equals(str)))
        {
            return "0";
        } else
        {
            return str;
        }
    }

    /** ����� debug �͡�ҧ˹�Ҩ������ٻẺ��� BPK DEBUG <���� Class>, message */
    public static void printCoreDebug(Object obj, String message)
    {
        // if (isTrue(System.getProperty("bpk.debug")))
        // {
        String className = obj.getClass().toString();
        className = className.substring(className.lastIndexOf(".") + 1);
        System.out.println("BPK DEBUG " + className + ", " + message);
        className = null;
        // log = Logger.getLogger(obj.getClass());
        // log.debug(message);
        // }
    }

    public static boolean isNotNull(String str)
    {
        return (str != null && !"".equals(str.trim()));
    }

    public static boolean isNotNull(List list)
    {
        return (list != null && !list.isEmpty());
    }

    public static boolean isNotNull(Collection coll)
    {
        return (coll != null && !coll.isEmpty());
    }

    public static boolean isNull(String str)
    {
        return (str == null || "".equals(str.trim()));
    }

    public static boolean isNull(List list)
    {
        return (list == null || list.isEmpty());
    }

    public static boolean isNull(Collection coll)
    {
        return (coll == null || coll.isEmpty());
    }

    /** ��Ǩ�ͺ����繵���Ţ������� */
    public static boolean isNumber(String number)
    {
        try
        {
            Double.parseDouble(number.trim());
            return true;
        } catch (Exception ex)
        {
            // ex.printStackTrace();
        }
        return false;
    }

    /** ���Ǩ�ͺ��� String �դ��������� TRUE ���� FALSE */
    public static boolean isTrue(String flag)
    {
        if (flag != null)
        {
            return "1".equals(flag) || "Y".equalsIgnoreCase(flag) || "true".equalsIgnoreCase(flag) || "T".equalsIgnoreCase(flag);
        }

        return false;
    }

    /** ��ͧ�ѹ�����Դ��Ҵ����Դ�ҡ��ä鹤ӷ��������ͧ���� ' */
    public static String addSingleQuote(String keyword)
    {
        if (Utility.isNotNull(keyword))
        {
            keyword = keyword.replaceAll("\'", "\'\'").trim();
        }
        return keyword;
    }

    /** ���ҧ�����Ţ Object ID */
    public static String generateObjectID() throws Exception
    {
        try
        {
            return getUUID();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        } finally
        {
        }
    }

    public static String getUUID()
    {
        String curTime = getTimeNow();
        SEQ_NUM = LAST_CURRENT_TIME.equals(curTime) ? SEQ_NUM + 1 : 1;

        LAST_CURRENT_TIME = curTime;
        return "0" + curTime + nfInt2.format(SEQ_NUM);
    }

    private static String getTimeNow()
    {
        try
        {
            Calendar cal = Calendar.getInstance(new Locale("en", "EN"));
            return new StringBuffer(nfInt2.format(cal.get(Calendar.YEAR))).append(nfInt2.format(cal.get(Calendar.MONTH) + 1)).append(nfInt2.format(cal.get(Calendar.DAY_OF_MONTH))).append(nfInt2.format(cal.get(Calendar.HOUR_OF_DAY))).append(nfInt2.format(cal.get(Calendar.MINUTE))).append(nfInt2.format(cal.get(Calendar.SECOND))).append(nfInt3.format(cal.get(Calendar.MILLISECOND))).toString();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return "";
    }

    /** �红����Ţͧ text ������������ѹ�֡������ */
    public static void keepLog(String logText)
    {
        logError.append(logText).append(System.getProperty("line.separator"));
    }

    /** �红����Ţͧ text ������������ѹ�֡������ */
    public static void writeLog(String absFilename)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(absFilename);
            Writer out = new OutputStreamWriter(fos);
            out.write(logError.toString());
            out.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
