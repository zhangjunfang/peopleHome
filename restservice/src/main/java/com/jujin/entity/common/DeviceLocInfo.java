package com.jujin.entity.common;

public class DeviceLocInfo {
	
 
	private String formatted_address;
	
	private String version;
	private String type;
	private String userId;
	 
	private Location location;
	private AddressComponent addressComponent;
	
	
	public  DeviceLocInfo()
	{
		location=new Location();
		addressComponent=new AddressComponent();
	}
	
	public String getVersion() {
		return version;
	}
	 
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}
	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	
	
	
	
	
}
