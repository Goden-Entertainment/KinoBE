package org.example.kinobe.repository;

import org.example.kinobe.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    //Sætter til optional så slipper vi for at checke i controlleren, slipper for en NullPointerException.
Optional<Admin> findByUsername (String username);
Admin findByAdminId(int adminId);
Optional<Admin> findByUsernameAndPassword(String username, String password);
boolean existsByUsername(String username);


}
