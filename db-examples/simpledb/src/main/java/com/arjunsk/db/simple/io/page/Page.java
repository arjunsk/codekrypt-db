package com.arjunsk.db.simple.io.page;

public interface Page {

  PageId getId();

  byte[] getPageData();
}
