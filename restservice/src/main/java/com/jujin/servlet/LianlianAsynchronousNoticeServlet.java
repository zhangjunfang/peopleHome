package com.jujin.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jujin.biz.BaseBiz;
import com.jujin.biz.RechargeBiz;
import com.jujin.common.SystemConfig;
import com.jujin.entity.UserIdentityCard;
import com.jujin.entity.pay.lianlian.PartnerConfig;
import com.jujin.entity.pay.lianlian.PayResult;
import com.jujin.entity.pay.lianlian.RetBean;
import com.jujin.lianlian.util.YinTongUtil;
import com.jujin.thread.SmsRunnable;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.model.Model;
import com.pro.common.util.StringUtils;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.web.person.recharge.bean.RechargeBean;
import com.wicket.loan.web.person.recharge.mediator.RechargeMediator;

import cryptix.jce.provider.MD5;

@SuppressWarnings("serial")
public class LianlianAsynchronousNoticeServlet extends HttpServlet {

	// https://yintong.com.cn/llpayh5/authpay.htm
	@Inject
	protected RechargeMediator mediator;

	private static final RechargeBiz biz = new RechargeBiz();
	protected static final RetBean FAIL_BEAN = new RetBean("9999", "交易失败");
	protected static final RetBean SUCCESSBEAN_BEAN = new RetBean("0000",
			"交易成功");

	protected static final Logger logger = LoggerFactory
			.getLogger(LianlianAsynchronousNoticeServlet.class);

	private boolean VerifySign(String reqStr, HttpServletResponse resp)
			throws IOException {
		try {
			logger.info(String.format("连连支付回调原始数据为:[%s]",
					StringUtils.isEmpty(reqStr) ? "" : reqStr));
			if (YinTongUtil.isnull(reqStr)) {

				resp.getWriter().write(JSON.toJSONString(FAIL_BEAN));
				resp.getWriter().flush();
				return false;
			}

			if (!YinTongUtil.checkSign(reqStr, PartnerConfig.YT_PUB_KEY,
					PartnerConfig.MD5_KEY)) {
				resp.getWriter().write(JSON.toJSONString(FAIL_BEAN));
				resp.getWriter().flush();
				logger.error("支付异步通知验签失败");
				return false;
			}
		} catch (Exception e) {
			logger.error("异步通知报文解析异常：" + e);
			resp.getWriter().write(JSON.toJSONString(FAIL_BEAN));
			resp.getWriter().flush();
			return false;
		}
		return true;
	}

	private void updateRechargeError(RechargeBean saveBean, String memo,
			String reason) {
		mediator.editOnlineRechargeNo(saveBean);
		logger.error("连连支付异步处理失败订单号：" + memo);
		logger.error("连连支付异步处理失败原因：" + reason);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RechargeBean saveBean = new RechargeBean();

		try {
			String reqStr = YinTongUtil.readReqStr(req);

			PayResult entity = null;
			String memo = "";
			if (!VerifySign(reqStr, resp)) {
				if (!StringUtils.isEmpty(reqStr)) {
					entity = JSON.parseObject(reqStr, PayResult.class);
					memo = String
							.format("用户：[%s]	聚金订单号：[%s]	连连订单号:[%s] 订单时间:[%s]	支付结果:[%s]",
									entity.getInfo_order(),
									entity.getNo_order(),
									entity.getOid_paybill(),
									entity.getDt_order(),
									entity.getResult_pay());
					saveBean.setOrderId(entity.getNo_order());
					saveBean.setUserId(entity.getInfo_order());// (mediator.getOrderUserId(saveBean));//TODO:姓名标识
					updateRechargeError(saveBean, memo, "验签出错");
					resp.getWriter().write(JSON.toJSONString(FAIL_BEAN));
					return;
				}
				return;
			} else {
				entity = JSON.parseObject(reqStr, PayResult.class);
				saveBean.setOrderId(entity.getNo_order());

				String content = entity.getInfo_order();

				String userId = "";
				String cardId = "";
				if (!StringUtils.isEmpty(content) && content.indexOf(",") != -1) {
					String[] tmpArray = content.split(",");

					userId = tmpArray[0];
					cardId = tmpArray[1];
				} else {
					userId = mediator.getOrderUserId(saveBean);
				}
				saveBean.setUserId(userId);// (mediator.getOrderUserId(saveBean));//TODO:姓名标识

				memo = String.format(
						"用户：[%s]	聚金订单号：[%s]	连连订单号:[%s] 订单时间:[%s]	支付结果:[%s]",
						entity.getInfo_order(), entity.getNo_order(),
						entity.getOid_paybill(), entity.getDt_order(),
						entity.getResult_pay());
				logger.info(memo);

				if ("SUCCESS".equals(entity.getResult_pay())) {
					int succCnt = mediator
							.selectOnlineRechargeYesInfo(saveBean);
					String billNo = saveBean.getOrderId();

					if (succCnt < 1) {

						String rtnFlg = mediator
								.editOnlineRechargeYes(saveBean);
						logger.info(saveBean.getUserId()
								+ "：在线支付充值处理结果 orderId == " + billNo
								+ " rtnFlg == " + rtnFlg
								+ ":(0:成功 1：已经充值 2：锁等待失败 9：其他异常)");
						if ("0".equals(rtnFlg)) {
							mediator.sendMobileMessage(saveBean);
							logger.info("连连支付异步处理成功订单号（本次处理）： billNo == "
									+ billNo);
							resp.getWriter().write(
									JSON.toJSONString(SUCCESSBEAN_BEAN));

						} else if ("9".equals(rtnFlg)) {
							updateRechargeError(saveBean, memo,
									"网银支付异步处理失败订单号(调单处理)");
							resp.getWriter()
									.write(JSON.toJSONString(FAIL_BEAN));
						}
					} else {
						logger.info(String.format("连连支付异步处理成功订单号(已处理)：  %s",
								memo));
						resp.getWriter().write(
								JSON.toJSONString(SUCCESSBEAN_BEAN));
					}

					logger.info("cardId:" + cardId);
					if (!StringUtils.isEmpty(cardId)) {
						UserIdentityCard card = biz.getUserIdentityCard(userId,
								0, cardId);
						if (card != null
								&& StringUtils.isEmpty(card.getNoAgree())) {

							String noAgree = entity.getNo_agree();
							logger.info("noAgree:[" + card.getNoAgree() + "]"
									+ "  noAgree " + noAgree);
							if (!StringUtils.isEmpty(noAgree))
							{
								logger.info("更新 noAgree：");
								biz.UpdateBankNoAgree(userId, cardId, noAgree);
							}
						}
					}
				} else {
					updateRechargeError(saveBean, memo, "交易状态是不成功");
					resp.getWriter().write(JSON.toJSONString(FAIL_BEAN));
				}
			}

		} catch (IOException e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			return;
		} finally {
			resp.getWriter().close();
		}
	}

	protected String getSignMD5Key(String billNo) {
		String returnStr = "";
		RechargeBean saveBean = new RechargeBean();
		saveBean.setOrderId(billNo);
		String userId = mediator.getOrderUserId(saveBean);
		Model<String, Object> model = mediator.getOnlineBankConfig(userId,
				billNo);
		if (!model.isEmpty()) {
			String config = model.getStringValue("CONFIG");
			if (!StringUtils.isEmpty(config)) {
				String[] paramMer = config.split("&");
				for (String keyValue : paramMer) {
					if (!StringUtils.isEmpty(keyValue)) {
						String[] conf = keyValue.split("=");
						if ("Mer_key".equals(conf[0])) {
							returnStr = conf[1];
						}
					}
				}
			}
		}
		return returnStr;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
