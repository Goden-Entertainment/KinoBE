package org.example.kinobe.servicetest;

import org.example.kinobe.model.Admin;
import org.example.kinobe.repository.AdminRepository;
import org.example.kinobe.service.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceImplTest {

    @Autowired
    AdminServiceImpl adminServiceImpl;

    @MockitoBean
    AdminRepository adminRepository;


    @Test
    void createAdmin_ShouldReturnSavedAdmin_WhenUsernameIsAvailable(){
        //Arange
        Admin admin = new Admin("Bob", "kode123");
        when(adminRepository.existsByUsername("Bob")).thenReturn(false);
        when(adminRepository.save(admin)).thenReturn(admin);

        //Act
        Admin result = adminServiceImpl.createAdmin(admin);
        System.out.println("Created Admin: " + result.getUsername());

        //Assert
        assertNotNull(result);
        assertEquals("Bob", result.getUsername());
        verify(adminRepository, times(1)).save(admin);

    }

    @Test
    void createAdmin_ShouldThrowException_WhenUsernameIsTaken(){
        //Arange
        Admin admin = new Admin("John", "kode123");
        when(adminRepository.existsByUsername("John")).thenReturn(true);

        //Act
        try{
            adminServiceImpl.createAdmin(admin);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e){
            //Assert
            assertEquals("Username already taken: John", e.getMessage());
        }
        verify(adminRepository, never()).save(admin);
    }
}
