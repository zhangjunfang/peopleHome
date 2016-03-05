package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.TicketBiz;
import com.jujin.common.OpResult;

/**
 * 查看加息券
 * 
 * */
@Controller
public class TicetController extends BaseController {

	TicketBiz biz = new TicketBiz();

	@RequestMapping(value = "/ticket", method = RequestMethod.GET)
	public @ResponseBody
	Object getTicketInfo(
			@RequestParam(value = "type", required = true) int type, int pi,
			int ps, HttpServletRequest request) {

		OpResult result = new OpResult();

		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		String userId = this.getLoginedUserId(request);
		return biz.QueryTicketInfo(userId, type, pi, ps);

	}
}
