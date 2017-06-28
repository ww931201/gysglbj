#供应商信息的sql文件
#namespace("supplier")
	
	### 查询所有的供应商供应商信息
	#sql("supplierList")
		select ID, GYSBH, SHXYDM, YYZZZCH, QYMC, FDDBR, FDDBRDH, SSS, SSS1, ZS, ZCZB, CLRQ, YYQX, QYLX, ZZJGDM, SWDJH, YXQ, YWLXR, LXRSJ, BGCZ, BGDH, LXRYX, LXRZW, BGDZ, ZZZS, BLGYSCFZQ, HMD, BLGYSXYPJDJ, GYSJYFW, ENTRY_TIME
		from E_SUPPLIER
		where 1 = 1
		#if(GYSBH ??)
			and GYSBH like '%#(GYSBH)%'
		#end
		#if(QYMC ??)
			and QYMC like '%#(QYMC)%'
		#end
		#if(HMD ??)
			and HMD like '%#(HMD)%'
		#end
		ORDER BY ENTRY_TIME DESC
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
	
	### 根据供应商ID和证书ID查询证书信息
	
	#sql("findByGysIdAndCerId")
		select ecer.no,ecer.name,ecer.content,ecer.start_time,ecer.end_time,ecer.unit from E_SUPPLIER esup,E_CERTIFICATE ecer where esup.ID = ecer.SUPPLIER_ID and esup.ID = #para(0)
	#end
	
#end
#namespace("certificate")
	#sql("findBySupplierId")
		select * from E_CERTIFICATE where SUPPLIER_ID = #para(0)
	#end
#end

