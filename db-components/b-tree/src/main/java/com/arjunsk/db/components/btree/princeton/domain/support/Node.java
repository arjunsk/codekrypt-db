package com.arjunsk.db.components.btree.princeton.domain.support;

// helper B-tree node data type
public class Node {

  private static final int MAX_CHILD_COUNT = 4;

  public int currentChildCount; // number of children
  public Entry[] children = new Entry[MAX_CHILD_COUNT]; // the array of children

  // create a node with childrenCount
  public Node(int currentChildCount) {
    this.currentChildCount = currentChildCount;
  }
}
