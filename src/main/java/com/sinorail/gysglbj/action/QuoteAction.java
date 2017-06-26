package com.sinorail.gysglbj.action;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sinorail.gysglbj.extend.QuiController;
import com.sinorail.gysglbj.model.Quote;

public class QuoteAction extends QuiController {
	
	public void list() {
		SqlPara sqp = Db.getSqlPara("quote.paginateList", getModel(Quote.class));
		renderJson(Db.paginate(pageNumber(), pageSize(), sqp));
	}
	
}
