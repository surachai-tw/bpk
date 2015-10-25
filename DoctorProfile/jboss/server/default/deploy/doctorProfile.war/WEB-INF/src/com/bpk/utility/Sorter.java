package com.bpk.utility;

import java.text.Collator;
import java.util.*;

/**
 * <p>Title: Sorter Utility</p>
 * <p>Description: For Sort Any Object</p>
 * <p>Copyright: Copyright (c) 2546</p>
 * @author Surachai Thowong,
 * <br> Version 1.0, Create on 21 Jan 04, Bubble Sort for String[] and list of any Object.
 * <br> Version 1.5, Update on 11 Dec 04, Quick Sort for String[] and list of any Object.
 *                   Improve performance and save used time to sort more than 80%
 * @version 1.5
 */

public class Sorter
{
  /** เรียงจากน้อยไปมาก */
  public final static int ASCENDING = 1;
  /** เรียงจากมากไปน้อย */
  public final static int DESCENDING = -1;

  Collator theCollator;

  public Sorter()
  {
    theCollator = Collator.getInstance();
  }

  public void sort(String a[], int sortType) throws Exception
  {
     if(a!=null)
     {
       try
       {
	   qSort(a, sortType, 0, a.length-1);
       }
       catch(Exception ex)
       {
	   throw ex;
       }
     }
  }

  public void sort(int a[], int sortType) throws Exception
  {
     if(a!=null)
     {
       try
       {
	   qSort(a, sortType, 0, a.length-1);
       }
       catch(Exception ex)
       {
	   throw ex;
       }
     }
  }

   /** Bubble Sort String[] */
   protected void bSort(String a[], int sortType) throws Exception
   {
    for (int i = 0; i < a.length; i++)
    {
      for (int j = i + 1; j < a.length; j++)
      {
	if (compare(a[i], a[j]) == sortType)
	{
	  swap(a, i, j);
	}
      }
    }
   }

   /** Quick Sort String[] */
   protected void qSort(String a[], int sortType, int lo0, int hi0) throws Exception
   {
      if(sortType==Sorter.ASCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  String mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = a[ ( lo0 + hi0 ) / 2 ];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(a[lo], mid)<0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(a[hi], mid)>0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(a, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( a, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( a, sortType, lo, hi0 );
	  }
      }
      else if(sortType==Sorter.DESCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  String mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = a[ ( lo0 + hi0 ) / 2 ];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(a[lo], mid)>0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(a[hi], mid)<0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(a, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( a, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( a, sortType, lo, hi0 );
	  }
      }
   }

   /** Quick Sort double[] */
   protected void qSort(int a[], int sortType, int lo0, int hi0) throws Exception
   {
      if(sortType==Sorter.ASCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  int mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = a[ ( lo0 + hi0 ) / 2 ];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(a[lo], mid)<0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(a[hi], mid)>0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(a, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( a, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( a, sortType, lo, hi0 );
	  }
      }
      else if(sortType==Sorter.DESCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  int mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = a[ ( lo0 + hi0 ) / 2 ];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(a[lo], mid)>0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(a[hi], mid)<0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(a, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( a, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( a, sortType, lo, hi0 );
	  }
      }
   }

  /** Sort */
  @SuppressWarnings("rawtypes")
  public void sort(List listData, String index[], int sortType) throws Exception
  {
    if(listData!=null && listData.size()>1)
    {
		try
		{
		  qSort(listData, index, sortType, 0, listData.size()-1);
		  //bSort(listData, index, sortType);
		}
		catch(Exception ex)
		{
		    throw ex;
		}
    }
  }

  /** Sort */
  public void sort(Object data[], String index[], int sortType) throws Exception
  {
      if(data!=null && data.length>1)
      {
	try
	{
	  qSort(data, index, sortType, 0, data.length-1);
	  //bSort(data, index, sortType);
	}
	catch(Exception ex)
	{
	    throw ex;
	}
      }
  }

  /** Sort */
  @SuppressWarnings("rawtypes")
  public void sort(List listData, double index[], int sortType) throws Exception
  {
    if(listData!=null && listData.size()>1)
    {
		try
		{
		  qSort(listData, index, sortType, 0, listData.size()-1);
		  //bSort(listData, index, sortType);
		}
		catch(Exception ex)
		{
		    throw ex;
		}
    }
  }

  /** Bubble sort แบบใช้ index */
  @SuppressWarnings("rawtypes")
  protected void bSort(List listData, String index[], int sortType) throws Exception
  {
    for (int i = 0; i < index.length; i++)
    {
      for (int j = i + 1; j < index.length; j++)
      {
		if (compare(index[i], index[j]) == sortType)
		{
		  swap(listData, index, i, j);
		}
      }
    }
  }

  /** Quick Sort */
  @SuppressWarnings("rawtypes")
  protected void qSort(List listData, String index[], int sortType, int lo0, int hi0) throws Exception
  {
      if(sortType==Sorter.ASCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  String mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = index[( lo0 + hi0 ) / 2];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(index[lo], mid)<0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(index[hi], mid)>0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(listData, index, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( listData, index, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( listData, index, sortType, lo, hi0 );
	  }
      }
      else if(sortType==Sorter.DESCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  String mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = index[ ( lo0 + hi0 ) / 2 ];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(index[lo], mid)>0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(index[hi], mid)<0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(listData, index, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( listData, index, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( listData, index, sortType, lo, hi0 );
	  }
      }
  }

  /** Quick Sort */
  protected void qSort(Object data[], String index[], int sortType, int lo0, int hi0) throws Exception
  {
      if(sortType==Sorter.ASCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  String mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = index[( lo0 + hi0 ) / 2];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(index[lo], mid)<0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(index[hi], mid)>0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(data, index, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( data, index, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( data, index, sortType, lo, hi0 );
	  }
      }
      else if(sortType==Sorter.DESCENDING)
      {
	  int lo = lo0;
	  int hi = hi0;
	  String mid;

	  if (hi0 > lo0)
	  {

	     /* Arbitrarily establishing partition element as the midpoint of
	      * the array.
	      */
	     mid = index[ ( lo0 + hi0 ) / 2 ];

	     // loop through the array until indices cross
	     while(lo <= hi)
	     {
		/* find the first element that is greater than or equal to
		 * the partition element starting from the left Index.
		 */
		 while((lo<hi0) && (compare(index[lo], mid)>0))
		     ++lo;

		/* find an element that is smaller than or equal to
		 * the partition element starting from the right Index.
		 */
		 while((hi>lo0) && (compare(index[hi], mid)<0))
		     --hi;

		// if the indexes have not crossed, swap
		if(lo <= hi)
		{
		   swap(data, index, lo, hi);
		   ++lo;
		   --hi;
		}
	     }

	     /* If the right index has not reached the left side of array
	      * must now sort the left partition.
	      */
	     if( lo0 < hi )
		qSort( data, index, sortType, lo0, hi );

	     /* If the left index has not reached the right side of array
	      * must now sort the right partition.
	      */
	     if( lo < hi0 )
		qSort( data, index, sortType, lo, hi0 );
	  }
      }
  }

  /** Quick Sort */
  @SuppressWarnings("rawtypes")
  protected void qSort(List listData, double index[], int sortType, int lo0, int hi0) throws Exception
  {
      if(sortType==Sorter.ASCENDING)
      {
		  int lo = lo0;
		  int hi = hi0;
		  double mid;
	
		  if (hi0 > lo0)
		  {
	
		     /* Arbitrarily establishing partition element as the midpoint of
		      * the array.
		      */
		     mid = index[( lo0 + hi0 ) / 2];
	
		     // loop through the array until indices cross
		     while(lo <= hi)
		     {
				/* find the first element that is greater than or equal to
				 * the partition element starting from the left Index.
				 */
				 while((lo<hi0) && (compare(index[lo], mid)<0))
				     ++lo;
		
				/* find an element that is smaller than or equal to
				 * the partition element starting from the right Index.
				 */
				 while((hi>lo0) && (compare(index[hi], mid)>0))
				     --hi;
		
				 // if the indexes have not crossed, swap
				 if(lo <= hi)
				 {
					   swap(listData, index, lo, hi);
					   ++lo;
					   --hi;
				 }
		     }
	
		     /* If the right index has not reached the left side of array
		      * must now sort the left partition.
		      */
		     if( lo0 < hi )
				qSort( listData, index, sortType, lo0, hi );
		
		     /* If the left index has not reached the right side of array
		      * must now sort the right partition.
		      */
		     if( lo < hi0 )
				qSort( listData, index, sortType, lo, hi0 );
		  }
      }
      else if(sortType==Sorter.DESCENDING)
      {
		  int lo = lo0;
		  int hi = hi0;
		  double mid;
	
		  if (hi0 > lo0)
		  {
	
		     /* Arbitrarily establishing partition element as the midpoint of
		      * the array.
		      */
		     mid = index[ ( lo0 + hi0 ) / 2 ];
	
		     // loop through the array until indices cross
		     while(lo <= hi)
		     {
				/* find the first element that is greater than or equal to
				 * the partition element starting from the left Index.
				 */
				 while((lo<hi0) && (compare(index[lo], mid)>0))
				     ++lo;
		
				/* find an element that is smaller than or equal to
				 * the partition element starting from the right Index.
				 */
				 while((hi>lo0) && (compare(index[hi], mid)<0))
				     --hi;
		
				// if the indexes have not crossed, swap
				if(lo <= hi)
				{
				   swap(listData, index, lo, hi);
				   ++lo;
				   --hi;
				}
		     }
	
		     /* If the right index has not reached the left side of array
		      * must now sort the left partition.
		      */
		     if( lo0 < hi )
		    	 qSort( listData, index, sortType, lo0, hi );
	
		     /* If the left index has not reached the right side of array
		      * must now sort the right partition.
		      */
		     if( lo < hi0 )
		    	 qSort( listData, index, sortType, lo, hi0 );
		  }
      }
  }

  /** Sort แบบดั้งเดิม */
  private void swap(String a[], int i, int j)
  {
    String T;
    T = a[i];
    a[i] = a[j];
    a[j] = T;
  }

  /** Sort แบบดั้งเดิม */
  private void swap(int a[], int i, int j)
  {
    int T;
    T = a[i];
    a[i] = a[j];
    a[j] = T;
  }

  /** Sort แบบใช้ index */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private void swap(List listData, String index[], int i, int j)
  {
    String T;
    T = index[i];
    Object objT = listData.get(i);
    index[i] = index[j];
    listData.set(i, listData.get(j));
    index[j] = T;
    listData.set(j, objT);
  }

  private void swap(Object data[], String index[], int i, int j)
  {
    String T;
    T = index[i];
    Object objT = data[i];
    index[i] = index[j];
    data[i] = data[j];
    index[j] = T;
    data[j] = objT;
  }

  /** ใช้ Collator เข้ามาช่วย */
  private int compare(String a1, String a2)
  {
    if(a1==null)
      a1 = "";
    if(a2==null)
      a2 = "";

    /* สำหรับ แก้ bugs ของ Collator.compare
       ซึ่ง จะมีปัญหา เมื่อ string a1 = a2 และมีอักษรภาษาไทยที่เป็นสระ อยู่ในตำแหน่งท้ายสุด
    */
    if(a1.equals(a2))
      return 0;

    int result = theCollator.compare(a1, a2);

    if (result < 0)
      return -1;
    else if (result == 0)
      return 0;
    else
      return 1;
  }

  /** Sort แบบใช้ index */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private void swap(List listData,  double index[], int i, int j)
  {
    double T;
    T = index[i];
    Object objT = listData.get(i);
    index[i] = index[j];
    listData.set(i, listData.get(j));
    index[j] = T;
    listData.set(j, objT);
  }

  /** เปรียบเทียบตัวเลข */
  private int compare(double a1, double a2)
  {
    if(a1==a2)
      return 0;
    else if(a1 < a2)
      return -1;
    else
      return 1;
  }

}