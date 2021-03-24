package com.arjunsk.db.simple.io.page.impl.heap;

import com.arjunsk.db.simple.db.BufferPool;
import com.arjunsk.db.simple.db.Database;
import com.arjunsk.db.simple.io.page.Page;
import com.arjunsk.db.simple.io.page.PageId;
import com.arjunsk.db.simple.io.record.RecordId;
import com.arjunsk.db.simple.io.tuple.Tuple;
import com.arjunsk.db.simple.io.tuple.desc.TupleDesc;
import com.arjunsk.db.simple.io.tuple.fileds.Field;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HeapPage implements Page {

  private final HeapPageId heapPageId;
  private final int numSlots;
  private final byte[] header;

  private TupleDesc tupleDesc;
  private Tuple[] tuples;

  public HeapPage(HeapPageId id, byte[] data) {
    this.heapPageId = id;
    this.tupleDesc = Database.getCatalog().getTupleDesc(id.getTableId());
    this.numSlots = getNumTuples();
    this.header = new byte[getHeaderSize()];

    try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data))) {
      // Header
      for (int i = 0; i < header.length; i++) header[i] = dis.readByte();

      // Data
      tuples = new Tuple[numSlots];
      for (int i = 0; i < tuples.length; i++) tuples[i] = readNextTuple(dis, i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public PageId getId() {
    return null;
  }

  @Override
  public byte[] getPageData() {
    return new byte[0];
  }

  private int getHeaderSize() {
    return (int) Math.ceil(getNumTuples() / 8.0);
  }

  private int getNumTuples() {
    if (numSlots != 0) {
      return numSlots;
    }
    return (BufferPool.PAGE_SIZE * 8) / (tupleDesc.getSize() * 8 + 1);
  }

  public int getNumEmptySlots() {
    int emptySlots = 0;
    for (int i = 0; i < getNumTuples(); i++) {
      if (!isSlotUsed(i)) {
        emptySlots++;
      }
    }
    return emptySlots;
  }

  private Tuple readNextTuple(DataInputStream dis, int slotId) throws NoSuchElementException {

    if (!isSlotUsed(slotId)) {
      for (int i = 0; i < tupleDesc.getSize(); i++) {
        try {
          dis.readByte();
        } catch (IOException e) {
          throw new NoSuchElementException("error reading empty tuple");
        }
      }
      return null;
    }

    // read fields in the tuple
    Tuple tuple = new Tuple(tupleDesc);
    RecordId recordId = new RecordId(heapPageId, slotId);
    tuple.setRecordId(recordId);
    try {
      for (int fieldIndex = 0; fieldIndex < tupleDesc.numFields(); fieldIndex++) {
        Field fieldValue = tupleDesc.getFieldType(fieldIndex).parse(dis);
        tuple.setField(fieldIndex, fieldValue);
      }
    } catch (ParseException e) {
      e.printStackTrace();
      throw new NoSuchElementException("parsing error!");
    }

    return tuple;
  }

  public boolean isSlotUsed(int i) {
    int byteNum = i / 8;
    int posInByte = i % 8;
    return isOne(header[byteNum], posInByte);
  }

  private boolean isOne(byte target, int posInByte) {
    return (byte) (target << (7 - posInByte)) < 0;
  }

  public Iterator<Tuple> iterator() {
    // some code goes here
    return new UsedTupleIterator();
  }

  private class UsedTupleIterator implements Iterator<Tuple> {

    private final int usedTuplesNum = getNumTuples() - getNumEmptySlots();
    private int pos = 0;
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < getNumTuples() && pos < usedTuplesNum;
    }

    @Override
    public Tuple next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      while (!isSlotUsed(index)) index++;
      pos++;
      return tuples[index++];
    }
  }
}
