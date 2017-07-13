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
package com.sesygroup.choreography.web.presentation.mock;

import java.util.UUID;

import com.sesygroup.choreography.web.business.model.Network;
import com.sesygroup.choreography.web.business.model.NetworkConstants;
import com.sesygroup.choreography.web.business.model.NetworkEdge;
import com.sesygroup.choreography.web.business.model.NetworkNode;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class CoordinationDelegatesMock {

   public static Network sixCDs() {
      Network network = new Network();
      // CD 1.3
      NetworkNode s013 = new NetworkNode(UUID.randomUUID().toString(), "S0","S0","S0",NetworkConstants.STATE_SOURCE,"P1-P3");
      NetworkNode smid013 = new NetworkNode(UUID.randomUUID().toString(), "Smid0","Smid0","Smid0",NetworkConstants.STATE_GENERIC,"P1-P3");
      NetworkNode s113 = new NetworkNode(UUID.randomUUID().toString(), "S1","S1","S1",NetworkConstants.STATE_GENERIC,"P1-P3");
      NetworkNode ssynch113 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch1","Ssynch1","Ssynch1",NetworkConstants.STATE_GENERIC,"P1-P3");
      NetworkNode s213 = new NetworkNode(UUID.randomUUID().toString(), "S2","S2","S2",NetworkConstants.STATE_GENERIC,"P1-P3");
      NetworkNode s313 = new NetworkNode(UUID.randomUUID().toString(), "S3","S3","S3",NetworkConstants.STATE_GENERIC,"P1-P3");
      NetworkNode s413 = new NetworkNode(UUID.randomUUID().toString(), "S4","S4","S4",NetworkConstants.STATE_GENERIC,"P1-P3");
      NetworkNode s513 = new NetworkNode(UUID.randomUUID().toString(), "S5","S5","S5",NetworkConstants.STATE_FINAL,"P1-P3");
      network.getNodes().add(s013);
      network.getNodes().add(smid013);
      network.getNodes().add(s113);
      network.getNodes().add(ssynch113);
      network.getNodes().add(s213);
      network.getNodes().add(s313);
      network.getNodes().add(s413);
      network.getNodes().add(s513);
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s013.getId(), smid013.getId(), "m1?", "m1?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, "m1"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), smid013.getId(), s113.getId(), "m1!", "m1!", false, NetworkConstants.TRANSITION_SEND_ACTION, "m1"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s113.getId(), ssynch113.getId(), "Synch_{1,3}->{2,3}!", "Synch_{1,3}->{2,3}!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION,""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch113.getId(), s213.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s213.getId(), s313.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s213.getId(), s413.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s213.getId(), s513.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s313.getId(), s413.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s413.getId(), s513.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));

      // CD 2.3
      NetworkNode s023 = new NetworkNode(UUID.randomUUID().toString(), "S0","S0","S0",NetworkConstants.STATE_SOURCE,"P2-P3");
      NetworkNode ssynch023 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch0","Ssynch0","Ssynch0",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode s123 = new NetworkNode(UUID.randomUUID().toString(), "S1","S1","S1",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode smid123 = new NetworkNode(UUID.randomUUID().toString(), "Smid1","Smid1","Smid1",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode ssynch223 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch2","Ssynch2","Ssynch2",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode sbranch223 = new NetworkNode(UUID.randomUUID().toString(), "Sbranch2","Sbranch2","Sbranch2",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode s323 = new NetworkNode(UUID.randomUUID().toString(), "S3","S3","S3",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode s423 = new NetworkNode(UUID.randomUUID().toString(), "S4","S4","S4",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode s223 = new NetworkNode(UUID.randomUUID().toString(), "S2","S2","S2",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode smid223 = new NetworkNode(UUID.randomUUID().toString(), "Smid2","Smid2","Smid2",NetworkConstants.STATE_GENERIC,"P2-P3");
      NetworkNode s523 = new NetworkNode(UUID.randomUUID().toString(), "S5","S5","S5",NetworkConstants.STATE_FINAL,"P2-P3");

      network.getNodes().add(s023);
      network.getNodes().add(ssynch023);
      network.getNodes().add(s123);
      network.getNodes().add(smid123);
      network.getNodes().add(ssynch223);
      network.getNodes().add(sbranch223);
      network.getNodes().add(s323);
      network.getNodes().add(s423);
      network.getNodes().add(s223);
      network.getNodes().add(smid223);
      network.getNodes().add(s523);
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s023.getId(), ssynch023.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch023.getId(), s123.getId(), "Synch_{1,3}->{2,3}?", "Synch_{1,3}->{2,3}?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s123.getId(), smid123.getId(), "m2?", "m2?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, "m2"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), smid123.getId(), ssynch223.getId(), "m2!", "m2!", false, NetworkConstants.TRANSITION_SEND_ACTION,"m2"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch223.getId(), sbranch223.getId(), "Synch_{2,3}->{4,6}{5,6}!", "Synch_{2,3}->{4,6}{5,6}!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch223.getId(), s323.getId(), "Synch_{4,6}->{2,3}?", "Synch_{4,6}->{2,3}?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s323.getId(), s423.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s423.getId(), s523.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch223.getId(), s423.getId(), "Synch_{5,6}->{2,3}?", "Synch_{5,6}->{2,3}?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION,""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch223.getId(), s223.getId(), "Synch_{2,3}->{4,6}{5,6}!", "Synch_{2,3}->{4,6}{5,6}!", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s223.getId(), smid223.getId(), "m5?", "m5?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, "m5"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), smid223.getId(), s523.getId(), "m5!", "m5!", false, NetworkConstants.TRANSITION_SEND_ACTION, "m5"));


      // CD 4.6
      NetworkNode s046 = new NetworkNode(UUID.randomUUID().toString(), "S0","S0","S0",NetworkConstants.STATE_SOURCE,"P4-P6");
      NetworkNode s146 = new NetworkNode(UUID.randomUUID().toString(), "S1","S1","S1",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode ssynch246 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch2","Ssynch2","Ssynch2",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode sbranch246 = new NetworkNode(UUID.randomUUID().toString(), "Sbranch2","Sbranch2","Sbranch2",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode s446 = new NetworkNode(UUID.randomUUID().toString(), "S4","S4","S4",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode s246 = new NetworkNode(UUID.randomUUID().toString(), "S2","S2","S2",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode smid246 = new NetworkNode(UUID.randomUUID().toString(), "Smid2","Smid2","Smid2",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode s346 = new NetworkNode(UUID.randomUUID().toString(), "S3","S3","S3",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode ssynch346 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch3","Ssynch3","Ssynch3",NetworkConstants.STATE_GENERIC,"P4-P6");
      NetworkNode s546 = new NetworkNode(UUID.randomUUID().toString(), "S5","S5","S5",NetworkConstants.STATE_FINAL,"P4-P6");

      network.getNodes().add(s046);
      network.getNodes().add(s146);
      network.getNodes().add(ssynch246);
      network.getNodes().add(sbranch246);
      network.getNodes().add(s446);
      network.getNodes().add(s546);
      network.getNodes().add(s246);
      network.getNodes().add(smid246);
      network.getNodes().add(s346);
      network.getNodes().add(ssynch346);
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s046.getId(), s146.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s146.getId(), ssynch246.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch246.getId(), sbranch246.getId(), "Synch_{2,3}->{4,6}?", "Synch_{2,3}->{4,6}?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch246.getId(), s446.getId(), "Synch_{5,6}->{4,6}?", "Synch_{5,6}->{4,6}?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch246.getId(), s546.getId(), "Synch_{2,3}->{4,6}?", "Synch_{2,3}->{4,6}?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s446.getId(), s546.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch246.getId(), s246.getId(), "Synch_{4,6}->{2,3}{5,6}!", "Synch_{4,6}->{2,3}{5,6}!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s246.getId(), smid246.getId(), "m3?", "m3?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, "m3"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), smid246.getId(), s346.getId(), "m3!", "m3!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION,"m3"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s346.getId(), ssynch346.getId(), "Synch_{4,6}->{3,6}!", "Synch_{4,6}->{3,6}!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch346.getId(), s446.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));


      // CD 5.6
      NetworkNode s056 = new NetworkNode(UUID.randomUUID().toString(), "S0","S0","S0",NetworkConstants.STATE_SOURCE,"P5-P6");
      NetworkNode s156 = new NetworkNode(UUID.randomUUID().toString(), "S1","S1","S1",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode ssynch256 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch2","Ssynch2","Ssynch2",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode sbranch256 = new NetworkNode(UUID.randomUUID().toString(), "Sbranch2","Sbranch2","Sbranch2",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode s356 = new NetworkNode(UUID.randomUUID().toString(), "S3","S3","S3",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode s256 = new NetworkNode(UUID.randomUUID().toString(), "S2","S2","S2",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode smid256 = new NetworkNode(UUID.randomUUID().toString(), "Smid2","Smid2","Smid2",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode s456 = new NetworkNode(UUID.randomUUID().toString(), "S4","S4","S4",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode smid456 = new NetworkNode(UUID.randomUUID().toString(), "Smid4","Smid4","Smid4",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode ssynch456 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch4","Ssynch4","Ssynch4",NetworkConstants.STATE_GENERIC,"P5-P6");
      NetworkNode s556 = new NetworkNode(UUID.randomUUID().toString(), "S5","S5","S5",NetworkConstants.STATE_FINAL,"P5-P6");

      network.getNodes().add(s056);
      network.getNodes().add(s156);
      network.getNodes().add(ssynch256);
      network.getNodes().add(sbranch256);
      network.getNodes().add(s356);
      network.getNodes().add(s556);
      network.getNodes().add(s256);
      network.getNodes().add(smid256);
      network.getNodes().add(s456);
      network.getNodes().add(smid456);
      network.getNodes().add(ssynch456);
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s056.getId(), s156.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s156.getId(), ssynch256.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch256.getId(), sbranch256.getId(), "Synch_{2,3}->{5,6}?", "Synch_{2,3}->{5,6}?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch256.getId(), s356.getId(), "Synch_{4,6}->{5,6}?", "Synch_{4,6}->{5,6}?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch256.getId(), s556.getId(), "Synch_{2,3}->{5,6}?", "Synch_{2,3}->{5,6}?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), sbranch256.getId(), s256.getId(), "Synch_{5,6}->{2,3}{4,6}!", "Synch_{5,6}->{2,3}{4,6}!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s256.getId(), smid256.getId(), "m4?", "m4?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, "m4"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), smid256.getId(), s456.getId(), "m4!", "m4!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION,"m4"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s456.getId(), smid456.getId(), "m7?", "m7?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, "m7"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), smid456.getId(), s556.getId(), "m7!", "m7!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION, "m7"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s356.getId(), ssynch456.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch456.getId(), s456.getId(), "Synch_{3,6}->{5,6}?", "Synch_{3,6}->{5,6}?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION, ""));


      // CD 3.6
      NetworkNode s036 = new NetworkNode(UUID.randomUUID().toString(), "S0","S0","S0",NetworkConstants.STATE_SOURCE,"P3-P6");
      NetworkNode s136 = new NetworkNode(UUID.randomUUID().toString(), "S1","S1","S1",NetworkConstants.STATE_GENERIC,"P3-P6");
      NetworkNode s236 = new NetworkNode(UUID.randomUUID().toString(), "S2","S2","S2",NetworkConstants.STATE_GENERIC,"P3-P6");
      NetworkNode s436 = new NetworkNode(UUID.randomUUID().toString(), "S4","S4","S4",NetworkConstants.STATE_GENERIC,"P3-P6");
      NetworkNode ssynch336 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch3","Ssynch3","Ssynch3",NetworkConstants.STATE_GENERIC,"P3-P6");
      NetworkNode ssynch436 = new NetworkNode(UUID.randomUUID().toString(), "Ssynch4","Ssynch4","Ssynch4",NetworkConstants.STATE_GENERIC,"P3-P6");
      NetworkNode s336 = new NetworkNode(UUID.randomUUID().toString(), "S3","S3","S3",NetworkConstants.STATE_GENERIC,"P3-P6");
      NetworkNode smid336 = new NetworkNode(UUID.randomUUID().toString(), "Smid3","Smid3","Smid3",NetworkConstants.STATE_GENERIC,"P3-P6");
      NetworkNode s536 = new NetworkNode(UUID.randomUUID().toString(), "S5","S5","S5",NetworkConstants.STATE_FINAL,"P3-P6");

      network.getNodes().add(s036);
      network.getNodes().add(s136);
      network.getNodes().add(s236);
      network.getNodes().add(s436);
      network.getNodes().add(s536);
      network.getNodes().add(ssynch336);
      network.getNodes().add(ssynch436);
      network.getNodes().add(s336);
      network.getNodes().add(smid336);
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s036.getId(), s136.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s136.getId(), s236.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s236.getId(), ssynch336.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s236.getId(), s536.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s236.getId(), ssynch436.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch336.getId(), s336.getId(), "Synch_{4,6}->{3,6}?", "Synch_{4,6}->{3,6}?", false, NetworkConstants.TRANSITION_SYNCH_RECEIVE_ACTION,""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s336.getId(), smid336.getId(), "m6?", "m6?", false, NetworkConstants.TRANSITION_RECEIVE_ACTION, "m6"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), smid336.getId(), s436.getId(), "m6!", "m6!", false, NetworkConstants.TRANSITION_SEND_ACTION, "m6"));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), s436.getId(), ssynch436.getId(), "Synch_{3,6}->{5,6}!", "Synch_{3,6}->{5,6}!", false, NetworkConstants.TRANSITION_SYNCH_SEND_ACTION,""));
      network.getEdges().add(new NetworkEdge(UUID.randomUUID().toString(), ssynch436.getId(), s536.getId(), NetworkConstants.TRANSITION_EPSILON, NetworkConstants.TRANSITION_EPSILON, false, NetworkConstants.TRANSITION_INTERNAL_ACTION, ""));

      return network;
   }
}
