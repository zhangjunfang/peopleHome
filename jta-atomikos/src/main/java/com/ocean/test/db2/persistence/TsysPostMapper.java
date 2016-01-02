
package com.ocean.test.db2.persistence;
import java.util.List;
import java.util.Map;

import com.ocean.test.core.baseclass.persistence.BaseSqlMapper;

public interface TsysPostMapper<T> extends BaseSqlMapper<T> {
       
	public List<T> selectPageTsysPostByMap(Map<String, Object> para);

	public List<T> selectPageTsysPost();
}