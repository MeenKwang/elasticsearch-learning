package com.loadcell;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellPhoneRepository extends JpaRepository<CellPhone, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);
}
