package com.arjunsk.db.simple.io.file;

import com.arjunsk.db.simple.io.id.TransactionId;
import com.arjunsk.db.simple.io.page.Page;
import com.arjunsk.db.simple.io.page.PageId;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import java.io.IOException;

public interface DBFile {

  Page readPage(PageId id) throws IllegalArgumentException;

  void writePage(Page p) throws IOException;

  DbFileIterator iterator(TransactionId tid);

  int getId();

  TupleDesc getTupleDesc();
}
