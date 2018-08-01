/*
 * Copyright 2018 Software Engineering and Synthesis Group
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
package com.sesygroup.choreography.networkedsystemprotocol.model;

import java.io.Serializable;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class Transition implements Serializable {
   private static final long serialVersionUID = 6117608264638661658L;
   private State sourceState;
   private State targetState;
   private Action action;

   public Transition() {
      super();
      sourceState = null;
      targetState = null;
      action = null;
   }

   public Transition(final State sourceState, final State targetState, final Action action) {
      super();
      this.sourceState = sourceState;
      this.targetState = targetState;
      this.action = action;
   }

   public State getSourceState() {
      return sourceState;
   }

   public void setSourceState(final State sourceState) {
      this.sourceState = sourceState;
   }

   public State getTargetState() {
      return targetState;
   }

   public void setTargetState(final State targetState) {
      this.targetState = targetState;
   }

   public Action getAction() {
      return action;
   }

   public void setAction(Action action) {
      this.action = action;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((sourceState == null) ? 0 : sourceState.hashCode());
      result = prime * result + ((targetState == null) ? 0 : targetState.hashCode());
      result = prime * result + ((action == null) ? 0 : action.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Transition other = (Transition) obj;
      if (sourceState == null) {
         if (other.sourceState != null) {
            return false;
         }
      } else if (!sourceState.equals(other.sourceState)) {
         return false;
      }
      if (targetState == null) {
         if (other.targetState != null) {
            return false;
         }
      } else if (!targetState.equals(other.targetState)) {
         return false;
      }
      if (action == null) {
         if (action != null) {
            return false;
         }
      } else if (!action.equals(other.action)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "(" + sourceState + ", " + action.toString() + ", " + targetState + ")";
   }

}
