/**   
 * @author wangning
 * @date 2015年3月4日 下午2:23:17 
 * @version V1.0   
 * @Description: 
 */
package com.citic.risk.entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import javax.activation.FileDataSource;

import org.apache.log4j.Logger;

import com.jujin.biz.AccountBiz;
import com.jujin.biz.AttestBiz;
import com.jujin.biz.HomeBiz;
import com.jujin.biz.UserBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.SystemConfig;
import com.jujin.controller.BaseController;
import com.jujin.controller.account.AccountControler;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.InviteReward;
import com.jujin.entity.borrow.*;
import com.jujin.utils.ExceptionHelper;
import com.wicket.loan.common.utils.UserUtils;

/**
 * 
 */
public class ConfigHelper {

	static String[] NUMBERS = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9" };

	static String[] CHARS = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "E", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	protected static final Logger logger = Logger
			.getLogger(BaseController.class);

	public static void main(String[] args) {

		String waitVerifyCode = "13683815260|094738";
		String[] tmpArrays = waitVerifyCode.split("\\|");
		if (tmpArrays.length < 2) {

		}
		System.out.println("result:" + tmpArrays.length);
		String tmpTel = tmpArrays[0];
		waitVerifyCode = tmpArrays[1];
		System.out.println(tmpTel);
		System.out.println(waitVerifyCode);

		// String userId=biz.GetSingleUserId("136775125");

		//String userId = biz.getUserIdFromInvite("徐柯");

		String tel = SystemConfig.getPhoneToConceal("13683815260");

		System.out.println(tel);

		//System.out.println(userId);

		//System.out.println(UserUtils.realNameToConceal(null));
		
		//System.out.println(biz.);

		AttestBiz aBiz=new AttestBiz();
		System.out.println(aBiz.getGenderByIdCard("370727791118517"));
		System.out.println(aBiz.getGenderByIdCard("411426198902264523"));
		System.out.println(aBiz.getGenderByIdCard("13068219850823782X"));
		System.out.println(aBiz.getGenderByIdCard("41061119791229302X"));
		System.out.println("Completed");

		// System.out.println(userId);

		/*
		 * JsonList<Borrow> borrows= biz.getBorrowDirect();
		 * 
		 * for(Borrow borrow :borrows.getList()) { System.out.println(borrow); }
		 * 
		 * BorrowInfo info= biz.getBorrowInfo("2015012300000000000000001283");
		 * 
		 * System.out.println(info);
		 */

		// BorrowInfo info = biz.getBorrowInfo("2014102400000000000000001177");
		// System.out.println(info);

		/*
		 * System.out.println(biz.getUserInfo("2015012300000000000000001283"));
		 * MapChoicesData mc = new MapChoicesData("MARRY_LIST");
		 * List<ChoiceListValues> collections = mc.getChoicesList();
		 * 
		 * System.out.println("hello "); System.out.println(collections.size());
		 * 
		 * for (ChoiceListValues o : collections) { System.out.println(o); }
		 * 
		 * // Borrow borrow= Class<?> type = BorrowInfoItem.class;
		 * 
		 * Field[] fields = type.getDeclaredFields();
		 * 
		 * StringBuilder builder = new StringBuilder();
		 * 
		 * builder.append(String.format("<resultMap type=\"%s\" id=\"%s\">\n",
		 * type.getSimpleName(), type.getSimpleName()));
		 * 
		 * // builder.append("");
		 * 
		 * for (Field field : fields) { // System.out.println(field.getName());
		 * builder.append(String.format(
		 * "		<result column=\"%s\" property=\"%s\" />\n", field
		 * .getName().toUpperCase(), field.getName())); }
		 * builder.append("</resultMap>\n");
		 * System.out.println(builder.toString());
		 */

		/*
		 * <resultMap type="UserNickName" id="UserNickName"> <result
		 * column="USER_ID" property="userId" /> <result column="NICK_NAME"
		 * property="nickName" /> <result column="FILE_URL"
		 * property="userHeadImage" /> <result column="TYPE_ID"
		 * property="typeId" /> <result column="VIP_FLG" property="vipFlg" />
		 * <result column="VIP_NAME" property="vipName" /> <result
		 * column="TYPE_IMG_FILE_ID" property="typeImageFileId" /> </resultMap>
		 */

	}

	public static OpEntityResult<InviteReward> SayInfo() {
		String userId = "lvlf";
		int pi = 0;
		int ps = 10;

		AccountBiz accountBiz = new AccountBiz();
		logger.info(String.format("获取用户[%s]的邀请人统计信息奖励列表", userId));

		OpEntityResult<InviteReward> entity = null;
		InviteReward ireward = null;
		try {
			ireward = accountBiz.getStatInvite(userId, pi, ps);
			entity = new OpEntityResult<InviteReward>(ireward);
			entity.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			if (entity == null)
				entity = new OpEntityResult<InviteReward>(null);
			entity.setMsg(String.format("获取用户[%s]的邀请人统计信息奖励列表失败,请和管理员联系",
					userId));
			entity.setStatus(false);
		}
		return entity;
	}

}
