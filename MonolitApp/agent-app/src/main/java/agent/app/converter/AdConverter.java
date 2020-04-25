package agent.app.converter;

import agent.app.dto.AdCreateDTO;
import agent.app.dto.AdPageDTO;
import agent.app.model.Ad;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.HashSet;

public class AdConverter {

    public static Ad toCreateAdFromRequest(AdCreateDTO adCreateDTO){
        return Ad.builder()
                .name(adCreateDTO.getName())
                .location(adCreateDTO.getLocation())
                .coverPhoto(adCreateDTO.getCoverPhoto())
                .distanceLimitFlag(adCreateDTO.getDistanceLimitFlag())
                .distanceLimit(adCreateDTO.getDistanceLimit())
                .publishedDate(new DateTime(DateTimeZone.UTC))
                .deleted(false)
                .enabled(true)
                .discountLists(new HashSet<>())
                .requests(new HashSet<>())
                .comments(new HashSet<>())
                .carOccupationTerms(new HashSet<>())
                .rentCnt(0L)
                .ratingCnt(0L)
                .ratingNum(0L)
                .build();
    }

    public static AdPageDTO toCreateAdPageDTOFromAd(Ad ad){
        return AdPageDTO.builder()
                .name(ad.getName())
                .location(ad.getLocation())
                .coverPhoto(ad.getCoverPhoto())
                .build();
    }
}
