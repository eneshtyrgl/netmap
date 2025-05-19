package com.netmap.netmapservice.repository;

import com.netmap.netmapservice.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
    List<Skill> findByNameIn(List<String> names);

    @Query("SELECT s.name FROM Skill s ORDER BY s.name")
    List<String> findAllSkillNames();
}
