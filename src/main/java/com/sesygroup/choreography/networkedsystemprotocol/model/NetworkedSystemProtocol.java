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
import java.util.HashSet;
import java.util.Set;

public class NetworkedSystemProtocol implements Serializable {
   private static final long serialVersionUID = 8815617060540221440L;
   private Set<State> states;
   private State initialState;
   private Set<State> finalStates;
   private Set<Action> actions;
   private Set<Transition> transitions;

   public NetworkedSystemProtocol() {
      super();
      states = new HashSet<State>();
      initialState = null;
      finalStates = new HashSet<State>();
      actions = new HashSet<Action>();
      transitions = new HashSet<Transition>();
   }

   public NetworkedSystemProtocol(final Set<State> states, final State initialState, final Set<State> finalStates,
         final Set<Action> actions, final Set<Transition> transitions) {
      super();
      this.states = states;
      this.initialState = initialState;
      this.finalStates = finalStates;
      this.actions = actions;
      this.transitions = transitions;
   }

   public Set<State> getStates() {
      return states;
   }

   public void setStates(Set<State> states) {
      this.states = states;
   }

   public State getInitialState() {
      return initialState;
   }

   public void setInitialState(State initialState) {
      this.initialState = initialState;
   }

   public Set<State> getFinalStates() {
      return finalStates;
   }

   public void setFinalStates(Set<State> finalStates) {
      this.finalStates = finalStates;
   }

   public Set<Action> getActions() {
      return actions;
   }

   public void setActions(Set<Action> actions) {
      this.actions = actions;
   }

   public Set<Transition> getTransitions() {
      return transitions;
   }

   public void setTransitions(Set<Transition> transitions) {
      this.transitions = transitions;
   }

   /**
    * Checks whether the SourceState and TargetState of the transition are
    * contained in the set of states. Checks whether the initialState is contained
    * in the set of states. Checks whether all finalStates are contained
    * in the set of states.
    *
    * @return {@code true} if the NetworkedSystemProtocol is validated,
    *         {@code false} otherwise
    */
   public boolean validate() {
      // TODO improve validate method

      // checks whether the NetworkedSystemProtocol has an empty set of states,
      // finalStates, actions, transitions and the
      // initialState is equals to null
      if (states.isEmpty() && initialState == null && finalStates.isEmpty() && actions.isEmpty()
            && transitions.isEmpty()) {
         return true;
      }

      for (State state : finalStates) {
         if (!states.contains(state)) {
            // TODO throw new ValidationException("The final state "+ state.getName() +" is
            // not contained in the set of states");
            return false;
         }
      }

      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((states == null) ? 0 : states.hashCode());
      result = prime * result + ((initialState == null) ? 0 : initialState.hashCode());
      result = prime * result + ((finalStates == null) ? 0 : finalStates.hashCode());
      result = prime * result + ((finalStates == null) ? 0 : actions.hashCode());
      result = prime * result + ((actions == null) ? 0 : transitions.hashCode());
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
      NetworkedSystemProtocol other = (NetworkedSystemProtocol) obj;
      if (states == null) {
         if (other.states != null) {
            return false;
         }
      } else if (!states.equals(other.states)) {
         return false;
      }
      if (initialState == null) {
         if (other.initialState != null) {
            return false;
         }
      } else if (!initialState.equals(other.initialState)) {
         return false;
      }
      if (finalStates == null) {
         if (other.finalStates != null) {
            return false;
         }
      } else if (!finalStates.equals(other.finalStates)) {
         return false;
      }
      if (actions == null) {
         if (other.actions != null) {
            return false;
         }
      } else if (!actions.equals(other.actions)) {
         return false;
      }
      if (transitions == null) {
         if (other.transitions != null) {
            return false;
         }
      } else if (!transitions.equals(other.transitions)) {
         return false;
      }
      return true;
   }

}
