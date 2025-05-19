package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.model.Skill;
import com.netmap.netmapservice.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillRepository skillRepository;

    @GetMapping
    public ResponseEntity<List<String>> getAllSkillNames() {
        return ResponseEntity.ok(skillRepository.findAllSkillNames());
    }
    @GetMapping("/all")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillRepository.findAll());
    }

}
