package com.xatljxxs.ywjx;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.sohnny.util.dataBase.DataBaseSchema;
import com.sohnny.util.dataBase.OracleSchema;

public class DbTools {
	public static void main(String[] args) {

		DataBaseSchema dbs = new OracleSchema();

		dbs.start();

		List<Record> rList = dbs.getFieldsAttributeByTableName("E_QUOTE");

		System.out.println("字段个数: " + rList.size());

		// createGrid(rList);
		// createJSNewRowData(rList,"popedom");
		createGrid(rList);

	}

	public static void createGrid(List<Record> rList) {
		String colName;
		String colComment;
		for (Record r : rList) {
			// System.out.println(r.get("COLUMN_NAME")+"\t"+r.getStr("COLUMN_COMMENT"));
			colName = r.getStr(DataBaseSchema.col_name);
			colComment = r.getStr(DataBaseSchema.col_comment);
			System.out.println("{ display: '" + colComment + "', name: '" + colName
					+ "', align: 'center', editor: { type: 'text'}, width: '" + 100 / 13 + "%'},");
		}
	}

	public static void createSeletSql(List<Record> rList, String tableTempName) {
		String colName;
		for (Record r : rList) {
			// System.out.println(r.get("COLUMN_NAME")+"\t"+r.getStr("COLUMN_COMMENT"));
			colName = r.getStr(DataBaseSchema.col_name);
			System.out.print(tableTempName + "." + colName + ", ");
		}
	}

	public static void createJSNewRowData(List<Record> rList, String modelName) {
		String colName;
		String colComment;
		for (Record r : rList) {
			// System.out.println(r.get("COLUMN_NAME")+"\t"+r.getStr("COLUMN_COMMENT"));
			colName = r.getStr(DataBaseSchema.col_name);
			colComment = r.getStr(DataBaseSchema.col_comment);
			System.out.println(modelName + "." + colName + " : " + colComment);
		}
	}

}
