package com.sinorail.gysglbj.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseDicRolePopedom<M extends BaseDicRolePopedom<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("ID", id);
	}

	public java.lang.String getId() {
		return get("ID");
	}

	public void setRoleId(java.lang.String roleId) {
		set("ROLE_ID", roleId);
	}

	public java.lang.String getRoleId() {
		return get("ROLE_ID");
	}

	public void setPopedomId(java.lang.String popedomId) {
		set("POPEDOM_ID", popedomId);
	}

	public java.lang.String getPopedomId() {
		return get("POPEDOM_ID");
	}

}
