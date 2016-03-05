package com.jujin.biz.luckDraw;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sms.main.enums.SmsTypeEnum;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.jujin.biz.JujinBaseBiz;
import com.jujin.common.OpEntityResult;
import com.jujin.entity.coin.TpaUserCoinBean;
import com.jujin.entity.coin.TpaUserCoinDetailBean;
import com.jujin.entity.luckDraw.AwardRecord;
import com.jujin.entity.luckDraw.AwardSet;
import com.jujin.redis.CacheConstants;
import com.jujin.redis.RedisUtil;
import com.jujin.utils.BugUtil;
import com.pro.common.util.StringUtils;

/**
 * Title: PersistenceBiz
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月13日
 */
public class LuckDrawPersistenceBiz extends JujinBaseBiz{
    /** serialVersionUID*/
    private static final long serialVersionUID = 1L;

    /**
     * Title: sendSms Description: 发送短信通知
     * 
     * @param userId
     * @param phoneNumber
     * @param coin
     */
    private void sendSms(String userId, String phoneNumber, String coin,SmsTypeEnum smsType) {
        try {
            /*SendSmsBean msgBean = new SendSmsBean();
            msgBean.setPhoneNumber(phoneNumber);
            msgBean.setSendType(SendTypeEnum.MSG);
            msgBean.setBorrowTitle("");
            msgBean.setMoney("");
            msgBean.setUserId(userId);
            msgBean.setSmsType(smsType);
            msgBean.setParam1(coin);
            logger.info(String.format("Send Msg For [%s]", phoneNumber));
            sendMobileMessage(msgBean);*/
        } catch (Exception ex) {
            logger.error("发送短信通知失败",ex);
            RedisUtil.setString(CacheConstants.CACHE_GLOBAL_PERSISTENCE, "fail");
        }
    }

    /**
     * 发放聚金币
    * Title: sendLuckyAward
    * Description: 
    * @param ar
     * @throws SQLException 
     */
    public synchronized void sendCoin(List<AwardRecord> list){
        SqlSession session = null;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Transaction newTransaction = null;
        try {
            session = getSession(false);
            newTransaction = transactionFactory.newTransaction(session.getConnection());
            logger.info("发放抽奖聚金币-start");
            for(AwardRecord ar :list){
                String reward = ar.getQuantity();
                String userId = ar.getUserId();
                String phoneNumber = ar.getPhoneNumber();
                logger.info("开始发放抽奖聚金币[userId:"+userId+",reward:"+reward+"]");
                // 发放聚金币
                TpaUserCoinBean coinBean = session.selectOne("com.jujin.sign.mapper.getTpaUserCoinByUserId", userId);
                int coinrs = 0;
                if(coinBean != null){
                    double amount = coinBean.getAmount();
                    coinBean.setAmount(amount + Double.valueOf(ar.getQuantity()));
                    coinrs = session.update("com.jujin.sign.mapper.updateTpaUserCoin", coinBean);
                }else{
                    coinBean = new TpaUserCoinBean();
                    coinBean.setAmount(Double.valueOf(reward));
                    coinBean.setFrost(0);
                    coinBean.setUserId(userId);
                    coinBean.setPhoneNumber(phoneNumber);
                    coinrs = session.insert("com.jujin.sign.mapper.insertTpaUserCoin", coinBean);
                }
                logger.info("发放聚金币-"+(coinrs==1?"成功":"失败"));
                // 4.保存聚金币发放流水
                TpaUserCoinDetailBean coinDetailBean = new TpaUserCoinDetailBean();
                coinDetailBean.setPhoneNumber(phoneNumber);
                coinDetailBean.setUserId(userId);
                coinDetailBean.setAmount(Double.valueOf(reward));

                coinDetailBean.setMemo("2016跨年暖冬抽奖获取聚金币"+reward+"元");
                int coindetailrs = session.insert("com.jujin.luckDraw.mapper.insertTpaUserCoinDetail", coinDetailBean);
                logger.info("保存聚金币流水-"+(coindetailrs==1?"成功":"失败"));
                // 5.短信通知用户
                if(!StringUtils.isEmpty(phoneNumber)){
                    sendSms(userId, phoneNumber, reward,SmsTypeEnum.SMS_JUJIN_DRAW_COIN_NOTIFY);
                }
            }
            newTransaction.commit();
            logger.info("发送所有抽奖聚金币-success");
        } catch (Exception e) {
            logger.error("发放聚金币失败",e);
            BugUtil.sendBugEmail("[2016跨年抽奖]发放聚金币失败", e);
            try {
                newTransaction.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (session != null){
                session.close();
            }
            if(newTransaction != null){
                try {
                    newTransaction.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发放聚金券
    * Title: sendLuckyAward
    * Description: 
    * @param ticketList
     * @throws SQLException 
     */
    public synchronized void sendTicket(List<AwardRecord> ticketList) {
        SqlSession session = null;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Transaction newTransaction = null;
        try {
            session = getSession(false);
            newTransaction = transactionFactory.newTransaction(session.getConnection());
            logger.info("发放抽奖获得聚金券-start");
            for(AwardRecord ar :ticketList){
                String reward = ar.getQuantity();
                String userId = ar.getUserId();
                String phoneNumber = ar.getPhoneNumber();

                logger.info("开始发放抽奖聚金券[userId:"+userId+",reward:"+reward+"]");
                String ticketId = AwardSet.getTicketIdByAwardCode(ar.getAwardCode());
                Map<String,Object> paramMap = new HashMap<String,Object>();
                paramMap.put("userId", userId);
                paramMap.put("ticketId", ticketId);
                String ticketCount = session.selectOne("com.jujin.luckDraw.mapper.queryTicketByUserId", paramMap);
                int ticketrs = 0;
                if(ticketCount != null){
                    paramMap.put("ticketCount", Integer.parseInt(ticketCount)+1);
                    ticketrs = session.update("com.jujin.luckDraw.mapper.updateTicket", paramMap);
                }else{
                    ticketrs = session.insert("com.jujin.luckDraw.mapper.insertTicket", paramMap);
                }

                // 2.短信通知用户
                if(!StringUtils.isEmpty(phoneNumber)){
                    sendSms(userId, phoneNumber, reward,SmsTypeEnum.SMS_JUJIN_DRAW_TICKET_NOTIFY);
                }
                logger.info("发放抽奖获得聚金券-"+(ticketrs==1?"成功":"失败"));
            }
            newTransaction.commit();
            logger.info("所有抽奖获得聚金券发放-success");
        } catch (Exception e) {
            logger.error("发放聚金券失败",e);
            BugUtil.sendBugEmail("[2016跨年抽奖]发放聚金券失败", e);
            try {
                newTransaction.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (session != null){
                session.close();
            }
            if(newTransaction != null){
                try {
                    newTransaction.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * 保存中奖纪录
    * Title: saveUserAwardRecord
    * Description: 
    * @param list
    * @return
    * @throws SQLException
     */
    public synchronized OpEntityResult<String> saveUserAwardRecord(List<AwardRecord> list){
        OpEntityResult<String> result = new OpEntityResult<String>(null);
        SqlSession session = null;
        int rs = 0;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Transaction newTransaction = null;
        try {
            session = getSession(false);
            newTransaction = transactionFactory.newTransaction(session.getConnection());
            logger.info("保存中奖记录-start");
            if(list.size()>0) {
                rs = session.insert("com.jujin.luckDraw.mapper.saveUserAwardRecord",list);
            }
            newTransaction.commit();
            logger.info("保存中奖记录-success");
            result.setEntity(String.valueOf(rs));
            result.setStatus(true);
        } catch (Exception e) {
            logger.error("保存中奖纪录失败",e);
            BugUtil.sendBugEmail("[2016跨年抽奖]保存中奖纪录失败", e);
            try {
                newTransaction.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            result.setStatus(false);
        } finally {
            if (session != null){
                session.close();
            }
            if(newTransaction!= null){
                try {
                    newTransaction.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
