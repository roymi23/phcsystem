package com.phc.identity.controllers;

import com.phc.identity.dto.ChangePasswordRequest;
import com.phc.identity.dto.UserActionResult;
import com.phc.identity.entity.UserIdentity;
import com.phc.identity.exception.UserNotFoundException;
import com.phc.identity.service.UserIdentityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "identityapi")
@RequestMapping("/phc")
@CrossOrigin(origins = "http://localhost:3000")
public class IdentityController {

    @Autowired
    private UserIdentityService userIdentityService;

    @GetMapping("/identity")
    public String entry() {
        return "Welcome";
    }

    @PostMapping("/identity/register")
    public ResponseEntity<UserIdentity> registerNewUser(@RequestBody UserIdentity userIdentity) {
        UserIdentity storedUserIdentity = userIdentityService.createUserIdentity(userIdentity);
        if (userIdentity != null && userIdentity.getId() > 0) {
            return new ResponseEntity<>(userIdentity, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(UserIdentity.builder().build(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/identity/password")
    public ResponseEntity<UserActionResult> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {

        Optional<UserIdentity> updatedIdentity = userIdentityService.changePassword(changePasswordRequest);

        String feedback =
                updatedIdentity.isPresent() ? String.format("Password successfully updated for %s", changePasswordRequest.getUserName())
                        : "Password update failed!";

        final UserActionResult result = UserActionResult.builder()
                .actionName("Change Password")
                .success(true)
                .errorMessage("None")
                .message(feedback).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/identity/change")
    public ResponseEntity<UserActionResult> updateIdentity(@RequestBody UserIdentity userIdentity) {

        Optional<UserIdentity> updatedIdentity = userIdentityService.updateIdentity(userIdentity);

        String feedback =
                updatedIdentity.isPresent() ? String.format("Identity successfully updated for %s", userIdentity.getUserName())
                        : "Identity update failed!";

        final UserActionResult result = UserActionResult.builder()
                .actionName("Update Identity")
                .success(true)
                .errorMessage("None")
                .message(feedback).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/identity")
    public ResponseEntity<UserActionResult> deleteIdentity(@RequestBody UserIdentity userIdentity) {

        Boolean result = userIdentityService.deleteIdentity(userIdentity);

        String feedback =
                result ? String.format("Identity successfully deleted for %s", userIdentity.getUserName())
                        : "Identity delete failed!";

        final UserActionResult actionResult = UserActionResult.builder()
                .actionName("Change Password")
                .success(true)
                .errorMessage("None")
                .message(feedback).build();

        return new ResponseEntity<>(actionResult, HttpStatus.OK);
    }

    @PostMapping("/identity/admin/loadjson")
    public ResponseEntity<UserActionResult> loadIdentitiesFromJson() {

        UserActionResult result = UserActionResult.builder()
                .actionName("loadIdentityJson")
                .success(true)
                .errorMessage("None").build();
        long count = userIdentityService.loadIdentitiesFromJson();
        result.setMessage(String.format("%d identities were saved!", count));
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/identity/all")
    public ResponseEntity<List<UserIdentity>> getAllUserIdentities() {
        List<UserIdentity> identities = userIdentityService.getAllIdentities();
        return new ResponseEntity<>(identities, HttpStatus.ACCEPTED);
    }

    @GetMapping("/identity/{id}")
    public ResponseEntity<UserIdentity> getById(@PathVariable int id) throws UserNotFoundException {
        UserIdentity identity = userIdentityService.getById(id);
        return new ResponseEntity<>(identity, HttpStatus.ACCEPTED);
    }


    @GetMapping("/identity/login")
    public String login() {
        return "You are now logged in!";
    }

    @GetMapping("/identity/role/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN'")
    public String allRoles() {
        return "You are now logged in!";
    }
}
