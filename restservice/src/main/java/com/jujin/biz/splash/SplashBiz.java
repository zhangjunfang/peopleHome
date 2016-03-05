package com.jujin.biz.splash;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.BaseBiz;
import com.jujin.entity.splash.Splash;
import com.jujin.entity.splash.SplashItem;

public class SplashBiz  extends BaseBiz  {

	public Splash GetSplash()
	{
		Splash splash=new Splash();
		SqlSession session=null;
		
		try
		{
			session=this.getSession();
			List<SplashItem> list=session.selectList("com.jujin.mapper.QuerySplash");
			if(list!=null&&list.size()>0)
			{
				for (int i = 0; i < list.size(); i++) {
					SplashItem item=list.get(i);
					if("0".equals(item.getType()))
					{
						splash.getBanners().add(item);
					}
					else if("1".equals(item.getType()))
					{
						splash.getItems().add(item);
					}
					else if("2".equals(item.getType()))
					{
						splash.setDefaultSplash(item);
					}
				}
			}
		}
		finally{
			if(session!=null)
			{
				session.close();
			}
		}
		return splash;
	}
}
