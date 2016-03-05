package com.jujin.entity.splash;

import java.util.ArrayList;
import java.util.List;

public class Splash {

	private List<SplashItem> items;
	
	private List<SplashItem> banners;
	
	private SplashItem defaultSplash;
	
	
	public Splash()
	{
		items=new ArrayList<SplashItem>();
		
		banners=new ArrayList<SplashItem>();
	}
 
	public SplashItem getDefaultSplash() {
		return defaultSplash;
	}
	 
	public void setDefaultSplash(SplashItem defaultSplash) {
		this.defaultSplash = defaultSplash;
	}

	public List<SplashItem> getItems() {
		return items;
	}

	public void setItems(List<SplashItem> items) {
		this.items = items;
	}

	public List<SplashItem> getBanners() {
		return banners;
	}

	public void setBanners(List<SplashItem> banners) {
		this.banners = banners;
	}
	
	
}
