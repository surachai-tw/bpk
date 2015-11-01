package com.bpk.utility;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletRequest;

public class BpkUtility
{

	@SuppressWarnings("unused")
	public static boolean isNumeric(String num)
	{
		try
		{
			float newNum = Float.parseFloat(num);

			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	/** Validate ค่า String ให้เป็น "" ไม่ใช่ null */
	public static String getValidateString(Object paramObj)
	{
		if (paramObj != null && paramObj instanceof String)
		{
			return getValidateString((String) paramObj);
		}

		return "";
	}

	/** Validate ค่า String ให้เป็น "" ไม่ใช่ null */
	public static String getValidateString(String paramString)
	{
		if (paramString == null || "null".equalsIgnoreCase(paramString))
		{
			return "";
		}
		return paramString.trim();
	}

	/** Validate ค่า String ให้เป็น "" ไม่ใช่ null */
	public static String getValidateStringForServlet(ServletRequest req, Object paramObj)
	{
		if (paramObj != null && paramObj instanceof String)
		{
			return getValidateStringForServlet(req, (String) paramObj);
		}

		return "";
	}

	public static String DECODE_CHARSET = "ISO-8859-1";
	public static String ENCODE_CHARSET = "TIS-620";

	/** Validate ค่า String ให้เป็น "" ไม่ใช่ null */
	public static String getValidateStringForServlet(ServletRequest req, String paramString)
	{
		// printDebug(new BpkUtility(), req.getParameter(paramString));
		if (req.getParameter(paramString) == null || "null".equalsIgnoreCase(req.getParameter(paramString)))
		{
			return "";
		}
		try
		{
			return new String(req.getParameter(paramString).getBytes(DECODE_CHARSET), ENCODE_CHARSET);
			// return req.getParameter(paramString);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return "";
	}

	/** สำหรับจัด Format ของตัวเลข 2 ตัวเท่านั้น */
	private final static NumberFormat nf2 = NumberFormat.getInstance();
	/** สำหรับจัด Format ของตัวเลข 4 ตัวเท่านั้น NO COMMA */
	private final static NumberFormat nf4 = NumberFormat.getInstance();

	/** สำหรับจัด Format ของตัวเลข 6 ตัวเท่านั้น NO COMMA */
	private final static NumberFormat nf9 = NumberFormat.getInstance();

	static
	{
		nf2.setMaximumFractionDigits(0);
		nf2.setMinimumFractionDigits(0);
		nf2.setMaximumIntegerDigits(2);
		nf2.setMinimumIntegerDigits(2);
		nf2.setGroupingUsed(false);

		nf4.setMaximumFractionDigits(0);
		nf4.setMinimumFractionDigits(0);
		nf4.setMaximumIntegerDigits(4);
		nf4.setMinimumIntegerDigits(4);
		nf4.setGroupingUsed(false);

		nf9.setMaximumFractionDigits(0);
		nf9.setMinimumFractionDigits(0);
		nf9.setMaximumIntegerDigits(9);
		nf9.setMinimumIntegerDigits(9);
		nf9.setGroupingUsed(false);
	}

	/** สำหรับใช้ในการกำหนด Locale TH */
	public final static Locale LOCALE_THAI = new Locale("TH", "th");

	public static String formatDateTimeShort(Locale lc, String dateStd)
	{
		if (dateStd != null && (dateStd.length() == 19 || dateStd.length() == 16))
		{
			try
			{
				int yyyy = Integer.parseInt(dateStd.substring(0, 4));

				if (lc == null)
				{
					lc = LOCALE_THAI;
				}

				if (!LOCALE_THAI.equals(lc) && yyyy > 2500)
				{
					yyyy = yyyy - 543;
				}

				String YY = nf2.format(yyyy);
				String MM = nf2.format(Integer.parseInt(dateStd.substring(5, 7)));
				String DD = nf2.format(Integer.parseInt(dateStd.substring(8, 10)));

				String HH = nf2.format(Integer.parseInt(dateStd.substring(11, 13)));
				String mm = nf2.format(Integer.parseInt(dateStd.substring(14, 16)));

				return new StringBuilder(DD).append("/").append(MM).append("/").append(YY).append(" ").append(HH).append(":").append(mm).toString();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
			}
		}
		return "";
	}

	public static String formatDateMedium(Locale lc, String dateStd)
	{
		if (dateStd != null && dateStd.length() >= 10)
		{
			try
			{
				if (lc == null)
				{
					lc = LOCALE_THAI;
				}

				java.util.Date date = getDateChristFromString(dateStd.substring(0, 10));
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("d MMM yyyy", lc);

				// System.out.println("dateFormat.format(" + date + ") = " +
				// dateFormat.format(date));
				return dateFormat.format(date);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
			}
		}
		return "";
	}

	public static String formatDateTimeMedium(Locale lc, String dateStd)
	{
		if (dateStd != null && (dateStd.length() == 19 || dateStd.length() == 16))
		{
			try
			{
				if (lc == null)
				{
					lc = LOCALE_THAI;
				}

				java.util.Date date = getDateChristFromString(dateStd.substring(0, 10), dateStd.substring(11));
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("d MMM yy HH:mm", lc);

				// System.out.println("dateFormat.format(" + date + ") = " +
				// dateFormat.format(date));
				return dateFormat.format(date);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
			}
		}
		return "";
	}

	public static String convertDate2StdFormat(java.util.Date aDate)
	{
		if (aDate != null)
		{
			Calendar aCal = Calendar.getInstance(new Locale("en", "US"));
			StringBuffer stdDate = new StringBuffer();

			try
			{
				aCal.setTime(aDate);

				int year = aCal.get(Calendar.YEAR);
				int month = aCal.get(Calendar.MONTH) + 1;
				int date = aCal.get(Calendar.DAY_OF_MONTH);

				String newDate = stdDate.append(nf4.format(year)).append("-").append(nf2.format(month)).append("-").append(nf2.format(date)).toString();
				// System.out.println(newDate);

				return newDate;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				aCal = null;
			}
			return stdDate.toString();
		}
		return "";
	}

	/**
	 * แปลงข้อมูลจาก yyyy-mm-dd String ให้เป็น Date ส่งค่าออกเป็น ค.ศ. เสมอ
	 */
	public static Date getDateChristFromString(String yyyy_mm_dd)
	{
		if (yyyy_mm_dd != null && (yyyy_mm_dd.length() == 10 || yyyy_mm_dd.length() == 19))
		{
			String yyyy = yyyy_mm_dd.substring(0, 4);
			String mm = yyyy_mm_dd.substring(5, 7);
			String dd = yyyy_mm_dd.substring(8, 10);

			Calendar cal = Calendar.getInstance(Locale.UK);
			int y = Integer.parseInt(yyyy);
			if (y > 2500)
			{
				y = y - 543;
			}
			cal.set(Calendar.YEAR, y);
			cal.set(Calendar.MONTH, Integer.parseInt(mm) - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));

			return cal.getTime();
		}

		return null;
	}

	/**
	 * แปลงข้อมูลจาก yyyy-mm-dd String และ hh-mm-ss String ในหน่วย 24 ชม.
	 * ให้เป็น Date ส่งค่าออกเป็น ค.ศ. เสมอ
	 */
	public static Date getDateChristFromString(String yyyy_mm_dd, String hh_mm_ss)
	{
		// วันที่ต้องมีครบทั้ง ปี เดือน วัน ส่วนเวลาอาจจะมีแค่ ชั่วโมง กับ นาที
		// ก็ได้
		if (yyyy_mm_dd != null && yyyy_mm_dd.length() >= 10)
		{
			if (hh_mm_ss != null && hh_mm_ss.length() >= 5)
			{
				String yyyy = yyyy_mm_dd.substring(0, 4);
				String mm = yyyy_mm_dd.substring(5, 7);
				String dd = yyyy_mm_dd.substring(8, 10);

				String hh = hh_mm_ss.substring(0, 2);
				String min = hh_mm_ss.substring(3, 5);
				String ss = null;
				if (hh_mm_ss.length() == 8)
				{
					ss = hh_mm_ss.substring(6);
				}
				else
				{
					ss = "00";
				}

				Calendar cal = Calendar.getInstance(new Locale("en", "US"));
				int y = Integer.parseInt(yyyy);
				if (y > 2500)
				{
					y = y - 543;
				}
				cal.set(Calendar.YEAR, y);
				cal.set(Calendar.MONTH, Integer.parseInt(mm) - 1);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hh));
				cal.set(Calendar.MINUTE, Integer.parseInt(min));
				cal.set(Calendar.SECOND, Integer.parseInt(ss));
				cal.set(Calendar.MILLISECOND, 0);
				return cal.getTime();
			}
			else
			{
				return getDateFromString(yyyy_mm_dd);
			}
		}
		return null;
	}

	/**
	 * แปลงข้อมูลจาก yyyy-mm-dd String ให้เป็น Date
	 */
	public static Date getDateFromString(String yyyy_mm_dd)
	{
		if (yyyy_mm_dd != null && (yyyy_mm_dd.length() == 10 || yyyy_mm_dd.length() == 19))
		{
			String yyyy = yyyy_mm_dd.substring(0, 4);
			String mm = yyyy_mm_dd.substring(5, 7);
			String dd = yyyy_mm_dd.substring(8, 10);

			Calendar cal = Calendar.getInstance(Locale.UK);
			cal.set(Calendar.YEAR, Integer.parseInt(yyyy));
			cal.set(Calendar.MONTH, Integer.parseInt(mm) - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));

			return cal.getTime();
		}

		return null;
	}

	public static void printDebug(Object objDebug, String text)
	{
		String debug = System.getProperty("bpk.debug");
		if(BpkUtility.isTrue(debug))
		{		
			objDebug = objDebug != null ? "[BPK DEBUG " + objDebug.getClass().getSimpleName() + "], " : "[BPK DEBUG NULL], ";
			System.out.println(objDebug + text);
		}
	}

	public static boolean isTrue(String flag)
	{
		if (flag != null)
		{
			return "1".equals(flag) || "Y".equalsIgnoreCase(flag) || "true".equalsIgnoreCase(flag) || "T".equalsIgnoreCase(flag);
		}

		return false;
	}

	public static String[] splitString(String splitText, String splitBy)
	{
		StringTokenizer tokens = new StringTokenizer(splitText, splitBy);
		String[] splited = new String[tokens.countTokens()];
		int index = 0;
		while (tokens.hasMoreTokens())
		{
			splited[index] = tokens.nextToken();
			++index;
		}

		tokens = null;

		return splited;
	}
	
	/** แปลง HN กลับเป็นรูปแบบ Database format เพื่อใช้ค้นหาได้เร็วกว่า */
	public static String getHnDbFormat(String hn)
	{
		if(hn!=null && hn.length()==11)
		{
			try
			{
				return hn.substring(0, 1)+hn.substring(2, 4)+nf9.format(Integer.parseInt(hn.substring(5)));
			}
			catch(Exception ex)
			{
				return hn;
			}
		}
		return hn;
	}
}
