package com.MarketConnect.GaadiDealer.controllers;

import com.MarketConnect.GaadiDealer.dtos.ContactsDto;
import com.MarketConnect.GaadiDealer.services.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/contacts")
public class ContactsController {

        @Autowired
        private ContactsService contactsService;

        @GetMapping("/get")
        public ResponseEntity<Page<ContactsDto>> getAllContacts(Pageable pageable)
        {
            return ResponseEntity.status(HttpStatus.OK).body(contactsService.getAllLeadsFromDB(pageable));
        }

//        @PostMapping("/save/{lead")
//        public ResponseEntity<Void> saveContacts()
//        {
//            contactsService.saveContactsInDB();
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }

        @GetMapping("/getAll")
        public ResponseEntity<Long> getAllContactsCount()
        {
            return ResponseEntity.status(HttpStatus.OK).body(contactsService.getAllContactsFromDb());
        }
}
