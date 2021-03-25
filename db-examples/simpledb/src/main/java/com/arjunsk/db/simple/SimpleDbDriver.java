package com.arjunsk.db.simple;

import com.arjunsk.db.simple.db.Database;
import com.arjunsk.db.simple.io.file.impl.heap.HeapFile;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import com.arjunsk.db.simple.io.fileds.Type;
import com.arjunsk.db.simple.operator.SeqScan;
import java.io.File;

public class SimpleDbDriver {

  public static void main(String[] args) {

    Type[] types = new Type[] {Type.INT_TYPE, Type.STRING_TYPE};
    String[] names = new String[] {"id", "name"};
    TupleDesc descriptor = new TupleDesc(types, names);

    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    File file = new File(loader.getResource("authors.dat").getFile());
    HeapFile testTable = new HeapFile(file, descriptor);

    Database.getCatalog().addTable(testTable);

    TransactionId transactionId = new TransactionId();
    SeqScan fileIterator = new SeqScan(transactionId, testTable.getId());

    fileIterator.open();
    while (fileIterator.hasNext()) {
      Tuple tuple = fileIterator.next();
      System.out.print(tuple);
    }

    fileIterator.close();
    Database.getBufferPool().transactionComplete(transactionId);
  }
}
