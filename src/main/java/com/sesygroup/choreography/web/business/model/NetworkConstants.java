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
package com.sesygroup.choreography.web.business.model;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class NetworkConstants {
   public static final String STATE_SOURCE = "sourceNode";

   public static final String STATE_FINAL = "sinkNode";

   public static final String STATE_GENERIC = "genericNode";

   public static final String TRANSITION_EPSILON = "epsilon";

   public static final String TRANSITION_INTERNAL_ACTION = "internalAction";

   public static final String TRANSITION_SEND_ACTION = "sendAction";

   public static final String TRANSITION_RECEIVE_ACTION = "receiveAction";

   public static final String TRANSITION_ASYNCH_SEND_ACTION = "asynchSendAction";

   public static final String TRANSITION_ASYNCH_RECEIVE_ACTION = "asynchReceiveAction";

   public static final String TRANSITION_SYNCH_SEND_ACTION = "synchSendAction";

   public static final String TRANSITION_SYNCH_RECEIVE_ACTION = "synchReceiveAction";

   public static final String TRANSITION_ASYNCH_RECEIVE_ACTION_AND_MESSAGE_CONSUMPTION = "asynchReceiveActionAndMessageConsumption";

   public static final String TRANSITION_SYNCH_SEND_RECEIVE_ACTION_AND_MESSAGE_CONSUMPTION = "synchSendReceiveActionAndMessageConsumption";

   public static final String ACTION_PROVIDED_REQUEST_RESPONSE = "request-response";

   public static final String ACTION_PROVIDED_ONE_WAY = "one-way";
   
   public static final String ACTION_REQUIRED_SOLICIT_RESPONSE = "solicit-response";
   
   public static final String ACTION_REQUIRED_NOTIFICATION = "notification";
   
   public static final String MESSAGE_INPUT = "?";

   public static final String MESSAGE_OUTPUT = "!";
}
