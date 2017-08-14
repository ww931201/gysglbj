package com.sinorail.gysglbj.model;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sinorail.gysglbj.model.base.BaseCertificate;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Certificate extends BaseCertificate<Certificate> {
	public static final Certificate dao = new Certificate().dao();

	public Page<Certificate> queryBySupplierId(Integer pageNumber, Integer pageSize, String supplierid) {
		SqlPara sqlp = getSqlPara("certificate.findBySupplierId", supplierid);
		return paginate(pageNumber, pageSize, sqlp);
	}

	/**
	 * 查询单行记录
	 */
	public Object queryById(String cerId) {
		
		return findFirst(getSql("certificate.selectById"),cerId);
	}

	public boolean isExistZsbh(String no) { 
		
		Certificate certificate = findFirst(getSql("certificate.findByZsbh"),no);
		if(certificate == null){
			return false;
		}
		return true;
	} 

	public boolean isExistZsbh(String no, String id) {
		
		Certificate certificate = findFirst(getSql("certificate.findByZsbhAndId"), no, id);
		if(certificate == null){
			return false;
		}
		return true;
	}
	
}
