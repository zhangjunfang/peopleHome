package com.jujin.controller.splash;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.splash.SplashBiz;
import com.jujin.common.OpEntityResult;
import com.jujin.controller.BaseController;
import com.jujin.entity.splash.Splash;
import com.jujin.entity.splash.SplashItem;
import com.jujin.utils.ExceptionHelper;

@Controller
public class SplashController extends BaseController {

	
	
	SplashBiz biz=new SplashBiz();
	
	//TODO:从DB获取数据
	@RequestMapping(value = "/splash", method = RequestMethod.GET)
	public @ResponseBody
	Object getSplash() {

		logger.info("获取Splash数据");
		Splash splash=null;
		OpEntityResult<Splash> splashes = new OpEntityResult<Splash>(splash);
		try {
			  splash=biz.GetSplash();
			  splashes.setEntity(splash);
			  splashes.setStatus(true);
		} catch (Exception ex) {
			splashes.setStatus(false);
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		return splashes;
	}
}
