package com.arjunsk.db.simple;

import com.arjunsk.db.simple.db.Database;
import com.arjunsk.db.simple.db.iterator.impl.SeqScan;
import com.arjunsk.db.simple.io.file.impl.heap.HeapFile;
import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import com.arjunsk.db.simple.io.tuple.fileds.Type;
import java.io.File;

public class SimpleDbDriver {

  public static void main(String[] args) {

    Type[] types = new Type[] {Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE};
    String[] names = new String[] {"field0", "field1", "field2"};
    TupleDesc descriptor = new TupleDesc(types, names);

    HeapFile testTable = new HeapFile(new File("some_data_file.data"), descriptor);

    Database.getCatalog().addTable(testTable, "test");

    TransactionId transactionId = new TransactionId();
    SeqScan fileIterator = new SeqScan(transactionId, testTable.getId());

    fileIterator.open();
    while (fileIterator.hasNext()) {
      Tuple tuple = fileIterator.next();
      System.out.println(tuple);
    }

    fileIterator.close();
    Database.getBufferPool().transactionComplete(transactionId);
  }
}
