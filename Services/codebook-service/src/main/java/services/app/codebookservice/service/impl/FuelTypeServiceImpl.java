package services.app.codebookservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import services.app.codebookservice.dto.FuelTypeDTO;
import services.app.codebookservice.exception.ExistsException;
import services.app.codebookservice.exception.NotFoundException;
import services.app.codebookservice.model.FuelType;
import services.app.codebookservice.repository.FuelTypeRepository;
import services.app.codebookservice.service.intf.FuelTypeService;

import java.util.List;

@Service
public class FuelTypeServiceImpl implements FuelTypeService {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Override
    public FuelType findById(Long id) {
        return fuelTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Tip pogonskog goriva ne postoji u sifarniku."));
    }

    @Override
    public List<FuelType> findAll() {
        return fuelTypeRepository.findAll();
    }

    @Override
    public FuelTypeDTO findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<FuelType> fuelTypePage = fuelTypeRepository.findAll(pageable);
        FuelTypeDTO fuelTypeDTO = FuelTypeDTO.builder()
                .totalPageCnt(fuelTypePage.getTotalPages())
                .fuelTypes(fuelTypePage.getContent())
                .build();

        return fuelTypeDTO;
    }

    @Override
    public Integer createFuelType(String name) {
        FuelType fuelType = FuelType.builder()
                .name(name)
                .build();
        this.save(fuelType);
        return 1;
    }

    @Override
    public Integer editFuelType(FuelType fuelType) {
        this.findById(fuelType.getId());
        fuelTypeRepository.save(fuelType);
        return 1;
    }

    @Override
    public Integer deleteById(Long id) {
        FuelType fuelType = this.findById(id);
        this.delete(fuelType);
        return 1;
    }

    @Override
    public void delete(FuelType fuelType) {
        fuelTypeRepository.delete(fuelType);
    }

    @Override
    public FuelType save(FuelType fuelType) {
        if (fuelTypeRepository.existsByName(fuelType.getName())) {
            throw new ExistsException(String.format("Tip pogonskog goriva sa imenom '%s' vec postoji u sifarniku", fuelType.getName()));
        }

        return fuelTypeRepository.save(fuelType);
    }
}
