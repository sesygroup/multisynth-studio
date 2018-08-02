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
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkAction;
import com.sesygroup.choreography.web.business.model.NetworkConstants;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class NetworkedSystemProtocolConverter {

   public static NetworkedSystemProtocol getNetworkedSystemProtocol(Network network) {
      NetworkedSystemProtocol networkedSystemProtocol = new NetworkedSystemProtocol();

      // add all states
      networkedSystemProtocol.getStates()
            .addAll(network.getNodes().stream().map(state -> new State(state.getName())).collect(Collectors.toSet()));

      // add initial state, we suppose that there is only one initial state
      networkedSystemProtocol
            .setInitialState(new State(NetworkUtils.getInitialStates(network).iterator().next().getName()));

      // add final states
      networkedSystemProtocol.setFinalStates(NetworkUtils.getFinalStates(network).stream()
            .map(state -> new State(state.getName())).collect(Collectors.toSet()));

      // add actions and transitions
      network.getEdges().forEach(item -> {
         State sourceState = new State(
               network.getNodes().get(network.getNodes().indexOf(new NetworkNode(item.getFrom()))).getName());
         State targetState = new State(
               network.getNodes().get(network.getNodes().indexOf(new NetworkNode(item.getTo()))).getName());

         if (item.getAction().getType().equals(NetworkConstants.ACTION_PROVIDED_REQUEST_RESPONSE)) {
            Action action = new RequestResponseAction(item.getAction().getOperationName(),
                  item.getAction().getInputParameters().stream().map(parameter -> new InputParameter(parameter))
                        .collect(Collectors.toSet()),
                  item.getAction().getOutputParameters().stream().map(parameter -> new OutputParameter(parameter))
                        .collect(Collectors.toSet()));
            networkedSystemProtocol.getActions().add(action);
            networkedSystemProtocol.getTransitions().add(new Transition(sourceState, targetState, action));
         }

         if (item.getAction().getType().equals(NetworkConstants.ACTION_PROVIDED_ONE_WAY)) {
            Action action = new OneWayAction(item.getAction().getOperationName(), item.getAction().getInputParameters()
                  .stream().map(parameter -> new InputParameter(parameter)).collect(Collectors.toSet()));
            networkedSystemProtocol.getActions().add(action);
            networkedSystemProtocol.getTransitions().add(new Transition(sourceState, targetState, action));
         }

         if (item.getAction().getType().equals(NetworkConstants.ACTION_REQUIRED_SOLICIT_RESPONSE)) {
            Action action = new SolicitResponseAction(item.getAction().getOperationName(),
                  item.getAction().getInputParameters().stream().map(parameter -> new InputParameter(parameter))
                        .collect(Collectors.toSet()),
                  item.getAction().getOutputParameters().stream().map(parameter -> new OutputParameter(parameter))
                        .collect(Collectors.toSet()));
            networkedSystemProtocol.getActions().add(action);
            networkedSystemProtocol.getTransitions().add(new Transition(sourceState, targetState, action));
         }

         if (item.getAction().getType().equals(NetworkConstants.ACTION_REQUIRED_NOTIFICATION)) {
            Action action = new NotificationAction(item.getAction().getOperationName(),
                  item.getAction().getInputParameters().stream().map(parameter -> new InputParameter(parameter))
                        .collect(Collectors.toSet()));
            networkedSystemProtocol.getActions().add(action);
            networkedSystemProtocol.getTransitions().add(new Transition(sourceState, targetState, action));
         }

      });

      return networkedSystemProtocol;
   }

   public static Network getNetwork(NetworkedSystemProtocol networkedSystemProtocol) {
      List<NetworkNode> nodes = new ArrayList<NetworkNode>();
      List<NetworkEdge> edges = new ArrayList<NetworkEdge>();

      // create a vis nodes and generate a unique ID for each node
      Map<State, String> stateToID = new HashMap<State, String>();
      networkedSystemProtocol.getStates().forEach(state -> {
         final String uuid = UUID.randomUUID().toString();
         String stateType = "";
         if (networkedSystemProtocol.getInitialState().equals(state)) {
            stateType = NetworkConstants.STATE_SOURCE;
         } else if (networkedSystemProtocol.getFinalStates().contains(state)) {
            stateType = NetworkConstants.STATE_FINAL;
         } else {
            stateType = NetworkConstants.STATE_GENERIC;
         }

         stateToID.put(state, uuid);

         nodes.add(new NetworkNode(uuid, state.getName(), state.getName(), state.getName(), stateType));
      });

      // create a vis edges and generate a unique ID for each edge
      networkedSystemProtocol.getTransitions().forEach(transition -> {
         NetworkAction action = null;
         if (RequestResponseAction.class.isInstance(transition.getAction())) {
            action = new NetworkAction(
                  ((RequestResponseAction) transition.getAction()).getInputParameters().stream()
                        .map(parameter -> new String(parameter.getName())).collect(Collectors.toList()),
                  ((RequestResponseAction) transition.getAction()).getOutputParameters().stream()
                        .map(parameter -> new String(parameter.getName())).collect(Collectors.toList()),
                  transition.getAction().getOperationName(), NetworkConstants.ACTION_PROVIDED_REQUEST_RESPONSE);
         }
         
         if (OneWayAction.class.isInstance(transition.getAction())) {
            action = new NetworkAction(
                  ((OneWayAction) transition.getAction()).getInputParameters().stream()
                        .map(parameter -> new String(parameter.getName())).collect(Collectors.toList()),
                  transition.getAction().getOperationName(), NetworkConstants.ACTION_PROVIDED_ONE_WAY);
         }
         
         if (SolicitResponseAction.class.isInstance(transition.getAction())) {
            action = new NetworkAction(
                  ((SolicitResponseAction) transition.getAction()).getInputParameters().stream()
                        .map(parameter -> new String(parameter.getName())).collect(Collectors.toList()),
                  ((SolicitResponseAction) transition.getAction()).getOutputParameters().stream()
                        .map(parameter -> new String(parameter.getName())).collect(Collectors.toList()),
                  transition.getAction().getOperationName(), NetworkConstants.ACTION_REQUIRED_SOLICIT_RESPONSE);
         }
         
         if (NotificationAction.class.isInstance(transition.getAction())) {
            action = new NetworkAction(
                  ((NotificationAction) transition.getAction()).getInputParameters().stream()
                        .map(parameter -> new String(parameter.getName())).collect(Collectors.toList()),
                  transition.getAction().getOperationName(), NetworkConstants.ACTION_REQUIRED_NOTIFICATION);
         }
         
         edges.add(new NetworkEdge(UUID.randomUUID().toString(), stateToID.get(transition.getSourceState()),
               stateToID.get(transition.getTargetState()), transition.toString(), transition.getAction().toString(), action));
      });

      return new Network(nodes, edges);
   }

}
