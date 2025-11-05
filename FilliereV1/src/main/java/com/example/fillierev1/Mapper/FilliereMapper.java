package com.example.fillierev1.Mapper;

import com.example.fillierev1.DTO.FilliereRequest;
import com.example.fillierev1.DTO.FilliereResponse;
import com.example.fillierev1.Entities.Filliere;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FilliereMapper {
    public Filliere dtoToEntity(FilliereRequest filliereRequest) {
        Filliere filliere = new Filliere();
        BeanUtils.copyProperties(filliereRequest,filliere);
        return filliere;
    }
    public FilliereResponse entityToDto (Filliere filliere) {
        FilliereResponse filliereResponse = new FilliereResponse();
        BeanUtils.copyProperties(filliere,filliereResponse);
        return filliereResponse;
    }
}
