package com.jujin.controller.duozhuan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.DuozhuanBiz;
import com.jujin.entity.duozhuan.DzBorrowInfo;
import com.jujin.entity.duozhuan.BorrowList;
import com.jujin.util.xglc.SignUtil;

/**
 * Title: QueryController
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年12月4日
 */
@Controller
@RequestMapping("/duozhuan")
public class DuozhuanApiController {
	
	private static final String KEY = "jujinziben";
	DuozhuanBiz biz = new DuozhuanBiz();
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Object borrowList(int page,int pagesize,String token,HttpServletRequest request, HttpServletResponse response) {
		BorrowList list = new BorrowList();
		String thisToken = SignUtil.encryptMD5(""+page + pagesize + KEY);
		if(thisToken.equals(token)){
			if (page <= 0) {			
				return "页数必须大于0";
			} else if (pagesize <= 0) {
				return "每页显示数量必须大于0";
			}
			list = biz.getBorrowUrls(page, pagesize);
		}else{
			return String.format("token[%s]验证失败",token);
		}
		return list;
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public @ResponseBody Object borrowInfo(String projectId,String token,HttpServletRequest request, HttpServletResponse response) {
		DzBorrowInfo info = new DzBorrowInfo();
		String thisToken = SignUtil.encryptMD5(""+projectId + KEY);
		if(thisToken.equals(token)){
			info = biz.getBorrowInfo(projectId);
			if(info == null){
				return String.format("不存在产品编号为:[%s]的产品",projectId);
			}
		}else{
			return String.format("token[%s]验证失败",token);
		}
		return info;
	}
	
}
