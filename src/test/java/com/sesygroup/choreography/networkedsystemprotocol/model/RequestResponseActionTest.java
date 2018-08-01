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

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.sesygroup.choreography.networkedsystemprotocol.model.action.RequestResponseAction;
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
      Mockito.when(mockedRequestResponseOperation.toString()).thenReturn("<login, {CustomerId}, {SessionId}, provided>");
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
      RequestResponseAction requestResponseOperationOne = new RequestResponseAction("login", inputParametersOne, outputParametersOne);

      Set<InputParameter> inputParametersTwo = new HashSet<InputParameter>();
      Set<OutputParameter> outputParametersTwo = new HashSet<OutputParameter>();
      inputParametersTwo.add(new InputParameter("CustomerId"));
      inputParametersTwo.add(new InputParameter("CustomerId2"));
      outputParametersTwo.add(new OutputParameter("SessionId"));
      outputParametersTwo.add(new OutputParameter("SessionId2"));
      RequestResponseAction requestResponseOperationTwo = new RequestResponseAction("login", inputParametersTwo, outputParametersTwo);
      
      MatcherAssert.assertThat(requestResponseOperationOne, Matchers.is(requestResponseOperationTwo));

      MatcherAssert.assertThat(requestResponseOperationOne.equals(requestResponseOperationTwo), Matchers.is(true));
  }
   
 /*  @Test
   public void testEquals() {
      Set<State> states = new HashSet<State>();
      states.add(stateOne);
      /*
       * important: convert the set to list before using the contains method because, if you change an element in the
       * Set, Set.contains(element) may fail to locate it, since the hashCode of the element will be different than what
       * it was when the element was first added to the HashSet
       */
   //   MatcherAssert.assertThat(states.stream().collect(Collectors.toList()).contains(stateTwo), Matchers.is(true));
   //}

}
