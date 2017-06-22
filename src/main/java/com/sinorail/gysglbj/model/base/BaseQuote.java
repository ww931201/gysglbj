package com.sinorail.gysglbj.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseQuote<M extends BaseQuote<M>> extends Model<M> implements IBean {

	public void setBjh(java.lang.String bjh) {
		set("BJH", bjh);
	}

	public java.lang.String getBjh() {
		return get("BJH");
	}

	public void setWzbm(java.lang.String wzbm) {
		set("WZBM", wzbm);
	}

	public java.lang.String getWzbm() {
		return get("WZBM");
	}

	public void setWzmc(java.lang.String wzmc) {
		set("WZMC", wzmc);
	}

	public java.lang.String getWzmc() {
		return get("WZMC");
	}

	public void setGgxh(java.lang.String ggxh) {
		set("GGXH", ggxh);
	}

	public java.lang.String getGgxh() {
		return get("GGXH");
	}

	public void setJsyq(java.lang.String jsyq) {
		set("JSYQ", jsyq);
	}

	public java.lang.String getJsyq() {
		return get("JSYQ");
	}

	public void setJldw(java.lang.String jldw) {
		set("JLDW", jldw);
	}

	public java.lang.String getJldw() {
		return get("JLDW");
	}

	public void setYcsl(java.lang.String ycsl) {
		set("YCSL", ycsl);
	}

	public java.lang.String getYcsl() {
		return get("YCSL");
	}

	public void setDjxjBhs(java.lang.String djxjBhs) {
		set("DJXJ_BHS", djxjBhs);
	}

	public java.lang.String getDjxjBhs() {
		return get("DJXJ_BHS");
	}

	public void setZxjBhs(java.lang.String zxjBhs) {
		set("ZXJ_BHS", zxjBhs);
	}

	public java.lang.String getZxjBhs() {
		return get("ZXJ_BHS");
	}

	public void setSydwjdq(java.lang.String sydwjdq) {
		set("SYDWJDQ", sydwjdq);
	}

	public java.lang.String getSydwjdq() {
		return get("SYDWJDQ");
	}

	public void setCsbdjBhs(java.lang.String csbdjBhs) {
		set("CSBDJ_BHS", csbdjBhs);
	}

	public java.lang.String getCsbdjBhs() {
		return get("CSBDJ_BHS");
	}

	public void setCsbzxjBhs(java.lang.String csbzxjBhs) {
		set("CSBZXJ_BHS", csbzxjBhs);
	}

	public java.lang.String getCsbzxjBhs() {
		return get("CSBZXJ_BHS");
	}

	public void setId(java.lang.String id) {
		set("ID", id);
	}

	public java.lang.String getId() {
		return get("ID");
	}

	public void setEntryTime(java.util.Date entryTime) {
		set("ENTRY_TIME", entryTime);
	}

	public java.util.Date getEntryTime() {
		return get("ENTRY_TIME");
	}

	public void setSupplierId(java.lang.String supplierId) {
		set("SUPPLIER_ID", supplierId);
	}

	public java.lang.String getSupplierId() {
		return get("SUPPLIER_ID");
	}

	public void setProjectId(java.lang.String projectId) {
		set("PROJECT_ID", projectId);
	}

	public java.lang.String getProjectId() {
		return get("PROJECT_ID");
	}

}
