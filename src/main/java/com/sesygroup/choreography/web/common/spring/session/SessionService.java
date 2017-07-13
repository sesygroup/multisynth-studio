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
package com.sesygroup.choreography.web.common.spring.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.sesygroup.choreography.web.business.model.User;
import com.sesygroup.choreography.web.common.spring.security.UserDetailsImpl;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
@Service
public class SessionService {
   @Autowired
   private SessionRegistry sessionRegistry;

   public void expireUserSession(User user) {
      sessionRegistry.getAllPrincipals().forEach(principal -> {
         if (principal instanceof UserDetailsImpl && ((UserDetailsImpl) principal).getUser().getId().equals(user.getId())){
            sessionRegistry.getAllSessions(principal, false).forEach(userSession -> {
               userSession.expireNow();
            });
         }
      });
   }

   public void updateUserSession(User user) {
      sessionRegistry.getAllPrincipals().forEach(principal -> {
         if (principal instanceof UserDetailsImpl && ((UserDetailsImpl) principal).getUser().getId().equals(user.getId())){
            ((UserDetailsImpl) principal).setUser(user);
            sessionRegistry.getAllSessions(principal, false).forEach(userSession -> {
               ((UserDetailsImpl) userSession.getPrincipal()).setUser(user);
            });
         }
      });
   }

}
