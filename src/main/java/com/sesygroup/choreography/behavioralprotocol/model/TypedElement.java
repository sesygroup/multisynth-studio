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
package com.sesygroup.choreography.behavioralprotocol.model;

import java.io.Serializable;

import com.sesygroup.choreography.behavioralprotocol.model.Type;



/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class TypedElement implements Serializable {
   private static final long serialVersionUID = -3803770144969934249L;
   private String name;
   private Type type;
   
   public TypedElement() {
      super();
   }

   public TypedElement(final String name, final Type type) {
      super();
      this.name = name;
      this.type = type;
   }
   
   public String getName() {
      return name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public Type getType() {
      return type;
   }

   public void setType(final Type type) {
      this.type = type;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      TypedElement other = (TypedElement) obj;
      if (name == null) {
         if (other.name != null)
            return false;
      } else if (!name.equals(other.name))
         return false;
      if (type == null) {
         if (other.type != null)
            return false;
      } else if (!type.equals(other.type))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return name + ":" + type.toString()+";";
   }
   
   

}
