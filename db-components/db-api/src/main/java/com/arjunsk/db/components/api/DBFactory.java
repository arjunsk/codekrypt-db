package com.arjunsk.db.components.api;

import com.arjunsk.db.components.api.domain.Options;
import java.io.File;
import java.io.IOException;

public interface DBFactory {
  DB open(File path, Options options) throws IOException;

  void destroy(File path, Options options) throws IOException;

  void repair(File path, Options options) throws IOException;
}
