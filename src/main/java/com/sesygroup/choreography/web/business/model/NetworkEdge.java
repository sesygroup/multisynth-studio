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
public class NetworkEdge implements java.io.Serializable {
   private static final long serialVersionUID = -8376145544555337133L;
   private String id;
   private String from;
   private String to;
   private String title;
   private String label;
   private boolean dashes;
   private String type;
   private String message;
   private String participantSendMessage;
   private String participantReceiveMessage;
   private NetworkAction action;
   
   public NetworkEdge() {
      super();
   }

   public NetworkEdge(String id) {
      super();
      this.id = id;
   }

   // used for a transition in a concrete participant behavior
   public NetworkEdge(String id, String from, String to, String title, String label, boolean dashes, String type,
         String message) {
      super();
      this.id = id;
      this.from = from;
      this.to = to;
      this.title = title;
      this.label = label;
      this.dashes = dashes;
      this.type = type;
      this.message = message;
   }

   // used for a transition in a choreography specification and hybrid system behavior
   public NetworkEdge(String id, String from, String to, String title, String label, boolean dashes, String type,
         String message, String participantSendMessage, String participantReceiveMessage) {
      super();
      this.id = id;
      this.from = from;
      this.to = to;
      this.title = title;
      this.label = label;
      this.dashes = dashes;
      this.type = type;
      this.message = message;
      this.participantSendMessage = participantSendMessage;
      this.participantReceiveMessage = participantReceiveMessage;
   }
   
   // used for a transition in a networked system protocol
   public NetworkEdge(String id, String from, String to, String title, String label,
         NetworkAction action) {
      super();
      this.id = id;
      this.from = from;
      this.to = to;
      this.title = title;
      this.label = label;
      this.action = action;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getFrom() {
      return from;
   }

   public void setFrom(String from) {
      this.from = from;
   }

   public String getTo() {
      return to;
   }

   public void setTo(String to) {
      this.to = to;
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

   public boolean isDashes() {
      return dashes;
   }

   public void setDashes(boolean dashes) {
      this.dashes = dashes;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getParticipantSendMessage() {
      return participantSendMessage;
   }

   public void setParticipantSendMessage(String participantSendMessage) {
      this.participantSendMessage = participantSendMessage;
   }

   public String getParticipantReceiveMessage() {
      return participantReceiveMessage;
   }

   public void setParticipantReceiveMessage(String participantReceiveMessage) {
      this.participantReceiveMessage = participantReceiveMessage;
   }
   
   public NetworkAction getAction() {
      return action;
   }

   public void setAction(NetworkAction action) {
      this.action = action;
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
      NetworkEdge other = (NetworkEdge) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      return true;
   }

}
