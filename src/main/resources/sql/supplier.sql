#供应商信息的sql文件
#namespace("supplier")
	
	### 查询所有的供应商供应商信息
	#sql("supplierList")
		select ID, GYSBH, SHXYDM, YYZZZCH, QYMC, FDDBR, FDDBRDH, SSS, SSS1, ZS, ZCZB, CLRQ, YYQX, QYLX, ZZJGDM, SWDJH, YXQ, YWLXR, LXRSJ, BGCZ, BGDH, LXRYX, LXRZW, BGDZ, ZZZS, BLGYSCFZQ, HMD, BLGYSXYPJDJ, GYSJYFW, ENTRY_TIME
		from E_SUPPLIER
	#end

	
	
	### 根据供应商编号查询供应商
	#sql("findByGysbh")
		select * from E_SUPPLIER where GYSBH = ?
	#end
	
	### 根据供应商Id查询供应商
	#sql("selectById")
		select * from E_SUPPLIER where Id = ?
	#end
	
	### 根据供应商ID和供应商编号查询
	
	#sql("findByGysbhAndId")
		select * from E_SUPPLIER where GYSBH = ? and Id != ?
	#end
	
#end
