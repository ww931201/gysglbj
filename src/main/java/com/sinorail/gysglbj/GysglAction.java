package com.sinorail.gysglbj;



import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Page;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Certificate;
import com.sinorail.gysglbj.model.Supplier;

public class GysglAction extends QuiController {
	
	private Logger log = Logger.getLogger(GysglAction.class);

	public void index() {
		render("view.html");
	}
	
	/*添加弹出框进行保存*/
	public void addView(){
		render("save.html");
	}
	
	/**
	 * 
	 * 展示供应商管理信息
	 */
	public void list(){ 
		Page<Supplier> page = Supplier.dao.findPaginate(pageNumber(), pageSize(),getModel(Supplier.class));
		renderJson(page);
	} 
	
	/**
	 * 保存信息的方法
	 * 
	 */
	public void save(){
		
		boolean status = false;
		Supplier supplier = null;
		
		try {
			 supplier = getModel(Supplier.class,"supplier");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (supplier.getId() == null) {
			supplier.remove("ID");
			if(!Supplier.dao.isExistGysbh(supplier.getGysbh())) {		
				status = supplier.save();
				log.info("****保存供应商,编号为"+ supplier.getGysbh());
			}else {
				setAttr("content", "供应商编号已存在!");
			}
		} else {
			//修改
			try {
				if(!Supplier.dao.isExistGysbh(supplier.getGysbh(), supplier.getId())) {	
					status = supplier.update();
				}else {
					setAttr("content", "供应商编号已存在!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		setAttr("message", status ? "保存成功!" : "保存失败!");
		renderJson();
	}
	/***
	 * detailView
	 * 查看每一行的信息
	 * 
	 */
	public void detailView() {
		setAttr("supplier", Supplier.dao.queryById(getPara("id")));
		render("detail.html");
	}
	
	/**
	 * 
	 * 删除对应的一行
	 */
	
	public void delete(){
		if(Supplier.dao.deleteById(getPara("id"))){
			setAttr("status", 1);
			log.info("****删除供应商记录ID="+ getPara("id"));
		}
		renderJson(); 
		
	}
	
	/**
	 * 
	 * 批量删除
	 */ 
	public void deleteBatch(){
		
		if(Supplier.dao.deleteByIds(getPara("ids"))){
			setAttr("status",1);
			log.info("****删除供应商记录ID="+ getPara("ids")); 
		}
		renderJson();
		
	}
	
	/**
	 * 修改供应商记录在save.html页面展示
	 * 
	 */ 
	public void updateView(){
		setAttr("supplier",Supplier.dao.queryById(getPara("id")));
		render("save.html");
	}
	
	/**
	 * 子窗口信息展示
	 * 
	 */
	
	public void showCer(){
		
		Page<Certificate> page = Certificate.dao.queryBySupplierId(pageNumber(), pageSize(), getPara("supplierId"));
		
		renderJson(page);
	}
}
	
