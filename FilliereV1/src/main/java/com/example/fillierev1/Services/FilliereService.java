package com.example.fillierev1.Services;

import com.example.fillierev1.DTO.FilliereRequest;
import com.example.fillierev1.DTO.FilliereResponse;
import com.example.fillierev1.Entities.Filliere;
import com.example.fillierev1.Mapper.FilliereMapper;
import com.example.fillierev1.Repository.FilliereRepository;
import org.springframework.stereotype.Service;

@Service
public class FilliereService {
    FilliereRepository filliereRepository;
    FilliereMapper filliereMapper;
    public FilliereService(FilliereRepository filliereRepository, FilliereMapper filliereMapper) {
        this.filliereRepository = filliereRepository;
        this.filliereMapper = filliereMapper;
    }
    public FilliereResponse create (FilliereRequest filliereresquest) {
        Filliere filliere = filliereMapper.dtoToEntity(filliereresquest);
        Filliere saved =filliereRepository.save(filliere);
        return filliereMapper.entityToDto(saved);
    }
}
