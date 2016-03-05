package com.jujin.biz.coin;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.JujinBaseBiz;
import com.jujin.common.JsonList;
import com.jujin.entity.borrow.Borrow;
import com.jujin.entity.coin.CoinItem;
import com.jujin.utils.Toolkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 
 * **/
public class CoinItemBiz extends JujinBaseBiz {
	
	

	public JsonList<CoinItem> QueryUserCoinOverdue(String userId) {

		return QueryCoinItem(userId, "QueryUserCoinOverdue");
	}

	public JsonList<CoinItem> QueryUserCoinGet(String userId) {

		return QueryCoinItem(userId, "QueryUserCoinGet");
	}

	public JsonList<CoinItem> QueryUserCoinUse(String userId) {
		return QueryCoinItem(userId, "QueryUserCoinUse");
	}

	private JsonList<CoinItem> QueryCoinItem(String userId, String keyName) {

		SqlSession session = null;
		JsonList<CoinItem> result = null;

		try {
			session = this.getSession();

			List<CoinItem> list = session.selectList("com.jujin.mapper."
					+ keyName, userId);

			if ("QueryUserCoinUse".equals(keyName)) {
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setMemo(Toolkit.RemoveLink(list.get(i).getMemo()));
					}
				}
			}
			result = new JsonList<CoinItem>();
			result.addRange(list);
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	
}
