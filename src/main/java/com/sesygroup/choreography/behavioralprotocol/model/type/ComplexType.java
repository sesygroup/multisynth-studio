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
package com.sesygroup.choreography.behavioralprotocol.model.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sesygroup.choreography.behavioralprotocol.model.Type;
import com.sesygroup.choreography.behavioralprotocol.model.TypedElement;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class ComplexType extends Type implements Serializable {
   private static final long serialVersionUID = -5241614058470398134L;
   private List<TypedElement> typedElements;

   public ComplexType() {
      super();
      typedElements = new ArrayList<TypedElement>();
   }

   public ComplexType(final List<TypedElement> typedElements) {
      super();
      this.typedElements = typedElements;
   }

   public List<TypedElement> getTypedElements() {
      return typedElements;
   }

   public void setTypedElements(final List<TypedElement> typedElements) {
      this.typedElements = typedElements;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((typedElements == null) ? 0 : typedElements.hashCode());
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
      ComplexType other = (ComplexType) obj;
      if (typedElements == null) {
         if (other.typedElements != null)
            return false;
      } else if (!typedElements.equals(other.typedElements))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "[" + typedElements.stream().map(Object::toString).collect(Collectors.joining(" ")) + "]";
   }

}
