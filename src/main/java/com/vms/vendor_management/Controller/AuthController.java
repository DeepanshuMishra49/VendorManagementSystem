package com.vms.vendor_management.Controller;

import com.vms.vendor_management.Entity.User;
import com.vms.vendor_management.Service.EmailService;
import com.vms.vendor_management.Service.OTPService;
import com.vms.vendor_management.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints for user management")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Request OTP", description = "Request OTP for login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid email")
    })
    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOTP(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }

        // Check if user exists, if not create a new user
        User user = userService.findByEmail(email);
        if (user == null) {
            // Create new user
            user = new User();
            user.setEmail(email);
            user.setName(email.split("@")[0]); // Default name from email
            user.setRole("USER"); // Default role
            user = userService.save(user);
        }

        // Generate and send OTP
        String otp = otpService.generateOTP(email);
        emailService.sendEmail(
            email,
            "Your OTP for Login",
            "Your OTP is: " + otp
        );

        return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
    }

    @Operation(summary = "Verify OTP and Login", description = "Verify OTP and complete login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "400", description = "Invalid OTP or credentials")
    })
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String password = request.get("password");

        if (email == null || email.isEmpty() || otp == null || otp.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email, OTP and password are required"));
        }

        if (otpService.validateOTP(email, otp)) {
            User user = userService.findByEmail(email);
            if (user != null) {
                // Set the password for the user
                user.setPassword(password);
                user = userService.save(user);

                // Generate JWT token
                String token = userService.generateToken(user);
                return ResponseEntity.ok(Map.of(
                    "message", "OTP verified successfully",
                    "token", token,
                    "user", Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "role", user.getRole()
                    )
                ));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Invalid OTP"));
    }

    @Operation(
        summary = "Get User Profile",
        description = "Retrieves the profile information of the authenticated user",
        security = @SecurityRequirement(name = "JWT")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user profile"),
        @ApiResponse(responseCode = "400", description = "No authentication found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(
        @Parameter(hidden = true) Authentication authentication
    ) {
        if (authentication == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "No authentication found"
            ));
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("role", user.getRole());
        
        return ResponseEntity.ok(response);
    }
}
