package com.jujin.entity.borrow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 标的物材料证明
 * **/
public class BorrowAttestion {

		private List<BorrowAttestationItem> attestType=new ArrayList<BorrowAttestationItem>();
		
		
		private HashMap<String,List<String>> attestFiles=new HashMap<String,List<String>>();


		public List<BorrowAttestationItem> getAttestType() {
			return attestType;
		}


		public void setAttestType(List<BorrowAttestationItem> attestType) {
			this.attestType = attestType;
		}


		public HashMap<String, List<String>> getAttestFiles() {
			return attestFiles;
		}


		public void setAttestFiles(HashMap<String, List<String>> attestFiles) {
			this.attestFiles = attestFiles;
		}
		
		
		
		
		
}
