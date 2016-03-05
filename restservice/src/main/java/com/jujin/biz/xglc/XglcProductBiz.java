package com.jujin.biz.xglc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.jujin.biz.JujinBaseBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.entity.xglc.borrow.BorrowInfoDTO;
import com.jujin.entity.xglc.borrow.BorrowSeriesDTO;
import com.jujin.entity.xglc.borrow.BorrowUpdateInfoDTO;

/**
 * Title: XglcProductBiz
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class XglcProductBiz extends JujinBaseBiz {
	private static final long serialVersionUID = 1L;

	/**
	 * 获取平台状态为预售、在售的产品列表
	* Title: getSaleProduct
	* Description: 
	* @return
	 */
	public List<BorrowInfoDTO> getSaleProduct() {
		List<BorrowInfoDTO> list = new ArrayList<BorrowInfoDTO>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList("com.jujin.xglc.mapper.getSaleProductList");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * 获取产品的当前更新信息
	* Title: getProductStateInfo
	* Description: 
	* @param queryProductIdList
	* @return
	 */
	public List<BorrowUpdateInfoDTO> getProductStateInfo(String queryProductIdList) {
		List<BorrowUpdateInfoDTO> list = new ArrayList<BorrowUpdateInfoDTO>();
		SqlSession session = null;
		try {
			session = getSession();
			if(queryProductIdList != null && queryProductIdList.length()>0){
				List<String> paramList =  Arrays.asList(queryProductIdList.split(","));
				list = session.selectList("com.jujin.xglc.mapper.getProductStateInfoList",paramList);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * 获取平台下各个系列的全量付息方式和全量担保机构
	* Title: getSeriesInfo
	* Description: 
	* @return
	 */
	public List<BorrowSeriesDTO> getSeriesInfo(String path) {
		List<BorrowSeriesDTO> list = new ArrayList<BorrowSeriesDTO>();
        File file = new File(path);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        list = JSON.parseArray(buffer.toString(), BorrowSeriesDTO.class);
		return list;
	}

//	public String getAppUrl(String borrowId) {
//		String url = null;
//		SqlSession session = null;
//		try {
//			session = getSession();
//			url = session.selectOne("com.jujin.xglc.mapper.getAppUrl",borrowId);
//		} catch (Exception e) {
//			logger.error(ExceptionHelper.getExceptionDetail(e));
//		} finally {
//			if (session != null)
//				session.close();
//		}
//		return url;
//	}


	
}
