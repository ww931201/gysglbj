package com.sinorail.gysglbj.action;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Certificate;
import com.sinorail.gysglbj.util.ExcelUtils;

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
		setAttr("fatherId", fatherId);
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
					
					//取消资质证书的父ID
					
					certificate.remove("SUPPLIER_ID");
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
	
	/**
	 * 
	 */
	public void importView(){
		setAttr("supplierId", getPara("supplierId"));
		render("importView.html");
		
	}
	
	public void importExcel() throws IOException, ParseException{
		  
		String msg  = "0"; // renderText 参数不能为汉字
		
		UploadFile file = getFile("excel");
		
		List<List<Object>> list = null;
		
		String[] field = {"NO", "NAME", "CONTENT", "START_TIME", "END_TIME", "UNIT"};
		
		try {
			list = ExcelUtils.readExcel(file.getFile(), 1);
		} catch (IOException e1) {
			
			e1.printStackTrace();
			renderText("3"); return;
			
		}
		/**进行证书编码重复校验*/
		//1.导入excel文件本身的证书编码重复
		 
		String temp = "";
		List<Object> lists = new ArrayList<Object>();
		for (List<Object> cerNo : list) {
			if(lists!=null){
				for (Object object : lists) {
					if(cerNo.get(0).equals(object)){
						temp += cerNo.get(0)+",";
					}
				}
			}
			  lists.add(cerNo.get(0));
		}
		
		if(temp.length()>0){
			renderText("5"); return;
		}
		
		//2.导入的excel文件与系统数据库的编码重复
		String temps = "";
		List<Certificate> noList = Certificate.dao.find("select NO from E_CERTIFICATE"); 
		if(noList!=null){
				for (Certificate certificate : noList) {
					if(list!=null){
						for (List<Object> excNo : list) {
							if(excNo.get(0).equals(certificate.getNo())){
								temps += certificate.getNo()+",";
							}
						}
					}
				}
			}
		
		if(temps.length()>0){
			renderText("6");
			return;
		}
		
		List<Record> recordList = new LinkedList<Record>();
		
		boolean temp_is_stop = false;
		
		for (List<Object> listm : list) {
			
			Record r = new Record().set("SUPPLIER_ID", getPara("supplierId"));
			
			for(int i=0; i<field.length; i++) {
				
				r.set(field[i], listm.get(i));
				
				if(i == 3 || i == 4){
					r.set(field[i], new Timestamp(((Date) listm.get(i)).getTime()));
				}
				/* 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				if(i == 3 || i == 4){
					
					long str = new Double((Double) listm.get(i)).longValue();
					
					new Timestamp(str);
					System.out.println(new Date(str));
					r.set(field[i], new Timestamp(str));
				}*/
			}
			if(temp_is_stop) break;
			
			if(r != null) recordList.add(r);
			
		}
		
		if(!(Db.batchSave("E_CERTIFICATE", recordList, recordList.size()).length > 0) ) {
			
			msg = "导入失败!";
			renderText("4"); return;
		}
		
		file.getFile().delete();//删除上传的缓存文件
		
		//renderJson();
		renderText(msg);
		
		}
	/**
	 * 导出证书模板
	 */
	public void export(){
		
		String path = Thread.currentThread().getContextClassLoader().getResource("templates/certificate_template.xlsx").getPath();
		
		File file = new File(path);
		
		renderFile(file);
		
	}
}
