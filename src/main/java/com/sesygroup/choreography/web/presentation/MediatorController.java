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
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sesygroup.choreography.abstractparticipantbehavior.generator.ChoreographyProjection;
import com.sesygroup.choreography.abstractparticipantbehavior.model.AbstractParticipantBehavior;
import com.sesygroup.choreography.choreographyspecification.model.Participant;
import com.sesygroup.choreography.web.business.GenericResponseBody;
import com.sesygroup.choreography.web.business.GenericResponseBody.GenericResponseBodyState;
import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.common.utility.AbstractParticipantBehaviorConverter;
import com.sesygroup.choreography.web.common.utility.ChoreographySpecificationConverter;
import com.sesygroup.choreography.web.common.utility.NetworkedSystemProtocolConverter;
import com.sesygroup.choreography.web.presentation.mock.NetworkedSystemProtocolMock;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
@Controller
@RequestMapping("/mediator")
public class MediatorController {

   @RequestMapping("/synthesis")
   public String showChoreographySynthesis() {
      return "mediator.synthesis";
   }

   // Service Blue Client
   @RequestMapping("/nsp/loadblueclient")
   public @ResponseBody GenericResponseBody serviceLoadBlueClient() throws IOException {
      return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
            NetworkedSystemProtocolConverter.getNetwork(NetworkedSystemProtocolMock.blueClientProtocol()));
   }

   // Moon Service
   @RequestMapping("/nsp/loadmoonservice")
   public @ResponseBody GenericResponseBody serviceLoadMoonService() throws IOException {
      return new GenericResponseBody(GenericResponseBodyState.SUCCESS,
            NetworkedSystemProtocolConverter.getNetwork(NetworkedSystemProtocolMock.moonServiceProtocol()));
   }

   // Test Moon Service
   @RequestMapping(value = "/nsp/testmoonservice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody GenericResponseBody choreographySpecificationProject(@RequestBody Network network)
         throws IOException {
      try {

         if (NetworkedSystemProtocolConverter.getNetworkedSystemProtocol(network)
               .equals(NetworkedSystemProtocolMock.moonServiceProtocol())) {
            return new GenericResponseBody(GenericResponseBodyState.SUCCESS);
         } else {
            return new GenericResponseBody(GenericResponseBodyState.ERROR, "false equals");
         }
      } catch (Exception exception) {
         return new GenericResponseBody(GenericResponseBodyState.ERROR, exception);
      }
   }

}
