package com.jujin.thread;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

import com.jujin.biz.BaseBiz;
import com.jujin.biz.UserAwardMediator;
import com.jujin.biz.VerifyCodeBiz;
import com.jujin.utils.ExceptionHelper;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;

public class SmsRunnable implements Runnable {

	BaseBiz biz = null;
	String userId = "";
	String money = "";
	VerifyCodeBiz codeBiz = new VerifyCodeBiz();

	protected static final Logger logger = Logger.getLogger(SmsRunnable.class);

	public SmsRunnable(BaseBiz biz, String userId, String money) {
		this.biz = biz;
		this.userId = userId;
		this.money=money;
	}

	@Override
	public void run() {

		if (StringUtils.isEmpty(userId) || null == biz
				|| StringUtils.isEmpty(money)) {
			logger.error(String.format(
					"参数值异常，请检查 userId:[%s] biz：[%s] money [%s]", StringUtils
							.isEmpty(userId) ? "" : userId,
					null == biz ? "null" : biz.toString(), StringUtils
							.isEmpty(money) ? "" : money));
			return;
		}
		try {
			String phoneNumber = codeBiz.getUserMobile(userId);
			if (UserUtils.isMobileNumber(phoneNumber)) {
				SendSmsBean bean = new SendSmsBean();
				bean.setUserId(userId);
				bean.setPhoneNumber(phoneNumber);
				bean.setSendType(SendTypeEnum.MSG);
				bean.setSmsType(SmsTypeEnum.NOTIFY_REWARD);
				bean.setContents("");
				bean.setMoney(money);
				if(biz.sendMobileMessage(bean))
				{
					logger.info("用户[%s]中奖短信通知发送成功");
				}
				else
				{
					logger.info("用户[%s]中奖短信通知发送失败");
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
	}

}
