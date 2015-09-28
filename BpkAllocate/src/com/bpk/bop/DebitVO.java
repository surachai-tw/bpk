package com.bpk.bop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SURACHAI.TO
 */
public class DebitVO
{
    private String visitId;
    private String vn;
    private String receiptId;
    private Float sale;
    private Float discount;
    private Float specialDiscount;
    private Float decimalDiscount;
    private Float paid;

    private List listDebitBillGroupVO = new ArrayList();

    public void addBillGroup(DebitBillGroupVO aBillGrpVO)
    {
        if(aBillGrpVO!=null)
        {
            aBillGrpVO.setReceiptId(getReceiptId());
            this.listDebitBillGroupVO.add(aBillGrpVO);
        }
    }

    /**
     * @return the visitId
     */
    public String getVisitId()
    {
        return visitId;
    }

    /**
     * @param visitId the visitId to set
     */
    public void setVisitId(String visitId)
    {
        this.visitId = visitId;
    }

    /**
     * @return the sale
     */
    public Float getSale()
    {
        return sale;
    }

    /**
     * @param sale the sale to set
     */
    public void setSale(Float sale)
    {
        this.sale = sale;
    }

    /**
     * @return the discount
     */
    public Float getDiscount()
    {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(Float discount)
    {
        this.discount = discount;
    }

    /**
     * @return the specialDiscount
     */
    public Float getSpecialDiscount()
    {
        return specialDiscount;
    }

    /**
     * @param specialDiscount the specialDiscount to set
     */
    public void setSpecialDiscount(Float specialDiscount)
    {
        this.specialDiscount = specialDiscount;
    }

    /**
     * @return the decimalDiscount
     */
    public Float getDecimalDiscount()
    {
        return decimalDiscount;
    }

    /**
     * @param decimalDiscount the decimalDiscount to set
     */
    public void setDecimalDiscount(Float decimalDiscount)
    {
        this.decimalDiscount = decimalDiscount;
    }

    /**
     * @return the paid
     */
    public Float getPaid()
    {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(Float paid)
    {
        this.paid = paid;
    }

    /**
     * @return the listDebitBillGroupVO
     */
    public List<DebitBillGroupVO> getListDebitBillGroupVO()
    {
        return listDebitBillGroupVO;
    }

    /**
     * @param listDebitBillGroupVO the listDebitBillGroupVO to set
     */
    public void setListDebitBillGroupVO(List listDebitBillGroupVO)
    {
        this.listDebitBillGroupVO = listDebitBillGroupVO;
    }

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

    /** Allocate สำหรับส่วนลดพิเศษ และส่วนลดปัดเศษ กระจายเข้าไปให้แต่ละรายการใบเสร็จ */
    public void allocateForReceipt()
    {
        for(int i=0, sizei=this.listDebitBillGroupVO.size(); i<sizei; i++)
        {
            DebitBillGroupVO aDebitBillGroupVO = (DebitBillGroupVO)this.listDebitBillGroupVO.get(i);

            // 1. คำนวณหา Ratio ของ LinePaid/(Paid+Decimal+Special) ก่อน
            Float denominator = this.getPaid()+this.getDecimalDiscount()+this.getSpecialDiscount();
            Float ratioLinePerSale = denominator!=0 ? aDebitBillGroupVO.getLinePaid()/denominator : 0;
            //System.out.println("ratioLinePerSale = "+ratioLinePerSale);

            // 2. Weight ของ Decimal ให้ Billing Group จาก Ratio
            Float wLineDecimal = ratioLinePerSale*this.getDecimalDiscount();
            //System.out.println("wLineDecimal = "+wLineDecimal);

            // 3. Weight ของ Special ให้ Billing Group จาก Ratio
            Float wLineSpecial = ratioLinePerSale*this.getSpecialDiscount();
            //System.out.println("wLineSpecial = "+wLineSpecial);

            // 4. Weight ของ Line Paid มาจาก การหักออกของ Line Paid - Weight Line Decimal - Weight Line Special
            Float wLinePaid = aDebitBillGroupVO.getLinePaid()-wLineDecimal-wLineSpecial;
            //System.out.println("wLinePaid = "+wLinePaid);
            aDebitBillGroupVO.setWlinePaid(wLinePaid);

            // 5. Weight ชอง Line Discount ให้หักจาก Weight ที่ได้จาก Line Paid
            aDebitBillGroupVO.setWlineDiscount(aDebitBillGroupVO.getLineSale()-aDebitBillGroupVO.getWlinePaid());
        }
    }

    /**
     * @return the vn
     */
    public String getVn()
    {
        return vn;
    }

    /**
     * @param vn the vn to set
     */
    public void setVn(String vn)
    {
        this.vn = vn;
    }

}
