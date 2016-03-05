package com.jujin.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.common.CommonBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.controller.BaseController;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.Account;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.entity.common.AppVersion;
import com.jujin.entity.common.DeviceLocInfo;
import com.jujin.entity.common.AppVersion;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.DesCodeUtil;
import com.wicket.loan.common.utils.NumberUtils;


/**
 * 
 * 
 * 
 * **/
@Controller
public class CommonController  extends BaseController{
	
	CommonBiz biz = new CommonBiz();
	/**2、iOS;3、Andriod**/
	@RequestMapping(value = "/checkupdate", method = RequestMethod.GET)
	public @ResponseBody
	Object getAccountMemo(@RequestParam(value = "v", required = true) String version,
			
			@RequestParam(value = "t", required = true) String type) {
		
		OpEntityResult<AppVersion> entity = new OpEntityResult<AppVersion>(null);
		
		if (StringUtils.isEmpty(version)||StringUtils.isEmpty(type)) {
			entity.setStatus(false);
			entity.setMsg("数据格式不正确");
			return entity;
		}
		AppVersion ver = null;
		try{
			ver = biz.getVersion(type,version);
			if(ver!=null ){
				entity.setStatus(true);
				entity.setEntity(ver);
				
				//entity.setMsg(String.format("平台类型[%s]", "3".equals(type)?"iOS":"2".equals(type)?"Andriod":"未知","最新版本[%s]", list.getVersion(),"最新版本说明[%s]", list.getMemo(),"最新版本地址[%s]", list.getPath()));
				logger.info(String.format("平台类型[%s]", "3".equals(type)?"iOS":"2".equals(type)?"Andriod":"未知","最新版本[%s]", ver.getVersion(),"最新版本说明[%s]", ver.getMemo(),"最新版本地址[%s]", ver.getPath()));
			}else{
				entity.setMsg(String.format("未发现该平台下的版本类型", "3".equals(type)?"iOS":"2".equals(type)?"Andriod":"未知",version));
			}
		}catch(Exception e){
			logger.error(ExceptionHelper.getExceptionDetail(e));
			entity.setMsg(String.format("查询数据有异常，请与客服联系"));
			entity.setStatus(false);
		}
		return entity;
	}
	
	 
	

 
	@RequestMapping(value = "/postdeviceinfo",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object Postdeviceinfo(HttpEntity<DeviceLocInfo> entity,
			HttpServletRequest request){
		
		OpResult result = new OpResult();
		DeviceLocInfo fm=null;
		if(entity == null||(fm=entity.getBody())==null){
			result.setMsg("数据类型错误");
			return result;
		} 
		logger.info(fm);
		String userId=this.getLoginedUserId(request);
		if(!StringUtils.isEmpty(userId))
		{
			fm.setUserId(userId);
		}
		try{
			biz.InsertDeviceLocInf(fm);
			result.setStatus(true);
		}catch(Exception ex){
			result.setStatus(false);
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setMsg("提交失败，请重试或者与客服联系");
		}
		return result;
	}
	
}
