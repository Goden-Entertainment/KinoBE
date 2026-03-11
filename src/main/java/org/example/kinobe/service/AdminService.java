package org.example.kinobe.service;

import org.example.kinobe.model.Admin;

import java.util.List;

public interface AdminService {
    Admin createAdmin(Admin admin);
    List<Admin> readAllAdmin();
    Admin readAdminById(int adminId);
    Admin updateAdmin(int adminId, Admin updateAdmin);
    void deleteAdmin(int adminId);

}
