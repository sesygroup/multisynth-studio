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
package com.sesygroup.choreography.web.common.spring.security;

import org.springframework.security.core.GrantedAuthority;

import com.sesygroup.choreography.web.business.model.Role;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
   private static final long serialVersionUID = -6656718807102452484L;
   private Role role;

   public GrantedAuthorityImpl(Role role) {
      super();
      this.role = role;
   }

   @Override
   public String getAuthority() {
      return role.getName();
   }

   @Override
   public String toString() {
      return "[autority=" + role.getName() + "]";
   }
}
