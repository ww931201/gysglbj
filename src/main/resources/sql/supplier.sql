#供应商信息的sql文件
#namespace("supplier")
	
	### 查询所有的供应商供应商信息
	#sql("supplierList")
		select *
		from E_SUPPLIER
		where 1 = 1
		#if(GYSBH ??)
			and upper(GYSBH) like upper('%#(GYSBH)%')
		#end
		#if(QYMC ??)
			and QYMC like '%#(QYMC)%'
		#end
		#if(HMD ??)
			and HMD like '%#(HMD)%'
		#end
		#if(GYSJYFW ??)
			and GYSJYFW like '%#(GYSJYFW)%'
		#end
		#if(CODE ??)
			and ID in(select SUPPLIER_ID from E_CERTIFICATE where ID in (SELECT CERID FROM E_CERTIFICATE_SUPCODE where CODE = #(CODE)))
		#end
		#if(ZSLB ??)
			and ID in((SELECT SUPPLIER_ID FROM E_CERTIFICATE where ZSLB like '#(ZSLB)'))
		#end
		#if(ZSXX ??)
			and ID in((SELECT SUPPLIER_ID FROM E_CERTIFICATE where ZSXX like '%#(ZSXX)%'))
		#end
		
		ORDER BY ENTRY_TIME DESC, ID
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
	
	#查找资质过期的供应商
	#sql("findOverDate")
		select gysbh, qymc,E_SUPPLIER.ID from E_SUPPLIER where id in(select SUPPLIER_ID from E_CERTIFICATE where END_TIME < to_char(SYSDATE, 'yyyy-mm-dd'))
		UNION
		select gysbh, qymc,E_SUPPLIER.ID from E_SUPPLIER where  YXQ  < to_char(SYSDATE, 'yyyy-mm-dd')
		
	#end
	
	#根据项目中编码查找过期供应商
	
	#sql("findProjectOverDate")
		select gysbh, qymc from E_SUPPLIER where id in(select SUPPLIER_ID from E_CERTIFICATE where END_TIME < to_char(SYSDATE+365, 'yyyy-mm-dd')) and id IN(select supplier_id from E_QUOTE where PROJECT_ID  = (select id from E_PROJECT where id = ?))
		UNION
		select gysbh, qymc from E_SUPPLIER where  YXQ  < to_char(SYSDATE+365, 'yyyy-mm-dd')  and id IN(select supplier_id from E_QUOTE where PROJECT_ID  = (select id from E_PROJECT where id = ?))
	#end
	
#end

#namespace("certificate")
	#sql("findBySupplierId")
		select * from E_CERTIFICATE where SUPPLIER_ID = #para(0)
	#end
#end

