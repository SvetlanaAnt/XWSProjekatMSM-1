package services.app.adservice.converter;


import services.app.adservice.dto.car.CarCalendarTermCreateDTO;
import services.app.adservice.model.CarCalendarTerm;

public class CarCalendarTermConverter {

    public static CarCalendarTerm toCreateCarCalendarTermFromRequest(CarCalendarTermCreateDTO carCalendarTermCreateDTO){

        return CarCalendarTerm.builder()
                .startDate(DateAPI.dateStringToDateTime(carCalendarTermCreateDTO.getStartDate()))
                .endDate(DateAPI.dateStringToDateTime(carCalendarTermCreateDTO.getEndDate()))
                .build();
    }
}
