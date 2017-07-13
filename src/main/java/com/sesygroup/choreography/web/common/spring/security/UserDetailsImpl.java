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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sesygroup.choreography.web.business.model.Role;
import com.sesygroup.choreography.web.business.model.User;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class UserDetailsImpl implements UserDetails {
   private static final long serialVersionUID = 8851321994996179060L;
   private User user;

   public UserDetailsImpl(User user) {
      super();
      this.user = user;
   }

   @Override
   public Collection<GrantedAuthority> getAuthorities() {
      List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
      for (Role role : user.getRoles()) {
         result.add(new GrantedAuthorityImpl(role));
      }
      return result;
   }

   @Override
   public String getPassword() {
      return this.user.getPassword();
   }

   @Override
   public String getUsername() {
      return user.getUsername();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return !user.isPasswordExpired();
   }

   @Override
   public boolean isEnabled() {
      return user.isActive();
   }

   @Override
   public String toString() {
      return "UserDetailsImpl [username=" + this.user.getUsername() + "]";
   }

   public User getUser() {
      return this.user;
   }

   public User setUser(User user) {
      return this.user = user;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((user == null) ? 0 : user.hashCode());
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
      UserDetailsImpl other = (UserDetailsImpl) obj;
      if (user == null) {
         if (other.user != null)
            return false;
      } else if (!user.equals(other.user))
         return false;
      return true;
   }


}
