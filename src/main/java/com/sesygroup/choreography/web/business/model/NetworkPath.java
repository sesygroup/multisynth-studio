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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class NetworkPath implements java.io.Serializable {
   private static final long serialVersionUID = -155247817168711751L;

   private List<NetworkEdge> edges;
   private NetworkNode node;
   private int length;

   public NetworkPath() {
      super();
      edges = new ArrayList<NetworkEdge>();
   }

   public NetworkPath(List<NetworkEdge> edges, NetworkNode node, int length) {
      super();
      this.edges = edges;
      this.node = node;
      this.length = length;
   }

   public List<NetworkEdge> getEdges() {
      return edges;
   }

   public void setEdges(List<NetworkEdge> edges) {
      this.edges = edges;
   }

   public int getLength() {
      return length;
   }

   public void setLength(int length) {
      this.length = length;
   }

   public NetworkNode getNode() {
      return node;
   }

   public void setNode(NetworkNode node) {
      this.node = node;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((edges == null)
            ? 0
            : edges.hashCode());
      result = prime * result + ((node == null)
            ? 0
            : node.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      NetworkPath other = (NetworkPath) obj;
      if (edges == null) {
         if (other.edges != null)
            return false;
      } else if (!edges.equals(other.edges))
         return false;
      if (node == null) {
         if (other.node != null)
            return false;
      } else if (!node.equals(other.node))
         return false;
      return true;
   }

}
