package com.devendra.SocietySync.repository;


import com.devendra.SocietySync.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    // ✅ This matches the field name: userId (not userid)
    Optional<FamilyMember> findByUserId(Long userId);
}
