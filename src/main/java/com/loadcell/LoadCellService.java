package com.loadcell;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Service
public class LoadCellService {

    private final SaveCellService saveCellService;
    private final LoadCellJdbcTemplate loadCellJdbcTemplate;
    private final CellPhoneRepository cellPhoneRepository;
    private final String FILE_PATH = "C:\\Users\\nmq13\\Desktop\\phone_numbers.txt";
    private final int BATCH_SIZE = 400000;

    public LoadCellService(SaveCellService saveCellService, LoadCellJdbcTemplate loadCellJdbcTemplate, CellPhoneRepository cellPhoneRepository) {
        this.saveCellService = saveCellService;
        this.loadCellJdbcTemplate = loadCellJdbcTemplate;
        this.cellPhoneRepository = cellPhoneRepository;
    }

    public void loadCell() {
        List<CellPhone> cellPhoneList = cellPhoneRepository.findAll();
        Set<CellPhone> set = new HashSet<>(4000000);
//        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                CellPhone cellPhone = new CellPhone();
//                cellPhone.setDateModify(LocalDate.now());
//                cellPhone.setPhoneNumber(line.trim());
//                set.add(cellPhone);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        List<FileChunkReader> lst = new ArrayList<>();
//        List<CellPhone> set = new ArrayList<>(BATCH_SIZE);
//        ExecutorService executorService = null;
//        int i = 0;
//        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                CellPhone cellPhone = new CellPhone();
//                cellPhone.setDateModify(LocalDate.now());
//                cellPhone.setPhoneNumber(line.trim());
//                set.add(cellPhone);
//                i++;
//                if (i == BATCH_SIZE || !reader.ready()) {
//                    FileChunkReader fileChunkReader = new FileChunkReader(new ArrayList<>(set));
//                    lst.add(fileChunkReader);
//                    // clear set
//                    set.clear();
//                    i = 0;
//                }
//            }
//            executorService = Executors.newFixedThreadPool(lst.size());
//            List<Future<Boolean>> futureLst = executorService.invokeAll(lst);
//            for (Future<Boolean> future : futureLst) {
//                try {
//                    if (!future.get()) {
//                        System.err.println("A task failed to execute successfully.");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            if (executorService != null) {
//                executorService.shutdown();
//                try {
//                    if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
//                        executorService.shutdownNow();
//                    }
//                } catch (InterruptedException e) {
//                    executorService.shutdownNow();
//                }
//            }
//        }
    }

    class FileChunkReader implements Callable<Boolean> {
        private final List<CellPhone> chunk;

        public FileChunkReader(List<CellPhone> chunk) {
            this.chunk = chunk;
        }

        @Override
        public Boolean call() {
            loadCellJdbcTemplate.saveBatch(chunk);
            return true;
        }
    }

    public void loadCell2() {
        List<CellPhone> set = new ArrayList<>(BATCH_SIZE);
        List<List<CellPhone>> lst = new ArrayList<>();
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CellPhone cellPhone = new CellPhone();
                cellPhone.setDateModify(LocalDate.now());
                cellPhone.setPhoneNumber(line.trim());
                set.add(cellPhone);
                i++;
                if (i == BATCH_SIZE || !reader.ready()) {
                    lst.add(new ArrayList<>(set));
                    // clear set
                    set.clear();
                    i = 0;
                }
            }
            for (List<CellPhone> item : lst) {
                saveCellService.saveBatch(item);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
