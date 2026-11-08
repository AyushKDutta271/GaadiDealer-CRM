package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.classconversions.ClassConversion;
import com.MarketConnect.GaadiDealer.dtos.ContactsDto;
import com.MarketConnect.GaadiDealer.entities.Contacts;
import com.MarketConnect.GaadiDealer.entities.Leads;
import com.MarketConnect.GaadiDealer.repositories.ContactsRepository;
import com.MarketConnect.GaadiDealer.repositories.LeadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsService {

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private LeadsRepository leadsRepository;

    @Autowired
    private ClassConversion classConversion;
    public Page<ContactsDto> getAllLeadsFromDB(Pageable pageable)
    {
//        System.out.println("Ftehced leads: "+contactsRepository.findAll(pageable));


        return contactsRepository.findAll(pageable).map(c->classConversion.contactsEntityToDto(c));
    }

    public void saveContactsInDB(Leads lead)
    {
//        contactsRepository.save(classConversion.contactsToContactsEntity(contactsDto));




            Contacts contacts=new Contacts();
            contacts.setUserId(lead.getUserId().getId());
            contacts.setCarPlateNo(lead.getCarId().getCarPlateNo());
            contacts.setPaidPrice(lead.getCarId().getCarPrice());

            contactsRepository.save(contacts);


    }

    public Long getAllContactsFromDb()
    {
        return contactsRepository.findAll().stream().count();
    }
}
