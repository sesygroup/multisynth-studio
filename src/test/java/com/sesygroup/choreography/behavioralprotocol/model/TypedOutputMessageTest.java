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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.sesygroup.choreography.behavioralprotocol.model.type.BasicType;
import com.sesygroup.choreography.behavioralprotocol.model.type.ComplexType;
import com.sesygroup.choreography.behavioralprotocol.model.typedmessage.TypedOutputMessage;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class TypedOutputMessageTest {
   private static TypedOutputMessage mockedTypedOutputMessageTest;
   private static TypedOutputMessage typedOutputMessageTest;

   @BeforeClass
   public static void setUp() {
      List<TypedElement> itemType = new ArrayList<TypedElement>();
      itemType.add(new TypedElement("id", new BasicType("string")));
      itemType.add(new TypedElement("quantity", new BasicType("int")));
      itemType.add(new TypedElement("descr", new BasicType("string")));

      List<TypedElement> typedElements = new ArrayList<TypedElement>();
      typedElements.add(new TypedElement("orderID", new BasicType("string")));
      typedElements.add(new TypedElement("item", new ComplexType(itemType)));

      typedOutputMessageTest = new TypedOutputMessage("AddItem", typedElements);

      mockedTypedOutputMessageTest = Mockito.mock(TypedOutputMessage.class);
      Mockito.when(mockedTypedOutputMessageTest.toString())
            .thenReturn("(AddItem,[orderID:string; item:[id:string; quantity:int; descr:string;];])!");
      
      Mockito.when(mockedTypedOutputMessageTest.typedElementsToString())
      .thenReturn("[orderID:string; item:[id:string; quantity:int; descr:string;];]");
      
   }

   @Test
   public void testToString() {
      Assert.assertEquals(mockedTypedOutputMessageTest.toString(), typedOutputMessageTest.toString());
   }
   
   @Test
   public void testTypedElementsToString() {
      Assert.assertEquals(mockedTypedOutputMessageTest.typedElementsToString(), typedOutputMessageTest.typedElementsToString());
   }
}
