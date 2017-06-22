package com.sinorail.gysglbj.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseDicRole<M extends BaseDicRole<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("ID", id);
	}

	public java.lang.String getId() {
		return get("ID");
	}

	public void setRoleName(java.lang.String roleName) {
		set("ROLE_NAME", roleName);
	}

	public java.lang.String getRoleName() {
		return get("ROLE_NAME");
	}

	public void setXh(java.math.BigDecimal xh) {
		set("XH", xh);
	}

	public java.math.BigDecimal getXh() {
		return get("XH");
	}

	public void setRemark(java.lang.String remark) {
		set("REMARK", remark);
	}

	public java.lang.String getRemark() {
		return get("REMARK");
	}

	public void setField1(java.math.BigDecimal field1) {
		set("FIELD1", field1);
	}

	public java.math.BigDecimal getField1() {
		return get("FIELD1");
	}

	public void setField2(java.lang.String field2) {
		set("FIELD2", field2);
	}

	public java.lang.String getField2() {
		return get("FIELD2");
	}

	public void setParentId(java.lang.String parentId) {
		set("PARENT_ID", parentId);
	}

	public java.lang.String getParentId() {
		return get("PARENT_ID");
	}

}
