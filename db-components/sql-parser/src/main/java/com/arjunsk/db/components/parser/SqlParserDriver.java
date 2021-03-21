package com.arjunsk.db.components.parser;

import com.arjunsk.db.components.parser.visitor.SQLitePrintVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.SQLiteLexer;
import parser.SQLiteParser;

public class SqlParserDriver {

  public static void main(String[] args) {
    String sql = "create table contacts(name TEXT, number TEXT);";

    SQLiteLexer lexer = new SQLiteLexer(CharStreams.fromString(sql));

    SQLiteParser parser = new SQLiteParser(new CommonTokenStream(lexer));

    // To know the root node, check the SQLiteParser.g4 and see the first entry.
    ParseTree tree = parser.parse();
    tree.accept(new SQLitePrintVisitor());
  }
}
