package com.jujin.controller.account;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.InterestBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.controller.BaseController;
import com.jujin.entity.interest.InterestBean;
import com.jujin.entity.interest.InterestDetail;
import com.jujin.utils.ExceptionHelper;

/**
 * Title: InterestController
 *  Description: 余额生息controller 
 *  Company: jujinziben
 * @author gaojunfeng
 * @date 2015年7月8日
 */
@Controller
public class InterestController extends BaseController {

	InterestBiz interestBiz = new InterestBiz();

	
	/**
	 * Title: getGraphCoinList Description: 获取指定电话的用户的收益（总收益和收益列表）
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getinterest", method = RequestMethod.GET)
	public @ResponseBody
	Object getInterestData(HttpServletRequest request) {
		
		OpResult validateResult = validateOpResult(1, 10);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		String userId=this.getLoginedUserId(request);
		if (StringUtils.isEmpty(userId)) {
			if (!this.onUserIdIsNull(validateResult, request).isStatus()) {
				return validateResult;
			}
		}
		JsonList<InterestDetail> detailList = null;
		InterestBean bean =null;
		 
		OpEntityResult<InterestBean> result = new OpEntityResult<InterestBean>(null);
		try {
			
			bean = interestBiz.getInterestAmount(userId);
			if(null!=bean)
			{
				result.setEntity(bean);
				detailList = interestBiz.getInterestList(userId, 1, 10);
				bean.setDetail(detailList);
			}
			result.setEntity(bean);
			result.setStatus(true);
			result.setMsg(String.format("[%s]收益列表查询正常", userId));
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("[%s]收益列表查询异常", userId));
		}
		return result;
	}
	
	@RequestMapping(value = "/getinterestlist", method = RequestMethod.GET)
	public @ResponseBody
	Object getInterestList(int pi,int ps,HttpServletRequest request) {
		
		JsonList<InterestDetail> result = null;
		
		OpResult validateResult = validateOpResult(pi,ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		String userId=this.getLoginedUserId(request);
		if (StringUtils.isEmpty(userId)) {
			if (!this.onUserIdIsNull(validateResult, request).isStatus()) {
				return validateResult;
			}
		}
		try {
			result = interestBiz.getInterestList(userId, pi, ps);
			if(result!=null)
			{
				result.setStatus(true);
			}
			else
			{
				result=new  JsonList<InterestDetail>();
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if(result==null)
			{
				result=new JsonList<InterestDetail>();
			}
			result.setStatus(false);
			result.setMsg(String.format("[%s]收益列表查询异常", userId));
		}
		return result;
	}

}
