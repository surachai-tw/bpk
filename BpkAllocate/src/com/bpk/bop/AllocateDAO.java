package com.bpk.bop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SURACHAI.TO
 */
public class AllocateDAO {

    public List<String> listOpdVisitIdForReceiveDae(String receiveDate)
    {
        List<String> listResult = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuilder sqlCmd = null;
        try
        {
            sqlCmd = new StringBuilder("SELECT DISTINCT visit_id, visit_date, visit_time FROM bpk_account_debit_detail WHERE fix_visit_type_id='0' AND receive_date='").append(receiveDate).append("' ORDER BY visit_date, visit_time");

            conn = DAOFactory.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlCmd.toString());

            for(;rs.next();)
            {
                listResult.add(rs.getString("visit_id"));
            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            sqlCmd = null;
            rs = null;
            stmt = null;
            conn = null;
        }
        return listResult;
    }

    public List<String> listIpdVisitIdForDischargeDae(String dischargeDate)
    {
        List<String> listResult = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuilder sqlCmd = null;
        try
        {
            sqlCmd = new StringBuilder("SELECT DISTINCT visit_id, visit_date, visit_time FROM bpk_account_debit_detail WHERE fix_visit_type_id='1' AND financial_discharge_date='").append(dischargeDate).append("' ORDER BY visit_date, visit_time");

            conn = DAOFactory.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlCmd.toString());

            for(;rs.next();)
            {
                listResult.add(rs.getString("visit_id"));
            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            sqlCmd = null;
            rs = null;
            stmt = null;
            conn = null;
        }
        return listResult;
    }

    /**
     * 1. ส่วนของการ Allocate ส่วน Debit กระจาย Discount ทั้งหมด ให้ระดับ Billing Group ทีละใบเสร็จ
     * 2. ส่วนของการ Allocate ส่วน Credit จาก Billing Group ของ Debit โดยรวมทั้ง Visit
     * @param visitId
     * @param receiveDate
     * @return
     */
    public boolean makeAlloacteOpd(String visitId, String receiveDate)
    {
        List<DebitVO> listDebitVO = new ArrayList<DebitVO>();
        Connection conn = null;
        Statement stmt = null, stmtBillGroup = null, stmtItem = null;
        ResultSet rs = null, rsBillGroup = null, rsItem = null;
        try
        {
            conn = DAOFactory.getConnection();

            // 1. ส่วนของการ Allocate ส่วน Debit กระจาย Discount ทั้งหมด ให้ระดับ Billing Group ทีละใบเสร็จ
            System.out.println();
            System.out.println("Start allocate "+visitId+" DEBIT...");
            stmt = conn.createStatement();
            StringBuilder sqlDebit = new StringBuilder("SELECT DISTINCT visit_id, receipt_id, receive_date, receive_time, SUM(sale) sale, SUM(discount) discount, SUM(special_discount) special_discount, SUM(decimal_discount) decimal_discount, SUM(paid) paid FROM bpk_account_debit_detail WHERE visit_id='").append(visitId).append("' AND receive_date='").append(receiveDate).append("' GROUP BY visit_id, receipt_id, receive_date, receive_time ORDER BY receive_date, receive_time");

            // System.out.println(sqlDebit.toString());
            rs = stmt.executeQuery(sqlDebit.toString());
            for(;rs.next();)
            {
                DebitVO aDebitVO = new DebitVO();
                aDebitVO.setVisitId(rs.getString("visit_id"));
                aDebitVO.setReceiptId(rs.getString("receipt_id"));
                aDebitVO.setSale(rs.getFloat("sale"));
                aDebitVO.setDiscount(rs.getFloat("discount"));
                aDebitVO.setDecimalDiscount(rs.getFloat("decimal_discount"));
                aDebitVO.setSpecialDiscount(rs.getFloat("special_discount"));
                aDebitVO.setPaid(rs.getFloat("paid"));

                listDebitVO.add(aDebitVO);

                StringBuilder sqlDebitBillGroup = new StringBuilder("SELECT id, base_billing_group_id, linesale, linediscount, linepaid FROM bpk_account_debit_detail WHERE visit_id='").append(visitId).append("' AND receive_date='").append(receiveDate).append("' AND receipt_id='").append(aDebitVO.getReceiptId()).append("' ORDER BY base_billing_group_id");
                stmtBillGroup = conn.createStatement();
                rsBillGroup = stmtBillGroup.executeQuery(sqlDebitBillGroup.toString());
                for(;rsBillGroup.next();)
                {
                    DebitBillGroupVO aDebitBillGroupVO = new DebitBillGroupVO();
                    aDebitBillGroupVO.setId(rsBillGroup.getLong("id"));
                    aDebitBillGroupVO.setBaseBillingGroupId(rsBillGroup.getString("base_billing_group_id"));
                    aDebitBillGroupVO.setLineSale(rsBillGroup.getFloat("linesale"));
                    aDebitBillGroupVO.setLineDiscount(rsBillGroup.getFloat("linediscount"));
                    aDebitBillGroupVO.setLinePaid(rsBillGroup.getFloat("linepaid"));

                    aDebitVO.addBillGroup(aDebitBillGroupVO);
                }
                rsBillGroup.close();
                stmtBillGroup.close();

                // Allocate ในใบเสร็จ/ใบแจ้งหนี้ ส่วนของ Discount ให้เกลี่ยยอด Paid, Discount ของแต่ละใบก่อน
                aDebitVO.allocateForReceipt();

                stmtBillGroup = conn.createStatement();
                // Update การ Allocate อีกที ให้กับส่วนของ Debit ก่อน
                List listDebitBillGroupVO = aDebitVO.getListDebitBillGroupVO();
                for(int j=0, sizej=listDebitBillGroupVO.size(); j<sizej; j++)
                {
                    DebitBillGroupVO tmpDebitBillGroupVO = (DebitBillGroupVO)listDebitBillGroupVO.get(j);

                    sqlDebitBillGroup = new StringBuilder("UPDATE bpk_account_debit_detail SET wlinediscount='").append(tmpDebitBillGroupVO.getWlineDiscount()).append("', wlinepaid='").append(tmpDebitBillGroupVO.getWlinePaid()).append("' WHERE id='").append(tmpDebitBillGroupVO.getId()).append("'");

                    // System.out.println(sqlDebitBillGroup.toString());
                    stmtBillGroup.executeUpdate(sqlDebitBillGroup.toString());
                }
                stmtBillGroup.close();
            }
            rs.close();
            stmt.close();
            System.out.println("Allocated finish "+visitId+" DEBIT");

            // 2. ส่วนของการ Allocate ส่วน Credit จาก Billing Group ของ Debit โดยรวมทั้ง Visit
            System.out.println("Start allocate "+visitId+" CREDIT in billing group to order item...");
            stmt = conn.createStatement();
            sqlDebit = new StringBuilder("SELECT base_billing_group_id, SUM(linesale) linesale, SUM(wlinediscount) AS wlinediscount, SUM(wlinepaid) AS wlinepaid FROM bpk_account_debit_detail WHERE visit_id='").append(visitId).append("' AND receive_date='").append(receiveDate).append("' GROUP BY base_billing_group_id ORDER BY base_billing_group_id");
            List listDebitBillGroupVO = new ArrayList();
            rs = stmt.executeQuery(sqlDebit.toString());
            for(;rs.next();)
            {
                DebitBillGroupVO aDebitBillGroupVO = new DebitBillGroupVO();

                aDebitBillGroupVO.setBaseBillingGroupId(rs.getString("base_billing_group_id"));
                aDebitBillGroupVO.setLineSale(rs.getFloat("linesale"));
                aDebitBillGroupVO.setWlineDiscount(rs.getFloat("wlinediscount"));
                aDebitBillGroupVO.setWlinePaid(rs.getFloat("wlinepaid"));
                listDebitBillGroupVO.add(aDebitBillGroupVO);

                // StringBuilder sqlCredit = new StringBuilder("SELECT id, SUM(linesale) AS linesale FROM bpk_account_credit_detail WHERE visit_id='").append(visitId).append("' AND verify_date='").append(receiveDate).append("' AND base_billing_group_id='").append(aDebitBillGroupVO.getBaseBillingGroupId()).append("' GROUP BY id ORDER BY id");
                StringBuilder sqlCredit = new StringBuilder("SELECT id, SUM(linesale) AS linesale FROM bpk_account_credit_detail WHERE visit_id='").append(visitId).append("' AND base_billing_group_id='").append(aDebitBillGroupVO.getBaseBillingGroupId()).append("' GROUP BY id ORDER BY id");
                stmtItem = conn.createStatement();
                rsItem = stmtItem.executeQuery(sqlCredit.toString());
                for(;rsItem.next();)
                {
                    CreditOrderItemVO aCreditOrderItemVO = new CreditOrderItemVO();

                    aCreditOrderItemVO.setId(rsItem.getLong("id"));
                    aCreditOrderItemVO.setBaseBillingGroupId(aDebitBillGroupVO.getBaseBillingGroupId());
                    aCreditOrderItemVO.setLineSale(rsItem.getFloat("linesale"));

                    aDebitBillGroupVO.addOrderItem(aCreditOrderItemVO);
                }
                rsItem.close();
                stmtItem.close();

                // Allocate ส่วนของแต่ละ BillGroup กระจายไปให้แต่ละ Order Item
                aDebitBillGroupVO.allocateForBillGroup();

                stmtItem = conn.createStatement();
                // Update ให้กับส่วนของ Credit หลัง Allocate แล้ว
                List listCreditOrderItemVO = aDebitBillGroupVO.getListCreditOrderItemVO();
                for(int j=0, sizej=listCreditOrderItemVO.size(); j<sizej; j++)
                {
                    CreditOrderItemVO tmpCreditOrderItemVO = (CreditOrderItemVO)listCreditOrderItemVO.get(j);

                    StringBuilder sqlCreditOrderItem = new StringBuilder("UPDATE bpk_account_credit_detail SET wlinediscount='").append(tmpCreditOrderItemVO.getWlineDisCount()).append("', wlinepaid='").append(tmpCreditOrderItemVO.getWlinePaid()).append("' WHERE id='").append(tmpCreditOrderItemVO.getId()).append("'");

                    // System.out.println(sqlDebitBillGroup.toString());
                    stmtItem.executeUpdate(sqlCreditOrderItem.toString());
                }
                stmtItem.close();
            }
            rs.close();
            stmt.close();
            System.out.println("Allocated "+visitId+" CREDIT in billing group to order item finished");

            conn.close();

            return true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }
        return false;
    }

    /**
     * 1. ส่วนของการ Allocate ส่วน Debit กระจาย Discount ทั้งหมด ให้ระดับ Billing Group ทีละใบเสร็จ
     * 2. ส่วนของการ Allocate ส่วน Credit จาก Billing Group ของ Debit โดยรวมทั้ง Visit
     * @param visitId
     * @param dischargeDate
     * @return
     */
    public boolean makeAlloacteIpd(String visitId, String dischargeDate)
    {
        List<DebitVO> listDebitVO = new ArrayList<DebitVO>();
        Connection conn = null;
        Statement stmt = null, stmtBillGroup = null, stmtItem = null;
        ResultSet rs = null, rsBillGroup = null, rsItem = null;
        try
        {
            conn = DAOFactory.getConnection();

            // 1. ส่วนของการ Allocate ส่วน Debit กระจาย Discount ทั้งหมด ให้ระดับ Billing Group ทีละใบเสร็จ
            System.out.println();
            System.out.println("Start allocate "+visitId+" DEBIT...");
            stmt = conn.createStatement();
            StringBuilder sqlDebit = new StringBuilder("SELECT DISTINCT visit_id, receipt_id, receive_date, receive_time, SUM(sale) sale, SUM(discount) discount, SUM(special_discount) special_discount, SUM(decimal_discount) decimal_discount, SUM(paid) paid FROM bpk_account_debit_detail WHERE visit_id='").append(visitId).append("' AND financial_discharge_date='").append(dischargeDate).append("' GROUP BY visit_id, receipt_id, receive_date, receive_time ORDER BY receive_date, receive_time");

            // System.out.println(sqlDebit.toString());
            rs = stmt.executeQuery(sqlDebit.toString());
            for(;rs.next();)
            {
                DebitVO aDebitVO = new DebitVO();
                aDebitVO.setVisitId(rs.getString("visit_id"));
                aDebitVO.setReceiptId(rs.getString("receipt_id"));
                aDebitVO.setSale(rs.getFloat("sale"));
                aDebitVO.setDiscount(rs.getFloat("discount"));
                aDebitVO.setDecimalDiscount(rs.getFloat("decimal_discount"));
                aDebitVO.setSpecialDiscount(rs.getFloat("special_discount"));
                aDebitVO.setPaid(rs.getFloat("paid"));

                listDebitVO.add(aDebitVO);

                StringBuilder sqlDebitBillGroup = new StringBuilder("SELECT id, base_billing_group_id, linesale, linediscount, linepaid FROM bpk_account_debit_detail WHERE visit_id='").append(visitId).append("' AND financial_discharge_date='").append(dischargeDate).append("' AND receipt_id='").append(aDebitVO.getReceiptId()).append("' ORDER BY base_billing_group_id");
                stmtBillGroup = conn.createStatement();
                // System.out.println(sqlDebitBillGroup.toString());
                rsBillGroup = stmtBillGroup.executeQuery(sqlDebitBillGroup.toString());
                for(;rsBillGroup.next();)
                {
                    DebitBillGroupVO aDebitBillGroupVO = new DebitBillGroupVO();
                    aDebitBillGroupVO.setId(rsBillGroup.getLong("id"));
                    aDebitBillGroupVO.setBaseBillingGroupId(rsBillGroup.getString("base_billing_group_id"));
                    aDebitBillGroupVO.setLineSale(rsBillGroup.getFloat("linesale"));
                    aDebitBillGroupVO.setLineDiscount(rsBillGroup.getFloat("linediscount"));
                    aDebitBillGroupVO.setLinePaid(rsBillGroup.getFloat("linepaid"));

                    aDebitVO.addBillGroup(aDebitBillGroupVO);
                }
                rsBillGroup.close();
                stmtBillGroup.close();

                // Allocate ในใบเสร็จ/ใบแจ้งหนี้ ส่วนของ Discount ให้เกลี่ยยอด Paid, Discount ของแต่ละใบก่อน
                aDebitVO.allocateForReceipt();

                stmtBillGroup = conn.createStatement();
                // Update การ Allocate อีกที ให้กับส่วนของ Debit ก่อน
                List listDebitBillGroupVO = aDebitVO.getListDebitBillGroupVO();
                for(int j=0, sizej=listDebitBillGroupVO.size(); j<sizej; j++)
                {
                    DebitBillGroupVO tmpDebitBillGroupVO = (DebitBillGroupVO)listDebitBillGroupVO.get(j);

                    sqlDebitBillGroup = new StringBuilder("UPDATE bpk_account_debit_detail SET wlinediscount='").append(tmpDebitBillGroupVO.getWlineDiscount()).append("', wlinepaid='").append(tmpDebitBillGroupVO.getWlinePaid()).append("' WHERE id='").append(tmpDebitBillGroupVO.getId()).append("'");

                    // System.out.println(sqlDebitBillGroup.toString());
                    stmtBillGroup.executeUpdate(sqlDebitBillGroup.toString());
                }
                stmtBillGroup.close();
            }
            rs.close();
            stmt.close();
            System.out.println("Allocated finish "+visitId+" DEBIT");

            // 2. ส่วนของการ Allocate ส่วน Credit จาก Billing Group ของ Debit โดยรวมทั้ง Visit
            System.out.println("Start allocate "+visitId+" CREDIT in billing group to order item...");
            stmt = conn.createStatement();
            sqlDebit = new StringBuilder("SELECT base_billing_group_id, SUM(linesale) linesale, SUM(wlinediscount) AS wlinediscount, SUM(wlinepaid) AS wlinepaid FROM bpk_account_debit_detail WHERE visit_id='").append(visitId).append("' AND financial_discharge_date='").append(dischargeDate).append("' GROUP BY base_billing_group_id ORDER BY base_billing_group_id");
            List listDebitBillGroupVO = new ArrayList();
            // System.out.println(sqlDebit.toString());
            rs = stmt.executeQuery(sqlDebit.toString());
            for(;rs.next();)
            {
                DebitBillGroupVO aDebitBillGroupVO = new DebitBillGroupVO();

                aDebitBillGroupVO.setBaseBillingGroupId(rs.getString("base_billing_group_id"));
                aDebitBillGroupVO.setLineSale(rs.getFloat("linesale"));
                aDebitBillGroupVO.setWlineDiscount(rs.getFloat("wlinediscount"));
                aDebitBillGroupVO.setWlinePaid(rs.getFloat("wlinepaid"));
                listDebitBillGroupVO.add(aDebitBillGroupVO);

                StringBuilder sqlCredit = new StringBuilder("SELECT id, SUM(linesale) AS linesale FROM bpk_account_credit_detail WHERE visit_id='").append(visitId).append("' AND financial_discharge_date='").append(dischargeDate).append("' AND base_billing_group_id='").append(aDebitBillGroupVO.getBaseBillingGroupId()).append("' GROUP BY id ORDER BY id");
                stmtItem = conn.createStatement();
                // System.out.println(sqlCredit.toString());
                rsItem = stmtItem.executeQuery(sqlCredit.toString());
                for(;rsItem.next();)
                {
                    CreditOrderItemVO aCreditOrderItemVO = new CreditOrderItemVO();

                    aCreditOrderItemVO.setId(rsItem.getLong("id"));
                    aCreditOrderItemVO.setBaseBillingGroupId(aDebitBillGroupVO.getBaseBillingGroupId());
                    aCreditOrderItemVO.setLineSale(rsItem.getFloat("linesale"));

                    aDebitBillGroupVO.addOrderItem(aCreditOrderItemVO);
                }
                rsItem.close();
                stmtItem.close();

                // Allocate ส่วนของแต่ละ BillGroup กระจายไปให้แต่ละ Order Item
                aDebitBillGroupVO.allocateForBillGroup();

                stmtItem = conn.createStatement();
                // Update ให้กับส่วนของ Credit หลัง Allocate แล้ว
                List listCreditOrderItemVO = aDebitBillGroupVO.getListCreditOrderItemVO();
                for(int j=0, sizej=listCreditOrderItemVO.size(); j<sizej; j++)
                {
                    CreditOrderItemVO tmpCreditOrderItemVO = (CreditOrderItemVO)listCreditOrderItemVO.get(j);

                    StringBuilder sqlCreditOrderItem = new StringBuilder("UPDATE bpk_account_credit_detail SET wlinediscount='").append(tmpCreditOrderItemVO.getWlineDisCount()).append("', wlinepaid='").append(tmpCreditOrderItemVO.getWlinePaid()).append("' WHERE id='").append(tmpCreditOrderItemVO.getId()).append("'");

                    // System.out.println(sqlCreditOrderItem.toString());
                    stmtItem.executeUpdate(sqlCreditOrderItem.toString());
                }
                stmtItem.close();
            }
            rs.close();
            stmt.close();
            System.out.println("Allocated "+visitId+" CREDIT in billing group to order item finished");

            conn.close();

            return true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }
        return false;
    }
}
