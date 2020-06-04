package services.app.adsearchservice.service.intf;



import org.joda.time.DateTime;
import services.app.adsearchservice.dto.AdPageContentDTO;
import services.app.adsearchservice.dto.AdPageDTO;
import services.app.adsearchservice.model.Ad;

import java.util.List;

public interface AdService {
    Ad findById(Long id);
    List<Ad> findAll();
    AdPageContentDTO findAll(Integer page, Integer size);
    void syncData();
    AdPageContentDTO findAllOrdinarySearch(Integer page, Integer size, String location, DateTime startDate, DateTime endDate);

    //moguce slati sort parametre
    AdPageDTO advancedSearch();
}
