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

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class GenericResponseBody implements java.io.Serializable {
   private static final long serialVersionUID = -2266138096109030987L;

   private GenericResponseBodyState state;
   private Object result;

   public GenericResponseBody(GenericResponseBodyState state) {
      super();
      this.state = state;
   }

   public GenericResponseBody(GenericResponseBodyState state, Object result) {
      super();
      this.state = state;
      this.result = result;
   }

   public GenericResponseBodyState getState() {
      return state;
   }

   public void setState(GenericResponseBodyState state) {
      this.state = state;
   }

   public Object getResult() {
      return result;
   }

   public void setResult(Object result) {
      this.result = result;
   }

   public enum GenericResponseBodyState {
      SUCCESS, ERROR;
   }

}
