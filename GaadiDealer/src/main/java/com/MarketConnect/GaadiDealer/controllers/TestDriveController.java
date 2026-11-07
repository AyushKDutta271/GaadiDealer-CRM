package com.MarketConnect.GaadiDealer.controllers;

import com.MarketConnect.GaadiDealer.classconversions.ClassConversion;
import com.MarketConnect.GaadiDealer.dtos.TestDriveDto;
import com.MarketConnect.GaadiDealer.services.TestDriveService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/testdrives")
public class TestDriveController {

    @Autowired
    private TestDriveService testDriveService;

    @Autowired
    private ClassConversion classConversion;

    @PostMapping("/add")
    public ResponseEntity<TestDriveDto> addTestDrive(@RequestBody TestDriveDto testDriveDto)
    {
        return  ResponseEntity.status(HttpStatus.CREATED).body(testDriveService.addTestdriveInDB(testDriveDto));
    }

    @GetMapping("/get")
    public ResponseEntity<Page<TestDriveDto>> getTestDrives(Pageable pageable)
    {
        return ResponseEntity.status(HttpStatus.OK).body(testDriveService.listAllTestDrives(pageable));
    }

    @GetMapping("/getTestDriveById/{id}")
    public ResponseEntity<TestDriveDto> getTestDriveById(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(testDriveService.getTestDriveById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTestDrive(@PathVariable Long id)
    {
        testDriveService.removeTestDriveFromDB(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<TestDriveDto> updateStatus(@PathVariable Long id,@RequestBody Map<String,Boolean> statusField)
    {
        return ResponseEntity.status(HttpStatus.OK).body(testDriveService.changeStatusOfATestDrive(id,statusField));
    }

    @PutMapping("/updateTestDate/{id}")
    public ResponseEntity<TestDriveDto> updateDateConfirm(@PathVariable Long id, @RequestBody Map<String,Boolean> field)
    {
        return ResponseEntity.status(HttpStatus.OK).body(testDriveService.changeStatusOfDateConfirmOfTestDrive(id,field));
    }

    @GetMapping("/getTotalTestDrives")
    public ResponseEntity<Long> getAllTestDrives()
    {
        return ResponseEntity.status(HttpStatus.OK).body(testDriveService.getAllTestDrivesFromDB());
    }

    @GetMapping("/getAllDoneTestDrives")
    public ResponseEntity<Long> getAllDoneTestDrives()
    {
        return ResponseEntity.status(HttpStatus.OK).body(testDriveService.getAllDoneTestDrivesFromDB());
    }

    @GetMapping("/getTestDrivesByUserId/{userId}")
    public ResponseEntity<Page<TestDriveDto>>  getTestDrivesByUserId(@PathVariable Long userId,Pageable pageable)
    {
        return ResponseEntity.status(HttpStatus.OK).body(testDriveService.getAllTestDrivesByUserIdFromDB(userId,pageable));
    }


}
