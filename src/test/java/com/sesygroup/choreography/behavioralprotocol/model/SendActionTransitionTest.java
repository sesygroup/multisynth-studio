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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.sesygroup.choreography.behavioralprotocol.model.transition.ReceiveActionTransition;
import com.sesygroup.choreography.behavioralprotocol.model.transition.SendActionTransition;
import com.sesygroup.choreography.behavioralprotocol.model.type.BasicType;
import com.sesygroup.choreography.behavioralprotocol.model.type.ComplexType;
import com.sesygroup.choreography.behavioralprotocol.model.typedmessage.TypedInputMessage;
import com.sesygroup.choreography.behavioralprotocol.model.typedmessage.TypedOutputMessage;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class SendActionTransitionTest {
   private static SendActionTransition mockedSendActionTransition;
   private static SendActionTransition sendActionTransition;

   @BeforeClass
   public static void setUp() {
      List<TypedElement> itemType = new ArrayList<TypedElement>();
      itemType.add(new TypedElement("id", new BasicType("string")));
      itemType.add(new TypedElement("quantity", new BasicType("int")));
      itemType.add(new TypedElement("descr", new BasicType("string")));

      List<TypedElement> typedElements = new ArrayList<TypedElement>();
      typedElements.add(new TypedElement("orderID", new BasicType("string")));
      typedElements.add(new TypedElement("item", new ComplexType(itemType)));

      TypedOutputMessage typedOutputMessage = new TypedOutputMessage("AddItem", typedElements);

      sendActionTransition = new SendActionTransition(new State("S2"), new State("S3"), typedOutputMessage);

      mockedSendActionTransition = Mockito.mock(SendActionTransition.class);
      Mockito.when(mockedSendActionTransition.toString())
            .thenReturn("(S2,(AddItem,[orderID:string; item:[id:string; quantity:int; descr:string;];])!,S3)");
   }

   @Test
   public void testToString() {
      Assert.assertEquals(mockedSendActionTransition.toString(), sendActionTransition.toString());
   }

   @Test
   public void testEquals() {
      List<TypedElement> itemTypeOne = new ArrayList<TypedElement>();
      itemTypeOne.add(new TypedElement("id", new BasicType("string")));
      itemTypeOne.add(new TypedElement("quantity", new BasicType("int")));
      itemTypeOne.add(new TypedElement("descr", new BasicType("string")));
      List<TypedElement> typedElementsOne = new ArrayList<TypedElement>();
      typedElementsOne.add(new TypedElement("orderID", new BasicType("string")));
      typedElementsOne.add(new TypedElement("item", new ComplexType(itemTypeOne)));
      TypedOutputMessage typedOutputMessageOne = new TypedOutputMessage("AddItem", typedElementsOne);

      SendActionTransition sendActionTransitionOne = new SendActionTransition(new State("S2"), new State("S3"),
            typedOutputMessageOne);

      List<TypedElement> itemTypeTwo = new ArrayList<TypedElement>();
      itemTypeTwo.add(new TypedElement("id", new BasicType("string")));
      itemTypeTwo.add(new TypedElement("quantity", new BasicType("int")));
      itemTypeTwo.add(new TypedElement("descr", new BasicType("string")));
      List<TypedElement> typedElementsTwo = new ArrayList<TypedElement>();
      typedElementsTwo.add(new TypedElement("orderID", new BasicType("string")));
      typedElementsTwo.add(new TypedElement("item", new ComplexType(itemTypeTwo)));
      TypedOutputMessage typedOutputMessageTwo = new TypedOutputMessage("AddItem", typedElementsTwo);

      SendActionTransition sendActionTransitionTwo = new SendActionTransition(new State("S2"), new State("S3"),
            typedOutputMessageTwo);

      MatcherAssert.assertThat(sendActionTransitionOne, Matchers.is(sendActionTransitionTwo));

      MatcherAssert.assertThat(sendActionTransitionOne.equals(sendActionTransitionTwo), Matchers.is(true));
   }

   @Test
   public void testEqualsSet() {
      Transition s0s1 = new SendActionTransition(new State("S0"), new State("S1"),
            new TypedOutputMessage("StartOrder", Arrays.asList(new TypedElement("username", new BasicType("string")),
                  new TypedElement("password", new BasicType("string")))));

      Transition s1s2 = new ReceiveActionTransition(new State("S1"), new State("S2"), new TypedInputMessage(
            "StartOrderResp", Arrays.asList(new TypedElement("orderID", new BasicType("string")))));

      Transition s2s3 = new SendActionTransition(new State("S2"), new State("S3"),
            new TypedOutputMessage("AddItem",
                  Arrays.asList(new TypedElement("orderID", new BasicType("string")),
                        new TypedElement("item",
                              new ComplexType(Arrays.asList(new TypedElement("id", new BasicType("string")),
                                    new TypedElement("quantity", new BasicType("int")),
                                    new TypedElement("descr", new BasicType("string"))))))));

      Transition s3s3 = new SendActionTransition(new State("S3"), new State("S3"),
            new TypedOutputMessage("AddItem",
                  Arrays.asList(new TypedElement("orderID", new BasicType("string")),
                        new TypedElement("item",
                              new ComplexType(Arrays.asList(new TypedElement("id", new BasicType("string")),
                                    new TypedElement("quantity", new BasicType("int")),
                                    new TypedElement("descr", new BasicType("string"))))))));

      Transition s3s4 = new SendActionTransition(new State("S3"), new State("S4"), new TypedOutputMessage(
            "Confirmation", Arrays.asList(new TypedElement("orderID", new BasicType("string")))));

      Transition s4s5 = new SendActionTransition(new State("S4"), new State("S5"),
            new TypedOutputMessage("CCInfo", Arrays.asList(new TypedElement("type", new BasicType("string")),
                  new TypedElement("number", new BasicType("string")),
                  new TypedElement("expiration", new BasicType("string")),
                  new TypedElement("cvv", new BasicType("int")), new TypedElement("name", new BasicType("string")),
                  new TypedElement("state", new BasicType("string")),
                  new TypedElement("zipCode", new BasicType("string")))));

      Transition s5s6 = new ReceiveActionTransition(new State("S5"), new State("S6"),
            new TypedInputMessage("PlaceOrder", Arrays.asList(new TypedElement("orderID", new BasicType("string")))));

      Transition s6s7 = new ReceiveActionTransition(new State("S6"), new State("S7"),
            new TypedInputMessage("Notification", Arrays.asList(new TypedElement("ack", new BasicType("string")))));

      Set<Transition> transitionsOne = new HashSet<Transition>(
            Arrays.asList(s0s1, s1s2, s2s3, s3s3, s3s4, s4s5, s5s6, s6s7));

      Set<Transition> transitionsTwo = new HashSet<Transition>(Arrays.asList(
            new SendActionTransition(new State("S0"), new State("S1"),
                  new TypedOutputMessage("StartOrder",
                        Arrays.asList(new TypedElement("username", new BasicType("string")),
                              new TypedElement("password", new BasicType("string"))))),

            new ReceiveActionTransition(new State("S1"), new State("S2"),
                  new TypedInputMessage("StartOrderResp",
                        Arrays.asList(new TypedElement("orderID", new BasicType("string"))))),

            new SendActionTransition(new State("S2"), new State("S3"),
                  new TypedOutputMessage("AddItem",
                        Arrays.asList(new TypedElement("orderID", new BasicType("string")),
                              new TypedElement("item",
                                    new ComplexType(Arrays.asList(new TypedElement("id", new BasicType("string")),
                                          new TypedElement("quantity", new BasicType("int")),
                                          new TypedElement("descr", new BasicType("string")))))))),

            new SendActionTransition(new State("S3"), new State("S3"),
                  new TypedOutputMessage("AddItem",
                        Arrays.asList(new TypedElement("orderID", new BasicType("string")),
                              new TypedElement("item",
                                    new ComplexType(Arrays.asList(new TypedElement("id", new BasicType("string")),
                                          new TypedElement("quantity", new BasicType("int")),
                                          new TypedElement("descr", new BasicType("string")))))))),

            new SendActionTransition(new State("S3"), new State("S4"),
                  new TypedOutputMessage("Confirmation",
                        Arrays.asList(new TypedElement("orderID", new BasicType("string"))))),

            new SendActionTransition(new State("S4"), new State("S5"),
                  new TypedOutputMessage("CCInfo",
                        Arrays.asList(new TypedElement("type", new BasicType("string")),
                              new TypedElement("number", new BasicType("string")),
                              new TypedElement("expiration", new BasicType("string")),
                              new TypedElement("cvv", new BasicType("int")),
                              new TypedElement("name", new BasicType("string")),
                              new TypedElement("state", new BasicType("string")),
                              new TypedElement("zipCode", new BasicType("string"))))),

            new ReceiveActionTransition(new State("S5"), new State("S6"),
                  new TypedInputMessage("PlaceOrder",
                        Arrays.asList(new TypedElement("orderID", new BasicType("string"))))),

            new ReceiveActionTransition(new State("S6"), new State("S7"), new TypedInputMessage("Notification",
                  Arrays.asList(new TypedElement("ack", new BasicType("string")))))));

      MatcherAssert.assertThat(transitionsOne, Matchers.is(transitionsTwo));

      MatcherAssert.assertThat(transitionsOne.equals(transitionsTwo), Matchers.is(true));
   }

   @Test
   public void testContains() {
      List<TypedElement> itemTypeOne = new ArrayList<TypedElement>();
      itemTypeOne.add(new TypedElement("id", new BasicType("string")));
      itemTypeOne.add(new TypedElement("quantity", new BasicType("int")));
      itemTypeOne.add(new TypedElement("descr", new BasicType("string")));
      List<TypedElement> typedElementsOne = new ArrayList<TypedElement>();
      typedElementsOne.add(new TypedElement("orderID", new BasicType("string")));
      typedElementsOne.add(new TypedElement("item", new ComplexType(itemTypeOne)));
      TypedOutputMessage typedOutputMessageOne = new TypedOutputMessage("AddItem", typedElementsOne);

      Transition sendActionTransitionOne = new SendActionTransition(new State("S2"), new State("S3"),
            typedOutputMessageOne);

      List<TypedElement> itemTypeTwo = new ArrayList<TypedElement>();
      itemTypeTwo.add(new TypedElement("id", new BasicType("string")));
      itemTypeTwo.add(new TypedElement("quantity", new BasicType("int")));
      itemTypeTwo.add(new TypedElement("descr", new BasicType("string")));
      List<TypedElement> typedElementsTwo = new ArrayList<TypedElement>();
      typedElementsTwo.add(new TypedElement("orderID", new BasicType("string")));
      typedElementsTwo.add(new TypedElement("item", new ComplexType(itemTypeTwo)));
      TypedOutputMessage typedOutputMessageTwo = new TypedOutputMessage("AddItem", typedElementsTwo);

      Transition sendActionTransitionTwo = new SendActionTransition(new State("S2"), new State("S3"),
            typedOutputMessageTwo);

      MatcherAssert.assertThat(sendActionTransitionOne, Matchers.is(sendActionTransitionTwo));

      MatcherAssert.assertThat(sendActionTransitionOne.equals(sendActionTransitionTwo), Matchers.is(true));

      Set<Transition> actions = new HashSet<Transition>();
      actions.add(sendActionTransitionOne);

      MatcherAssert.assertThat(actions.contains(sendActionTransitionTwo), Matchers.is(true));
   }

}
