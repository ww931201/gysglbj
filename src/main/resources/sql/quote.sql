#namespace("quote")
	
	### 查询所有的项目信息
	#sql("paginateList")
		select * from E_QUOTE WHERE 1=1
		#if(WZBM ??)
			and WZBM like '%#(WZBM)%'
		#end
		#if(WZMC ??)
			and WZMC like '%#(WZMC)%'
		#end
		#if(GGXH ??)
			and GGXH like '%#(GGXH)%'
		#end
		#if(SYDWJDQ ??)
			and SYDWJDQ like '%#(SYDWJDQ)%'
		#end
		ORDER BY WZBM, ENTRY_TIME DESC
	#end

#end