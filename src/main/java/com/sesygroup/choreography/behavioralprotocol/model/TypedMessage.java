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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public abstract class TypedMessage {
   protected String name;
   protected List<TypedElement> typedElements;

   public TypedMessage() {
      super();
      typedElements = new ArrayList<TypedElement>();
   }

   public TypedMessage(final String name, final List<TypedElement> typedElements) {
      super();
      this.name = name;
      this.typedElements = typedElements;
   }

   public String getName() {
      return name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public List<TypedElement> getTypedElements() {
      return typedElements;
   }

   public void setTypedElements(final List<TypedElement> typedElements) {
      this.typedElements = typedElements;
   }

   @Override
   public abstract int hashCode();

   @Override
   public abstract boolean equals(Object obj);

   @Override
   public abstract String toString();
   
   public abstract String typedElementsToString();
   
   public abstract String getSimpleTypedMessageName();

}