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
		SELECT a.* FROM VIEW_QUOTE a WHERE EXISTS (SELECT count(*) from VIEW_QUOTE WHERE a.WZBM = WZBM AND zxj_bhs < a.zxj_bhs HAVING count(*) < 3)
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
	    ORDER BY a.bjh, a.WZBM, a.ZXJ_BHS
	#end

#end