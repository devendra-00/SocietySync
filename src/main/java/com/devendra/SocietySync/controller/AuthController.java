package com.devendra.SocietySync.controller;

import com.devendra.SocietySync.model.Department;
import com.devendra.SocietySync.model.FamilyMember;
import com.devendra.SocietySync.model.Staff;
import com.devendra.SocietySync.model.User;
import com.devendra.SocietySync.repository.DepartmentRepository;
import com.devendra.SocietySync.repository.FamilyMemberRepository;
import com.devendra.SocietySync.repository.StaffRepository;
import com.devendra.SocietySync.repository.UserRepository;
import com.devendra.SocietySync.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private FamilyMemberRepository familyMemberRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login request DTO
    public static class AuthRequest {
        public String email;
        public String password;
    }

    // User Register Request DTO
    public static class UserRegisterRequest {
        public String name;
        public String email;
        public String password;
        public String aadhar;
        public String contactno;
        public String gender;
        public String dob;
        public String role; // "OWNER" or "FAMILY"

        // Only for FAMILY role:
        public Long ownerUserId;
        public String relationship;
    }


    // Staff Register Request DTO
    public static class StaffRegisterRequest {
        public String name;
        public String password;
        public String aadhar;
        public String contactNo;
        public String email;
        public String gender;
        public String dob;  // yyyy-MM-dd
        public String joiningDate;  // yyyy-MM-dd
        public String role; // MANAGER, PERMANENT_STAFF, TEMPORARY_STAFF
        public Long departmentId;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<User> userOpt = userRepository.findByEmail(authRequest.email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(authRequest.password, user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail());

                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", user.getUserid());
                userMap.put("name", user.getName());
                userMap.put("email", user.getEmail());
                userMap.put("aadhar", user.getAadhar());
                userMap.put("contactNo", user.getContactno());
                userMap.put("gender", user.getGender());
                userMap.put("dob", user.getDob());
                userMap.put("profileImage", user.getProfileImage());
                userMap.put("createdAt", user.getCreatedAt());
                userMap.put("role", user.getRole().name());

                // If the user is a FAMILY member, add ownerId and relationship
                if (user.getRole() == User.Role.FAMILY) {
                    familyMemberRepository.findByUserId(user.getUserid()).ifPresent(fm -> {
                        userMap.put("ownerId", fm.getOwnerUserId());
                        userMap.put("relationship", fm.getRelationship());
                    });
                }

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("userType", "USER");
                response.put("user", userMap);

                return ResponseEntity.ok(response);
            }
        }

        Optional<Staff> staffOpt = staffRepository.findByEmail(authRequest.email);
        if (staffOpt.isPresent()) {
            Staff staff = staffOpt.get();
            if (passwordEncoder.matches(authRequest.password, staff.getPassword())) {
                String token = jwtUtil.generateToken(staff.getEmail());

                Map<String, Object> deptMap = new HashMap<>();
                deptMap.put("id", staff.getDepartment().getDeptId());
                deptMap.put("name", staff.getDepartment().getName());
                deptMap.put("description", staff.getDepartment().getDescription());

                Map<String, Object> staffMap = new HashMap<>();
                staffMap.put("id", staff.getStaffId());
                staffMap.put("name", staff.getName());
                staffMap.put("email", staff.getEmail());
                staffMap.put("aadhar", staff.getAadhar());
                staffMap.put("contactNo", staff.getContactNo());
                staffMap.put("gender", staff.getGender());
                staffMap.put("dob", staff.getDob());
                staffMap.put("joiningDate", staff.getJoiningDate());
                staffMap.put("role", staff.getRole().name());
                staffMap.put("profileImage", staff.getProfileImage());
                staffMap.put("isPermanent", staff.getIsPermanent());
                staffMap.put("department", deptMap);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("userType", "STAFF");
                response.put("staff", staffMap);

                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(401).body("Invalid email or password");
    }


    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest request) {
        // Email & Aadhar uniqueness checks
        if (userRepository.findByEmail(request.email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        if (userRepository.findByAadhar(request.aadhar).isPresent()) {
            return ResponseEntity.badRequest().body("Aadhar already in use");
        }

        // Create and populate user entity
        User newUser = new User();
        newUser.setName(request.name);
        newUser.setEmail(request.email);
        newUser.setPassword(passwordEncoder.encode(request.password));
        newUser.setAadhar(request.aadhar);
        newUser.setContactno(request.contactno);
        newUser.setGender(request.gender);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setRole(User.Role.valueOf(request.role.toUpperCase())); // Either "OWNER" or "FAMILY"

        if (request.dob != null && !request.dob.isEmpty()) {
            newUser.setDob(LocalDate.parse(request.dob));
        }

        // Save the user
        userRepository.save(newUser);

        // If user is FAMILY, create family_members record
        if ("FAMILY".equalsIgnoreCase(request.role)) {
            if (request.ownerUserId == null || request.relationship == null || request.relationship.isBlank()) {
                return ResponseEntity.badRequest().body("Family member must provide ownerUserId and relationship");
            }

            FamilyMember familyMember = new FamilyMember();
            familyMember.setUserId(newUser.getUserid());
            familyMember.setOwnerUserId(request.ownerUserId);
            familyMember.setRelationship(request.relationship);
            familyMemberRepository.save(familyMember);
        }

        return ResponseEntity.ok("User registered successfully as " + request.role);
    }


    @PostMapping("/register/staff")
    public ResponseEntity<?> registerStaff(@RequestBody StaffRegisterRequest request) {
        if (staffRepository.findByEmail(request.email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        if (staffRepository.findByAadhar(request.aadhar).isPresent()) {
            return ResponseEntity.badRequest().body("Aadhar already in use");
        }

        Department dept = departmentRepository.findById(request.departmentId).orElse(null);
        if (dept == null) {
            return ResponseEntity.badRequest().body("Department not found");
        }

        Staff newStaff = new Staff();
        newStaff.setName(request.name);
        newStaff.setEmail(request.email);
        newStaff.setPassword(passwordEncoder.encode(request.password));
        newStaff.setAadhar(request.aadhar);
        newStaff.setContactNo(request.contactNo);
        newStaff.setGender(request.gender);
        newStaff.setDepartment(dept);

        if (request.dob != null && !request.dob.isEmpty()) {
            newStaff.setDob(java.sql.Date.valueOf(request.dob));
        }
        if (request.joiningDate != null && !request.joiningDate.isEmpty()) {
            newStaff.setJoiningDate(java.sql.Date.valueOf(request.joiningDate));
        }

        try {
            Staff.RoleName role = Staff.RoleName.valueOf(request.role);
            newStaff.setRole(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role");
        }

        staffRepository.save(newStaff);
        return ResponseEntity.ok("Staff registered successfully");
    }

}
