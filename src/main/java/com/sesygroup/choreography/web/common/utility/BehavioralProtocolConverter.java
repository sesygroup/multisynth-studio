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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sesygroup.choreography.behavioralprotocol.model.BehavioralProtocol;
import com.sesygroup.choreography.behavioralprotocol.model.State;
import com.sesygroup.choreography.behavioralprotocol.model.transition.InternalActionTransition;
import com.sesygroup.choreography.behavioralprotocol.model.transition.ReceiveActionTransition;
import com.sesygroup.choreography.behavioralprotocol.model.transition.SendActionTransition;
import com.sesygroup.choreography.behavioralprotocol.model.typedmessage.TypedInputMessage;
import com.sesygroup.choreography.behavioralprotocol.model.typedmessage.TypedOutputMessage;
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkConstants;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class BehavioralProtocolConverter {

   // FIX implementation
   public static BehavioralProtocol getBehavioralProtocol(Network network) {
      BehavioralProtocol behavioralProtocol = new BehavioralProtocol();

      // add all states
      behavioralProtocol.getStates()
            .addAll(network.getNodes().stream().map(state -> new State(state.getName())).collect(Collectors.toSet()));

      // add initial state, we suppose that there is only one initial state
      behavioralProtocol.setInitialState(new State(NetworkUtils.getInitialStates(network).iterator().next().getName()));

      // add final states
      behavioralProtocol.setFinalStates(NetworkUtils.getFinalStates(network).stream()
            .map(state -> new State(state.getName())).collect(Collectors.toSet()));

      // add typed messages and transitions
      network.getEdges().forEach(item -> {
         State sourceState = new State(
               network.getNodes().get(network.getNodes().indexOf(new NetworkNode(item.getFrom()))).getName());
         State targetState = new State(
               network.getNodes().get(network.getNodes().indexOf(new NetworkNode(item.getTo()))).getName());

         if (item.getType().equals(NetworkConstants.TRANSITION_SEND_ACTION)) {
            behavioralProtocol.getTransitions()
                  .add(new SendActionTransition(sourceState, targetState, new TypedOutputMessage()));
         }

         if (item.getType().equals(NetworkConstants.TRANSITION_RECEIVE_ACTION)) {

            behavioralProtocol.getTransitions()
                  .add(new ReceiveActionTransition(sourceState, targetState, new TypedInputMessage()));
         }

         if (item.getType().equals(NetworkConstants.TRANSITION_INTERNAL_ACTION)) {
            behavioralProtocol.getTransitions().add(new InternalActionTransition(sourceState, targetState));
         }

      });

      return behavioralProtocol;
   }

   public static Network getNetwork(BehavioralProtocol behavioralProtocol) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();

      // create a vis nodes and generate a unique ID for each node
      Map<State, String> stateToID = new HashMap<State, String>();
      behavioralProtocol.getStates().forEach(state -> {
         final String uuid = UUID.randomUUID().toString();
         String stateType = "";
         if (behavioralProtocol.getInitialState().equals(state)) {
            stateType = NetworkConstants.STATE_SOURCE;
         } else if (behavioralProtocol.getFinalStates().contains(state)) {
            stateType = NetworkConstants.STATE_FINAL;
         } else {
            stateType = NetworkConstants.STATE_GENERIC;
         }

         stateToID.put(state, uuid);

         nodes.add(new NetworkNode(uuid, state.getName(), state.getName(), state.getName(), stateType));
      });

      // create a vis edges and generate a unique ID for each edge
      behavioralProtocol.getTransitions().forEach(transition -> {

         if (InternalActionTransition.class.isInstance(transition)) {
            edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
                  stateToID.get(transition.getTargetState()), transition.toString(),
                  NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_INTERNAL_ACTION,
                  NetworkConstants.TRANSITION_EPSILON, ""));
         }

         if (ReceiveActionTransition.class.isInstance(transition)) {
            edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
                  stateToID.get(transition.getTargetState()), transition.toString(),
                  ((ReceiveActionTransition) transition).getTypedInputMessage().toString(),
                  NetworkConstants.TRANSITION_RECEIVE_ACTION,
                  ((ReceiveActionTransition) transition).getTypedInputMessage().getName(),
                  ((ReceiveActionTransition) transition).getTypedInputMessage().typedElementsToString()));
         }

         if (SendActionTransition.class.isInstance(transition)) {
            edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
                  stateToID.get(transition.getTargetState()), transition.toString(),
                  ((SendActionTransition) transition).getTypedOutputMessage().toString(),
                  NetworkConstants.TRANSITION_SEND_ACTION,
                  ((SendActionTransition) transition).getTypedOutputMessage().getName(),
                  ((SendActionTransition) transition).getTypedOutputMessage().typedElementsToString()));
         }

      });

      return new Network(nodes, edges);
   }

}
