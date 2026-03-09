package org.example.kinobe.controller;

import jakarta.servlet.HttpConstraintElement;
import jakarta.servlet.http.HttpSession;
import org.example.kinobe.model.Admin;
import org.example.kinobe.service.AdminService;
import org.example.kinobe.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    //Goden was here
    @Autowired
    private AdminServiceImpl adminServiceImpl;

    //CRUD-METHODS
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin, HttpSession session) {
        Admin savedAdmin = adminServiceImpl.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> readAllAdmin(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(adminServiceImpl.readAllAdmin());
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> readAdminById(@PathVariable int adminId, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Admin admin = adminServiceImpl.readAdminById(adminId);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int adminId, @RequestBody Admin updatedAdmin, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Admin admin = adminServiceImpl.updateAdmin(adminId, updatedAdmin);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int adminId, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (adminServiceImpl.readAdminById(adminId) == null) {
            return ResponseEntity.notFound().build();
        }
        adminServiceImpl.deleteAdmin(adminId);
        return ResponseEntity.noContent().build();
    }
}
