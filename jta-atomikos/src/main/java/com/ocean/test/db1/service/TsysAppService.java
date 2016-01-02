package com.ocean.test.db1.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocean.test.core.baseclass.service.BaseService;
import com.ocean.test.db1.persistence.TsysAppMapper;
import com.ocean.test.exception.ServiceException;

@Service(value ="tsysAppService")
@SuppressWarnings("rawtypes")
public class TsysAppService<T> extends BaseService<T> {
	@Autowired
	private TsysAppMapper<T> mapper;

	public TsysAppMapper<T> getMapper() {
		return mapper;
	}
	
	
	public List selectTsysApp() throws ServiceException {
		return getMapper().selectTsysApp();

	}

	
}
