package com.sinorail.gysglbj.action;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Quote;
import com.sinorail.gysglbj.util.ExcelUtils;

public class QuoteAction extends QuiController {
	
	
	public void index() {
		render("view.html");
	}
	public void addView() {
		render("save.html");
	}
	public void updateView() {
		setAttr("quote", Quote.dao.findById(getPara("id")));
		render("save.html");
	}
	public void detailView() {
		setAttr("quote", Quote.dao.findById(getPara("id")));
		render("detail.html");
	}
	public void importView() {
		setAttr("projectId", getPara("projectId"));
		render("importView.html");
	}
	
	public void list() {
		SqlPara sqp = Db.getSqlPara("quote.paginateList", getModel(Quote.class));
		renderJson(Db.paginate(pageNumber(), pageSize(), sqp));
	}
	
	public void save() {
		boolean status = false;
		Quote project = getModel(Quote.class);
		if (project.getId() == null) {
			project.remove("ID");
				status = project.save();
		} else {
				status = project.update();
		}
		if(!status) {			
			setAttr("message","保存失败!");
		}
		renderJson();
	} 
	
	public void delete() {
		if(Quote.dao.deleteById(getPara("id"))) {
			setAttr("status", 1);
		}
		renderJson();
	}
	
	@Before(Tx.class)
	public void deleteBatch() {
		String[] idArray = getPara("ids").split(",");
		for (String id : idArray) {
			if(Quote.dao.deleteById(id)) {
				setAttr("status", 1);
			}
		}
		renderJson();
	}
	
	public void importExcel() {
		
		UploadFile file = getFile("excel");
		
		String projectId = getPara("projectId");
		
		List<List<Object>> list = null;
		
		String[] field = {"BJH", "WZBM", "WZMC", "GGXH", "JSYQ", "JLDW", "YCSL", "DJXJ_BHS", "ZXJ_BHS", "SYDWJDQ", "CSBDJ_BHS", "CSBZXJ_BHS"};
		
		try {
			
			list = ExcelUtils.readExcel(file.getFile(), 5);
			
		} catch (IOException e1) {
			
			setAttr("msg", file.getFileName()+"上传失败!");
			e1.printStackTrace();
			
		}
		List<Record> recordList = new LinkedList<Record>();
		
		int temp_i = 0;
		
		for (List<Object> listm : list) {
			
			if(temp_i == 53)  break;
			
			Record r = new Record().set("PROJECT_ID", projectId).set("SUPPLIER_ID", null);
			
			for(int i=0; i<field.length; i++) {
				
				r.set(field[i], listm.get(i));
				
			}
			
			if(r != null) recordList.add(r);
			
			temp_i++;
		}
		
		
		if(!(Db.batchSave("E_QUOTE", recordList, recordList.size()).length > 0) ) {
			
			setAttr("msg", "导入失败!");
			
		}
		
		file.getFile().delete();//删除上传的缓存文件
		renderJson();
	}
}
