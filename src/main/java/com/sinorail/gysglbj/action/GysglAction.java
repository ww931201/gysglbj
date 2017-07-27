package com.sinorail.gysglbj.action;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Certificate;
import com.sinorail.gysglbj.model.Quote;
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
		
			//String msg  = "0"; // renderText 参数不能为汉字
			
			UploadFile file = getFile("excel");
			
			List<List<Object>> list = null;
			
			String[] field = {"GYSBH", "SHXYDM", "YYZZZCH", "QYMC", "FDDBR", "FDDBRDH", "SSS", "SSS1", "ZS", "ZCZB", "CLRQ", "YYQX", "QYLX", "ZZJGDM", "SWDJH", "YXQ", "YWLXR", "LXRSJ", "BGCZ", "BGDH", "LXRYX", "LXRZW", "BGDZ", "BLGYSCFZQ", "HMD", "BLGYSXYPJDJ", "GYSJYFW" };
			try {
				list = ExcelUtils.readExcel(file.getFile(), 1,field.length);
				
			} catch (IOException e1) {
				e1.printStackTrace();
				renderJson("result", "上传失败！"); return;
				
			}
			
			//EXCEL文件供应商编码重复
			String temp ="";
			List<Object> lists = new ArrayList<Object>();
			int a = 1;
			if(list!=null){
				for (List<Object> fileGysbh : list) { 
					if(lists!=null){
						for (Object object : lists) {
							if(fileGysbh.get(0).equals(object)){
								 temp += fileGysbh.get(0)+",";
							}
							if(temp.length()>0){
								renderJson("result", "第" + a + "行供应商编码在excel已经存在，请修改后再导入数据"); return; 
							}
							
						}
					}
					lists.add(fileGysbh.get(0));
					a++;
				}
			}
			/*if(temp.length()>0){
				renderText("5"); return;
			}*/
			
			//数据库已经存在此供应商编码
			String duipler = "";
			int b = 1;
			List<Supplier> findGysbh = Supplier.dao.findGysbh();
			if(findGysbh!=null){
				for (Supplier supplier : findGysbh) {
					String gysbh = supplier.getGysbh();
					if(list!=null){
						for (List<Object> listm : list) { 
							if(listm.get(0).equals(gysbh)){
								duipler += gysbh+",";
							}
							
							if(duipler.length()>0){
								renderJson("result", "第" + b + "行供应商编码在数据库已经存在，请修改后再导入数据"); return; 
							}
						}
					}
					b++;
				}
			}
			
			
			List<Record> recordList = new LinkedList<Record>();
			
			boolean temp_is_stop = false;
			
			for (List<Object> listm : list) { 
				
				Record r = new Record();

				for(int i=0; i<field.length; i++) {
						
						if(i == 10){
						
						Object obj = listm.get(i);
						
						String result = obj.toString();
						String pattern  = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
						
						Pattern p=Pattern.compile(pattern);
						Matcher m=p.matcher(result);
						
						if(m.matches()){
							System.out.println("匹配");
						
						}else{ 
							renderJson("result", "第11列'成立日期'日期格式填写错误！请修改后重新填写！"); return;
						}
					}
						
						if(i == 15){
							Object obj2 = listm.get(i);
							String result2 = obj2.toString();
							String pattern2  = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
							
							Pattern p2=Pattern.compile(pattern2);
							Matcher m2=p2.matcher(result2);
							
							if(m2.matches()){
								System.out.println("匹配");
							
							}else{
								renderJson("result", "第16列'有效期'日期格式填写错误！请修改后重新填写！"); return;
							}
						}
						
					r.set(field[i], listm.get(i));
				}
				
				if(temp_is_stop) break;
				
				if(r != null) recordList.add(r);
				
			}
			if(!(Db.batchSave("E_SUPPLIER", recordList, recordList.size()).length > 0) ) {
				/*renderText("4"); return;*/
				renderJson("result", "导入数据失败，请修改后重试！"); return;
			}
			file.getFile().delete();//删除上传的缓存文件
			
			renderJson("result", 0); 
			
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
		
		if(Quote.dao.queryByCerId(getPara("id")).size()>0){
			setAttr("status",2);
			log.info("****删除供应商记录ID在报价表存在了="+ getPara("id"));
		}else{
			if(Supplier.dao.deleteById(getPara("id"))){
				setAttr("status", 1);
				log.info("****删除供应商记录ID="+ getPara("id"));
			}
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
		}else{
			setAttr("status",2);
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
	
	/**
	 * 查找资质过期的供应商
	 */
	public void findOverDate() {
		renderJson(Db.find(Db.getSql("supplier.findOverDate")));
	}
}
	
