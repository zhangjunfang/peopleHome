package restservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.jujin.biz.coin.CoinItemBiz;
import com.jujin.biz.common.CommonBiz;
import com.jujin.entity.common.AddressComponent;
import com.jujin.entity.common.DeviceLocInfo;
import com.jujin.entity.common.Location;

public class CommonBizTest {

	public static void main(String[] args)
	{
		
		DeviceLocInfo device=new DeviceLocInfo(); 
		
		
		device.setFormatted_address("北京市海淀区海淀中街15号");
		
		
		Location location=new Location();
		location.setLat("111.22");
		location.setLng("34.28");
		device.setLocation(location);
		
		AddressComponent addressComponent=new  AddressComponent();
		
		addressComponent.setStreet_number("15号");
		addressComponent.setProvince("北京市");
		addressComponent.setDistrict("district");
		addressComponent.setStreet("海淀中街");
		addressComponent.setCity("北京市");
		
		
		device.setAddressComponent(addressComponent);
		device.setVersion("1.21");
		device.setType("2222");
		device.setUserId("firetw");
		
		String jsonStr=JSON.toJSONString(device);
		
		//System.out.println(jsonStr);
		//biz.InsertDeviceLocInf(device);
		
		
		//CoinUse();
		
		String content= "{\"formatted_address\":\"河南省郑州市金水区金水东路\",\"location\":{\"lat\":34.774204,\"lng\":113.775401},\"addressComponent\":{\"province\":\"河南省\",\"street_number\":\"\",\"distance\":\"\",\"district\":\"金水区\",\"direction\":\"\",\"street\":\"金水东路\",\"city\":\"郑州市\"}}";
		DeviceLocInfo info=JSON.parseObject(content, DeviceLocInfo.class);
		System.out.println(info);
		
		
		
		
		
	}
	
	
	private static String REGEX = "\\<[^>]*\\>*"; 
	   
    private static String REPLACE = ""; 
    
    
	public static void CoinUse()
	{
		String link="您对标<a target=\"blank\" href=\"http://www.jujinziben.com/borrowinfo.page?borrow_id=2015070200000000000000001491\">【房产抵押】150703YYX</a>成功投资，使用聚金币3.0453500000000004元(为您节省3.0453500000000004元)";
		Pattern p = Pattern.compile(REGEX); 
        Matcher m = p.matcher(link);  // 获得匹配器对象 
        String content = m.replaceAll(REPLACE); 
        
        
        CoinItemBiz biz=new CoinItemBiz();
        biz.QueryUserCoinUse("firetw");
        System.out.println(content);
	}
}
