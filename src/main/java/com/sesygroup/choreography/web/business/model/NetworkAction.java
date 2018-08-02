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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class NetworkAction implements java.io.Serializable {
   private static final long serialVersionUID = 6050345699211484663L;
   private List<String> inputParameters = new ArrayList<String>();
   private List<String> outputParameters = new ArrayList<String>();
   private String operationName;
   private String type;

   public NetworkAction() {
      super();
   }

   // used for request-response and solicit-response operations
   public NetworkAction(List<String> inputParameters, List<String> outputParameters, String operationName,
         String type) {
      super();
      this.inputParameters = inputParameters;
      this.outputParameters = outputParameters;
      this.operationName = operationName;
      this.type = type;
   }
   
   // used for one-way and notification operations   
   public NetworkAction(List<String> inputParameters, String operationName,
         String type) {
      super();
      this.inputParameters = inputParameters;
      this.operationName = operationName;
      this.type = type;
   }

   public List<String> getInputParameters() {
      return inputParameters;
   }

   public void setInputParameters(List<String> inputParameters) {
      this.inputParameters = inputParameters;
   }

   public List<String> getOutputParameters() {
      return outputParameters;
   }

   public void setOutputParameters(List<String> outputParameters) {
      this.outputParameters = outputParameters;
   }

   public String getOperationName() {
      return operationName;
   }

   public void setOperationName(String operationName) {
      this.operationName = operationName;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((inputParameters == null) ? 0 : inputParameters.hashCode());
      result = prime * result + ((operationName == null) ? 0 : operationName.hashCode());
      result = prime * result + ((outputParameters == null) ? 0 : outputParameters.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
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
      NetworkAction other = (NetworkAction) obj;
      if (inputParameters == null) {
         if (other.inputParameters != null)
            return false;
      } else if (!inputParameters.equals(other.inputParameters))
         return false;
      if (operationName == null) {
         if (other.operationName != null)
            return false;
      } else if (!operationName.equals(other.operationName))
         return false;
      if (outputParameters == null) {
         if (other.outputParameters != null)
            return false;
      } else if (!outputParameters.equals(other.outputParameters))
         return false;
      if (type == null) {
         if (other.type != null)
            return false;
      } else if (!type.equals(other.type))
         return false;
      return true;
   }


}
