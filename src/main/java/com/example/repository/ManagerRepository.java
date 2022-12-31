package com.example.repository;

import com.example.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findUserByUsernameAndPassword(String username, String password);

    @Query(value = "select * from user where user.company_name = ? and user.city = ?", nativeQuery = true)
    Manager findManagerByCompanyNameAndCity(String companyName, String city);
}
