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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.sesygroup.choreography.concreteparticipantbehavior.model.ConcreteParticipantBehavior;
import com.sesygroup.choreography.concreteparticipantbehavior.model.State;
import com.sesygroup.choreography.concreteparticipantbehavior.model.Transition;
import com.sesygroup.choreography.concreteparticipantbehavior.model.action.AsynchronousReceiveActionTransition;
import com.sesygroup.choreography.concreteparticipantbehavior.model.action.AsynchronousSendActionTransition;
import com.sesygroup.choreography.concreteparticipantbehavior.model.action.InternalActionTransition;
import com.sesygroup.choreography.concreteparticipantbehavior.model.action.SynchronousReceiveActionTransition;
import com.sesygroup.choreography.concreteparticipantbehavior.model.action.SynchronousSendActionTransition;
import com.sesygroup.choreography.concreteparticipantbehavior.model.message.InputMessage;
import com.sesygroup.choreography.concreteparticipantbehavior.model.message.OutputMessage;
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkConstants;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class ConcreteParticipantBehaviorConverter {

   public static Map<String, ConcreteParticipantBehavior> getConcreteParticipantBehaviors(Network network) {
      Map<String, ConcreteParticipantBehavior> participantToConcreteParticipantBehaviorMap
            = new HashMap<String, ConcreteParticipantBehavior>();

      Set<String> participants
            = network.getNodes().stream().map(NetworkNode::getParticipant).collect(Collectors.toSet());

      for (String participantName : participants) {
         ConcreteParticipantBehavior concreteParticipantBehavior = new ConcreteParticipantBehavior();

         network.getNodes().forEach(item -> {
            // add states
            if (item.getParticipant().equals(participantName)) {
               State state = new State(item.getName());
               concreteParticipantBehavior.getStates().add(state);
               // add initial state
               if (item.getType().equals(NetworkConstants.STATE_SOURCE)) {
                  concreteParticipantBehavior.setInitialState(state);
               }
            }
         });

         network.getEdges().forEach(item -> {
            // check whether the source state of the edge is the considered participant, we skip the test of the target
            // node.
            if (network.getNodes().get(network.getNodes().indexOf(new NetworkNode(item.getFrom()))).getParticipant()
                  .equals(participantName)) {
               State sourceState
                     = new State(network.getNodes().get(network.getNodes().indexOf(new NetworkNode(item.getFrom()))).getName());
               State targetState
                     = new State(network.getNodes().get(network.getNodes().indexOf(new NetworkNode(item.getTo()))).getName());

               // add messages
               if (item.getType().equals(NetworkConstants.TRANSITION_ASYNCH_SEND_ACTION)
                     || item.getType().equals(NetworkConstants.TRANSITION_SYNCH_SEND_ACTION)) {
                  concreteParticipantBehavior.getMessages().add(new OutputMessage(item.getMessage()));
               }
               if (item.getType().equals(NetworkConstants.TRANSITION_ASYNCH_RECEIVE_ACTION)
                     || item.getType().equals(NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION)) {
                  concreteParticipantBehavior.getMessages().add(new InputMessage(item.getMessage()));
               }

               // add transition
               if (item.getType().equals(NetworkConstants.TRANSITION_ASYNCH_SEND_ACTION)) {
                  concreteParticipantBehavior.getTransitions().add(new AsynchronousSendActionTransition(sourceState,
                        targetState, new OutputMessage(item.getMessage())));
               }

               if (item.getType().equals(NetworkConstants.TRANSITION_ASYNCH_RECEIVE_ACTION)) {
                  concreteParticipantBehavior.getTransitions().add(new AsynchronousReceiveActionTransition(sourceState,
                        targetState, new InputMessage(item.getMessage())));
               }

               if (item.getType().equals(NetworkConstants.TRANSITION_SYNCH_SEND_ACTION)) {
                  concreteParticipantBehavior.getTransitions().add(new SynchronousSendActionTransition(sourceState,
                        targetState, new OutputMessage(item.getMessage())));
               }

               if (item.getType().equals(NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION)) {
                  concreteParticipantBehavior.getTransitions().add(new SynchronousReceiveActionTransition(sourceState,
                        targetState, new InputMessage(item.getMessage())));
               }

               if (item.getType().equals(NetworkConstants.TRANSITION_INTERNAL_ACTION)) {
                  concreteParticipantBehavior.getTransitions()
                        .add(new InternalActionTransition(sourceState, targetState));
               }
            }
         });

         participantToConcreteParticipantBehaviorMap.put(participantName, concreteParticipantBehavior);
      }
      return participantToConcreteParticipantBehaviorMap;
   }

   public static Network getNetwork(
         Map<String, ConcreteParticipantBehavior> participantNameToConcreteParticipantBehaviorMap) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();
      for (Map.Entry<String, ConcreteParticipantBehavior> entry : participantNameToConcreteParticipantBehaviorMap
            .entrySet()) {
         Network network = getNetwork(entry.getKey(), entry.getValue());
         nodes.addAll(network.getNodes());
         edges.addAll(network.getEdges());
      }
      return new Network(nodes, edges);
   }

   public static Network getNetwork(String participant, ConcreteParticipantBehavior concreteParticipantBehavior) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();

      // find source and final states
      Collection<State> sourceStates = getSourceStates(concreteParticipantBehavior);
      Collection<State> finalStates = getFinalStates(concreteParticipantBehavior);

      // create a vis nodes and generate a unique ID for each node
      Map<State, String> stateToID = new HashMap<State, String>();
      concreteParticipantBehavior.getStates().forEach(state -> {
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

         nodes.add(new NetworkNode(uuid, state.getName(), state.getName(), state.getName(), stateType, participant));
      });
      // create a vis edges and generate a unique ID for each edge
      concreteParticipantBehavior.getTransitions().forEach(transition -> {
         edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
               stateToID.get(transition.getTargetState()), transition.toString(), getLabelTransition(transition),
               isDashesTransition(transition), getTypeTransition(transition), getMessageTransition(transition)));
      });

      return new Network(nodes, edges);
   }

   private static Collection<State> getSourceStates(ConcreteParticipantBehavior concreteParticipantBehavior) {
      return CollectionUtils.subtract(concreteParticipantBehavior.getStates().stream().collect(Collectors.toList()),
            concreteParticipantBehavior.getTransitions().stream().map(Transition::getTargetState)
                  .collect(Collectors.toList()));
   }

   private static Collection<State> getFinalStates(ConcreteParticipantBehavior concreteParticipantBehavior) {
      return CollectionUtils.subtract(concreteParticipantBehavior.getStates().stream().collect(Collectors.toList()),
            concreteParticipantBehavior.getTransitions().stream().map(Transition::getSourceState)
                  .collect(Collectors.toList()));
   }

   private static boolean isDashesTransition(Transition transition) {
      return (AsynchronousSendActionTransition.class.isInstance(transition)
            || AsynchronousReceiveActionTransition.class.isInstance(transition))
                  ? true
                  : false;
   }

   private static String getTypeTransition(Transition transition) {
      if (AsynchronousSendActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_ASYNCH_SEND_ACTION;
      }
      if (AsynchronousReceiveActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_ASYNCH_RECEIVE_ACTION;
      }
      if (SynchronousSendActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_SYNCH_SEND_ACTION;
      }
      if (SynchronousReceiveActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION;
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_INTERNAL_ACTION;
      }
      return "";
   }

   private static String getMessageTransition(Transition transition) {
      if (AsynchronousSendActionTransition.class.isInstance(transition)) {
         return ((AsynchronousSendActionTransition) transition).getOutputMessage().getName();
      }
      if (AsynchronousReceiveActionTransition.class.isInstance(transition)) {
         return ((AsynchronousReceiveActionTransition) transition).getInputMessage().getName();
      }
      if (SynchronousSendActionTransition.class.isInstance(transition)) {
         return ((SynchronousSendActionTransition) transition).getOutputMessage().getName();
      }
      if (SynchronousReceiveActionTransition.class.isInstance(transition)) {
         return ((SynchronousReceiveActionTransition) transition).getInputMessage().getName();
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return "";
      }
      return "";
   }

   private static String getLabelTransition(Transition transition) {
      if (AsynchronousSendActionTransition.class.isInstance(transition)) {
         return ((AsynchronousSendActionTransition) transition).getOutputMessage().getName()
               + NetworkConstants.MESSAGE_OUTPUT;
      }
      if (AsynchronousReceiveActionTransition.class.isInstance(transition)) {
         return ((AsynchronousReceiveActionTransition) transition).getInputMessage().getName()
               + NetworkConstants.MESSAGE_INPUT;
      }
      if (SynchronousSendActionTransition.class.isInstance(transition)) {
         return ((SynchronousSendActionTransition) transition).getOutputMessage().getName()
               + NetworkConstants.MESSAGE_OUTPUT;
      }
      if (SynchronousReceiveActionTransition.class.isInstance(transition)) {
         return ((SynchronousReceiveActionTransition) transition).getInputMessage().getName()
               + NetworkConstants.MESSAGE_INPUT;
      }
      if (InternalActionTransition.class.isInstance(transition)) {
         return NetworkConstants.TRANSITION_EPSILON;
      }
      return "";
   }

}
