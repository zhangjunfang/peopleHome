package restservice;

import com.jujin.biz.HomeBiz;
import com.jujin.entity.borrow.BorrowInfo;

public class HomeBizTest {
	
	public static void main(String[] args) {
		
		
		HomeBiz biz=new  HomeBiz();
		
		BorrowInfo info= biz.getBorrowInfo("2015080700000000000000001567");
		
		boolean objString=biz.QueryBorrowTransfer("2015080700000000000000001567");
		
		System.out.println(objString);
		System.out.println(info);
		 
		
	}

}
