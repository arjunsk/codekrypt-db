package com.arjunsk.db.components.fs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.Set;

public class SampleFileSystem extends FileSystem {

  private final FileSystemProvider fileSystemProvider;

  public SampleFileSystem(FileSystemProvider fileSystemProvider) {
    this.fileSystemProvider = fileSystemProvider;
  }

  @Override
  public FileSystemProvider provider() {
    return fileSystemProvider;
  }

  @Override
  public void close() throws IOException {}

  @Override
  public boolean isOpen() {
    return true;
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }

  @Override
  public String getSeparator() {
    return "/";
  }

  @Override
  public Iterable<Path> getRootDirectories() {
    return Collections.<Path>singleton(new SamplePath(this, new byte[] {'/'}));
  }

  @Override
  public Iterable<FileStore> getFileStores() {
    return null;
  }

  @Override
  public Set<String> supportedFileAttributeViews() {
    return null;
  }

  @Override
  public Path getPath(String first, String... more) {

    String path;
    if (more.length == 0) {
      path = first;
    } else {
      StringBuilder sb = new StringBuilder();
      sb.append(first);
      for (String segment : more) {
        if (segment.length() > 0) {
          if (sb.length() > 0) {
            sb.append('/');
          }
          sb.append(segment);
        }
      }
      path = sb.toString();
    }
    return new SamplePath(this, path.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public PathMatcher getPathMatcher(String syntaxAndPattern) {
    return null;
  }

  @Override
  public UserPrincipalLookupService getUserPrincipalLookupService() {
    throw new UnsupportedOperationException();
  }

  @Override
  public WatchService newWatchService() throws IOException {
    throw new UnsupportedOperationException();
  }

  // TODO: To understand and complete
  public <A extends BasicFileAttributes> A readAttributes(
      Path path, Class<A> clazz, LinkOption... options) throws IOException {
    if (clazz != BasicFileAttributes.class) {
      throw new UnsupportedOperationException();
    }
    Path absolute = path.toAbsolutePath();
    Path parent = absolute.getParent();
    String type = "";
    long size = 0;

    return (A) new SampleFileAttributes(type, size);
  }
}
