package com.loadcell;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LoadCellJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;
    private final ReentrantLock lock = new ReentrantLock();
    private static final int MAX_RETRIES = Integer.MAX_VALUE;
    private static final int BATCH_SIZE = 5000;

    public LoadCellJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int[][] saveBatch(final List<CellPhone> phones) {
//        lock.lock();
        int retries = 0;
        boolean success = false;
        while (retries < MAX_RETRIES && !success) {
            try {
                int[][] updateCounts = jdbcTemplate.batchUpdate(
                        "INSERT INTO cell_phone (phone_number, date_modify) VALUES (?, ?) " +
                                "ON DUPLICATE KEY UPDATE date_modify = VALUES(date_modify)",
                        phones,
                        BATCH_SIZE,
                        (PreparedStatement ps, CellPhone cellPhone) -> {
                            ps.setString(1, cellPhone.getPhoneNumber());
                            ps.setString(2, cellPhone.getDateModify().toString());
                        });
                success = true;
//                lock.unlock();
                return updateCounts;
            } catch (CannotAcquireLockException e) {
                retries++;
                if (retries >= MAX_RETRIES) {
                    throw e; // Rethrow exception after max retries
                }
                try {
                    Thread.sleep(3000); // Wait before retrying
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(interruptedException);
                }
            }
        }
        return null;
    }
//    @Transactional
//    public void saveBatch(List<CellPhone> phones) {
//        cellPhoneRepository.saveAll(phones);
//    }
}
