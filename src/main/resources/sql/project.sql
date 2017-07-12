#namespace("project")
	
	### 查询所有的项目信息
	#sql("paginateList")
		select * from E_PROJECT where 1=1
		#if(NAME ??)
			and NAME like '%#(NAME)%'
		#end
		#if(NO ??)
			and no like '%#(NO)%'
		#end
		ORDER BY ENTRY_TIME DESC
	#end
	
	### 是否重复
	#sql("isDuplicated")
		select * from E_PROJECT where no = ?
	#end

#end