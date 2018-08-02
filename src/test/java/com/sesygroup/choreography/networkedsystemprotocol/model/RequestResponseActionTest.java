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
package com.sesygroup.choreography.networkedsystemprotocol.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

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
public class RequestResponseActionTest {
   private static RequestResponseAction mockedRequestResponseOperation;
   private static RequestResponseAction requestResponseOperation;

   @BeforeClass
   public static void setUp() {
      Set<InputParameter> inputParameters = new HashSet<InputParameter>();
      Set<OutputParameter> outputParameters = new HashSet<OutputParameter>();

      inputParameters.add(new InputParameter("CustomerId"));
      outputParameters.add(new OutputParameter("SessionId"));

      requestResponseOperation = new RequestResponseAction("login", inputParameters, outputParameters);

      mockedRequestResponseOperation = Mockito.mock(RequestResponseAction.class);
      Mockito.when(mockedRequestResponseOperation.toString())
            .thenReturn("<login, {CustomerId}, {SessionId}, provided>");
   }

   @Test
   public void testToString() {
      Assert.assertEquals(mockedRequestResponseOperation.toString(), requestResponseOperation.toString());
   }

   @Test
   public void testEquals() {
      Set<InputParameter> inputParametersOne = new HashSet<InputParameter>();
      Set<OutputParameter> outputParametersOne = new HashSet<OutputParameter>();
      inputParametersOne.add(new InputParameter("CustomerId"));
      inputParametersOne.add(new InputParameter("CustomerId2"));
      outputParametersOne.add(new OutputParameter("SessionId"));
      outputParametersOne.add(new OutputParameter("SessionId2"));
      RequestResponseAction requestResponseOperationOne = new RequestResponseAction("login", inputParametersOne,
            outputParametersOne);

      Set<InputParameter> inputParametersTwo = new HashSet<InputParameter>();
      Set<OutputParameter> outputParametersTwo = new HashSet<OutputParameter>();
      inputParametersTwo.add(new InputParameter("CustomerId"));
      inputParametersTwo.add(new InputParameter("CustomerId2"));
      outputParametersTwo.add(new OutputParameter("SessionId"));
      outputParametersTwo.add(new OutputParameter("SessionId2"));
      RequestResponseAction requestResponseOperationTwo = new RequestResponseAction("login", inputParametersTwo,
            outputParametersTwo);

      MatcherAssert.assertThat(requestResponseOperationOne, Matchers.is(requestResponseOperationTwo));

      MatcherAssert.assertThat(requestResponseOperationOne.equals(requestResponseOperationTwo), Matchers.is(true));
   }

   @Test
   public void testEqualsSet() {
      Action s0s1 = new SolicitResponseAction("StartOrder",
            new HashSet<InputParameter>(Arrays.asList(new InputParameter("CustomerId"))),
            new HashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderId"))));

      Action s1s2_s2s2 = new NotificationAction("AddItemToOrder", new HashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("ProductItem"))));

      Action s2s3 = new NotificationAction("PlaceOrder", new HashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("CreditCardNumber"))));

      Action s3s4_s4s4 = new OneWayAction("GetConfirmation", new HashSet<InputParameter>(
            Arrays.asList(new InputParameter("OrderId"), new InputParameter("ProductItem"))));

      Action s4s5 = new SolicitResponseAction("Quit",
            new HashSet<InputParameter>(Arrays.asList(new InputParameter("OrderId"))),
            new HashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderResult"))));

      Set<Action> actionsOne = new HashSet<Action>(Arrays.asList(s0s1, s1s2_s2s2, s2s3, s3s4_s4s4, s4s5));

      Set<Action> actionsTwo = new HashSet<Action>(Arrays.asList(
            new SolicitResponseAction("StartOrder",
                  new HashSet<InputParameter>(Arrays.asList(new InputParameter("CustomerId"))),
                  new HashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderId")))),
            new NotificationAction("AddItemToOrder",
                  new HashSet<InputParameter>(
                        Arrays.asList(new InputParameter("OrderId"), new InputParameter("ProductItem")))),
            new NotificationAction("PlaceOrder",
                  new HashSet<InputParameter>(
                        Arrays.asList(new InputParameter("OrderId"), new InputParameter("CreditCardNumber")))),
            new OneWayAction("GetConfirmation",
                  new HashSet<InputParameter>(
                        Arrays.asList(new InputParameter("OrderId"), new InputParameter("ProductItem")))),
            new SolicitResponseAction("Quit", new HashSet<InputParameter>(Arrays.asList(new InputParameter("OrderId"))),
                  new HashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderResult"))))));

      MatcherAssert.assertThat(actionsOne, Matchers.is(actionsTwo));

      MatcherAssert.assertThat(actionsOne.equals(actionsTwo), Matchers.is(true));
   }

   @Test
   public void testContains() {
      Action actionOne = new SolicitResponseAction("StartOrder",
            new HashSet<InputParameter>(Arrays.asList(new InputParameter("CustomerId"))),
            new HashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderId"))));

      Action actionTwo = new SolicitResponseAction("StartOrder",
            new HashSet<InputParameter>(Arrays.asList(new InputParameter("CustomerId"))),
            new HashSet<OutputParameter>(Arrays.asList(new OutputParameter("OrderId"))));

      MatcherAssert.assertThat(actionOne, Matchers.is(actionTwo));

      MatcherAssert.assertThat(actionOne.equals(actionTwo), Matchers.is(true));

      Set<Action> actions = new HashSet<Action>();
      actions.add(actionOne);

      MatcherAssert.assertThat(actions.contains(actionTwo), Matchers.is(true));
   }

}
