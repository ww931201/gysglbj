package com.sinorail.gysglbj.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sinorail.gysglbj.model.base.BaseDicUser;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class DicUser extends BaseDicUser<DicUser> {
	public static final DicUser dao = new DicUser().dao();
	
	/**
	 * 登录判断
	 * @param user
	 * @return
	 */
	public DicUser queryIsPassed(DicUser user) {
		return findFirst(getSql("user.isPassed"), user.getUserCode(), user.getPasswd());
	}

	public Page<DicUser> queryZUserList(Integer pageNumber, Integer pageSize, String sort, DicUser user) {
		return paginate(pageNumber, pageSize, getSql("user.paginateSelect"),  getSql("user.paginateByOffcieId")+sort, user.getId());
	}

	public DicUser queryIsExistByLoginId(String loginId) {
		return findFirst(getSql("user.isExist"), loginId);
	}

	/**
	 * 分页查询全部用户
	 */
	public Page<DicUser> paginateList(Integer pageNumber, Integer pageSize, DicUser user) {
		SqlPara sqlp = getSqlPara("user.paginateSelect", user);
		return paginate(pageNumber, pageSize, sqlp);
	}
	/**
	 * 分页查询子部用户
	 */
	public Page<DicUser> paginateChildList(Integer pageNumber, Integer pageSize, DicUser user) {
		SqlPara sqlp = getSqlPara("user.paginateSelectChild", user);
		return paginate(pageNumber, pageSize, sqlp);
	}
	
	public List<DicRole> findRoleAll() {
		return DicRole.dao.find(getSql("user.roleAll"));
	}
	public List<DicRole> findRoleById(String roleId) {
		return DicRole.dao.find(getSql("user.roleListByParentId"), roleId, roleId);
	}

	public Object queryById(String userId) {
		return findFirst(getSql("user.selectById"), userId);
	}

	public boolean deleteBatchByIds(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			deleteById(id);
		}
		return true;
		//return Db.update("delete from DIC_USER where ID IN ("+ids+")");
	}

	/**
	 * 根据用户代码查询用户是否存在(添加)
	 * @param userCode
	 * @return
	 */
	public boolean isExistUserCode(String userCode) {
		DicUser user = findFirst(getSql("user.findByUserCode"), userCode);
		if(user == null){
			return false;
		}
		return true;
	}

	/**
	 * 根据用户代码和用户id查询用户是否存在(修改)
	 * @param userCode
	 * @param userId
	 * @return
	 */
	public boolean isExistUserCode(String userCode, String userId) {
		DicUser user = findFirst(getSql("user.findByUserCodeAndId"), userCode, userId);
		if(user == null){
			return false;
		}
		return true;
	}

	
}
