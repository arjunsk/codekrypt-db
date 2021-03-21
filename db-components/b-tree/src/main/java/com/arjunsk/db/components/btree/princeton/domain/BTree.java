package com.arjunsk.db.components.btree.princeton.domain;

import com.arjunsk.db.components.btree.princeton.domain.support.Entry;
import com.arjunsk.db.components.btree.princeton.domain.support.Node;

public class BTree<K extends Comparable<K>, V> {
  // max children per B-tree node = M-1
  // (must be even and greater than 2)
  private static final int MAX_CHILD_COUNT = 4;

  private Node root; // root of the B-tree
  private int height; // height of the B-tree
  private int keyCount; // number of key-value pairs in the B-tree

  /** Initializes an empty B-tree. */
  public BTree() {
    root = new Node(0);
  }

  /**
   * Returns true if this symbol table is empty.
   *
   * @return {@code true} if this symbol table is empty; {@code false} otherwise
   */
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Returns the number of key-value pairs in this symbol table.
   *
   * @return the number of key-value pairs in this symbol table
   */
  public int size() {
    return keyCount;
  }

  /**
   * Returns the height of this B-tree (for debugging).
   *
   * @return the height of this B-tree
   */
  public int height() {
    return height;
  }

  /**
   * Returns the value associated with the given key.
   *
   * @param key the key
   * @return the value associated with the given key if the key is in the symbol table and {@code
   *     null} if the key is not in the symbol table
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public V get(K key) {
    if (key == null) throw new IllegalArgumentException("argument to get() is null");
    return search(root, key, height);
  }

  private V search(Node node, K key, int currentHeight) {
    Entry[] children = node.children;

    // external node [ie Disk Node]
    if (currentHeight == 0) {
      for (int j = 0; j < node.currentChildCount; j++) {
        if (eq(key, children[j].key)) return (V) children[j].value;
      }
    }

    // internal node
    else {
      for (int j = 0; j < node.currentChildCount; j++) {
        if (j + 1 == node.currentChildCount || less(key, children[j + 1].key))
          return search(children[j].next, key, currentHeight - 1);
      }
    }
    return null;
  }

  /**
   * Inserts the key-value pair into the symbol table, overwriting the old value with the new value
   * if the key is already in the symbol table. If the value is {@code null}, this effectively
   * deletes the key from the symbol table.
   *
   * @param key the key
   * @param value the value
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public void put(K key, V value) {
    if (key == null) throw new IllegalArgumentException("argument key to put() is null");
    Node u = insert(root, key, value, height);
    keyCount++;
    if (u == null) return;

    // need to split root
    Node newRoot = new Node(2);
    newRoot.children[0] = new Entry(root.children[0].key, null, root);
    newRoot.children[1] = new Entry(u.children[0].key, null, u);
    root = newRoot;
    height++;
  }

  private Node insert(Node node, K key, V val, int currentHeight) {
    int j;
    Entry t = new Entry(key, val, null);

    // external node
    if (currentHeight == 0) {
      for (j = 0; j < node.currentChildCount; j++) {
        if (less(key, node.children[j].key)) break;
      }
    }

    // internal node
    else {
      for (j = 0; j < node.currentChildCount; j++) {
        if ((j + 1 == node.currentChildCount) || less(key, node.children[j + 1].key)) {
          Node u = insert(node.children[j++].next, key, val, currentHeight - 1);
          if (u == null) return null;
          t.key = u.children[0].key;
          t.value = null;
          t.next = u;
          break;
        }
      }
    }

    for (int i = node.currentChildCount; i > j; i--) node.children[i] = node.children[i - 1];
    node.children[j] = t;
    node.currentChildCount++;
    if (node.currentChildCount < MAX_CHILD_COUNT) return null;
    else return split(node);
  }

  // split node in half
  private Node split(Node h) {
    Node t = new Node(MAX_CHILD_COUNT / 2);
    h.currentChildCount = MAX_CHILD_COUNT / 2;
    for (int j = 0; j < MAX_CHILD_COUNT / 2; j++)
      t.children[j] = h.children[MAX_CHILD_COUNT / 2 + j];
    return t;
  }

  /**
   * Returns a string representation of this B-tree (for debugging).
   *
   * @return a string representation of this B-tree.
   */
  public String toString() {
    return toString(root, height, "") + "\n";
  }

  private String toString(Node node, int height, String indent) {
    StringBuilder s = new StringBuilder();
    Entry[] children = node.children;

    if (height == 0) {
      for (int j = 0; j < node.currentChildCount; j++) {
        s.append(indent + children[j].key + " " + children[j].value + "\n");
      }
    } else {
      for (int j = 0; j < node.currentChildCount; j++) {
        if (j > 0) s.append(indent + "(" + children[j].key + ")\n");
        s.append(toString(children[j].next, height - 1, indent + "     "));
      }
    }
    return s.toString();
  }

  // comparison functions - make Comparable instead of Key to avoid casts
  private boolean less(Comparable k1, Comparable k2) {
    return k1.compareTo(k2) < 0;
  }

  private boolean eq(Comparable k1, Comparable k2) {
    return k1.compareTo(k2) == 0;
  }
}
