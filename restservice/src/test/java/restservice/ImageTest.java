package restservice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.jujin.controller.QRCodeUtil;

public class ImageTest {

	
	public static void main(String[] args) {
		
		
		OutputStream os;
		try {
			String inviteUrl ="http://www.baidu.com"; //getInviteUrl("http://www.baidu.com");
			os = new FileOutputStream("E:/tempdir/a.png");
			os.write(QRCodeUtil.getImage(inviteUrl, true, 300, 300));

			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		 
	
	}
}
