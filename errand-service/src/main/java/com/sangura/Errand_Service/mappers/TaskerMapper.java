package com.sangura.Errand_Service.mappers;

import com.sangura.Errand_Service.Dtos.TaskerDto;
import com.sangura.Errand_Service.Dtos.TaskerSavedDto;
import com.sangura.Errand_Service.entities.Tasker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface TaskerMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "location", target = "location")
    Tasker toEntity(TaskerDto tasker);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "errandsCompleted", target = "errandsCompleted")
    @Mapping(source = "activeErrands", target = "activeErrandIds")
    @Mapping(source = "ratings", target = "ratings")
    TaskerSavedDto toDto(Tasker tasker);
}
