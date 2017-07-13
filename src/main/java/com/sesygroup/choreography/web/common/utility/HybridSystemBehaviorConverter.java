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
package com.sesygroup.choreography.web.common.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.sesygroup.choreography.hybridsystembehavior.model.HybridSystemBehavior;
import com.sesygroup.choreography.hybridsystembehavior.model.State;
import com.sesygroup.choreography.hybridsystembehavior.model.Transition;
import com.sesygroup.choreography.hybridsystembehavior.model.action.AsynchReceiveActAndMsgConsumptionTransition;
import com.sesygroup.choreography.hybridsystembehavior.model.action.AsynchSendActTransition;
import com.sesygroup.choreography.hybridsystembehavior.model.action.InternalActionTransition;
import com.sesygroup.choreography.hybridsystembehavior.model.action.SynchSendReceiveActAndMsgConsumptionTransition;
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkConstants;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class HybridSystemBehaviorConverter {

   public static Network getNetwork(HybridSystemBehavior hybridSystemBehavior) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();

      // find source and final states
      Collection<State> sourceStates = getSourceStates(hybridSystemBehavior);
      Collection<State> finalStates = getFinalStates(hybridSystemBehavior);

      // create a vis nodes and generate a unique ID for each node
      Map<State, String> stateToID = new HashMap<State, String>();
      hybridSystemBehavior.getStates().forEach(state -> {
         final String uuid = UUID.randomUUID().toString();
         String stateType = "";
         if (sourceStates.contains(state)) {
            stateType = NetworkConstants.STATE_SOURCE;
         } else if (finalStates.contains(state)) {
            stateType = NetworkConstants.STATE_FINAL;
         } else {
            stateType = NetworkConstants.STATE_GENERIC;
         }

         stateToID.put(state, uuid);

         nodes.add(new NetworkNode(uuid, state.toString(), state.toString(), state.toString(), stateType));
      });

      // create a vis edges and generate a unique ID for each edge
      hybridSystemBehavior.getTransitions().forEach(transition -> {
         edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
               stateToID.get(transition.getTargetState()), transition.toString(), getLabelTransition(transition),
               isDashesTransition(transition), getTypeTransition(transition), getMessageTransition(transition),
               getParticipantSendMessage(transition), getParticipantReceiveMessage(transition)));
      });

      return new Network(nodes, edges);
   }

   private static Collection<State> getSourceStates(HybridSystemBehavior hybridSystemBehavior) {
      return CollectionUtils.subtract(hybridSystemBehavior.getStates().stream().collect(Collectors.toList()),
            hybridSystemBehavior.getTransitions().stream().map(Transition::getTargetState)
                  .collect(Collectors.toList()));
   }

   private static Collection<State> getFinalStates(HybridSystemBehavior hybridSystemBehavior) {
      return CollectionUtils.subtract(hybridSystemBehavior.getStates().stream().collect(Collectors.toList()),
            hybridSystemBehavior.getTransitions().stream().map(Transition::getSourceState)
                  .collect(Collectors.toList()));
   }

   private static boolean isDashesTransition(Transition transition) {
      return (AsynchSendActTransition.class.isInstance(transition)
            || AsynchReceiveActAndMsgConsumptionTransition.class.isInstance(transition))
                  ? true
                  : false;
   }

   private static String getTypeTransition(Transition transition) {
      if (AsynchSendActTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_ASYNCH_SEND_ACTION;
      }
      if (AsynchReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_ASYNCH_RECEIVE_ACTION_AND_MESSAGE_CONSUMPTION;
      }
      if (SynchSendReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_SYNCH_SEND_RECEIVE_ACTION_AND_MESSAGE_CONSUMPTION;
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_INTERNAL_ACTION;
      }
      return "";
   }

   private static String getMessageTransition(Transition transition) {
      if (AsynchSendActTransition.class.isInstance(transition)) {
         return ((AsynchSendActTransition) transition).getOutputMessage().getName();
      }
      if (AsynchReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return "";
      }
      if (SynchSendReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return ((SynchSendReceiveActAndMsgConsumptionTransition) transition).getMessageName();
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return "";
      }
      return "";
   }

   private static String getLabelTransition(Transition transition) {
      if (AsynchSendActTransition.class.isInstance(transition)) {
         return ((AsynchSendActTransition) transition).getOutputMessage().getName() + " "
               + ((AsynchSendActTransition) transition).getSourceParticipant().getName() + "->"
               + ((AsynchSendActTransition) transition).getTargetParticipant().getName();
      }
      if (AsynchReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_EPSILON;
      }
      if (SynchSendReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return ((SynchSendReceiveActAndMsgConsumptionTransition) transition).getMessageName() + " "
               + ((SynchSendReceiveActAndMsgConsumptionTransition) transition).getSourceParticipant().getName() + "->"
               + ((SynchSendReceiveActAndMsgConsumptionTransition) transition).getTargetParticipant().getName();
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_EPSILON;
      }
      return "";
   }

   private static String getParticipantSendMessage(Transition transition) {
      if (AsynchSendActTransition.class.isInstance(transition)) {
         return ((AsynchSendActTransition) transition).getSourceParticipant().getName();
      }
      if (AsynchReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return "";
      }
      if (SynchSendReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return ((SynchSendReceiveActAndMsgConsumptionTransition) transition).getSourceParticipant().getName();
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return "";
      }
      return "";
   }

   private static String getParticipantReceiveMessage(Transition transition) {
      if (AsynchSendActTransition.class.isInstance(transition)) {
         return ((AsynchSendActTransition) transition).getTargetParticipant().getName();
      }
      if (AsynchReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return "";
      }
      if (SynchSendReceiveActAndMsgConsumptionTransition.class.isInstance(transition)) {
         return ((SynchSendReceiveActAndMsgConsumptionTransition) transition).getTargetParticipant().getName();
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return "";
      }
      return "";
   }

}
