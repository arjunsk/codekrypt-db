package com.arjunsk.db.components.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class SampleDriver implements Driver {

  private static final String START_URL = "jdbc:h2:";

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    if (url.startsWith(START_URL)) {
      return new SampleConnection(url, info, null, null);
    }
    return null;
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    return false;
  }

  @Override
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
    return new DriverPropertyInfo[0];
  }

  @Override
  public int getMajorVersion() {
    return 0;
  }

  @Override
  public int getMinorVersion() {
    return 0;
  }

  @Override
  public boolean jdbcCompliant() {
    return true;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }
}
