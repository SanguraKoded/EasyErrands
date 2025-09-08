package com.sangura.Tracking_Service.mapper;

import com.sangura.Tracking_Service.dtos.TrackingCreateDto;
import com.sangura.Tracking_Service.dtos.TrackingDto;
import com.sangura.Tracking_Service.entities.Tracking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackingMapper {

    Tracking toEntity(TrackingCreateDto trackingDto);
    TrackingDto toDto(Tracking tracking);
}
