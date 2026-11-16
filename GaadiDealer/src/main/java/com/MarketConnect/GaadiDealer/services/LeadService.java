package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.classconversions.ClassConversion;
import com.MarketConnect.GaadiDealer.dtos.LeadsDto;
import com.MarketConnect.GaadiDealer.entities.Leads;
import com.MarketConnect.GaadiDealer.entities.User;
import com.MarketConnect.GaadiDealer.entities.Vehicle;
import com.MarketConnect.GaadiDealer.repositories.LeadsRepository;
import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import com.MarketConnect.GaadiDealer.repositories.VehicleRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class LeadService {

    @Autowired
    private LeadsRepository leadsRepository;

    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassConversion classConversion;

    @Autowired
    private ContactsService contactsService;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private Cloudinary cloudinary;

    public byte[] generatePdfFile( Long leadId)
    {
       Leads lead= leadsRepository.findById(leadId).orElseThrow(()->new NoSuchElementException("No such lead exist with this userId and carId"));

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        Document document=new Document();

        PdfWriter.getInstance(document,byteArrayOutputStream);

        document.open();

        document.add(new Paragraph("CAR VERIFICATION DOCUMENT"));
        document.add(new Paragraph("Car Details:-"));
        document.add(new Paragraph("Customer Full Name: " + lead.getUserId().getFullName()));
        document.add(new Paragraph("Customer EmailId:"+lead.getUserId().getEmailId()));
        document.add(new Paragraph("Car Plate No: " + lead.getCarId().getCarPlateNo()));
        document.add(new Paragraph("Car Brand: " + lead.getCarId().getCarBrand()));
        document.add(new Paragraph("State: " + lead.getCarId().getState()));
        document.add(new Paragraph("City: " + lead.getCarId().getCity()));
        document.add(new Paragraph("Price: " + lead.getCarId().getCarPrice()));

        if(lead.getCarId().getCarImages()!=null && !lead.getCarId().getCarImages().isEmpty())
        {
            try {
                System.out.println(new URL(lead.getCarId().getCarImages().get(0)));
                Image image = Image.getInstance(lead.getCarId().getCarImages().get(0));
                System.out.println(lead.getCarId().getCarImages().get(0));
                image.scaleToFit(400, 400);
                document.add(image);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
                document.add(new Paragraph("Could not load image: " + lead.getCarId().getCarImages().get(0) ));
            }
        }
        document.add(new Paragraph("Signature: ______________________"));
        document.close();
        return byteArrayOutputStream.toByteArray();
    }



    public String generateFileUri(MultipartFile docFile,Long leadId)
    {
//        String fileurl=fileService.generateDownloadableLink(docFile);
        String fileurl;
        String originalName=docFile.getOriginalFilename();
        String publicId=originalName.substring(0,originalName.lastIndexOf("."));
      
            fileurl =cloudinary.url()
                    .resourceType("raw")
                    .type("authenticated")
                    .signed(true)
                    .format("pdf")
                    .generate(publicId);
            System.out.println("Recieved file from user: "+fileurl);
            Leads filteredLead= leadsRepository.findById(leadId).orElseThrow(()->new NoSuchElementException("No such lead having this userID and CarID exists!"));

            filteredLead.setUploadedDoc(fileurl);
            leadsRepository.save(filteredLead);
            return "doc file uploaded successfully!";



    }

    public byte[] generatePdfDocForAdmin(Long leadId)
    {
        Leads lead= leadsRepository.findById(leadId).orElseThrow(()->new NoSuchElementException("No such lead with this userId and carId exists!"));

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document,byteArrayOutputStream);
        document.open();
        document.add(new Paragraph(lead.getUploadedDoc()));
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    public LeadsDto saveLeadToDB(LeadsDto leadsDto)
    {

        Leads lead=classConversion.leadsDtoToLeadsEntity(leadsDto);
        leadsRepository.save(lead);
        return leadsDto;
    }

    public Page<LeadsDto> getLeadsFromDb(Pageable pageable)
    {
        return leadsRepository.findAll(pageable).map(l->classConversion.leadsEntityToDto(l));
    }

    public LeadsDto updateStatusOfALeadInDB(Long id, Map<String,Boolean> isVerified)
    {
        Leads lead = leadsRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such lead with id exists!"));

        isVerified.forEach((k,v)->lead.setIsVerified(v));

        leadsRepository.save(lead);
        return classConversion.leadsEntityToDto(lead);
    }

    public Long getTotalLeads()
    {
        return leadsRepository.findAllLeads(true);
    }

    public LeadsDto getLeadsByIdFromDB(Long leadId)
    {
        Leads lead=leadsRepository.findById(leadId).orElseThrow(()->new NoSuchElementException("No such lead with this id exists!"));
        return classConversion.leadsEntityToDto(lead);
    }

    public LeadsDto getLeadsByTestDriveId(Long testdriveId)
    {
        Leads lead=leadsRepository.findLeadsByTestDriveId(testdriveId).orElseThrow(()->new NoSuchElementException("No such lead with this testdrive-id exists!"));
        return classConversion.leadsEntityToDto(lead);
    }

    public LeadsDto updateBuyStatusOfALeadInDB(Long leadId, Map<String,Boolean> field)
    {
        Leads lead= leadsRepository.findById(leadId).orElseThrow(()->new NoSuchElementException("No such lead with this id exists!"));
        for(Map.Entry<String,Boolean>kv:field.entrySet())
        {
            lead.setBuy(kv.getValue());
            System.out.println(kv.getValue());
        }

        leadsRepository.save(lead);
        contactsService.saveContactsInDB(lead);
        return classConversion.leadsEntityToDto(lead);
    }
}
