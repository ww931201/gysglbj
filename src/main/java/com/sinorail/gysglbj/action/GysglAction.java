package com.sinorail.gysglbj.action;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Certificate;
import com.sinorail.gysglbj.model.Supplier;
import com.sinorail.gysglbj.util.ExcelUtils;

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
	 * 导入excel跳转页面
	 */
	public void importView(){
		render("importView.html");
	}
	
	/**
	 * 导入供应商excel数据
	 * @throws IOException
	 */
	public void importExcel() throws IOException {
		
			String msg  = "0"; // renderText 参数不能为汉字
			
			UploadFile file = getFile("excel");
			
			List<List<Object>> list = null;
			
			
			String[] field = {"GYSBH", "SHXYDM", "YYZZZCH", "QYMC", "FDDBR", "FDDBRDH", "SSS", "SSS1", "ZS", "ZCZB", "CLRQ", "YYQX", "QYLX", "ZZJGDM", "SWDJH", "YXQ", "YWLXR", "LXRSJ", "BGCZ", "BGDH", "LXRYX", "LXRZW", "BGDZ", "BLGYSCFZQ", "HMD", "BLGYSXYPJDJ", "GYSJYFW" };
			try {
				list = ExcelUtils.readExcel(file.getFile(), 1,field.length);
				
			} catch (IOException e1) {
				e1.printStackTrace();
				renderText("3"); return;
				
			}
			
			//EXCEL文件供应商编码重复
			String temp ="";
			List<Object> lists = new ArrayList<Object>();
			
			if(list!=null){
				for (List<Object> fileGysbh : list) { 
					if(lists!=null){
						for (Object object : lists) {
							if(fileGysbh.get(0).equals(object)){
								 temp += fileGysbh.get(0)+",";
							}
						}
					}
					lists.add(fileGysbh.get(0));
				}
			}
			if(temp.length()>0){
				renderText("5"); return;
			}
			
			//数据库已经存在此供应商编码
			String duipler = "";
			List<Supplier> findGysbh = Supplier.dao.findGysbh();
			if(findGysbh!=null){
				for (Supplier supplier : findGysbh) {
					String gysbh = supplier.getGysbh();
					if(list!=null){
						for (List<Object> listm : list) { 
							if(listm.get(0).equals(gysbh)){
								duipler += gysbh+",";
							}
						}
					}
				}
			}
			if(duipler.length()>0){
				renderText("6"); return;
			}
			
			List<Record> recordList = new LinkedList<Record>();
			
			boolean temp_is_stop = false;
			
			for (List<Object> listm : list) { 
				
				Record r = new Record();

				for(int i=0; i<field.length; i++) {
					
					r.set(field[i], listm.get(i));
				}
				
				if(temp_is_stop) break;
				
				if(r != null) recordList.add(r);
				
			}
			if(!(Db.batchSave("E_SUPPLIER", recordList, recordList.size()).length > 0) ) {
				renderText("4"); return;
			}
			file.getFile().delete();//删除上传的缓存文件
			renderText(msg);
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
	/**
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
	
	/**
	 * 
	 * 导出模板:将需要的模板进行上传到resources--templates--supplier_template.xls(supplier_template.xlsx)
	 * 直接下载模板
	 * @throws IOException 
	 */
	public void export() throws IOException {
		
		String path = Thread.currentThread().getContextClassLoader().getResource("templates/supplier_template.xlsx").getPath();
		
		File file = new File(path);
		
		renderFile(file);
	}
}
	
