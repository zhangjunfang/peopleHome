package spring.handlers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ocean.mybatis.model.People;

public class AAA {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");  
		People p = (People)ctx.getBean("cutesource");  
		System.out.println(p.getId());  
		System.out.println(p.getName());  
		System.out.println(p.getAge()); 
		
	}

}
