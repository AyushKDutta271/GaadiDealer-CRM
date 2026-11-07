package com.MarketConnect.GaadiDealer.classconversions;

import com.MarketConnect.GaadiDealer.dtos.*;
import com.MarketConnect.GaadiDealer.entities.*;

import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import com.MarketConnect.GaadiDealer.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ClassConversion {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRespository;

    public User userDtoToUserEntity(UserDto user)
    {
        return new User(user.getFullName(),user.getEmailId(),user.getPassword(),user.getConfirmPassword(),user.getUserState(),user.getUserCity(),user.getUserType());
    }

    public UserDto userToUserDto(User user)
    {
        return new UserDto(user.getId(),user.getFullName(),user.getEmailId(),user.getPassword(),user.getConfirmPassword(),user.getUserState(),user.getUserCity(),user.getUserType());
    }

    public Vehicle vehicleDtoToVehicleEntity(VehicleDto vehicle)
    {
        if(vehicle.getStatus()==null) vehicle.setStatus("NO");
        User userEntity =userRepository.findById(vehicle.getUserId()).orElseThrow(()->new NoSuchElementException("No such user with this ID exists!"));
        return new Vehicle(vehicle.getCarPlateNo(),vehicle.getCarBrand(),userEntity,vehicle.getState(),vehicle.getCity(),vehicle.getCarImages(),vehicle.getCarPrice(),vehicle.getStatus());
    }

    public VehicleDto vehicleToVehicleDto(Vehicle vehicle)
    {
        return new VehicleDto(vehicle.getId(), vehicle.getCarPlateNo(),vehicle.getCarBrand(),vehicle.getCarOwner().getId(),vehicle.getState(),vehicle.getCity(),vehicle.getCarImages(),vehicle.getCarPrice(),vehicle.getStatus());
    }

    public TestDrive testDriveDtoToEntity(TestDriveDto testDriveDto)
    {
        System.out.println("testdrive user's emailId: "+testDriveDto.getEmailId());
        User userEntity=userRepository.findByEmailId(testDriveDto.getEmailId()).orElseThrow(()->new NoSuchElementException("No user with this emailId exists!"));
        Vehicle carEntity=vehicleRespository.findById(testDriveDto.getCarId()).orElseThrow(()->new NoSuchElementException("No car with this Id exists!"));
        return new TestDrive(userEntity,testDriveDto.getDateAndTimeOfTestDrive(),carEntity,testDriveDto.getCity(),testDriveDto.getIsDateConfirmed(),testDriveDto.getIsDone());
    }

    public TestDriveDto testDriveEntityToDto(TestDrive testDrive)
    {
        return new TestDriveDto(testDrive.getId(),testDrive.getUserId().getEmailId(),testDrive.getDateAndTimeOfTestDrive(),testDrive.getTestCarId().getId(),testDrive.getCity(),testDrive.getIsDateConfirmed(),testDrive.getIsDone());
    }

    public Leads leadsDtoToLeadsEntity(LeadsDto leadsDto)
    {
        User userEntity = userRepository.findByEmailId(leadsDto.getEmailId()).orElseThrow(()->new NoSuchElementException("User with this EMAILID doesn't exist!"));
        System.out.println(userEntity.getEmailId());
        Vehicle carEntity=vehicleRespository.findById(leadsDto.getCarId()).orElseThrow(()->new NoSuchElementException("No Car with this ID exist!"));
        return new Leads(userEntity,leadsDto.getTestDriveId(),carEntity,leadsDto.getBuy(),leadsDto.getUploadedDoc(),leadsDto.getIsVerified(),leadsDto.getIsPaymentDone());
    }

    public LeadsDto leadsEntityToDto(Leads leads)
    {
        User u=userRepository.findById(leads.getUserId().getId()).orElseThrow(()->new NoSuchElementException("No user with this ID exists!"));

        return new LeadsDto(leads.getId(),leads.getTestDriveId(), u.getEmailId(),leads.getCarId().getId(),leads.getBuy(),leads.getUploadedDoc(),leads.getIsVerified(),leads.getIsPaymentDone());
    }

    public Contacts contactsToContactsEntity(ContactsDto contacts)
    {
        return new Contacts(contacts.getId(), contacts.getUserId(), contacts.getCarPlateNo(), contacts.getPaidPrice());
    }

    public ContactsDto contactsEntityToDto(Contacts contacts)
    {
        return new ContactsDto(contacts.getId(), contacts.getUserId(), contacts.getCarPlateNo(), contacts.getPaidPrice());
    }
}
