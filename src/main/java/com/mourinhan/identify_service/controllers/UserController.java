package com.mourinhan.identify_service.controllers;

import com.mourinhan.identify_service.dto.request.UserCreationRequest;
import com.mourinhan.identify_service.dto.request.UserUpdateRequest;
import com.mourinhan.identify_service.dto.response.ApiResponse;
import com.mourinhan.identify_service.dto.response.UserResponse;
import com.mourinhan.identify_service.entities.User;
import com.mourinhan.identify_service.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    List<UserResponse> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User has been deleted";
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

}
