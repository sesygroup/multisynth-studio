/*
 * Copyright 2018 Software Engineering and Synthesis Group
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
package com.sesygroup.choreography.networkedsystemprotocol.model.parameter;

import java.io.Serializable;

import com.sesygroup.choreography.networkedsystemprotocol.model.Parameter;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class InputParameter extends Parameter implements Serializable {
   private static final long serialVersionUID = -4819117447336615072L;

   public InputParameter() {
      super();
   }

   public InputParameter(final String name) {
      super(name);
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((super.name == null)
            ? 0
            : super.name.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      InputParameter other = (InputParameter) obj;
      if (super.name == null) {
         if (other.name != null) {
            return false;
         }
      } else if (!super.name.equals(other.name)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return super.name;
   }

}
