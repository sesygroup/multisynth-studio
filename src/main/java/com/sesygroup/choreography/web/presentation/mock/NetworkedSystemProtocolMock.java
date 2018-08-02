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
import java.util.LinkedHashSet;
import java.util.Set;

import com.sesygroup.choreography.networkedsystemprotocol.model.Action;
import com.sesygroup.choreography.networkedsystemprotocol.model.NetworkedSystemProtocol;
import com.sesygroup.choreography.networkedsystemprotocol.model.State;
import com.sesygroup.choreography.networkedsystemprotocol.model.Transition;
import com.sesygroup.choreography.networkedsystemprotocol.model.action.NotificationAction;
import com.sesygroup.choreography.networkedsystemprotocol.model.action.OneWayAction;
import com.sesygroup.choreography.networkedsystemprotocol.model.action.RequestResponseAction;
import com.sesygroup.choreography.networkedsystemprotocol.model.action.SolicitResponseAction;
import com.sesygroup.choreography.networkedsystemprotocol.model.parameter.InputParameter;
import com.sesygroup.choreography.networkedsystemprotocol.model.parameter.OutputParameter;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class NetworkedSystemProtocolMock {

   public static NetworkedSystemProtocol blueClientProtocol() {
      Set<State> states = new LinkedHashSet<State>(Arrays.asList(new State("s0"), new State("s1"), new State("s2"),
            new State("s3"), new State("s4"), new State("s5")));
      Set<State> finalStates = new LinkedHashSet<State>(Arrays.asList(new State("s5")));
      State initialState = new State("s0");

      Action s0s1 = new SolicitResponseAction("StartOrder",
            new LinkedHashSet<InputParameter>(Arrays.asList(new InputParameter("CustomerId"))),
            new LinkedHashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderId"))));

      Action s1s2_s2s2 = new NotificationAction("AddItemToOrder", new LinkedHashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("ProductItem"))));

      Action s2s3 = new NotificationAction("PlaceOrder", new LinkedHashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("CreditCardNumber"))));

      Action s3s4_s4s4 = new OneWayAction("GetConfirmation", new LinkedHashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("ProductItem"))));

      Action s4s5 = new SolicitResponseAction("Quit",
            new LinkedHashSet<InputParameter>(Arrays.asList(new InputParameter("OrderId"))),
            new LinkedHashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderResult"))));

      Set<Action> actions = new LinkedHashSet<Action>(Arrays.asList(s0s1, s1s2_s2s2, s2s3, s3s4_s4s4, s4s5));

      Set<Transition> transitions = new LinkedHashSet<Transition>(
            Arrays.asList(new Transition(new State("s0"), new State("s1"), s0s1),
                  new Transition(new State("s1"), new State("s2"), s1s2_s2s2),
                  new Transition(new State("s2"), new State("s2"), s1s2_s2s2),
                  new Transition(new State("s2"), new State("s3"), s2s3),
                  new Transition(new State("s3"), new State("s4"), s3s4_s4s4),
                  new Transition(new State("s4"), new State("s4"), s3s4_s4s4),
                  new Transition(new State("s4"), new State("s5"), s4s5)));

      return new NetworkedSystemProtocol(states, initialState, finalStates, actions, transitions);

   }

   public static NetworkedSystemProtocol moonServiceProtocol() {
      Set<State> states = new LinkedHashSet<State>(Arrays.asList(new State("s0"), new State("s1"), new State("s2"),
            new State("s3"), new State("s4"), new State("s5"), new State("s6"), new State("s7"), new State("s8")));
      Set<State> finalStates = new LinkedHashSet<State>(Arrays.asList(new State("s8")));
      State initialState = new State("s0");

      Action s0s1 = new RequestResponseAction("Login",
            new LinkedHashSet<InputParameter>(Arrays.asList(new InputParameter("CustomerId"))),
            new LinkedHashSet<OutputParameter>(Arrays.asList(new OutputParameter("SessionId"))));

      Action s1s2 = new RequestResponseAction("CreateOrder",
            new LinkedHashSet<InputParameter>(Arrays.asList(new InputParameter("SessionId"))),
            new LinkedHashSet<OutputParameter>(Arrays.asList(new OutputParameter("PurchaseOrder"))));

      Action s2s3_s5s3 = new OneWayAction("SelectItem", new LinkedHashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("ItemId"))));

      Action s3s4 = new OneWayAction("SetItemQuantity", new LinkedHashSet<InputParameter>(Arrays
            .asList(new InputParameter("OrderId"), new InputParameter("ItemId"), new InputParameter("ItemQuantity"))));

      Action s4s5 = new NotificationAction("ConfirmItem", new LinkedHashSet<InputParameter>(Arrays
            .asList(new InputParameter("OrderId"), new InputParameter("ItemId"), new InputParameter("ItemQuantity"))));

      Action s5s6 = new OneWayAction("CloseOrder", new LinkedHashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("CreditCardNumber"))));

      Action s6s7 = new SolicitResponseAction("PayThirdParty",
            new LinkedHashSet<InputParameter>(
                  Arrays.asList(new InputParameter("CreditCardNumber"), new InputParameter("Amout"))),
            new LinkedHashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderResult"))));

      Action s7s8 = new RequestResponseAction("CloseOrder", new LinkedHashSet<InputParameter>(),
            new LinkedHashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderResult"))));

      Set<Action> actions = new LinkedHashSet<Action>(
            Arrays.asList(s0s1, s1s2, s2s3_s5s3, s3s4, s4s5, s5s6, s6s7, s7s8));

      Set<Transition> transitions = new LinkedHashSet<Transition>(
            Arrays.asList(new Transition(new State("s0"), new State("s1"), s0s1),
                  new Transition(new State("s1"), new State("s2"), s1s2),
                  new Transition(new State("s2"), new State("s3"), s2s3_s5s3),
                  new Transition(new State("s3"), new State("s4"), s3s4),
                  new Transition(new State("s4"), new State("s5"), s4s5),
                  new Transition(new State("s5"), new State("s3"), s2s3_s5s3),
                  new Transition(new State("s5"), new State("s6"), s5s6),
                  new Transition(new State("s6"), new State("s7"), s6s7),
                  new Transition(new State("s7"), new State("s8"), s7s8)));

      return new NetworkedSystemProtocol(states, initialState, finalStates, actions, transitions);
   }
}
