package com.bpk.bop;

/**
 *
 * @author SURACHAI.TO
 */
public class CreditOrderItemVO
{
    private Long id;
    private String orderItemId;
    private String baseBillingGroupId;
    private Float lineSale;
    private Float WlineDisCount;
    private Float WlinePaid;

    /**
     * @return the orderItemId
     */
    public String getOrderItemId()
    {
        return orderItemId;
    }

    /**
     * @param orderItemId the orderItemId to set
     */
    public void setOrderItemId(String orderItemId)
    {
        this.orderItemId = orderItemId;
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
     * @return the WlineDisCount
     */
    public Float getWlineDisCount()
    {
        return WlineDisCount;
    }

    /**
     * @param WlineDisCount the WlineDisCount to set
     */
    public void setWlineDisCount(Float WlineDisCount)
    {
        this.WlineDisCount = WlineDisCount;
    }

    /**
     * @return the WlinePaid
     */
    public Float getWlinePaid()
    {
        return WlinePaid;
    }

    /**
     * @param WlinePaid the WlinePaid to set
     */
    public void setWlinePaid(Float WlinePaid)
    {
        this.WlinePaid = WlinePaid;
    }

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }
}
