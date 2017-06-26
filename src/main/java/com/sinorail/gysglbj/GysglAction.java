package com.sinorail.gysglbj;

import com.jfinal.plugin.activerecord.Page;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Supplier;

public class GysglAction extends QuiController {

	public void index() {
		render("view.html");
	}
	
	/**
	 * 
	 * 展示供应商管理信息
	 */
	public void list(){
		 
		//List<Record> find = Db.find(sql);
		//setAttr("rows", Supplier.dao.findPaginate(pageNumber(), pageSize()));
		Page<Supplier> page = Supplier.dao.findPaginate(pageNumber(), pageSize());
		
		//Supplier.dao.find("select * from supplier");
		
		renderJson(page);
	}
}
