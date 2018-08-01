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
package com.sesygroup.choreography.networkedsystemprotocol.model.action;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sesygroup.choreography.networkedsystemprotocol.model.Action;
import com.sesygroup.choreography.networkedsystemprotocol.model.parameter.InputParameter;
import com.sesygroup.choreography.networkedsystemprotocol.model.parameter.OutputParameter;

/**
 * 
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 * 
 *         This operation is for a provided interface.
 *
 */
public class RequestResponseAction extends Action implements Serializable {
   private static final long serialVersionUID = -1429841199034374346L;
   private Set<InputParameter> inputParameters;
   private Set<OutputParameter> outputParameters;

   public RequestResponseAction() {
      super();
      inputParameters = new HashSet<InputParameter>();
      outputParameters = new HashSet<OutputParameter>();
   }

   public RequestResponseAction(final String name, final Set<InputParameter> inputParameters,
         final Set<OutputParameter> outputParameters) {
      super(name);
      this.inputParameters = inputParameters;
      this.outputParameters = outputParameters;
   }
   
   public Set<InputParameter> getInputParameters() {
      return inputParameters;
   }

   public void setInputParameters(Set<InputParameter> inputParameters) {
      this.inputParameters = inputParameters;
   }

   public Set<OutputParameter> getOutputParameters() {
      return outputParameters;
   }

   public void setOutputParameters(Set<OutputParameter> outputParameters) {
      this.outputParameters = outputParameters;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((inputParameters == null) ? 0 : inputParameters.hashCode());
      result = prime * result + ((outputParameters == null) ? 0 : outputParameters.hashCode());
      result = prime * result + ((super.operationName == null) ? 0 : super.operationName.hashCode());
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
      RequestResponseAction other = (RequestResponseAction) obj;
      if (inputParameters == null) {
         if (other.inputParameters != null) {
            return false;
         }
      } else if (!inputParameters.equals(other.inputParameters)) {
         return false;
      }
      if (outputParameters == null) {
         if (other.outputParameters != null) {
            return false;
         }
      } else if (!outputParameters.equals(other.outputParameters)) {
         return false;
      }
      if (super.operationName == null) {
         if (other.operationName != null) {
            return false;
         }
      } else if (!super.operationName.equals(other.operationName)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return ("<" + super.operationName + ", " + inputParameters.toString() + ", " + outputParameters.toString() + ", provided>")
            .replaceAll("\\[", "{").replaceAll("\\]", "}");
   }

}
