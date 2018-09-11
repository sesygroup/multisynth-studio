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
package com.sesygroup.choreography.behavioralprotocol.model.typedmessage;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.sesygroup.choreography.behavioralprotocol.model.TypedElement;
import com.sesygroup.choreography.behavioralprotocol.model.TypedMessage;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class TypedOutputMessage extends TypedMessage implements Serializable {
   private static final long serialVersionUID = 3085101753257133166L;

   public TypedOutputMessage() {
      super();
   }

   public TypedOutputMessage(final String name, final List<TypedElement> typedElements) {
      super(name, typedElements);
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((super.name == null) ? 0 : super.name.hashCode());
      result = prime * result + ((super.typedElements == null) ? 0 : super.typedElements.hashCode());
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
      TypedOutputMessage other = (TypedOutputMessage) obj;
      if (super.name == null) {
         if (other.name != null) {
            return false;
         }
      } else if (!super.name.equals(other.name)) {
         return false;
      }
      if (super.typedElements == null) {
         if (other.typedElements != null)
            return false;
      } else if (!super.typedElements.equals(other.typedElements))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "(" + super.name + "," + this.typedElementsToString() + ")!";
   }

   public String typedElementsToString() {
      return "[" + super.typedElements.stream().map(Object::toString).collect(Collectors.joining(" ")) + "]";
   };
   
   public String getSimpleTypedMessageName() {
      return super.name+"!";
   }

}
