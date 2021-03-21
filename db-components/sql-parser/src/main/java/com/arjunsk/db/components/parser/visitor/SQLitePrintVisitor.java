package com.arjunsk.db.components.parser.visitor;

import parser.SQLiteParser.Create_table_stmtContext;
import parser.SQLiteParserBaseVisitor;

public class SQLitePrintVisitor extends SQLiteParserBaseVisitor<Void> {

  @Override
  public Void visitCreate_table_stmt(Create_table_stmtContext ctx) {
    System.out.println("Table Name =" + ctx.table_name().getText());
    return super.visitCreate_table_stmt(ctx);
  }
}
