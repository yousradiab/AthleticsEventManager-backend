package com.example.programmering2024.service;

import com.example.programmering2024.dto.ResultatDto;
import com.example.programmering2024.entity.Deltager;
import com.example.programmering2024.entity.Disciplin;
import com.example.programmering2024.entity.Resultat;
import com.example.programmering2024.mapper.ResultatMapper;
import com.example.programmering2024.repository.DeltagerRepository;
import com.example.programmering2024.repository.DisciplinRepository;
import com.example.programmering2024.repository.ResultatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultatService {

    private final ResultatRepository resultatRepository;
    private final DeltagerRepository deltagerRepository;
    private final DisciplinRepository disciplinRepository;

    public ResultatService(ResultatRepository resultatRepository, DeltagerRepository deltagerRepository, DisciplinRepository disciplinRepository) {
        this.resultatRepository = resultatRepository;
        this.deltagerRepository = deltagerRepository;
        this.disciplinRepository = disciplinRepository;
    }

    @Transactional
    public ResultatDto registerSingleResult(ResultatDto resultatDto) {
        Deltager deltager = deltagerRepository.findById(resultatDto.getDeltager().getId())
                .orElseThrow(() -> new RuntimeException("Deltager not found"));

        Disciplin disciplin = disciplinRepository.findById(resultatDto.getDisciplin().getId())
                .orElseThrow(() -> new RuntimeException("Disciplin not found"));

        Resultat nytResultat = ResultatMapper.mapToEntity(resultatDto);
        nytResultat.setDisciplin(disciplin);
        nytResultat.setDeltager(deltager);


        Resultat savedResultat = resultatRepository.save(nytResultat);
        return ResultatMapper.mapToDto(savedResultat);
    }

    @Transactional
    public void registerMultipleResults(List<ResultatDto> resultaterDto) {

        for (ResultatDto dto : resultaterDto) {


            Deltager deltager = deltagerRepository.findById(dto.getDeltager().getId())
                    .orElseThrow(() -> new RuntimeException("Deltager not found"));

            Disciplin disciplin = disciplinRepository.findById(dto.getDisciplin().getId())
                    .orElseThrow(() -> new RuntimeException("Disciplin not found"));

                Resultat nytResultat = ResultatMapper.mapToEntity(dto);
                nytResultat.setDisciplin(disciplin);
                nytResultat.setDeltager(deltager);
                resultatRepository.save(nytResultat);

    }
    }
}
