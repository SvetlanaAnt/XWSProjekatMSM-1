package agent.app.service.intf;


import agent.app.dto.ad.AdCreateDTO;
import agent.app.dto.ad.AdPageContentDTO;
import agent.app.model.Ad;

import java.util.List;

public interface AdService {
    Ad findById(Long id);

    List<Ad> findAll();

    AdPageContentDTO findAll(Integer page, Integer size);

    Ad save(Ad ad);

    void delete(Ad ad);

    void logicalDeleteOrRevertAds(List<Ad> ads, Boolean status);

    void logicalDeleteOrRevert(Ad ad, Boolean status);

    Integer deleteById(Long id);

    Integer createAd(AdCreateDTO adCreateDTO);

    void syncData();
//    AdPageContentDTO findAllPageAd(Integer page, Integer size, String sort);
//    AdSearchDTO findAllSearchAdd(Integer page, Integer size, String sort)


}
