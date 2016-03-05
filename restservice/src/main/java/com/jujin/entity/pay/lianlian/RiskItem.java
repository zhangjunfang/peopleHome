package com.jujin.entity.pay.lianlian;





public class RiskItem {

	/*
	 * \
	 * "frms_ware_category\":\"2009\",\"user_info_dt_register\":\"20141023173208\",\"user_info_full_name\":\"王宁\",
	 * \
	 * "user_info_id_no\":\"410225198312086132\",\"user_info_id_type\":\"0\",\"user_info_identify_state\":\"1\",
	 * \"user_info_identify_type\":\"1\",\"user_info_mercht_userno\":378799
	 */
	private String frms_ware_category="2009";
	private String user_info_dt_register="20141023173208";
	private String user_info_full_name;
	private String user_info_id_no;
	private String user_info_id_type;
	private String user_info_identify_state="1";
	private String user_info_identify_type;
	private String user_info_mercht_userno = PartnerConfig.OID_PARTNER;
	private String user_info_bind_phone;

	public String getFrms_ware_category() {
		return frms_ware_category;
	}

	public void setFrms_ware_category(String frms_ware_category) {
		this.frms_ware_category = frms_ware_category;
	}

	public String getUser_info_dt_register() {
		return user_info_dt_register;
	}

	public void setUser_info_dt_register(String user_info_dt_register) {
		this.user_info_dt_register = user_info_dt_register;
	}

	public String getUser_info_full_name() {
		return user_info_full_name;
	}

	public void setUser_info_full_name(String user_info_full_name) {
		this.user_info_full_name = user_info_full_name;
	}

	public String getUser_info_id_no() {
		return user_info_id_no;
	}

	public void setUser_info_id_no(String user_info_id_no) {
		this.user_info_id_no = user_info_id_no;
	}

	public String getUser_info_id_type() {
		return user_info_id_type;
	}

	public void setUser_info_id_type(String user_info_id_type) {
		this.user_info_id_type = user_info_id_type;
	}

	public String getUser_info_identify_state() {
		return user_info_identify_state;
	}

	public void setUser_info_identify_state(String user_info_identify_state) {
		this.user_info_identify_state = user_info_identify_state;
	}

	public String getUser_info_identify_type() {
		return user_info_identify_type;
	}

	public void setUser_info_identify_type(String user_info_identify_type) {
		this.user_info_identify_type = user_info_identify_type;
	}

	public String getUser_info_mercht_userno() {
		return user_info_mercht_userno;
	}

	public void setUser_info_mercht_userno(String user_info_mercht_userno) {
		this.user_info_mercht_userno = user_info_mercht_userno;
	}

}
