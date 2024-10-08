package org.example.controllers;

import org.example.databases.Cat;
import org.example.databases.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class RecordController {
    @Autowired
    CatRepository catRepository;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestParam("record") String record) {
            Cat entity = new Cat();
            entity.setRecord(record);
            catRepository.put(entity);
            return ResponseEntity.ok("Record added successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<Cat> get(@RequestParam("id") Long id){
        Cat entity = catRepository.get(id);
        if (entity == null) {
            throw new EntityNotFoundException("Cat with id " + id + " not found");
        }
        return new ResponseEntity<>(entity, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam("id") Long id,
                                         @RequestParam("new_record") String record) {
        Cat entity = new Cat();
        entity.setId(id);
        entity.setRecord(record);
        Cat old = catRepository.get(id);
        if (old == null) {
            throw new EntityNotFoundException("Record not found with id: " + id);
        }
        catRepository.update(entity);
        return ResponseEntity.ok("Record updated successfully");
    }
    @PostMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam("id") Long id) {
        Cat old = catRepository.get(id);
        if (old == null) {
            throw new EntityNotFoundException("Record not found with id: " + id);
        }
        catRepository.delete(id);
        return ResponseEntity.ok("Record deleted successfully");
    }
}
