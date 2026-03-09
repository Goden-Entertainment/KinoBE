package org.example.kinobe.service;

import org.example.kinobe.model.Admin;
import org.example.kinobe.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public Admin createAdmin(Admin admin) {
        if(adminRepository.existsByUsername(admin.getUsername())){
            throw new RuntimeException("Username already taken: " + admin.getUsername());
        }
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> readAllAdmin() {
        return adminRepository.findAll();
    }

    @Override
    public Admin readAdminById(int adminId) {
        Admin admin = adminRepository.findByAdminId(adminId);

        if(admin == null){
            throw new RuntimeException("Admin was not found with id: " + adminId);
        }
        return admin;
    }

    @Override
    public Admin updateAdmin(int adminId, Admin updatedAdmin) {
        Admin adminExist = readAdminById(adminId);

        //Checking if the current username is the same as the old and if theres a user with that name in the db.
        if(!adminExist.getUsername().equals(updatedAdmin.getUsername())){
            if(adminRepository.existsByUsername(updatedAdmin.getUsername())){
                throw new RuntimeException("Username already taken: " + updatedAdmin.getUsername());
            }
            adminExist.setUsername(updatedAdmin.getUsername());
        }

        //Checking if the user left the password textbox empty, it doesnt change the password to nothing but keeps the old.
        if(updatedAdmin.getPassword() != null && !updatedAdmin.getPassword().isBlank()){
            adminExist.setPassword(updatedAdmin.getPassword());
        }
        return adminRepository.save(adminExist);
    }

    @Override
    public void deleteAdmin(int adminId) {
        if(!adminRepository.existsById(adminId)){
            throw new RuntimeException("Admin not found with id: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }
}
