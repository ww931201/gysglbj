package com.sinorail.gysglbj.action;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
			int a = 2;
			if(list!=null){
				for (List<Object> fileGysbh : list) { 
					if(lists!=null){
						for (Object object : lists) {
							if(fileGysbh.get(0).equals(object)){
								 temp += fileGysbh.get(0)+",";
							}
							if(temp.length()>0){
								renderJson("result", "第" + a + "行供应商编码'"+object+"'在excel已经存在，请修改后再导入数据"); return; 
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
			List<Supplier> findGysbh = Supplier.dao.findGysbh();
			if(findGysbh!=null){
				for (Supplier supplier : findGysbh) {
					String gysbh = supplier.getGysbh();
					
					if(list!=null){
						for (int l = 0;list.size()>l;l++) { 
							
							if(list.get(l).get(0).equals(gysbh)){
								duipler += gysbh+",";
							}
							if(duipler.length()>0){
								renderJson("result", "第" + (l+2) + "行供应商编码'"+gysbh+"'在数据库已经存在，请修改后再导入数据"); return; 
							}
						}
					}
				}
			}
			
			List<Record> recordList = new LinkedList<Record>();
			
			String[][] fields = {{"GYSBH",".*","供应商编号"}, {"SHXYDM",".*","社会信用代码"}, {"YYZZZCH",".*","营业执照注册号"}, {"QYMC",".*","企业名称"}, {"FDDBR",".*","法定代表人"}, {"FDDBRDH",".*","法定代表人电话"}, {"SSS",".*","所属省"}, {"SSS1",".*","所属市"}, {"ZS",".*","住所"}, {"ZCZB",".*","注册资本"}, {"CLRQ","(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)","成立日期"}, {"YYQX",".*","营业期限"}, {"QYLX",".*","企业类型"}, {"ZZJGDM",".*","组织机构代码"}, {"SWDJH",".*","税务登记号"}, {"YXQ","(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)","有效期"}, {"YWLXR",".*","业务联系人"}, {"LXRSJ",".*","联系人手机"}, {"BGCZ",".*","办公传真"}, {"BGDH",".*","办公电话"}, {"LXRYX",".*","联系人邮箱"}, {"LXRZW",".*","联系人职务"}, {"BGDZ",".*","办公地址"},  {"BLGYSCFZQ",".*","不良供应商处罚周期"}, {"HMD",".*","黑名单"}, {"BLGYSXYPJDJ",".*","供应商信用评价等级"}, {"GYSJYFW",".*","供应商经营范围"}};
			
			for(int n = 0;n<list.size();n++){
				
				Record r = new Record();

				for(int i=0; i<fields.length; i++) {
					
					if(fields[i][0] == "GYSBH" && (list.get(n).get(i) == null || list.get(n).get(i) == "")){
						renderJson("result","第"+(n+2)+"行"+"第"+(i+1)+"列数据"+list.get(n).get(i)+"格式填写错误！请修改后重新填写！"); return;
					}
					if(list.get(n).get(i) != null) {
						boolean flag = Pattern.matches(fields[i][1], list.get(n).get(i).toString());
						if(!flag){
							renderJson("result","第"+(n+2)+"行"+"第"+(i+1)+"列数据"+fields[i][2]+list.get(n).get(i)+"格式填写错误！请修改后重新填写！"); return;
						}
					}
					r.set(fields[i][0], list.get(n).get(i));
				}
				
				if(r != null) recordList.add(r);
			}
			
			if(!(Db.batchSave("E_SUPPLIER", recordList, recordList.size()).length > 0) ) {
				renderJson("result", "导入数据失败，请修改后重试！"); return;
			}
			
			/*File file2 = file.getFile();
			System.out.println(file2.getName()); */

			file.getFile().delete();//删除上传的缓存文件
			
			renderJson("result", "0"); 
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
	
	
	/**
	 * 项目中供应商信息的编号查询供应商信息
	 */
	public void findProjectOverDate() {
		
		List<Supplier> findProjectOverDate = Supplier.dao.findProjectOverDate(getPara("PROJECT_ID"));
		
		/*for (Supplier supplier : findProjectOverDate) {
			
			renderJson(supplier);
			}*/
		renderJson(findProjectOverDate);
		}
	}
	
