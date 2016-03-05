package com.isea533.mybatis.controller.demo.ftl;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isea533.mybatis.model.Pojo;

/**
 * @author liuzh_3nofxnp
 * @since 2015-09-19 17:15
 */
@Controller
public class FTLController {

    private String index_ftl = "index_ftl";
    private String page_list = "page_ftl";
    @RequestMapping(value = {"index_ftl"})
    public ModelAndView  index() {
        ModelAndView result = new ModelAndView(index_ftl);
        result.addObject("admin", "zhangboyu");
        return result;
    }

    @RequestMapping(value = {"add_ftl"},method=RequestMethod.POST)
    public ModelAndView  add(Pojo pj) {
    	System.err.println("---------------------------");
    	System.err.println(pj);
        ModelAndView result = new ModelAndView(page_list);
        result.addObject("pj", pj);
        result.addObject("message", "操作成功！！！！");
        result.addObject("date", new Date());
        return result;
    }
    @ResponseBody
    public String  view(Pojo pj) {
    	System.err.println("---------------------------");
    	System.err.println(pj);
        ModelAndView result = new ModelAndView(page_list);
        result.addObject("pj", pj);
        result.addObject("message", "操作成功！！！！");
        result.addObject("date", new Date());
        return "";
    }
}
