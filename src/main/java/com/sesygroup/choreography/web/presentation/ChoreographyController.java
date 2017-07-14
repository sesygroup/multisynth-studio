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
package com.sesygroup.choreography.web.presentation;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sesygroup.choreography.abstractparticipantbehavior.generator.ChoreographyProjection;
import com.sesygroup.choreography.abstractparticipantbehavior.model.AbstractParticipantBehavior;
import com.sesygroup.choreography.choreographyspecification.model.Participant;
import com.sesygroup.choreography.concreteparticipantbehavior.model.ConcreteParticipantBehavior;
import com.sesygroup.choreography.coordinationlogic.extractor.CoordinationLogicExtractor;
import com.sesygroup.choreography.coordinationlogic.realizer.CoordinationLogicRealizer;
import com.sesygroup.choreography.hybridsystembehavior.generator.HybridSystemBehaviorGenerator;
import com.sesygroup.choreography.hybridsystembehavior.model.HybridSystemBehavior;
import com.sesygroup.choreography.web.business.GenericResponseBody;
import com.sesygroup.choreography.web.business.GenericResponseBody.GenericResponseBodyState;
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkMultiple;
import com.sesygroup.choreography.web.business.model.NetworkNode;
import com.sesygroup.choreography.web.common.utility.AbstractParticipantBehaviorConverter;
import com.sesygroup.choreography.web.common.utility.ChoreographySpecificationConverter;
import com.sesygroup.choreography.web.common.utility.ConcreteParticipantBehaviorConverter;
import com.sesygroup.choreography.web.common.utility.CoordinationDelegateGeneratorConverter;
import com.sesygroup.choreography.web.common.utility.HybridSystemBehaviorConverter;
import com.sesygroup.choreography.web.common.utility.NetworkUtils;
import com.sesygroup.choreography.web.presentation.mock.ChoreographySpecificationMock;
import com.sesygroup.choreography.web.presentation.mock.ConcreteParticipantsBehaviorMock;
import com.sesygroup.choreography.web.presentation.mock.CoordinationDelegatesMock;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
@Controller
@RequestMapping("/choreography")
public class ChoreographyController {

   @RequestMapping("/hybridsystembehavior")
   public String showHybridSystemBehavior() {
      return "choreography.hybridsystembehavior";
   }

   @RequestMapping("/abstractparticipantbehavior")
   public String showAbstractParticipantBehavior() {
      return "choreography.abstractparticipantbehavior";
   }

   @RequestMapping("/coordinationdelegate")
   public String showCoordinationDelegate() {
      return "choreography.coordinationdelegate";
   }

   @RequestMapping("/choreographedsystem")
   public String showChoreograpedSystem() {
      return "choreography.hybridsystembehavior";
   }

   @RequestMapping("/synthesis")
   public String showChoreographySynthesis() {
      return "choreography.synthesis";
   }

   // Choreography specification
   @RequestMapping("/choreographyspecification/loadsample")
   public @ResponseBody GenericResponseBody choreographySpecificationLoadSample() throws IOException {
      return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
            ChoreographySpecificationConverter.getNetwork(ChoreographySpecificationMock.sample()));
   }

   // Projection
   @RequestMapping(value = "/choreographyspecification/project", method = RequestMethod.POST,
         consumes = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody GenericResponseBody choreographySpecificationProject(@RequestBody Network network)
         throws IOException {
      try {

         Map<Participant, AbstractParticipantBehavior> participantToAbstractParticipantBehaviorMap
               = new ChoreographyProjection()
                     .projectAll(ChoreographySpecificationConverter.getChoreographySpecification(network));

         return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
               AbstractParticipantBehaviorConverter.getNetwork(participantToAbstractParticipantBehaviorMap));

      } catch (Exception exception) {
         return new GenericResponseBody(GenericResponseBodyState.ERROR, exception);
      }
   }

   // Coordination logic extraction
   @RequestMapping("/abstractcoordinationlogic/loadFive")
   public @ResponseBody GenericResponseBody abstractCoordinationLogicLoadFive() throws IOException {
      return new GenericResponseBody(GenericResponseBodyState.SUCCESS, CoordinationDelegatesMock.sixCDs());
   }

   @RequestMapping(value = "/abstractcoordinationlogic/extract", method = RequestMethod.POST,
         consumes = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody GenericResponseBody abstractCoordinationLogicExtract(@RequestBody Network network)
         throws IOException {
      try {
         Map<Pair<Participant, Participant>, AbstractParticipantBehavior> abstractCoordinationLogic
               = new CoordinationLogicExtractor(
                     ChoreographySpecificationConverter.getChoreographySpecification(network)).generate();

         return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
               AbstractParticipantBehaviorConverter.getNetworkFromParticipantsPair(abstractCoordinationLogic));

      } catch (Exception exception) {
         return new GenericResponseBody(GenericResponseBodyState.ERROR, exception);
      }
   }

   // Concrete participant behavior
   @RequestMapping("/concreteparticipantbehavior/loadthree")
   public @ResponseBody GenericResponseBody concreteParticipantBehaviorLoadThree() throws IOException {
      Map<String, ConcreteParticipantBehavior> participantToConcreteParticipantBehaviorMap
            = new HashMap<String, ConcreteParticipantBehavior>();
      participantToConcreteParticipantBehaviorMap.put("p1", ConcreteParticipantsBehaviorMock.p1());
      participantToConcreteParticipantBehaviorMap.put("p2", ConcreteParticipantsBehaviorMock.p2());
      participantToConcreteParticipantBehaviorMap.put("p3", ConcreteParticipantsBehaviorMock.p3());
      return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
            ConcreteParticipantBehaviorConverter.getNetwork(participantToConcreteParticipantBehaviorMap));
   }

   @RequestMapping("/concreteparticipantbehavior/loadsix")
   public @ResponseBody GenericResponseBody concreteParticipantBehaviorLoadSix() throws IOException {
      Map<String, ConcreteParticipantBehavior> participantToConcreteParticipantBehaviorMap
            = new HashMap<String, ConcreteParticipantBehavior>();
      participantToConcreteParticipantBehaviorMap.put("p1", ConcreteParticipantsBehaviorMock.p1());
      participantToConcreteParticipantBehaviorMap.put("p2", ConcreteParticipantsBehaviorMock.p2());
      participantToConcreteParticipantBehaviorMap.put("p3", ConcreteParticipantsBehaviorMock.p3());
      participantToConcreteParticipantBehaviorMap.put("p4", ConcreteParticipantsBehaviorMock.p4());
      participantToConcreteParticipantBehaviorMap.put("p5", ConcreteParticipantsBehaviorMock.p5());
      participantToConcreteParticipantBehaviorMap.put("p6", ConcreteParticipantsBehaviorMock.p6());
      return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
            ConcreteParticipantBehaviorConverter.getNetwork(participantToConcreteParticipantBehaviorMap));
   }

   // Coordination delegate
   @RequestMapping("/coordinationdelegate/loadFive")
   public @ResponseBody GenericResponseBody coordinationDelegateLoadFive() throws IOException {
      return new GenericResponseBody(GenericResponseBodyState.SUCCESS, CoordinationDelegatesMock.sixCDs());
   }

   @RequestMapping(value = "/coordinationdelegate/generate", method = RequestMethod.POST,
         consumes = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody GenericResponseBody coordinationDelegateGenerate(@RequestBody NetworkMultiple networks)
         throws IOException {
      try {

         Map<Pair<Participant, Participant>, ConcreteParticipantBehavior> cds = new CoordinationLogicRealizer(
               ChoreographySpecificationConverter.getChoreographySpecification(networks.getChoreographySpecification()),
               ConcreteParticipantBehaviorConverter
                     .getConcreteParticipantBehaviors(networks.getConcreteParticipantsBehavior())).realize();

         return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
               CoordinationDelegateGeneratorConverter.getNetwork(cds));

      } catch (Exception exception) {
         return new GenericResponseBody(GenericResponseBodyState.ERROR, exception);
      }
   }

   // Hybrid system behavior
   @RequestMapping(value = "/hybridsystembehavior/generate", method = RequestMethod.POST,
         consumes = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody GenericResponseBody hybridSystemBehaviorGenerate(@RequestBody Network network)
         throws IOException {

      Map<com.sesygroup.choreography.hybridsystembehavior.model.Participant, ConcreteParticipantBehavior> participantToConcreteParticipantBehaviorMap
            = new HashMap<com.sesygroup.choreography.hybridsystembehavior.model.Participant, ConcreteParticipantBehavior>();

      Map<com.sesygroup.choreography.hybridsystembehavior.model.Participant, Integer> participantToMessageQueueSizeMap
            = new HashMap<com.sesygroup.choreography.hybridsystembehavior.model.Participant, Integer>();

      ConcreteParticipantBehaviorConverter.getConcreteParticipantBehaviors(network).forEach((key, value) -> {
         participantToConcreteParticipantBehaviorMap
               .put(new com.sesygroup.choreography.hybridsystembehavior.model.Participant(key.getName()), value);
         // TODO fix assignment message queue, for now I fix to put 1 to except for participants: P4, P5, P6
         participantToMessageQueueSizeMap.put(
               new com.sesygroup.choreography.hybridsystembehavior.model.Participant(key.getName()),
               (Arrays.asList("P4", "P5", "P6").contains(key))
                     ? 0
                     : 1);
      });

      try {
         HybridSystemBehaviorGenerator hybridSystemBehaviorGenerator = new HybridSystemBehaviorGenerator(
               participantToConcreteParticipantBehaviorMap, participantToMessageQueueSizeMap);

         HybridSystemBehavior hybridSystemBehavior = hybridSystemBehaviorGenerator.generate();
         return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
               HybridSystemBehaviorConverter.getNetwork(hybridSystemBehavior));

      } catch (Exception exception) {
         return new GenericResponseBody(GenericResponseBodyState.ERROR, exception);
      }
   }

   @RequestMapping(value = "/hybridsystembehavior/find/reachability", method = RequestMethod.POST,
         consumes = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody GenericResponseBody hybridSystemBehaviorReachability(@RequestBody Network network,
         @RequestParam(name = "targetNode") NetworkNode targetNode) throws IOException {

      return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
            NetworkUtils.getReachability(network, targetNode));

   }
}
