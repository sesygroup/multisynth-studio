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

import com.sesygroup.choreography.choreographyspecification.model.ChoreographySpecification;
import com.sesygroup.choreography.choreographyspecification.model.Message;
import com.sesygroup.choreography.choreographyspecification.model.Participant;
import com.sesygroup.choreography.choreographyspecification.model.State;
import com.sesygroup.choreography.choreographyspecification.model.Transition;
import com.sesygroup.choreography.choreographyspecification.model.action.SendingMessageActionTransition;
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkConstants;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class ChoreographySpecificationConverter {

   public static ChoreographySpecification getChoreographySpecification(Network network) {
      ChoreographySpecification choreographySpecification = new ChoreographySpecification();

      // add all participants
      choreographySpecification.getParticipants().addAll(
            CollectionUtils
                  .union(
                        network.getEdges().stream().map(edge -> new Participant(edge.getParticipantSendMessage())).collect(Collectors.toSet()),
                        network.getEdges().stream().map(edge -> new Participant(edge.getParticipantReceiveMessage())).collect(Collectors.toSet()))
                  .stream().collect(Collectors.toSet()));
      // add all states
      choreographySpecification.getStates()
            .addAll(network.getNodes().stream().map(state -> new State(state.getName())).collect(Collectors.toSet()));

      // add initial state, we suppose that there is only one initial state
      choreographySpecification
            .setInitialState(new State(NetworkUtils.getInitialStates(network).iterator().next().getName()));

      // add all messages
      choreographySpecification.getMessages()
            .addAll(network.getEdges().stream().map(NetworkEdge::getMessage).collect(Collectors.toSet()).stream()
                  .map(messageName -> new Message(messageName)).collect(Collectors.toSet()));

      // add all Transitions
      choreographySpecification.getTransitions()
            .addAll(network.getEdges().stream().map(edge -> new SendingMessageActionTransition(
                  new State(
                        network.getNodes().get(network.getNodes().indexOf(new NetworkNode(edge.getFrom()))).getName()),
                  new State(
                        network.getNodes().get(network.getNodes().indexOf(new NetworkNode(edge.getTo()))).getName()),
                  new Participant(edge.getParticipantSendMessage()),
                  new Participant(edge.getParticipantReceiveMessage()), new Message(edge.getMessage())))
                  .collect(Collectors.toSet()));

      return choreographySpecification;
   }

   public static Network getNetwork(ChoreographySpecification choreographySpecification) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();

      // find source and final states
      Collection<State> sourceStates = getSourceStates(choreographySpecification);
      Collection<State> finalStates = getFinalStates(choreographySpecification);

      // create a vis nodes and generate a unique ID for each node
      Map<State, String> stateToID = new HashMap<State, String>();
      choreographySpecification.getStates().forEach(state -> {
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

         nodes.add(new NetworkNode(uuid, state.getName(), state.getName(), state.getName(), stateType));
      });
      // create a vis edges and generate a unique ID for each edge
      choreographySpecification.getTransitions().forEach(transition -> {
         edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
               stateToID.get(transition.getTargetState()), transition.toString(), getLabelTransition(transition),
               false, getTypeTransition(transition), getMessageTransition(transition),getParticipantSendMessage(transition), getParticipantReceiveMessage(transition) ));
      });

      return new Network(nodes, edges);
   }

   private static Collection<State> getSourceStates(ChoreographySpecification choreographySpecification) {
      return CollectionUtils.subtract(choreographySpecification.getStates().stream().collect(Collectors.toList()),
            choreographySpecification.getTransitions().stream().map(Transition::getTargetState)
                  .collect(Collectors.toList()));
   }

   private static Collection<State> getFinalStates(ChoreographySpecification choreographySpecification) {
      return CollectionUtils.subtract(choreographySpecification.getStates().stream().collect(Collectors.toList()),
            choreographySpecification.getTransitions().stream().map(Transition::getSourceState)
                  .collect(Collectors.toList()));
   }

   private static String getTypeTransition(Transition transition) {
      if (SendingMessageActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_SEND_ACTION;
      }
      return "";
   }

   private static String getMessageTransition(Transition transition) {
      if (SendingMessageActionTransition.class.isInstance(transition)) {
         return ((SendingMessageActionTransition) transition).getMessage().getName();
      }
      return "";
   }

   private static String getLabelTransition(Transition transition) {
      if (SendingMessageActionTransition.class.isInstance(transition)) {
         return ((SendingMessageActionTransition) transition).getMessage().getName() + " "
               + ((SendingMessageActionTransition) transition).getSourceParticipant().getName() + "->"
               + ((SendingMessageActionTransition) transition).getTargetParticipant().getName();
      }
      return "";
   }

   private static String getParticipantSendMessage(Transition transition) {
      if (SendingMessageActionTransition.class.isInstance(transition)) {
         return ((SendingMessageActionTransition) transition).getSourceParticipant().getName();
      }
      return "";
   }

   private static String getParticipantReceiveMessage(Transition transition) {
      if (SendingMessageActionTransition.class.isInstance(transition)) {
         return ((SendingMessageActionTransition) transition).getTargetParticipant().getName();
      }
      return "";
   }


}
