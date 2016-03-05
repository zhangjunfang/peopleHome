package com.jujin.controller.account;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.account.JuJinBaoBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.controller.BaseController;
import com.jujin.entity.borrow.ZhaiQuan;
import com.jujin.entity.interest.InterestBean;
import com.jujin.entity.interest.InterestDetail;
import com.jujin.utils.ExceptionHelper;

@Controller
public class JuJinBaoController extends BaseController {

	
	JuJinBaoBiz interestBiz = new JuJinBaoBiz();
	
	
	
	
	/**
	 * Title: getGraphCoinList Description: 获取指定电话的用户的收益（总收益和收益列表）
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getjujinbao", method = RequestMethod.GET)
	public @ResponseBody
	Object getJuJinBaoData(HttpServletRequest request) {
		
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
		JsonList<ZhaiQuan> zhaiQuanList=null;
		InterestBean bean =null;
		 
		OpEntityResult<InterestBean> result = new OpEntityResult<InterestBean>(null);
		try {
			
			bean = interestBiz.getInterestAmount(userId);
			if(null!=bean)
			{
				result.setEntity(bean);
				detailList = interestBiz.getJuJinBaoList(userId, 1, 10);
				zhaiQuanList=interestBiz.getJuJinBaoZhaiQuan(userId, 1, 10);
				bean.setDetail(detailList);
				bean.setZhaiQuan(zhaiQuanList);
			}
			result.setEntity(bean);
			result.setStatus(true);
			logger.info(String.format("[%s]聚金宝列表查询正常", userId));
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("[%s]聚金宝列表查询异常", userId));
		}
		return result;
	}
	
	@RequestMapping(value = "/getjujinbaolist", method = RequestMethod.GET)
	public @ResponseBody
	Object getJuJinBaoList(int pi,int ps,HttpServletRequest request) {
		
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
			result = interestBiz.getJuJinBaoList(userId, pi, ps);
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
			result.setMsg(String.format("[%s]聚金宝资金流水列表查询异常", userId));
		}
		return result;
	}
	
	@RequestMapping(value = "/getjujinbaozhaiquanlist", method = RequestMethod.GET)
	public @ResponseBody
	Object getJuJinBaoZhaiQuan(int pi,int ps,HttpServletRequest request) {
		
		JsonList<ZhaiQuan> result = null;
		
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
			result = interestBiz.getJuJinBaoZhaiQuan(userId, pi, ps);
			if(result!=null)
			{
				result.setStatus(true);
			}
			else
			{
				result=new  JsonList<ZhaiQuan>();
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if(result==null)
			{
				result=new JsonList<ZhaiQuan>();
			}
			result.setStatus(false);
			result.setMsg(String.format("[%s]债权列表查询异常", userId));
		}
		return result;
	}
	
}
