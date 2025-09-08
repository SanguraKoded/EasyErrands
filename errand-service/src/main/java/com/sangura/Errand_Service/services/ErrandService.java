package com.sangura.Errand_Service.services;

import com.sangura.Errand_Service.Dtos.ErrandDto;
import com.sangura.Errand_Service.Dtos.ErrandSavedDto;
import com.sangura.Errand_Service.entities.Errand;

import java.util.List;

public interface ErrandService {

    ErrandSavedDto createErrand(ErrandDto errandDto);

    String deleteErrand(Long id);

    ErrandSavedDto updateErrand(Long id, ErrandDto errandDto);

    List<ErrandSavedDto> getAllActiveErrands();

    ErrandSavedDto getErrandById(Long id);

}
