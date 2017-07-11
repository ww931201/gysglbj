package com.sinorail.gysglbj.action;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.sinorail.gysglbj.action.service.QuoteService;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Quote;
import com.sinorail.gysglbj.model.Supplier;
import com.sinorail.gysglbj.util.ExcelUtils;

public class QuoteAction extends QuiController {
	
	QuoteService quoteService = Duang.duang(QuoteService.class);
	
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
	public void filterView() {
		setAttr("projectId", getPara("projectId"));
		render("filterView.html");
	}
	
	public void list() {
		Quote quote = getModel(Quote.class);
		SqlPara sqp = Db.getSqlPara("quote.paginateList", quote);
		renderJson(Db.paginate(pageNumber(), pageSize(), sqp));
	}
	
	public void filterList() {
		Quote quote = getModel(Quote.class);
		SqlPara sqp = Db.getSqlPara("quote.filterList", quote);
		renderJson("rows", Db.find(sqp));
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
	
	public void importExcel() throws IOException {
		
		String msg  = "0"; // renderText 参数不能为汉字
		
		UploadFile file = getFile("excel");
		
		String projectId = getPara("projectId");
		
		Object supplierNo = ExcelUtils.getCellValueFromExcel(file.getFile(), 0, 1);
		
		//System.out.println("*********"+supplierId+"************");
		Supplier supplier = Supplier.dao.findFirst("select * from e_supplier where GYSBH = ?", supplierNo);
		
			if(supplier == null) {renderText("1"); return;}
		
		Quote quote = Quote.dao.findFirst("select * from e_quote where project_id = ? and supplier_id = ?",projectId, supplier.getId());
		
			if(quote != null) {renderText("2"); return;}
		
		List<List<Object>> list = null;
		
		String[] field = {"BJH", "WZBM", "WZMC", "GGXH", "JSYQ", "JLDW", "YCSL", "DJXJ_BHS", "ZXJ_BHS", "SYDWJDQ", "CSBDJ_BHS", "CSBZXJ_BHS"};
		
		try {
			
			list = ExcelUtils.readExcel(file.getFile(), 4);
			
		} catch (IOException e1) {
			
			//setAttr("msg", file.getFileName()+"上传失败!");
			//msg = file.getFileName()+"上传失败!";
			e1.printStackTrace();
			renderText("3"); return;
			
		}
		List<Record> recordList = new LinkedList<Record>();
		
		boolean temp_is_stop = false;
		
		for (List<Object> listm : list) {
			
			Record r = new Record().set("PROJECT_ID", projectId).set("SUPPLIER_ID", supplier.getId());
			
			for(int i=0; i<field.length; i++) {
				
				if(field[i] == "WZBM" && (listm.get(i) == null || listm.get(i).equals(""))) {	
					temp_is_stop= true;
					break;
				}
				r.set(field[i], listm.get(i));
				
			}
			if(temp_is_stop) break;
			
			if(r != null) recordList.add(r);
			
		}
		
		
		if(!(Db.batchSave("E_QUOTE", recordList, recordList.size()).length > 0) ) {
			
			//setAttr("msg", "导入失败!");
			msg = "导入失败!";
			renderText("4"); return;
		}
		
		file.getFile().delete();//删除上传的缓存文件
		
		//renderJson();
		renderText(msg);
	}
}
