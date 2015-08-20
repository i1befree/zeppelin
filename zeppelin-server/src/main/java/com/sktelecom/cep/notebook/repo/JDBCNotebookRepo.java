/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sktelecom.cep.notebook.repo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.notebook.Note;
import org.apache.zeppelin.notebook.NoteInfo;
import org.apache.zeppelin.notebook.Paragraph;
import org.apache.zeppelin.notebook.repo.NotebookRepo;
import org.apache.zeppelin.scheduler.Job.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
*
*/
public class JDBCNotebookRepo implements NotebookRepo {
  Logger logger = LoggerFactory.getLogger(JDBCNotebookRepo.class);

  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate;

  private ZeppelinConfiguration conf;

  public JDBCNotebookRepo(ZeppelinConfiguration conf) throws IOException, NamingException {
    this.conf = conf;

    Context ctx = new InitialContext();
    dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/cepDS");
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public List<NoteInfo> list() throws IOException {
    List<NoteInfo> infos = jdbcTemplate.query("select note_id, note_name, note_content, user_id from notebook", new RowMapper<NoteInfo>() {
      public NoteInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        NoteInfo info = null;
        try {
          info = getNoteInfo(rs.getString("note_content"));
          info.setUserId(rs.getString("user_id"));
          //info.setName(rs.getString("note_name"));
        } catch (IOException e) {
          e.printStackTrace();
        }
        return info;
      }
    });
    return infos;
  }

  private Note getNote(String json) throws IOException {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setPrettyPrinting();
    Gson gson = gsonBuilder.create();

    Note note = gson.fromJson(json, Note.class);
    for (Paragraph p : note.getParagraphs()) {
      if (p.getStatus() == Status.PENDING || p.getStatus() == Status.RUNNING) {
        p.setStatus(Status.ABORT);
      }
    }
    return note;
  }

  private NoteInfo getNoteInfo(String json) throws IOException {
    Note note = getNote(json);
    return new NoteInfo(note);
  }

  @Override
  public Note get(String noteId) throws IOException {
    try {
      Note note = jdbcTemplate.queryForObject("select note_id, note_name, note_content, user_id from notebook where note_id = ?", new Object[] { noteId }, new RowMapper<Note>() {
        public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
          Note note = null;
          try {
            note = getNote(rs.getString("note_content"));
            note.setUserId(rs.getString("user_id"));
            //note.setName(rs.getString("note_name"));
          } catch (IOException e) {
            e.printStackTrace();
          }
          return note;
        }
      });
      return note;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void save(Note note) throws IOException {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setPrettyPrinting();
    Gson gson = gsonBuilder.create();
    String json = gson.toJson(note);

    Note orgNote = get(note.id());
    if (orgNote == null) {
      if (note.getName() == null) {
        note.setName("Note " + note.id());
      }
      jdbcTemplate.update("insert into notebook(note_id, note_name, note_content, user_id) values (?, ?, ?, ?)", note.id(), note.getName(), json, note.getUserId());
    } else {
      jdbcTemplate.update("update notebook set note_content = ?, note_name = ? where note_id = ?", json, note.getName(), note.id());
    }
  }

  @Override
  public void remove(String noteId) throws IOException {
    jdbcTemplate.update("delete from notebook where note_id = ?", noteId);
  }
}
