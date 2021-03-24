package com.arjunsk.db.components.api.exceptions;

public class DBException extends RuntimeException {

  public DBException() {
    super("DB Exception");
  }

  public DBException(String message) {
    super(message);
  }

  public DBException(String message, Throwable cause) {
    super(message, cause);
  }
}
