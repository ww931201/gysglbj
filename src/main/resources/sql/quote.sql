#namespace("quote")
	
	### 查询所有的项目信息
	#sql("paginateList")
		select * from VIEW_QUOTE WHERE 1=1
		#if(WZBM ??)
			and WZBM like '%#(WZBM)%'
		#end
		#if(WZMC ??)
			and WZMC like '%#(WZMC)%'
		#end
		#if(GGXH ??)
			and GGXH like '%#(GGXH)%'
		#end
		#if(PROJECT_ID ??)
			and PROJECT_ID = '#(PROJECT_ID)'
		#end
		#if(SYDWJDQ ??)
			and SYDWJDQ like '%#(SYDWJDQ)%'
		#end
		ORDER BY GYSBH, WZBM, ENTRY_TIME DESC
	#end
	### 查询所有的项目信息
	#sql("filterList")
		select q.BJH, q.WZBM, q.WZMC, q.GGXH, q.JSYQ, q.JLDW, q.YCSL, q.DJXJ_BHS, min(q.ZXJ_BHS), q.SYDWJDQ, q.CSBDJ_BHS, q.CSBZXJ_BHS, q.ID, q.ENTRY_TIME, q.SUPPLIER_ID, q.PROJECT_ID, q.QYMC, q.GYSBH from VIEW_QUOTE q WHERE 1=1
		#if(WZBM ??)
			and WZBM like '%#(WZBM)%'
		#end
		#if(WZMC ??)
			and WZMC like '%#(WZMC)%'
		#end
		#if(GGXH ??)
			and GGXH like '%#(GGXH)%'
		#end
		#if(PROJECT_ID ??)
			and PROJECT_ID = '#(PROJECT_ID)'
		#end
		#if(SYDWJDQ ??)
			and SYDWJDQ like '%#(SYDWJDQ)%'
		#end
		group by q.WZBM, q.GYSBH, q.BJH, q.WZMC, q.GGXH, q.JSYQ, q.JLDW, q.YCSL, q.DJXJ_BHS, q.ZXJ_BHS, q.SYDWJDQ, q.CSBDJ_BHS, q.CSBZXJ_BHS, q.ID, q.ENTRY_TIME, q.SUPPLIER_ID, q.PROJECT_ID, q.QYMC
		ORDER BY WZBM DESC
	#end

#end