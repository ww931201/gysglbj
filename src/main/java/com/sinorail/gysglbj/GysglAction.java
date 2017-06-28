package com.sinorail.gysglbj;

import com.jfinal.plugin.activerecord.Page;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.DicUser;
import com.sinorail.gysglbj.model.Supplier;

public class GysglAction extends QuiController {

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
		 
		Page<Supplier> page = Supplier.dao.findPaginate(pageNumber(), pageSize());
		
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
			}else {
				setAttr("content", "供应商编号已存在!");
			}
		} else {
			//修改
			if(!Supplier.dao.isExistGysbh(supplier.getGysbh(), supplier.getId())) {	
				status = supplier.update();
			}else {
				setAttr("content", "供应商编号已存在!");
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
}
	
