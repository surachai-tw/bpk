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

    /** Allocate ����Ѻ��ǹŴ����� �����ǹŴ�Ѵ��� ��Ш���������������¡������� */
    public void allocateForReceipt()
    {
        for(int i=0, sizei=this.listDebitBillGroupVO.size(); i<sizei; i++)
        {
            DebitBillGroupVO aDebitBillGroupVO = (DebitBillGroupVO)this.listDebitBillGroupVO.get(i);

            // 1. �ӹǳ�� Ratio �ͧ LinePaid/(Paid+Decimal+Special) ��͹
            Float denominator = this.getPaid()+this.getDecimalDiscount()+this.getSpecialDiscount();
            Float ratioLinePerSale = denominator!=0 ? aDebitBillGroupVO.getLinePaid()/denominator : 0;
            //System.out.println("ratioLinePerSale = "+ratioLinePerSale);

            // 2. Weight �ͧ Decimal ��� Billing Group �ҡ Ratio
            Float wLineDecimal = ratioLinePerSale*this.getDecimalDiscount();
            //System.out.println("wLineDecimal = "+wLineDecimal);

            // 3. Weight �ͧ Special ��� Billing Group �ҡ Ratio
            Float wLineSpecial = ratioLinePerSale*this.getSpecialDiscount();
            //System.out.println("wLineSpecial = "+wLineSpecial);

            // 4. Weight �ͧ Line Paid �Ҩҡ ����ѡ�͡�ͧ Line Paid - Weight Line Decimal - Weight Line Special
            Float wLinePaid = aDebitBillGroupVO.getLinePaid()-wLineDecimal-wLineSpecial;
            //System.out.println("wLinePaid = "+wLinePaid);
            aDebitBillGroupVO.setWlinePaid(wLinePaid);

            // 5. Weight �ͧ Line Discount ����ѡ�ҡ Weight �����ҡ Line Paid
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
