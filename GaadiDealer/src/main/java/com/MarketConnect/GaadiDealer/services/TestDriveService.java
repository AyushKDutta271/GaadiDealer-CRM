package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.classconversions.ClassConversion;
import com.MarketConnect.GaadiDealer.dtos.TestDriveDto;
import com.MarketConnect.GaadiDealer.entities.TestDrive;
import com.MarketConnect.GaadiDealer.entities.User;
import com.MarketConnect.GaadiDealer.repositories.TestdriveRespository;
import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class TestDriveService {



    @Autowired
    private TestdriveRespository testdriveRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassConversion classConversion;


    public TestDriveDto getTestDriveById(Long id)
    {
        TestDrive td=testdriveRespository.findById(id).orElseThrow(()->new NoSuchElementException("No such testdrive with this ID exists!"));
        return classConversion.testDriveEntityToDto(td);
    }

    public TestDriveDto addTestdriveInDB(TestDriveDto testDriveDto)
    {

        testdriveRespository.save(classConversion.testDriveDtoToEntity(testDriveDto));
        return testDriveDto;
    }

    public Page<TestDriveDto> listAllTestDrives(Pageable pageable)
    {
        return testdriveRespository.findAll(pageable).map(td->classConversion.testDriveEntityToDto(td));
    }

    public String removeTestDriveFromDB(Long id)
    {
        testdriveRespository.deleteById(id);
        return "Testdrive has been deleted successfully!";
    }

    public TestDriveDto changeStatusOfATestDrive(Long id, Map<String,Boolean> statusField)
    {
        TestDrive testDrive=testdriveRespository.findById(id).orElseThrow(()->new NoSuchElementException("No such testrive with this ID exists!"));

        statusField.forEach((k,v)->{
            testDrive.setIsDone(v);
        });
        testdriveRespository.save(testDrive);
        return classConversion.testDriveEntityToDto(testDrive);
    }

    public TestDriveDto changeStatusOfDateConfirmOfTestDrive(Long id, Map<String,Boolean> statusField)
    {
        TestDrive testDrive=testdriveRespository.findById(id).orElseThrow(()->new NoSuchElementException("No such testrive with this ID exists!"));

        statusField.forEach((k,v)->{
            testDrive.setIsDateConfirmed(v);
        });
        testdriveRespository.save(testDrive);
        return classConversion.testDriveEntityToDto(testDrive);
    }


    public Long getAllTestDrivesFromDB()
    {
        return testdriveRespository.findAll().stream().count();
    }

    public Long getAllDoneTestDrivesFromDB()
    {
        return testdriveRespository.findAllTestDrivesByStatus(true);
    }

    public Page<TestDriveDto> getAllTestDrivesByUserIdFromDB(Long userId,Pageable pageable)
    {
        return testdriveRespository.findAllTestDrivesByUserId(userId,pageable).map(td->classConversion.testDriveEntityToDto(td));
    }






}
