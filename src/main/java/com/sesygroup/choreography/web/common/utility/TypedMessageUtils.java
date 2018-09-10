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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class TypedMessageUtils {
   private static final String EMPTY_TYPE = "The type must not be empty";

   private static final List<String> xsdStringTypes = new ArrayList<String>(
         Arrays.asList("string", "normalizedString"));

   private static final List<String> xsdDateTypes = new ArrayList<String>(Arrays.asList("date", "time", "dateTime"));

   private static final List<String> xsdNumericTypes = new ArrayList<String>(
         Arrays.asList("byte", "decimal", "int", "integer", "long", "short"));

   private static final List<String> xsdMiscTypes = new ArrayList<String>(
         Arrays.asList("anyURI", "base64Binary", "hexBinary", "boolean", "double", "float"));

   public static boolean isBasicType(final String type) {
      Validate.notEmpty(type, EMPTY_TYPE);
      return xsdStringTypes.contains(type) || xsdDateTypes.contains(type) || xsdNumericTypes.contains(type)
            || xsdMiscTypes.contains(type) ? true : false;
   }
}
