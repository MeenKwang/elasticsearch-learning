package com.loadcell;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaveCellService {

    private final CellPhoneRepository cellPhoneRepository;

    public SaveCellService(CellPhoneRepository cellPhoneRepository) {
        this.cellPhoneRepository = cellPhoneRepository;
    }

    @Transactional
    public void saveBatch(List<CellPhone> phones) {
        for (CellPhone cellPhone : phones) {
            if (!cellPhoneRepository.existsByPhoneNumber(cellPhone.getPhoneNumber())) {
                cellPhoneRepository.save(cellPhone);
            }
        }
//        cellPhoneRepository.saveAll(phones);
    }
}
