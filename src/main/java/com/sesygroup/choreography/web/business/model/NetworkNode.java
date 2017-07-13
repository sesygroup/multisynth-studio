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
public class NetworkNode implements java.io.Serializable {
   private static final long serialVersionUID = 7904723562098631084L;
   private String id;
   private String title;
   private String label;
   private String name;
   private String group;
   private String type;
   private String participant;

   public NetworkNode() {
      super();
   }

   public NetworkNode(String id) {
      super();
      this.id = id;
   }

   // used for a node in a abstract and concrete participant behavior
   public NetworkNode(String id, String title, String label, String name, String type, String participant) {
      super();
      this.id = id;
      this.title = title;
      this.label = label;
      this.name = name;
      this.group = type;
      this.type = type;
      this.participant = participant;
   }

   // used for a node in a choreography specification and hybrid system behavior
   public NetworkNode(String id, String title, String label, String name, String type) {
      super();
      this.id = id;
      this.title = title;
      this.label = label;
      this.name = name;
      this.group = type;
      this.type = type;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGroup() {
      return group;
   }

   public void setGroup(String group) {
      this.group = group;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getParticipant() {
      return participant;
   }

   public void setParticipant(String participant) {
      this.participant = participant;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null)
            ? 0
            : id.hashCode());
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
      NetworkNode other = (NetworkNode) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      return true;
   }

}
