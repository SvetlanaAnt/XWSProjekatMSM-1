package agent.app.service.impl;


import agent.app.converter.AdConverter;
import agent.app.converter.CarCalendarTermConverter;
import agent.app.dto.ad.AdCreateDTO;
import agent.app.dto.ad.AdPageContentDTO;
import agent.app.dto.ad.AdPageDTO;
import agent.app.dto.car.CarCalendarTermCreateDTO;
import agent.app.dto.image.ImageDTO;
import agent.app.exception.ExistsException;
import agent.app.exception.NotFoundException;
import agent.app.model.*;
import agent.app.repository.AdRepository;
import agent.app.service.intf.*;
import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private PriceListService priceListService;

    @Autowired
    private CarCalendarTermService carCalendarTermService;

    @Autowired
    private EndUserService endUserService;

    @Autowired
    private PublisherUserService publisherUserService;

    @Autowired
    private UserService userService;


    @Override
    public Ad findById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> new NotFoundException("Oglas ne postoji."));
    }

    @Override
    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    @Override
    public Ad save(Ad ad) {
        if (ad.getId() != null) {
            if (adRepository.existsById(ad.getId())) {
                throw new ExistsException(String.format("Oglas vec postoji."));
            }
        }

        return adRepository.save(ad);
    }

    @Override
    public void delete(Ad ad) {
        adRepository.delete(ad);
    }

    @Override
    public void logicalDeleteOrRevertAds(List<Ad> ads, Boolean status) {
        for (Ad ad : ads) {
            this.logicalDeleteOrRevert(ad, status);
        }
    }

    @Override
    public void logicalDeleteOrRevert(Ad ad, Boolean status) {
        ad.setDeleted(status);
        this.save(ad);
    }

    @Override
    public Integer deleteById(Long id) {
        Ad ad = this.findById(id);
        this.delete(ad);
        return 1;
    }

    @Override
    public Integer createAd(AdCreateDTO adCreateDTO, String email) {
        Integer rez = endUserService.getAdLimitNum(email);
        if(rez == 4){
            System.out.println("nije end userrrr");
        }else if(rez == 0){
            System.out.println("end user");
            System.out.println("ne sme dodavati vise oglasa");
            return 2;
        }


        Ad ad = AdConverter.toCreateAdFromRequest(adCreateDTO);

        Car car = carService.createCar(adCreateDTO.getCarCreateDTO());
        ad.setCar(car);

        if (adCreateDTO.getPriceListCreateDTO().getId() == null) {
            //pravljenje novog cenovnika
            PriceList priceList = priceListService.createPriceList(adCreateDTO.getPriceListCreateDTO());
            //TODO 1: DODATI PUBLISHERA I DODATI OGLAS TAJ PRICE LISTI
            ad.setPriceList(priceList);
        } else {
            //dodavanje vec postojeceg cenovnika
            PriceList priceList = priceListService.findById(adCreateDTO.getPriceListCreateDTO().getId());
            //TODO 2: DODATI OGLAS TAJ PRICE LISTI
            ad.setPriceList(priceList);
        }

        if (adCreateDTO.getCarCalendarTermCreateDTOList() != null) {
            List<CarCalendarTermCreateDTO> carCalendarTermCreateDTOList = adCreateDTO.getCarCalendarTermCreateDTOList();
            for (CarCalendarTermCreateDTO carCalendarTermCreateDTO : carCalendarTermCreateDTOList) {
                CarCalendarTerm carCalendarTerm = CarCalendarTermConverter.toCreateCarCalendarTermFromRequest(carCalendarTermCreateDTO);
                carCalendarTerm = carCalendarTermService.save(carCalendarTerm);
                ad.getCarCalendarTerms().add(carCalendarTerm);
            }
        }
        PublisherUser publisherUser = publisherUserService.findByEmail(email);
        ad.setPublisherUser(publisherUser);
        ad = this.save(ad);

        if(rez != 4){
            Integer r = endUserService.reduceAdLimitNum(email);
            System.out.println("Limit num: "+ r);
        }
        return 1;
    }


    @Override
    public void syncData() {

    }

    @Override
    public AdPageContentDTO findAllOrdinarySearch(Integer page, Integer size, String location, DateTime startDate, DateTime endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Ad> ads = adRepository.findByDeletedAndLocationAndCarCalendarTermsStartDateBeforeAndCarCalendarTermsEndDateAfter(false, location, startDate, endDate, pageable);
        List<AdPageDTO> ret = ads.stream().map(AdConverter::toCreateAdPageDTOFromAd).collect(Collectors.toList());

        System.out.println(ret.size());
        AdPageContentDTO adPageContentDTO = AdPageContentDTO.builder()
                .totalPageCnt(ads.getTotalPages())
                .ads(ret)
                .build();

        return adPageContentDTO;
    }

    @Override
    public AdPageContentDTO findAll(Integer page, Integer size) {

//        Pageable pageable;
//        if(sort.equals("-")){
//            pageable = PageRequest.of(page, size);
//        }else{
//            String par[] = sort.split(" ");
//            if(par[1].equals("opadajuce")) {
//                pageable = PageRequest.of(page, size, Sort.by(par[0]).descending());
//            }else{
//                pageable = PageRequest.of(page, size, Sort.by(par[0]).ascending());
//            }
//
//        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Ad> ads = adRepository.findAllByDeleted(false, pageable);
      

        List<AdPageDTO> ret = ads.stream().map(AdConverter::toCreateAdPageDTOFromAd).collect(Collectors.toList());
        System.out.println(ret.size());
        AdPageContentDTO adPageContentDTO = AdPageContentDTO.builder()
                .totalPageCnt(ads.getTotalPages())
                .ads(ret)
                .build();


        return adPageContentDTO;
    }

    @Override
    public AdPageContentDTO findAll(Integer page, Integer size, String email) {
        User pu = userService.findByEmail(email);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Ad> ads = adRepository.findAllByDeletedAndPublisherUserEmail(false, email, pageable);

        List<AdPageDTO> ret = ads.stream().map(AdConverter::toCreateAdPageDTOFromAd).collect(Collectors.toList());
        System.out.println(ret.size());
        AdPageContentDTO adPageContentDTO = AdPageContentDTO.builder()
                .totalPageCnt(ads.getTotalPages())
                .ads(ret)
                .build();


        return adPageContentDTO;
    }


}
