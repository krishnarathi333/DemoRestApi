/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.mindBowser.controller;

import com.mindBowser.exception.ResourceNotFoundException;
import com.mindBowser.model.User;
import com.mindBowser.repository.ManagerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type User controller.
 *
 * @author krishna
 */
@RestController
@RequestMapping("/api/mindBowser")
public class UserController {

  @Autowired
  private ManagerRepository managerRepository;

  /**
   * @return the list of managers Detailas
   */
  @GetMapping("/allManagerDetails")
  public List<User> getAllManagersDetails() {
    return managerRepository.findAll();
  }

  /**
   * Gets manager by mgrId.
   *
   * @param managerId the user id
   * @return the managerDetails by mgrId
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/managerDetails/{mgrId}")
  public ResponseEntity<User> getUsersById(@PathVariable(value = "mgrId") Long managerId)
      throws ResourceNotFoundException {
    User user =
        managerRepository
            .findById(managerId)
            .orElseThrow(() -> new ResourceNotFoundException("Manager Details not found on :: " + managerId));
    return ResponseEntity.ok().body(user);
  }

  /**
   * Create user Manager Record.
   *
   * @param user the mgrDetails
   * @return the mgrDetails
   */
  @PostMapping("/managerDetails")
  public User createUser(@Valid @RequestBody User mgrDetails) {
    return managerRepository.save(mgrDetails);
  }

  /**
   * Update user response entity.
   *
   * @param userId the user mgrId
   * @param managerDetails the user details
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/managerDetails/{mgrId}")
  public ResponseEntity<User> updateUser(
      @PathVariable(value = "mgrId") Long mgrId, @Valid @RequestBody User managerDetails)
      throws ResourceNotFoundException {

    User user =
        managerRepository
            .findById(mgrId)
            .orElseThrow(() -> new ResourceNotFoundException("manager Details not found on :: " + mgrId));

    user.setEmail(managerDetails.getEmail());
    user.setLastName(managerDetails.getLastName());
    user.setFirstName(managerDetails.getFirstName());
    user.setPassword(managerDetails.getPassword());
    user.setCompanyName(managerDetails.getCompanyName());
    final User updatedUser = managerRepository.save(user);
    return ResponseEntity.ok(updatedUser);
  }

  /**
   * Delete user map.
   *
   * @param managerId the user id
   * @return the map
   * @throws Exception the exception
   */
  @DeleteMapping("/managerDetail/{mgrId}")
  public Map<String, Boolean> deleteUser(@PathVariable(value = "mgrId") Long managerId) throws Exception {
    User user =
        managerRepository
            .findById(managerId)
            .orElseThrow(() -> new ResourceNotFoundException("manager Details not found on :: " + managerId));

    managerRepository.delete(user);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
