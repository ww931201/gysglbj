package com.sinorail.gysglbj.action;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Project;

public class ProjectAction extends QuiController {

	public void index() {
		render("view.html");
	}
	public void addView() {
		render("save.html");
	}
	public void updateView() {
		setAttr("project", Project.dao.findById(getPara("id")));
		render("save.html");
	}
	public void detailView() {
		setAttr("project", Project.dao.findById(getPara("id")));
		render("detail.html");
	}
	
	public void list() {
		SqlPara sqp = Db.getSqlPara("project.paginateList", getModel(Project.class));
		renderJson(Db.paginate(pageNumber(), pageSize(), sqp));
	}
	
	public void save() {
		boolean status = false;
		Project project = getModel(Project.class);
		if (project.getId() == null) {
			project.remove("ID");
			project.setCreaterId(getSessionUser().getId());
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
		if(Project.dao.deleteById(getPara("id"))) {
			setAttr("status", 1);
		}
		renderJson();
	}
	
}
