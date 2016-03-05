package com.jujin.biz;

import org.apache.ibatis.session.SqlSession;
import org.springframework.util.StringUtils;

import com.jujin.entity.UserAttestation;

/*
 * 
 * 认证相关
 * */
public class AttestBiz extends BaseBiz {

	/**
	 * 
	 * 根据身份证号判断性别
	 * 
	 * 1：男；2：女
	 * 
	 * @param card
	 * @return
	 */
	public String getGenderByIdCard(String card) {
		if (StringUtils.isEmpty(card)) {
			return "1";
		}

		String genderStr = "";
		if (card.length() == 18) {
			genderStr = card.substring(14, 17);
		} else if (card.length() == 15) {
			genderStr = card.substring(12, 15);
		}
		if (StringUtils.isEmpty(genderStr)) {
			return "1";
		}
		System.out.println(genderStr);
		// 性别代码为偶数是女性奇数为男性
		if (Integer.valueOf(genderStr) % 2 == 0) {
			return "2";
		} else {
			return "1";
		}
	}

	public UserAttestation QueryUserAttestation(String userId) {

		SqlSession session = null;
		UserAttestation entity = null;
		try {
			session = this.getSession();
			entity = session.selectOne("com.jujin.mapper.QueryUserAttestation",
					userId);
		} finally {
			if (session != null)
				session.close();
		}
		return entity;
	}

}
