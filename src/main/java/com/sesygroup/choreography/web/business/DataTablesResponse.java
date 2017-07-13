/*
 * Copyright 2017 Software Engineering and Synthesis Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sesygroup.choreography.web.business;

import java.util.List;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 * @param <R>
 */
public class DataTablesResponse<R> implements java.io.Serializable {
   private static final long serialVersionUID = -7301213727017992064L;

   private String sEcho;
   private long iTotalRecords;
   private long iTotalDisplayRecords;
   private List<R> rows;

   public DataTablesResponse(String sEcho, long iTotalRecords, long iTotalDisplayRecords, List<R> rows) {
      super();
      this.sEcho = sEcho;
      this.iTotalRecords = iTotalRecords;
      this.iTotalDisplayRecords = iTotalDisplayRecords;
      this.rows = rows;
   }

   public String getsEcho() {
      return sEcho;
   }

   public void setsEcho(String sEcho) {
      this.sEcho = sEcho;
   }

   public long getiTotalRecords() {
      return iTotalRecords;
   }

   public void setiTotalRecords(long iTotalRecords) {
      this.iTotalRecords = iTotalRecords;
   }

   public long getiTotalDisplayRecords() {
      return iTotalDisplayRecords;
   }

   public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
      this.iTotalDisplayRecords = iTotalDisplayRecords;
   }

   public List<R> getRows() {
      return rows;
   }

   public void setRows(List<R> rows) {
      this.rows = rows;
   }

}
