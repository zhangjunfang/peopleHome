package com.jujin.controller;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.common.OpResult;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.model.ModelConfig;
import com.wicket.loan.web.common.mediator.CommonAttestMediator;
import com.wicket.loan.web.person.withdraw.mediator.WithdrawMediator;

@Controller
public class TestWithDraw {

	@RequestMapping(value = "/wn", method = RequestMethod.GET)
	public @ResponseBody
	Object withdrawTest(HttpServletRequest request) {

		// WithdrawMediator mediator = new WithdrawMediator();

		// String feeString = mediator.getFeeAmountForWithdraw("6000", "hnfxp",
		// "6000");

		// return feeString;

		Class<?> tmpClas = ModelConfig.class;
		System.out.println(tmpClas);

		URL tmpUrl = tmpClas.getResource("/models.xml");

		System.out.println(ModelConfig.class.getResource("").getPath());
		
		System.out.println(tmpUrl.toString());

		URL url = tmpClas.getResource("/models.xml");

		System.out.println(url.toString());

		return url.toString();

	}

	@RequestMapping(value = "/regjujin", method = RequestMethod.GET)
	public @ResponseBody
	Object notifyJuJin(String id, HttpServletRequest request) {

		String userId = id;
		OpResult result = new OpResult();
		try {
			CommonAttestMediator mediator = new CommonAttestMediator();
			mediator.notifyAttestRegister(userId);
			result.setStatus(true);
		} catch (Exception ex) {
			result.setMsg(ExceptionHelper.getExceptionDetail(ex));
		}

		return result;

	}

}
