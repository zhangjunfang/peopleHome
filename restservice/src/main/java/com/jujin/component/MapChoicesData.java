/**
 * 
 */
package com.jujin.component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cn.wicket.common.common.component.ChoiceListValues;
import com.pro.common.util.ConvUtil;

/**
 * @author 宁
 * 
 */
public class MapChoicesData {

	private List<ChoiceListValues> choicesList = new ArrayList();
	private HashMap<Integer, String> choiceMap = new HashMap<Integer, String>();

	private ResourceBundle messageResourceBundle = ResourceBundle
			.getBundle("Infomart_ChoicesDataInfo");
	private static String ITEM = ".item";
	private static String DISPLAY = ".display";
	private static String VALUE = ".value";

	public MapChoicesData(String paramString) {
		initChoicesList(paramString);
	}

	private void initChoicesList(String paramString) {
		Set localSet = this.messageResourceBundle.keySet();
		Iterator localIterator = localSet.iterator();
		String str1 = null;
		ChoiceListValues localChoiceListValues = null;
		int i = 1;
		for (;;) {
			if (localIterator.hasNext()) {
				str1 = (String) localIterator.next();
				try {
					if (str1.length() >= paramString.length()) {
						if (paramString.equals(str1.substring(0,
								paramString.length()))) {
							localChoiceListValues = new ChoiceListValues();
							String str2 = paramString + ITEM
									+ String.valueOf(i) + DISPLAY;
							String str3 = paramString + ITEM
									+ String.valueOf(i) + VALUE;

							String tmpKey = getChoicesInfo(str2);
							String tmpValue = getChoicesInfo(str3);
							localChoiceListValues.setDisplay(tmpKey);
							localChoiceListValues.setValue(tmpValue);

							System.out.println(tmpKey + tmpValue);

							this.choicesList.add(localChoiceListValues);

							choiceMap.put(
									StringUtils.isNotEmpty(tmpKey) ? ConvUtil
											.convToInt(tmpKey) : -1, tmpValue);
							i++;
						} else {
							continue;
						}
					}
				} catch (IllegalArgumentException localIllegalArgumentException) {
				} catch (RuntimeException localRuntimeException) {
				}
			}
		}
	}

	private String getChoicesInfo(String paramString) {
		return MessageFormat.format(
				this.messageResourceBundle.getString(paramString),
				new Object[] { "" });
	}

	public List<ChoiceListValues> getChoicesList() {
		return this.choicesList;
	}

	public HashMap<Integer, String> getChoicesMap() {
		return this.choiceMap;
	}
}