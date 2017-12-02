package com.ifengxue.todolist.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_task_comment")
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskComment extends AbstractEntity<Long> {

  private static final long serialVersionUID = -8189632378757684394L;
  @Column(name = "task_id", nullable = false)
  private Long taskId;
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(nullable = false, columnDefinition = "MEDIUMTEXT NOT NULL")
  private Clob content;

  public static TaskComment from(Long taskId, InputStream inputStream, long contentLength) {
    TaskComment taskComment = new TaskComment();
    taskComment.setTaskId(taskId);
    taskComment.setContent(new SimpleNClob(inputStream, contentLength));
    return taskComment;
  }

  public static class SimpleNClob implements NClob {

    private final InputStream inputStream;
    private final long contentLength;

    public SimpleNClob(InputStream inputStream, long contentLength) {
      this.inputStream = inputStream;
      this.contentLength = contentLength;
    }

    @Override
    public long length() throws SQLException {
      return contentLength;
    }

    @Override
    public String getSubString(long pos, int length) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
      return new InputStreamReader(inputStream);
    }

    @Override
    public InputStream getAsciiStream() throws SQLException {
      return inputStream;
    }

    @Override
    public long position(String searchstr, long start) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public long position(Clob searchstr, long start) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public int setString(long pos, String str) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public int setString(long pos, String str, int offset, int len) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public OutputStream setAsciiStream(long pos) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public Writer setCharacterStream(long pos) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public void truncate(long len) throws SQLException {
      throw new UnsupportedOperationException();
    }

    @Override
    public void free() throws SQLException {
      try {
        inputStream.close();
      } catch (IOException e) {
        throw new SQLException(e);
      }
    }

    @Override
    public Reader getCharacterStream(long pos, long length) throws SQLException {
      throw new UnsupportedOperationException();
    }
  }
}
