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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.sesygroup.choreography.choreographyspecification.model.Participant;
import com.sesygroup.choreography.concreteparticipantbehavior.model.ConcreteParticipantBehavior;
import com.sesygroup.choreography.web.business.model.Network;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class CoordinationDelegateGeneratorConverter {

   public static Map<String, ConcreteParticipantBehavior> getCoordinationDelegates(Network network) {
      Map<String, ConcreteParticipantBehavior> participantToConcreteParticipantBehaviorMap
            = new HashMap<String, ConcreteParticipantBehavior>();
      return participantToConcreteParticipantBehaviorMap;
   }

   public static Network getNetwork(
         Map<Pair<Participant, Participant>, ConcreteParticipantBehavior> participantPairToCoordinationDelegateMap) {

      Map<String, ConcreteParticipantBehavior> participantNameToCoordinationDelegateMap
            = participantPairToCoordinationDelegateMap.entrySet().stream()
                  .collect(Collectors.toMap(
                        e -> "CD_" + e.getKey().getLeft().getName() + "." + e.getKey().getRight().getName(),
                        e -> e.getValue()));
      return ConcreteParticipantBehaviorConverter.getNetwork(participantNameToCoordinationDelegateMap);

   }
}
