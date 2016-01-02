package com.ocean.test.core.baseclass.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.ocean.test.core.baseclass.persistence.BaseSqlMapper;
import com.ocean.test.exception.ServiceException;

public abstract class BaseService<T>{
	protected final Logger log = Logger.getLogger(this.getClass());
	public abstract BaseSqlMapper<T> getMapper();

	public void insert(T t)  throws ServiceException{
		getMapper().insert(t);
	}

	public int insertBatch(List<T> list){  
        return getMapper().insertBatch(list);  
    }

	public void update(T t)  throws ServiceException{
		getMapper().update(t);
	}
	

	public void updateBySelective(T t){
		getMapper().updateBySelective(t);
	}

	public void delete(String... ids) throws ServiceException{
		if(ids == null || ids.length < 1){
			return;
		}
		for(String id : ids ){
			getMapper().delete(id);
		}
	}
	
	public T selectDetailById(String id) throws ServiceException{
		return getMapper().selectDetailById(id);
	}

}
