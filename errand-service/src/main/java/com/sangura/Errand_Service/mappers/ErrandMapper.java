package com.sangura.Errand_Service.mappers;

import com.sangura.Errand_Service.Dtos.ErrandDto;
import com.sangura.Errand_Service.Dtos.ErrandSavedDto;
import com.sangura.Errand_Service.entities.Errand;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ErrandMapper {

    Errand toEntity(ErrandDto errandDto);
    ErrandSavedDto toDto(Errand errand);
}
