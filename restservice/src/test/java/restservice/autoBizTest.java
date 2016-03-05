package restservice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.HomeBiz;
import com.jujin.biz.auto.AutoInvestBiz;
import com.jujin.common.JsonList;
import com.jujin.entity.auto.AutoInvestBean;
import com.jujin.entity.auto.AutoInvestRecord;
import com.jujin.entity.auto.AutoInvestSetting;
import com.jujin.entity.borrow.BorrowInvestIndicate;
import com.jujin.entity.ticket.Ticket;

import net.sms.main.SendSmsMessage;
import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

public class autoBizTest {

	static AutoInvestBiz biz = new AutoInvestBiz();

	public static void main(String[] args) {

		//String userId = "firetw";
		// AutoInvestBean(userId);
		// getAutoSetting(userId);
		// getAutoRecord(userId);

		//addOrModifyAutoSetting();

		getBorrowStatus();

	}

	public static void AutoInvestBean(String userId) {
		AutoInvestBean bean = biz.getAutoInvestBean(userId);

		System.out.println(bean.getDefaultSetting());

		System.out.println();
		System.out.println();

		System.out.println(bean.getRecords());

		System.out.println();
		System.out.println();

		System.out.println(bean.getSettings());
	}

	public static void getBorrowStatus() {
		HomeBiz biz = new HomeBiz();
		List<BorrowInvestIndicate> list = biz.getBorrowStatus();
		
		for (int i = 0; i < list.size(); i++) {
			BorrowInvestIndicate str=list.get(i);
			
			System.out.println(str);
		}

	}

	public static void getAutoSetting(String userId) {
		int pi = 0;
		int ps = 20;

		JsonList<AutoInvestSetting> settings = biz.getAutoSetting(userId, pi,
				ps);
		System.out.println(settings);

	}

	public static void getAutoRecord(String userId) {
		int pi = 0;
		int ps = 20;

		JsonList<AutoInvestRecord> records = biz.getAutoRecord(userId, pi, ps);
		System.out.println(records);
	}

	public static void addOrModifyAutoSetting() {
		AutoInvestSetting setting = new AutoInvestSetting();
		setting.setAmount(50);
		setting.setAward(true);
		setting.setBorrowType("2,8");
		setting.setCapital(50);
		setting.setEnable(true);
		setting.setPeriodBegin(1);
		setting.setPeriodEnd(12);
		setting.setRate(12);
		setting.setUserId("firetw");

		Boolean result = biz.addOrModifyAutoSetting(setting);

		System.out.println(result);
	}

	public static void Send() {
		SendSmsBean smsBean = new SendSmsBean();

		smsBean.setPhoneNumber("18538180904");// 13683815260,

		smsBean.setContents("聚金资本短信测试");
		smsBean.setUserId("firetw");

		smsBean.setSendType(SendTypeEnum.MSG);
		smsBean.setSmsType(SmsTypeEnum.CUSTOMER_MESSAGE);

		SendSmsMessage.makeSmsMessage(smsBean);
	}

}
