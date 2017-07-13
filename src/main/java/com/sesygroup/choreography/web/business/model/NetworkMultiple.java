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
public class NetworkMultiple implements java.io.Serializable {
   private static final long serialVersionUID = -5321903959980069976L;

   private Network choreographySpecification;
   private Network abstractParticipantsBehavior;
   private Network concreteParticipantsBehavior;
   private Network coordinationDelegates;
   private Network hybridSystemBehaviour;

   public NetworkMultiple() {
      super();
      choreographySpecification = new Network();
      abstractParticipantsBehavior = new Network();
      concreteParticipantsBehavior = new Network();
      coordinationDelegates = new Network();
      hybridSystemBehaviour = new Network();
   }

   public Network getChoreographySpecification() {
      return choreographySpecification;
   }

   public void setChoreographySpecification(Network choreographySpecification) {
      this.choreographySpecification = choreographySpecification;
   }

   public Network getAbstractParticipantsBehavior() {
      return abstractParticipantsBehavior;
   }

   public void setAbstractParticipantsBehavior(Network abstractParticipantsBehavior) {
      this.abstractParticipantsBehavior = abstractParticipantsBehavior;
   }

   public Network getConcreteParticipantsBehavior() {
      return concreteParticipantsBehavior;
   }

   public void setConcreteParticipantsBehavior(Network concreteParticipantsBehavior) {
      this.concreteParticipantsBehavior = concreteParticipantsBehavior;
   }

   public Network getCoordinationDelegates() {
      return coordinationDelegates;
   }

   public void setCoordinationDelegates(Network coordinationDelegates) {
      this.coordinationDelegates = coordinationDelegates;
   }

   public Network getHybridSystemBehaviour() {
      return hybridSystemBehaviour;
   }

   public void setHybridSystemBehaviour(Network hybridSystemBehaviour) {
      this.hybridSystemBehaviour = hybridSystemBehaviour;
   }



}
