package com.asynclabs.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@PreAuthorize("hasRole('ADMIN')")
public class TestController {

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String testMethod() {
        throw new IllegalStateException("Oops cannot access this method!");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String testMethod2() {
        return "Post Test successful!";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String testMethod3() {
        return "Put Test successful!";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String testMethod4() {
        return "Delete Test successful!";
    }
}
