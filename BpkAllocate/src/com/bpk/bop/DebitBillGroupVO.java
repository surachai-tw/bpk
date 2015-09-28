package com.bpk.bop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SURACHAI.TO
 */
public class DebitBillGroupVO
{
    private long id;
    private String receiptId;
    private String baseBillingGroupId;
    private Float lineSale;
    private Float lineDiscount;
    private Float wlineDiscount;
    private Float linePaid;
    private Float wlinePaid;

    private List<CreditOrderItemVO> listCreditOrderItemVO = new ArrayList();

    /**
     * @return the receiptId
     */
    public String getReceiptId()
    {
        return receiptId;
    }

    /**
     * @param receiptId the receiptId to set
     */
    public void setReceiptId(String receiptId)
    {
        this.receiptId = receiptId;
    }

    /**
     * @return the baseBillingGroupId
     */
    public String getBaseBillingGroupId()
    {
        return baseBillingGroupId;
    }

    /**
     * @param baseBillingGroupId the baseBillingGroupId to set
     */
    public void setBaseBillingGroupId(String baseBillingGroupId)
    {
        this.baseBillingGroupId = baseBillingGroupId;
    }

    /**
     * @return the lineSale
     */
    public Float getLineSale()
    {
        return lineSale;
    }

    /**
     * @param lineSale the lineSale to set
     */
    public void setLineSale(Float lineSale)
    {
        this.lineSale = lineSale;
    }

    /**
     * @return the lineDiscount
     */
    public Float getLineDiscount()
    {
        return lineDiscount;
    }

    /**
     * @param lineDiscount the lineDiscount to set
     */
    public void setLineDiscount(Float lineDiscount)
    {
        this.lineDiscount = lineDiscount;
    }

    /**
     * @return the linePaid
     */
    public Float getLinePaid()
    {
        return linePaid;
    }

    /**
     * @param linePaid the linePaid to set
     */
    public void setLinePaid(Float linePaid)
    {
        this.linePaid = linePaid;
    }

    /**
     * @return the wlineDiscount
     */
    public Float getWlineDiscount()
    {
        return wlineDiscount;
    }

    /**
     * @param wlineDiscount the wlineDiscount to set
     */
    public void setWlineDiscount(Float wlineDiscount)
    {
        this.wlineDiscount = wlineDiscount;
    }

    /**
     * @return the wlinePaid
     */
    public Float getWlinePaid()
    {
        return wlinePaid;
    }

    /**
     * @param wlinePaid the wlinePaid to set
     */
    public void setWlinePaid(Float wlinePaid)
    {
        this.wlinePaid = wlinePaid;
    }

    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id)
    {
        this.id = id;
    }

    public void addOrderItem(CreditOrderItemVO aItemVO)
    {
        if(aItemVO!=null)
        {
            aItemVO.setBaseBillingGroupId(this.getBaseBillingGroupId());
            this.listCreditOrderItemVO.add(aItemVO);
        }
    }

    public void allocateForBillGroup()
    {
        for(int i=0, sizei=this.listCreditOrderItemVO.size(); i<sizei; i++)
        {
            CreditOrderItemVO aCreditOrderItemVO = (CreditOrderItemVO)this.listCreditOrderItemVO.get(i);

            // 1. คำนวณหา Ratio ของ LineSaleOrderItem/LineSale ก่อน
            Float ratioLinePerSale = this.getLineSale()!=0 ? aCreditOrderItemVO.getLineSale()/this.getLineSale() : 0;

            // 2. Weight ของ Line Paid มาจาก Ratio x Paid ทั้งหมดของ Receipt
            Float wLinePaid = ratioLinePerSale*this.getWlinePaid();
            aCreditOrderItemVO.setWlinePaid(wLinePaid);

            // 3. Weight ชอง Line Discount ให้หักจาก Weight ที่ได้จาก Line Paid
            aCreditOrderItemVO.setWlineDisCount(aCreditOrderItemVO.getLineSale()-aCreditOrderItemVO.getWlinePaid());
        }
    }

    public List<CreditOrderItemVO> getListCreditOrderItemVO()
    {
        return this.listCreditOrderItemVO;
    }
}
