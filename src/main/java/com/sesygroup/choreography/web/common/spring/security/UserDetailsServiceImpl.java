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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sesygroup.choreography.web.business.BusinessException;
import com.sesygroup.choreography.web.business.UserService;
import com.sesygroup.choreography.web.business.model.User;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {


   @Autowired
   private UserService userService;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user;
      try {
         user = userService.findByUsername(username);
      } catch (BusinessException e) {
         throw new UsernameNotFoundException("User not found.");
      }

      if (user == null) {
         throw new UsernameNotFoundException("User not found.");
      }

      return new UserDetailsImpl(user);
   }



}
