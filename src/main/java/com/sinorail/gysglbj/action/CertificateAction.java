package com.sinorail.gysglbj.action;


import org.apache.log4j.Logger;

import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Certificate;

/**
 * 
 *  证书的Action
 * @author wangwei
 *
 */
public class CertificateAction extends QuiController{
	
	private Logger log  = Logger.getLogger(CertificateAction.class);
	
	public void index(){
		render("view.html");
		log.info("***跳转到证书的展示页面");
	}
	
	
	public void detailViewSon(){
		setAttr("certificate", Certificate.dao.queryById(getPara("id")));
		render("detail.html");
	}
	
	
	public void addViewSon(){
		String fatherId = getPara("fatherId");
		setAttr("certificate.SUPPLIER_ID", fatherId);
		render("save.html");
	}
	
	/**
	 * 证书保存
	 */
	public void save(){
		
		boolean status = false;
		Certificate certificate = getModel(Certificate.class,"certificate");
		/*certificate.setStartTime(new Timestamp(getParaToDate("START_TIME").getTime()));
		certificate.setEndTime(new Timestamp(getParaToDate("END_TIME").getTime()));*/
		
		if (certificate.getId() == null) {
			certificate.remove("ID");
			if(!Certificate.dao.isExistZsbh(certificate.getNo())) {		
				status = certificate.save();
				log.info("****保存证书,编号为"+ certificate.getNo());
			}else {
				setAttr("content", "证书编号已存在!");
			}
		} else {
			//修改
			try {
				if(!Certificate.dao.isExistZsbh(certificate.getNo(), certificate.getId())) {	
					status = certificate.update();
				}else {
					setAttr("content", "证书编号已存在!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		setAttr("message", status ? "保存成功!" : "保存失败!");
		renderJson();
	}
	
	/**
	 * 证书修改
	 */ 
	
	public void updateViewSon(){
		
		setAttr("certificate", Certificate.dao.queryById(getPara("id")));
		render("save.html");
		
	}
	/**
	 * 证书删除
	 * 
	 */
	
	public void deleteSon(){
		if(Certificate.dao.deleteById(getPara("id"))){
			setAttr("status", 1);
			log.info("****删除ID="+ getPara("id"));
		}
		renderJson(); 
		
	}
}
