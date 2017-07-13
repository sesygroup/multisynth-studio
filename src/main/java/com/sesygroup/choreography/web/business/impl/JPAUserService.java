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
package com.sesygroup.choreography.web.business.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesygroup.choreography.web.business.BusinessException;
import com.sesygroup.choreography.web.business.UserService;
import com.sesygroup.choreography.web.business.model.User;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
@Service
public class JPAUserService extends JPACRUDService<Long, User> implements UserService {

   @Override
   @Transactional
   public User findByUsername(String username) throws BusinessException {
      try{
         Query query = em.createQuery("from User u where u.username = :username");
         query.setParameter("username", username);
         return (User) query.getSingleResult();
      } catch (NoResultException e) {
         throw new BusinessException("No Result", e);
      }
   }

   @Override
   @Transactional
   public User findByEmail(String email) throws BusinessException {
      try{
         Query query = em.createQuery("from User u where u.email = :email");
         query.setParameter("email", email);
         return (User) query.getSingleResult();
      } catch (NoResultException e) {
         throw new BusinessException("No Result", e);
      }
   }

}
