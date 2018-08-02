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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.Validate;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;
import com.sesygroup.choreography.web.business.model.NetworkPath;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class NetworkUtils {
   private static final String SOURCE_NODE_NOT_FOUND = "Source node not found.";
   private static final String SOURCE_NODE_GRATHER_THAN_ONE = "there are more than one source node.";

   public static List<NetworkPath> getReachability(Network network, NetworkNode targetNode) {
      Collection<NetworkNode> sources = getInitialStates(network);
      Validate.notEmpty(sources, SOURCE_NODE_NOT_FOUND);
      // we assume that we have only one source state
      Validate.inclusiveBetween(1, 1, sources.size(), SOURCE_NODE_GRATHER_THAN_ONE);

      DirectedGraph<String, DefaultEdge> directedGraph
            = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
      network.getNodes().forEach(node -> directedGraph.addVertex(node.getId()));
      network.getEdges().forEach(edge -> directedGraph.addEdge(edge.getFrom(), edge.getTo()));
      AllDirectedPaths<String, DefaultEdge> directed = new AllDirectedPaths<String, DefaultEdge>(directedGraph);
      List<GraphPath<String, DefaultEdge>> paths = directed.getAllPaths(sources.iterator().next().getId(),
            targetNode.getId(), false, network.getEdges().size());
      directedGraph.getEdgeSource(paths.get(0).getEdgeList().get(0));

      return paths.stream()
            .map(path -> new NetworkPath(
                  path.getEdgeList().stream()
                        .map(edge -> getNetworkEdge(network.getEdges(), directedGraph.getEdgeSource(edge),
                              directedGraph.getEdgeTarget(edge)))
                        .collect(Collectors.toList()),
                  targetNode, path.getLength()

            )).collect(Collectors.toList());
   }

   private static NetworkEdge getNetworkEdge(List<NetworkEdge> networkEdges, String from, String target) {
      return IterableUtils.find(networkEdges, new Predicate<NetworkEdge>() {
         @Override
         public boolean evaluate(final NetworkEdge object) {
            return object.getFrom().equals(from) && object.getTo().equals(target);
         }
      });
   }

   public static Collection<NetworkNode> getInitialStates(Network network) {
      List<NetworkNode> allNodes = network.getNodes().stream().collect(Collectors.toList());
      List<NetworkNode> allTargetNodes = network.getEdges().stream().map(NetworkEdge::getTo)
            .collect(Collectors.toList()).stream().map(id -> new NetworkNode(id)).collect(Collectors.toList());
      return Utility.convertTo(CollectionUtils.subtract(allNodes, allTargetNodes), NetworkNode.class);
   }
   
   public static Collection<NetworkNode> getFinalStates(Network network) {
      List<NetworkNode> allNodes = network.getNodes().stream().collect(Collectors.toList());
      List<NetworkNode> allSourceNodes = network.getEdges().stream().map(NetworkEdge::getFrom)
            .collect(Collectors.toList()).stream().map(id -> new NetworkNode(id)).collect(Collectors.toList());
      return Utility.convertTo(CollectionUtils.subtract(allNodes, allSourceNodes), NetworkNode.class);
   }
}
