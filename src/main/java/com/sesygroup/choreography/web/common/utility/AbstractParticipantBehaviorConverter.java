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

import com.sesygroup.choreography.abstractparticipantbehavior.model.AbstractParticipantBehavior;
import com.sesygroup.choreography.abstractparticipantbehavior.model.State;
import com.sesygroup.choreography.abstractparticipantbehavior.model.Transition;
import com.sesygroup.choreography.abstractparticipantbehavior.model.action.InternalActionTransition;
import com.sesygroup.choreography.abstractparticipantbehavior.model.action.ReceiveActionTransition;
import com.sesygroup.choreography.abstractparticipantbehavior.model.action.SendActionTransition;
import com.sesygroup.choreography.choreographyspecification.model.Participant;
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkConstants;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class AbstractParticipantBehaviorConverter {

   public static Network getNetwork(
         Map<Participant, AbstractParticipantBehavior> participantToAbstractParticipantBehaviorMap) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();

      for (Map.Entry<Participant, AbstractParticipantBehavior> entry : participantToAbstractParticipantBehaviorMap
            .entrySet()) {
         Network network = getNetwork(entry.getKey(), entry.getValue());
         nodes.addAll(network.getNodes());
         edges.addAll(network.getEdges());
      }
      return new Network(nodes, edges);
   }

   public static Network getNetwork(Participant participant, AbstractParticipantBehavior abstractParticipantBehavior) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();

      // find source and final states
      Collection<State> sourceStates = getSourceStates(abstractParticipantBehavior);
      Collection<State> finalStates = getFinalStates(abstractParticipantBehavior);

      // create a vis nodes and generate a unique ID for each node
      Map<State, String> stateToID = new HashMap<State, String>();
      abstractParticipantBehavior.getStates().forEach(state -> {
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

         nodes.add(new NetworkNode(uuid, state.getName(), state.getName(), state.getName(), stateType,
               participant.getName()));
      });
      // create a vis edges and generate a unique ID for each edge
      abstractParticipantBehavior.getTransitions().forEach(transition -> {
         edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
               stateToID.get(transition.getTargetState()), transition.toString(), getLabelTransition(transition), false,
               getTypeTransition(transition), getMessageTransition(transition)));
      });

      return new Network(nodes, edges);
   }

   private static Collection<State> getSourceStates(AbstractParticipantBehavior abstractParticipantBehavior) {
      return CollectionUtils.subtract(abstractParticipantBehavior.getStates().stream().collect(Collectors.toList()),
            abstractParticipantBehavior.getTransitions().stream().map(Transition::getTargetState)
                  .collect(Collectors.toList()));
   }

   private static Collection<State> getFinalStates(AbstractParticipantBehavior abstractParticipantBehavior) {
      return CollectionUtils.subtract(abstractParticipantBehavior.getStates().stream().collect(Collectors.toList()),
            abstractParticipantBehavior.getTransitions().stream().map(Transition::getSourceState)
                  .collect(Collectors.toList()));
   }

   private static String getTypeTransition(Transition transition) {
      if (SendActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_SEND_ACTION;
      }
      if (ReceiveActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_RECEIVE_ACTION;
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_INTERNAL_ACTION;
      }
      return "";
   }

   private static String getMessageTransition(Transition transition) {
      if (SendActionTransition.class.isInstance(transition)) {
         return ((SendActionTransition) transition).getOutputMessage().getName();
      }
      if (ReceiveActionTransition.class.isInstance(transition)) {
         return ((ReceiveActionTransition) transition).getInputMessage().getName();
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return "";
      }
      return "";
   }

   private static String getLabelTransition(Transition transition) {
      if (SendActionTransition.class.isInstance(transition)) {
         return ((SendActionTransition) transition).getOutputMessage().getName() + NetworkConstants.MESSAGE_OUTPUT;
      }
      if (ReceiveActionTransition.class.isInstance(transition)) {
         return ((ReceiveActionTransition) transition).getInputMessage().getName() + NetworkConstants.MESSAGE_INPUT;
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_EPSILON;
      }
      return "";
   }

}
