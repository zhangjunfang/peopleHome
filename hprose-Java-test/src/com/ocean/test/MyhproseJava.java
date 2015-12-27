package com.ocean.test;

import hprose.client.HproseHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyhproseJava {
    private  static  final    HproseHttpClient client = new HproseHttpClient();
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		
	        client.useService("http://localhost:8080");
	        String result = (String) client.invoke("Hello", new Object[] { "word-zhangboyu" });
	        System.out.println(result);
	        ArrayList<Integer> list = (ArrayList<Integer>) client.invoke("Swap", new Object[] { 33,22 });
	        System.out.println(Arrays.deepToString(list.toArray()));
	}

}
