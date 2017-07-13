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

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sesygroup.choreography.web.business.UserService;
import com.sesygroup.choreography.web.business.model.User;
import com.sesygroup.choreography.web.common.spring.security.AuthenticationHolder;
import com.sesygroup.choreography.web.common.spring.session.SessionService;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {
   @Autowired
   private SessionService sessionService;

   @Autowired
   private UserService userService;

   @RequestMapping(value = "/account", method = { RequestMethod.GET })
   public String showUpdateAccount(Model model) {
      User user = new AuthenticationHolder().getAuthenticatedUser();
      model.addAttribute("user", user);
      return "settings.account";
   }

   @RequestMapping(value = "/account", method = { RequestMethod.POST })
   public String updateAccount(@ModelAttribute User user) {
      User persistentUser = userService.findByPK(user.getId());
      persistentUser.setFirstname(user.getFirstname());
      persistentUser.setLastname(user.getLastname());
      persistentUser.setEmail(user.getEmail());
      userService.update(persistentUser);

      // update user information without expiring her session
      sessionService.updateUserSession(persistentUser);

      return "redirect:/settings/account?success=true";
   }

   @RequestMapping(value = "/password", method = { RequestMethod.GET })
   public String showUpdatePassword(Model model) {
      User user = new AuthenticationHolder().getAuthenticatedUser();
      model.addAttribute("user", user);
      return "settings.password";
   }

   @RequestMapping(value = "/password", method = { RequestMethod.POST })
   public String updatePassword(@ModelAttribute User user, @RequestParam String newPassword) {
      User persistentUser = userService.findByPK(user.getId());
      persistentUser.setPassword(DigestUtils.md5Hex(newPassword));
      userService.update(persistentUser);

      // update user information without expiring her session
      sessionService.updateUserSession(persistentUser);

      return "redirect:/settings/password?success=true";
   }
}
