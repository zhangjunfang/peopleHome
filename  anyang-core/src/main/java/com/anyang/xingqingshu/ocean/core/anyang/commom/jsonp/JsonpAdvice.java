package com.anyang.xingqingshu.ocean.core.anyang.commom.jsonp;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{
	public JsonpAdvice() {
		super("callback","jsonp");
	}
}
