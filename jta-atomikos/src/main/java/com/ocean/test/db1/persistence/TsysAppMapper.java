

package com.ocean.test.db1.persistence;
import java.util.List;

import com.ocean.test.core.baseclass.persistence.BaseSqlMapper;

public interface TsysAppMapper<T> extends BaseSqlMapper<T> {
       
	@SuppressWarnings("rawtypes")
	public List selectTsysApp();

	
}