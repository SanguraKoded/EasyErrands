package com.sangura.Errand_Service.services;

import com.sangura.Errand_Service.Dtos.ErrandDto;
import com.sangura.Errand_Service.Dtos.ErrandSavedDto;
import com.sangura.Errand_Service.entities.Errand;
import com.sangura.Errand_Service.mappers.ErrandMapper;
import com.sangura.Errand_Service.repos.ErrandsRepo;
import com.sangura.common.events.DTOs.ErrandEvent;
import com.sangura.common.events.enums.ErrandEventType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RefreshScope
public class ErrandServiceImpl implements ErrandService {

    private final ErrandEventProducer errandEventProducer;

    private static final Logger log = LoggerFactory.getLogger(ErrandServiceImpl.class);

    private final ErrandsRepo errandsRepo;
    private final ErrandMapper errandMapper;

    public ErrandServiceImpl(ErrandEventProducer errandEventProducer, ErrandsRepo errandsRepo, ErrandMapper errandMapper) {
        this.errandEventProducer = errandEventProducer;
        this.errandsRepo = errandsRepo;
        this.errandMapper = errandMapper;
    }

    @Override
    @Transactional
    public ErrandSavedDto createErrand(ErrandDto errandDto) {

        Errand errand = errandMapper.toEntity(errandDto);

        log.info("Mapped entity: {}", errand);
        errand.setTimeCreated(LocalDateTime.now());
        errand.setDone(Boolean.FALSE);
        errand.setTimeTaken(0);
        errand.setLate(Boolean.FALSE);
        Errand savedErrand = errandsRepo.save(errand);

        ErrandEvent errandEvent = new ErrandEvent();
        errandEvent.setErrandId(savedErrand.getId());
        errandEvent.setDescription(savedErrand.getDescription());
        errandEvent.setEventType(ErrandEventType.CREATED);
        errandEventProducer.publishErrandCreated(errandEvent);
        errandEvent.setOccuredAt(Instant.now());

        log.info("Saved Entity: {}", errand);


        return errandMapper.toDto(savedErrand);
    }

    @Override
    @Transactional
    @CacheEvict(value="errand", key="#id")
    public String deleteErrand(Long id) {
        Errand errand = errandsRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));
        errandsRepo.delete(errand);
        ErrandEvent errandEvent = new ErrandEvent();
        errandEvent.setErrandId(errand.getId());
        errandEvent.setDescription(errand.getDescription());
        errandEvent.setEventType(ErrandEventType.CANCELLED);
        errandEvent.setOccuredAt(Instant.now());
        errandEventProducer.publishErrandDeleted(errandEvent);
        return "Successfully Deleted Errand";
    }

    @Override
    @Transactional
    @CachePut(value="users", key="#id")
    public ErrandSavedDto updateErrand(Long id, ErrandDto errandDto) {
        Errand errand = errandsRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));
        Errand updatedErrand = errandMapper.toEntity(errandDto);
        updatedErrand.setLate(errand.getLate());
        updatedErrand.setTimeTaken(errand.getTimeTaken());
        updatedErrand.setTimeCreated(errand.getTimeCreated());
        updatedErrand.setAssignedTasker(errand.getAssignedTasker());
        Errand savedErrand = errandsRepo.save(updatedErrand);
        ErrandEvent errandEvent = new ErrandEvent();
        errandEvent.setErrandId(savedErrand.getId());
        errandEvent.setDescription(savedErrand.getDescription());
        errandEvent.setEventType(ErrandEventType.UPDATED);
        errandEvent.setOccuredAt(Instant.now());
        errandEventProducer.publishErrandUpdated(errandEvent);
        return errandMapper.toDto(savedErrand);
    }

    @Override
    @Transactional
    @Cacheable(value="errands", key="#id")
    public List<ErrandSavedDto> getAllActiveErrands() {


        List<Errand> allErrandEntities = errandsRepo.findAll();
        List<ErrandSavedDto> allErrandDtos = new ArrayList<>();
        for(Errand errand : allErrandEntities){
            allErrandDtos.add(errandMapper.toDto(errand));
        }
    return allErrandDtos;
    }

    @Override
    @Transactional
    @Cacheable(value="errand", key="#id")
    public ErrandSavedDto getErrandById(Long id) {
        Errand foundErrand =  errandsRepo.findById(id).orElseThrow(()-> new RuntimeException("Please Enter Valid Errand ID"));
        return errandMapper.toDto(foundErrand);
    }
}
