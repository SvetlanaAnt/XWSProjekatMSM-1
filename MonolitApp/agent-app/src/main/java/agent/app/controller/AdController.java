package agent.app.controller;

import agent.app.converter.AdConverter;
import agent.app.dto.ad.AdCreateDTO;
import agent.app.model.CarManufacturer;
import agent.app.service.intf.AdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/ad", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

//    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_AGENT') or hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAd(@PathVariable("id") Long id) {
        System.out.println("Service ad !!!!!");
        return new ResponseEntity<>(AdConverter.toAdDetailViewDTOFromAd(adService.findById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_AGENT')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam(value = "photo", required = true) MultipartFile photo,
                                         @RequestParam(value = "data", required = true) String data) throws IOException {
        System.out.println("-----------------------UPLOAD FILE---------------------");

        System.out.println(data);
        try{
            System.out.println(photo);
            File file = new File("photos");
            String uploadDirectory = file.getAbsolutePath() + "\\" + photo.getOriginalFilename();

            System.out.println(uploadDirectory);
            System.out.println("slika : " + photo.getOriginalFilename());

            File convertFile = new File(uploadDirectory.toString());
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(photo.getBytes());
            fout.close();
            //TODO 1: POZVATI METODE IMAGE SERVISA ZA UPLOAD SLIKE
            return new ResponseEntity<>("Slika uspesno dodata.", HttpStatus.CREATED);
        }catch(Exception e){

            return new ResponseEntity<>("Slika nije dodata.", HttpStatus.BAD_REQUEST);
        }

    }



//    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_AGENT')")
//    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> createAd(@RequestParam(value="coverPhoto", required = true) MultipartFile coverPhoto, @RequestParam(value="data", required = true)  String data, Principal principal) throws IOException{
//
//        System.out.println("-----------------------UPLOAD FILE---------------------");
//
//        File file = new File("photos");
//        String uploadDirectory = file.getAbsolutePath() + "\\" + coverPhoto.getOriginalFilename();
//
//        System.out.println(uploadDirectory);
//        System.out.println("slika : " + coverPhoto.getOriginalFilename());
//
//        File convertFile = new File(uploadDirectory.toString());
//        convertFile.createNewFile();
//        FileOutputStream fout = new FileOutputStream(convertFile);
//        fout.write(coverPhoto.getBytes());
//        fout.close();
//        System.out.println(data.toString());
//        AdCreateDTO adCreateDTO = objectMapper.readValue(data, AdCreateDTO.class);
//        adCreateDTO.setCoverPhoto(coverPhoto.getOriginalFilename());
//        Integer flag = adService.createAd(adCreateDTO);
//        if(flag == 1){
//            return new ResponseEntity<>("Oglas uspesno kreiran.", HttpStatus.CREATED);
//        }else{
//            return new ResponseEntity<>("Desila se greska.", HttpStatus.BAD_REQUEST);
//        }
//    }


    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_AGENT')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAd(@RequestBody AdCreateDTO adCreateDTO, Principal principal) {
        System.out.println(adCreateDTO);
//        AdCreateDTO adCreateDTO = objectMapper.readValue(data, AdCreateDTO.class);
//        adCreateDTO.setCoverPhoto(coverPhoto.getOriginalFilename());

        adCreateDTO.getPriceListCreateDTO().setPublisherUsername(principal.getName());
        Integer flag = adService.createAd(adCreateDTO);

        if (flag == 1) {
            return new ResponseEntity<>("Oglas uspesno kreiran.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Desila se greska.", HttpStatus.BAD_REQUEST);
        }
    }


    //    @RequestMapping(value = "{?page,size,sort}", method = RequestMethod.GET)
//    public ResponseEntity<?> findAllPageAd(@PathVariable("page") Integer page,@PathVariable("size") Integer size,
//                                         @PathVariable("sort") String sort) {
//        return new ResponseEntity<>(adService.findAllPageAd(page, size, sort), HttpStatus.OK);
//    }

    //@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_AGENT') or hasAuthority('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllPageAd(@RequestParam(value = "nextPage", required = false) Integer nextPage, @RequestParam(value = "size", required = false) Integer size) {

        if (nextPage != null) {
            System.out.println("ima 1 str");
            return new ResponseEntity<>(adService.findAll(nextPage, size), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(adService.findAll(), HttpStatus.OK);
        }

    }

}
