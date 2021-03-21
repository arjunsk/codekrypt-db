package com.arjunsk.db.components.fs;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;

public final class FileUtils {

  public static boolean isSymbolicLink(File file) {
    try {
      File canonicalFile = file.getCanonicalFile();
      File absoluteFile = file.getAbsoluteFile();
      File parentFile = file.getParentFile();
      // a symbolic link has a different name between the canonical and absolute path
      return !canonicalFile.getName().equals(absoluteFile.getName())
          ||
          // or the canonical parent path is not the same as the file's parent path,
          // provided the file has a parent path
          parentFile != null && !parentFile.getCanonicalPath().equals(canonicalFile.getParent());
    } catch (IOException e) {
      // error on the side of caution
      return true;
    }
  }

  public static ImmutableList<File> listFiles(File dir) {
    File[] files = dir.listFiles();
    if (files == null) {
      return ImmutableList.of();
    }
    return ImmutableList.copyOf(files);
  }

  public static boolean deleteDirectoryContents(File directory) {
    checkArgument(directory.isDirectory(), "Not a directory: %s", directory);

    // Don't delete symbolic link directories
    if (isSymbolicLink(directory)) {
      return false;
    }

    boolean success = true;
    for (File file : listFiles(directory)) {
      success = deleteRecursively(file) && success;
    }
    return success;
  }

  private static boolean deleteRecursively(File file) {
    boolean success = true;
    if (file.isDirectory()) {
      success = deleteDirectoryContents(file);
    }

    return file.delete() && success;
  }
}
