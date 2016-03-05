package com.jujin.entity.recharge;

import java.util.List;

import com.jujin.entity.account.UserBankCard;

public class BankCardEntity {
	
	
	
	List<UserBankCard> cards=null;
	boolean isRealName;
	
	
	public boolean isRealName() {
		return isRealName;
	}

	public void setRealName(boolean isRealName) {
		this.isRealName = isRealName;
	}

	public List<UserBankCard> getCards() {
		return cards;
	}

	public void setCards(List<UserBankCard> cards) {
		this.cards = cards;
	}


}
