package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.classconversions.ClassConversion;
import com.MarketConnect.GaadiDealer.dtos.UserDto;
import com.MarketConnect.GaadiDealer.entities.User;
import com.MarketConnect.GaadiDealer.exception.DuplicateEmailException;
import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ClassConversion classConversion;

    public UserDto addUserToDB(UserDto userDto)
    {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

            if(userRepository.existsByEmailId(userDto.getEmailId()))
            {
                throw new DuplicateEmailException("duplicateEmailIdException");
            }
            else {
                userRepository.save(classConversion.userDtoToUserEntity(userDto));
            }
       return userDto;
    }

    public UserDto getUserByEmailId( String emailId)
    {
        User user=userRepository.findByEmailId(emailId).orElseThrow(()->new NoSuchElementException("No user with this EmailId exists!"));
        return classConversion.userToUserDto(user);
    }
}
