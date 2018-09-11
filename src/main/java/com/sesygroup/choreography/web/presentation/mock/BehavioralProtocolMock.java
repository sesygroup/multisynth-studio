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
package com.sesygroup.choreography.web.presentation.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.sesygroup.choreography.behavioralprotocol.model.BehavioralProtocol;
import com.sesygroup.choreography.behavioralprotocol.model.State;
import com.sesygroup.choreography.behavioralprotocol.model.Transition;
import com.sesygroup.choreography.behavioralprotocol.model.TypedElement;
import com.sesygroup.choreography.behavioralprotocol.model.TypedMessage;
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
public class BehavioralProtocolMock {

   public static BehavioralProtocol blueClientBehavioralProtocol() {
      Set<State> states = new HashSet<State>(Arrays.asList(new State("s0"), new State("s1"), new State("s2"),
            new State("s3"), new State("s4"), new State("s5"), new State("s6"), new State("s7")));
      Set<State> finalStates = new LinkedHashSet<State>(Arrays.asList(new State("s7")));
      State initialState = new State("s0");

      TypedOutputMessage startOrder = new TypedOutputMessage("StartOrder",
            Arrays.asList(new TypedElement("username", new BasicType("string")),
                  new TypedElement("password", new BasicType("string"))));

      TypedInputMessage startOrderResp = new TypedInputMessage("StartOrderResp",
            Arrays.asList(new TypedElement("orderID", new BasicType("string"))));

      TypedOutputMessage addItem = new TypedOutputMessage("AddItem",
            Arrays.asList(new TypedElement("orderID", new BasicType("string")),
                  new TypedElement("item",
                        new ComplexType(Arrays.asList(new TypedElement("id", new BasicType("string")),
                              new TypedElement("quantity", new BasicType("int")),
                              new TypedElement("descr", new BasicType("string")))))));
      TypedOutputMessage confirmation = new TypedOutputMessage("Confirmation",
            Arrays.asList(new TypedElement("orderID", new BasicType("string"))));

      TypedOutputMessage cCInfo = new TypedOutputMessage("CCInfo", Arrays.asList(
            new TypedElement("type", new BasicType("string")), new TypedElement("number", new BasicType("string")),
            new TypedElement("expiration", new BasicType("string")), new TypedElement("cvv", new BasicType("int")),
            new TypedElement("name", new BasicType("string")), new TypedElement("state", new BasicType("string")),
            new TypedElement("zipCode", new BasicType("string"))));

      TypedInputMessage placeOrder = new TypedInputMessage("PlaceOrder",
            Arrays.asList(new TypedElement("orderID", new BasicType("string"))));

      TypedInputMessage notification = new TypedInputMessage("Notification",
            Arrays.asList(new TypedElement("ack", new BasicType("string"))));

      Set<TypedMessage> typedMessages = new HashSet<TypedMessage>(
            Arrays.asList(startOrder, startOrderResp, addItem, confirmation, cCInfo, placeOrder, notification));

      Set<Transition> transitions = new HashSet<Transition>(
            Arrays.asList(new SendActionTransition(new State("s0"), new State("s1"), startOrder),
                  new ReceiveActionTransition(new State("s1"), new State("s2"), startOrderResp),
                  new SendActionTransition(new State("s2"), new State("s3"), addItem),
                  new SendActionTransition(new State("s3"), new State("s3"), addItem),
                  new SendActionTransition(new State("s3"), new State("s4"), confirmation),
                  new SendActionTransition(new State("s4"), new State("s5"), cCInfo),
                  new ReceiveActionTransition(new State("s5"), new State("s6"), placeOrder),
                  new ReceiveActionTransition(new State("s6"), new State("s7"), notification)));

      return new BehavioralProtocol(states, initialState, finalStates, typedMessages, transitions);

   }

   public static BehavioralProtocol moonServiceBehavioralProtocol() {
      Set<State> states = new LinkedHashSet<State>(Arrays.asList(new State("s0"), new State("s1"), new State("s2"),
            new State("s3"), new State("s4"), new State("s5"), new State("s6"), new State("s7"), new State("s8"),
            new State("s9"), new State("s10"), new State("s11"), new State("s12"), new State("s13")));
      Set<State> finalStates = new LinkedHashSet<State>(Arrays.asList(new State("s8")));
      State initialState = new State("s0");

      TypedInputMessage login = new TypedInputMessage("Login",
            Arrays.asList(new TypedElement("username", new BasicType("string")),
                  new TypedElement("password", new BasicType("string"))));

      TypedOutputMessage loginResp = new TypedOutputMessage("LoginResp",
            Arrays.asList(new TypedElement("sessionID", new BasicType("string"))));

      TypedInputMessage createOrder = new TypedInputMessage("CreateOrder",
            Arrays.asList(new TypedElement("sessionID", new BasicType("string"))));

      TypedOutputMessage createOrderResp = new TypedOutputMessage("CreateOrderResp",
            Arrays.asList(new TypedElement("orderID", new BasicType("string"))));

      TypedInputMessage selectItem = new TypedInputMessage("SelectItem",
            Arrays.asList(new TypedElement("orderID", new BasicType("string")),
                  new TypedElement("itemID", new BasicType("long")),
                  new TypedElement("descr", new BasicType("string"))));

      TypedInputMessage setQuantity = new TypedInputMessage("SetQuantity",
            Arrays.asList(new TypedElement("orderID", new BasicType("string")),
                  new TypedElement("itemID", new BasicType("long")),
                  new TypedElement("itemQty", new BasicType("int"))));

      TypedInputMessage confirmItems = new TypedInputMessage("CconfirmItems",
            Arrays.asList(new TypedElement("orderID", new BasicType("string"))));

      TypedOutputMessage closeOrder = new TypedOutputMessage("CloseOrder",
            Arrays.asList(new TypedElement("orderID", new BasicType("string"))));

      List<TypedElement> cCDetailsTypedElements = Arrays.asList(new TypedElement("type", new BasicType("string")),
            new TypedElement("number", new BasicType("string")), new TypedElement("expDate", new BasicType("date")),
            new TypedElement("cvv", new BasicType("int")), new TypedElement("holderName", new BasicType("string")));

      TypedInputMessage cCDetails = new TypedInputMessage("CCDetails", cCDetailsTypedElements);

      TypedOutputMessage thirdPartyPayment = new TypedOutputMessage("ThirdPartyPayment",
            Arrays.asList(new TypedElement("ccDetails", new ComplexType(cCDetailsTypedElements)),
                  new TypedElement("amount", new BasicType("double"))));

      TypedInputMessage paymentResp = new TypedInputMessage("PaymentResp", Arrays.asList(
            new TypedElement("res", new BasicType("boolean")), new TypedElement("msg", new BasicType("string"))));

      TypedOutputMessage confirmPayment = new TypedOutputMessage("ConfirmPayment",
            Arrays.asList(new TypedElement("msg", new BasicType("string"))));

      Set<TypedMessage> typedMessages = new HashSet<TypedMessage>(
            Arrays.asList(login, loginResp, createOrder, createOrderResp, selectItem, setQuantity, confirmItems,
                  closeOrder, cCDetails, thirdPartyPayment, paymentResp, confirmPayment));

      Set<Transition> transitions = new HashSet<Transition>(
            Arrays.asList(new ReceiveActionTransition(new State("s0"), new State("s1"), login),
                  new SendActionTransition(new State("s1"), new State("s2"), loginResp),
                  new ReceiveActionTransition(new State("s2"), new State("s3"), createOrder),
                  new SendActionTransition(new State("s3"), new State("s4"), createOrderResp),
                  new ReceiveActionTransition(new State("s4"), new State("s5"), selectItem),
                  new ReceiveActionTransition(new State("s5"), new State("s6"), setQuantity),
                  new ReceiveActionTransition(new State("s6"), new State("s7"), selectItem),
                  new ReceiveActionTransition(new State("s7"), new State("s6"), setQuantity),
                  new ReceiveActionTransition(new State("s6"), new State("s8"), confirmItems),
                  new SendActionTransition(new State("s8"), new State("s9"), closeOrder),
                  new ReceiveActionTransition(new State("s9"), new State("s10"), cCDetails),
                  new SendActionTransition(new State("s10"), new State("s11"), thirdPartyPayment),
                  new ReceiveActionTransition(new State("s11"), new State("s12"), paymentResp),
                  new SendActionTransition(new State("s12"), new State("s13"), confirmPayment)));

      return new BehavioralProtocol(states, initialState, finalStates, typedMessages, transitions);
   }
}
