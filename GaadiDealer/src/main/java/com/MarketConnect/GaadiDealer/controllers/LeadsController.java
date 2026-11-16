package com.MarketConnect.GaadiDealer.controllers;

import com.MarketConnect.GaadiDealer.dtos.LeadsDto;
import com.MarketConnect.GaadiDealer.entities.Leads;
import com.MarketConnect.GaadiDealer.services.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

//@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/leads")
public class LeadsController {

    @Autowired
    private LeadService leadService;



        @GetMapping("/customerVerificationDoc/download/{leadId}")
        public ResponseEntity<byte[]> verificationDocDownload(@PathVariable Long leadId)
        {

            byte[] pdfFileBytes=leadService.generatePdfFile(leadId);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            httpHeaders.setContentDispositionFormData("attachment","car-verification.pdf");

            return ResponseEntity.ok().headers(httpHeaders).body(pdfFileBytes);
        }

    @PutMapping("/customerVerificationDoc/upload/{leadId}")
    public ResponseEntity<?> verificationDocUpload(@PathVariable Long leadId,@RequestParam("docFile") MultipartFile docFile)
    {
        System.out.println("downloadbale link: "+docFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.generateFileUri(docFile,leadId));
    }

        @GetMapping("/adminVerificationDoc/download/{leadId}")
        public ResponseEntity<?> downloadFileFromAdmin(@PathVariable Long leadId)
        {

        }


        @PostMapping("/save")
        public ResponseEntity<LeadsDto> saveLead(@RequestBody LeadsDto leadsDto)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(leadService.saveLeadToDB(leadsDto));
        }

        @GetMapping("/getLeads")
        public ResponseEntity<Page<LeadsDto>> getLeads(Pageable pageable)
        {
            System.out.println("fetched leads: "+leadService.getLeadsFromDb(pageable).getContent());
            return ResponseEntity.status(HttpStatus.OK).body(leadService.getLeadsFromDb(pageable));
        }

        @PatchMapping("/updateStatus/{id}")
        public ResponseEntity<LeadsDto> updateStatusOfALead(@PathVariable Long id, @RequestBody Map<String,Boolean> isVerified)
        {
            return ResponseEntity.status(HttpStatus.OK).body(leadService.updateStatusOfALeadInDB(id,isVerified));
        }

        @GetMapping("/getTotalLeads")
        public ResponseEntity<Long> getAllLeads()
        {
            return ResponseEntity.status(HttpStatus.OK).body(leadService.getTotalLeads());
        }

        @GetMapping("/getLeadById/{leadId}")
        public ResponseEntity<LeadsDto> getLeadById(@PathVariable Long leadId)
        {
            return ResponseEntity.status(HttpStatus.OK).body(leadService.getLeadsByIdFromDB(leadId));
        }

        @GetMapping("/getLeadByTestDriveId/{testdriveId}")
        public ResponseEntity<LeadsDto> getLeadByTestDriveId(@PathVariable Long testdriveId)
        {
            return ResponseEntity.status(HttpStatus.OK).body(leadService.getLeadsByTestDriveId(testdriveId));
        }

        @PatchMapping("/updateByStatusOfALead/{leadId}")
        public ResponseEntity<LeadsDto> updateBuyOfLead(@PathVariable Long leadId, @RequestBody Map<String,Boolean> field)
        {
            return ResponseEntity.status(HttpStatus.OK).body(leadService.updateBuyStatusOfALeadInDB(leadId,field));
        }

}
