package com.arjunsk.db.components.fs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

// TODO: This is the core which requires more implementation.
public class SamplePath implements Path {

  private final SampleFileSystem fileSystem;
  private final byte[] path;

  public SamplePath(SampleFileSystem fileSystem, byte[] path) {
    this.fileSystem = fileSystem;
    this.path = path;
  }

  @Override
  public SampleFileSystem getFileSystem() {
    return new SampleFileSystem(null);
  }

  @Override
  public boolean isAbsolute() {
    return (path.length > 0) && (path[0] == '/');
  }

  @Override
  public Path getRoot() {
    if (isAbsolute()) {
      return new SamplePath(fileSystem, new byte[] {this.path[0]});
    }
    return null;
  }

  @Override
  public Path getFileName() {
    return null;
  }

  @Override
  public Path getParent() {
    return null;
  }

  @Override
  public int getNameCount() {
    return 0;
  }

  @Override
  public Path getName(int index) {
    return null;
  }

  @Override
  public Path subpath(int beginIndex, int endIndex) {
    return null;
  }

  @Override
  public boolean startsWith(Path other) {
    return false;
  }

  @Override
  public boolean startsWith(String other) {
    return false;
  }

  @Override
  public boolean endsWith(Path other) {
    return false;
  }

  @Override
  public boolean endsWith(String other) {
    return false;
  }

  @Override
  public Path normalize() {
    return null;
  }

  @Override
  public Path resolve(Path other) {
    return null;
  }

  @Override
  public Path resolve(String other) {
    return null;
  }

  @Override
  public Path resolveSibling(Path other) {
    return null;
  }

  @Override
  public Path resolveSibling(String other) {
    return null;
  }

  @Override
  public Path relativize(Path other) {
    return null;
  }

  @Override
  public URI toUri() {
    return null;
  }

  @Override
  public Path toAbsolutePath() {
    return null;
  }

  @Override
  public Path toRealPath(LinkOption... options) throws IOException {
    return null;
  }

  @Override
  public File toFile() {
    return null;
  }

  @Override
  public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers)
      throws IOException {
    return null;
  }

  @Override
  public WatchKey register(WatchService watcher, Kind<?>... events) throws IOException {
    return null;
  }

  @Override
  public Iterator<Path> iterator() {
    return null;
  }

  @Override
  public int compareTo(Path other) {
    return 0;
  }

  @Override
  public boolean equals(Object other) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return null;
  }
}
