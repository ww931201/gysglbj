package com.sinorail.gysglbj.model;



import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sinorail.gysglbj.model.base.BaseSupplier;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Supplier extends BaseSupplier<Supplier> {
	public static final Supplier dao = new Supplier().dao();
	
	public Page<Supplier> findPaginate(Integer pageNumber, Integer pageSize, Supplier supplier) {
		
		SqlPara sqlp = getSqlPara("supplier.supplierList",supplier);
		
		return paginate(pageNumber, pageSize, sqlp);
	}
	
	/**
	 * 根据供应商编号查询供应商是否存在(添加)
	 * @param gysBh
	 * @return
	 */
	public boolean isExistGysbh(String gysBh) {
		Supplier supplier = findFirst(getSql("supplier.findByGysbh"),gysBh); 
		if(supplier == null){
			return false;
		}
		return true;
	}
	
	/**
	 * 根据供应商编号和id查供应商是否存在(修改)
	 * @param gysBh
	 * @param userId
	 * @return
	 */
	public boolean isExistGysbh(String gysBh, String SupId) {
		Supplier supplier = findFirst(getSql("supplier.findByGysbhAndId"), gysBh, SupId);
		if(supplier == null){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 
	 * @param SupId
	 * @return
	 */
	public Object queryById(String SupId) {
		return findFirst(getSql("supplier.selectById"), SupId);
	}
	

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(String ids){
		
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			deleteById(id);
		}
		return true;
	}
}
