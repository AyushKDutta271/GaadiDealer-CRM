package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.FileStorageProperties;
import com.MarketConnect.GaadiDealer.repositories.VehicleRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private final Path ROOTLOCATION;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private VehicleRepository vehicleRepository;

    public FileService(FileStorageProperties props)
    {
        this.ROOTLOCATION= Paths.get(props.getUploadDir());
    }

    public List<String> fileUpload(List<MultipartFile> fileImages)
    {
        List<String> imageURLS=new ArrayList<>();
        fileImages.forEach(img->{
//            Path destination=this.ROOTLOCATION.resolve(img.getOriginalFilename());
//            try {
//                InputStream is = img.getInputStream();
//                Files.copy(is,destination, StandardCopyOption.REPLACE_EXISTING);
//                String imageURL= ServletUriComponentsBuilder
//                        .fromCurrentContextPath()
//                        .path(img.getOriginalFilename())
//                        .toUriString();
//                imageURLS.add(imageURL);
//            }
//            catch(IOException ex)
//            {
//                throw new RuntimeException("failed to get bytes of image file!");
//            }
            try {
                Map result= cloudinary.uploader().upload(
                        img.getBytes(),
                        ObjectUtils.emptyMap()
                );

                String imageUrl=(String)result.get("secure_url");
                imageURLS.add(imageUrl);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        });
        return imageURLS;
    }

    public String generateDownloadableLink(MultipartFile file) {
        Path destination = this.ROOTLOCATION.resolve(file.getOriginalFilename());

        try {
            InputStream is = file.getInputStream();
            Files.copy(is, destination, StandardCopyOption.REPLACE_EXISTING);
             String imageURL = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path(file.getOriginalFilename())
                    .toUriString();
             return imageURL;
        }
        catch(IOException ex)
        {
            throw new RuntimeException("Failed to generate download link");
        }

    }
}
