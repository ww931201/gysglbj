package com.sinorail.gysglbj.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.sinorail.gysglbj.model.Project;
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
		Project project = Project.dao.findById(getPara("projectId"));
		setAttr("projectName", project.getName());
		//类型 1 竞买  2 竞价(整包)
		if(project.getType().equals(new BigDecimal("1"))) {
			render("filterView.html");
		} else {
			render("filterJJView.html");
		}
	}
	
	public void list() {
		Quote quote = getModel(Quote.class);
		SqlPara sqp = Db.getSqlPara("quote.paginateList", quote);
		renderJson(Db.paginate(pageNumber(), pageSize(), sqp));
	}
	
	public void filterList() {
		Quote quote = getModel(Quote.class);
		SqlPara sqp;
		//类型 1 竞买  2 竞价(整包)
		Project project = Project.dao.findById(quote.getProjectId());
		if(project.getType().equals(new BigDecimal("1"))) {
			sqp = Db.getSqlPara("quote.filterList", quote);
		} else {
			sqp = Db.getSqlPara("quote.filterJJList", quote);
		}
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
		
		UploadFile file = getFile("excel");
		
		String projectId = getPara("projectId");
		
		Object supplierNo = ExcelUtils.getCellValueFromExcel(file.getFile(), 0, 1);
		
		//System.out.println("*********"+supplierId+"************");
		Supplier supplier = Supplier.dao.findFirst("select * from e_supplier where GYSBH = ?", supplierNo);
		
			if(supplier == null) {renderJson("msg", "检查文件中供应商编码是否填写,或 系统中是否录入该供应商!"); return;}
		
		Quote quote = Quote.dao.findFirst("select * from e_quote where project_id = ? and supplier_id = ?",projectId, supplier.getId());
		
			if(quote != null) {renderJson("msg", "已导入该供应商信息!"); return;}
		
		List<List<Object>> list = null;
		
		//String[] field = {"BJH", "WZBM", "WZMC", "GGXH", "JSYQ", "JLDW", "YCSL", "DJXJ_BHS", "ZXJ_BHS", "SYDWJDQ", "CSBDJ_BHS", "CSBZXJ_BHS"};
		String[][] field = {{"BJH","^[0-9]+(.[0]{1,2})?$","包件号"}, {"WZBM",".*","物资编码"}, {"WZMC",".*","物资名称"}, {"GGXH",".*","规格型号"}, {"JSYQ",".*","技术要求"}, {"JLDW",".*","计量单位"}, {"YCSL","^[0-9]+(.[0]{1,2})?$","预测数量"}, {"DJXJ_BHS","^[0-9]+(.[0-9]{1,2})?$","单价限价(不含税）"}, {"ZXJ_BHS","^[0-9]+(.[0-9]{1,2})?$","总限价（不含税）"}, {"SYDWJDQ",".*","使用单位及地区"}, {"CSBDJ_BHS","^[0-9]+(.[0-9]{1,2})?$","厂商报单价(不含税）"}, {"CSBZXJ_BHS","^[0-9]+(.[0-9]{1,2})?$","厂商报总限价（不含税）"}};
		
		try {
			
			list = ExcelUtils.readExcel(file.getFile(), 4);
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
			renderJson("msg", "上传失败!"); return;
			
		}
		List<Record> recordList = new LinkedList<Record>();
		
		int rows = 1;
		boolean temp_is_stop = false;
		
		for (List<Object> listm : list) {
			
			Record r = new Record().set("PROJECT_ID", projectId).set("SUPPLIER_ID", supplier.getId());
			
			for(int i=0; i<field.length; i++) {
				
				if(field[i][0] == "WZBM") {
					if( listm.get(i) == null || listm.get(i).toString().trim().equals("")){						
						temp_is_stop = true;
						break;
					}
				}
				if(listm.get(i) != null && !listm.get(i).toString().matches(field[i][1])) {
					renderJson("msg", "第"+(rows+4)+"行，"+field[i][2]+"："+listm.get(i)+" 规则不匹配！"); return;
				}
				r.set(field[i][0], listm.get(i));
				System.out.println(field[i][0]+": "+listm.get(i));
			}
			if(temp_is_stop) break;
			if(r != null) recordList.add(r);
			
			rows++;
		}
		
		if(!(Db.batchSave("E_QUOTE", recordList, recordList.size()).length > 0) ) {
			
			renderJson("msg", "导入失败!"); return;
		}
		
		file.getFile().delete(); //删除上传的缓存文件
		
		renderJson("msg", "导入成功！");
	}

	/**
	 * 导出数据过滤结果
	 * @throws IOException 
	 */
	public void filterDataExport() throws IOException {
		
		Quote quote = getModel(Quote.class);
		File file = new File("filter.xls");
		
		//类型 1 竞买  2 竞价(整包)
		Project project = Project.dao.findById(quote.getProjectId());
		if(project.getType().equals(new BigDecimal("1"))) {
			
			SqlPara sqp = Db.getSqlPara("quote.filterList", quote);
			List<Record> list = Db.find(sqp);
			
			String[] title = {"包件号", "供应商编号", "企业名称", "物资编码", "物资名称", "规格型号", "技术要求", "计量单位", "预测数量", "单价限价(不含税）", "总限价（不含税）", "使用单位及地区", "厂商报单价(不含税）", "厂商报总限价（不含税）"};
			String[] field = {"BJH", "GYSBH", "QYMC", "WZBM", "WZMC", "GGXH", "JSYQ", "JLDW", "YCSL", "DJXJ_BHS", "ZXJ_BHS", "SYDWJDQ", "CSBDJ_BHS", "CSBZXJ_BHS"};
			
			ExcelUtils.export(list, title, field, file);
		} else {
			
			SqlPara sqp = Db.getSqlPara("quote.filterJJList", quote);
			List<Record> list = Db.find(sqp);
			
			String[] title = {"供应商编号", "企业名称", "总报价"};
			String[] field = {"GYSBH", "QYMC", "ZXJ_BHS"};
			
			ExcelUtils.export(list, title, field, file);
		}
		
		renderFile(file);
	}
}
