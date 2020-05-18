package services.app.codebookservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import services.app.codebookservice.dto.CarTypeDTO;
import services.app.codebookservice.exception.ExistsException;
import services.app.codebookservice.exception.NotFoundException;
import services.app.codebookservice.model.CarType;
import services.app.codebookservice.repository.CarTypeRepository;
import services.app.codebookservice.service.intf.CarTypeService;

import java.util.List;

@Service
public class CarTypeServiceImpl implements CarTypeService {

    @Autowired
    private CarTypeRepository carTypeRepository;

    @Override
    public CarType findById(Long id) {
        return carTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Tip automobila ne postoji u sifarniku."));
    }

    @Override
    public List<CarType> findAll() {
        return carTypeRepository.findAll();
    }

    @Override
    public CarTypeDTO findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<CarType> carTypePage = carTypeRepository.findAll(pageable);
        CarTypeDTO carTypeDTO = CarTypeDTO.builder()
                .totalPageCnt(carTypePage.getTotalPages())
                .carTypes(carTypePage.getContent())
                .build();

        return carTypeDTO;
    }

    @Override
    public Integer createCarType(String name) {
        CarType carType = CarType.builder()
                .name(name)
                .build();
        this.save(carType);
        return 1;
    }

    @Override
    public Integer editCarType(CarType carType) {
        this.findById(carType.getId());
        carTypeRepository.save(carType);
        return 1;
    }

    @Override
    public Integer deleteById(Long id) {
        CarType carType = this.findById(id);
        this.delete(carType);
        return 1;
    }

    @Override
    public void delete(CarType carType) {
        carTypeRepository.delete(carType);
    }

    @Override
    public CarType save(CarType carType) {
        if (carTypeRepository.existsByName(carType.getName())) {
            throw new ExistsException(String.format("Tip automobila sa imenom '%s' vec postoji u sifarniku", carType.getName()));
        }

        return carTypeRepository.save(carType);
    }
}
