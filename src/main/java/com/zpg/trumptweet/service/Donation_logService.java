package com.zpg.trumptweet.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zpg.trumptweet.domain.Donation_log;
import com.zpg.trumptweet.repository.Donation_logRepository;

/**
 * Service Implementation for managing Donation_log.
 */
@Service
@Transactional
public class Donation_logService {

    private final Logger log = LoggerFactory.getLogger(Donation_logService.class);
    
    private final Donation_logRepository donation_logRepository;

    public Donation_logService(Donation_logRepository donation_logRepository) {
        this.donation_logRepository = donation_logRepository;
    }

    /**
     * Save a donation_log.
     *
     * @param donation_log the entity to save
     * @return the persisted entity
     */
    public Donation_log save(Donation_log donation_log) {
        log.debug("Request to save Donation_log : {}", donation_log);
        Donation_log result = donation_logRepository.save(donation_log);
        return result;
    }

    /**
     *  Get all the donation_logs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Donation_log> findAll(Pageable pageable) {
        log.debug("Request to get all Donation_logs");
        Page<Donation_log> result = donation_logRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one donation_log by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Donation_log findOne(Long id) {
        log.debug("Request to get Donation_log : {}", id);
        Donation_log donation_log = donation_logRepository.findOne(id);
        return donation_log;
    }
    
    /**
     *  Get total donations for one user
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BigDecimal findTotal() {
        log.debug("Request to get Donation_total");
        List<Donation_log> donation_logs = donation_logRepository.findByUserIsCurrentUser();
        BigDecimal total = BigDecimal.ZERO;
        for (Donation_log log : donation_logs) {
        	if (log.isProcessed()){
        		total = total.add(log.getAmount());
        	}
        }
        return total;
    }
    
    /**
     *  Get total donations for current month
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BigDecimal findTotalMonth() {
        log.debug("Request to get Donation_total_month");
        Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		month ++; //months are 0 indexed. neat. why.
		int year = cal.get(Calendar.YEAR);		
		String yearMonth;
		if (month >= 10){ //Need padding for months under 10
			yearMonth = year + "/" + month;
		}else {
			yearMonth = year + "/0" + month;
		}
		log.debug("yearmonth is " + yearMonth);
        List<Donation_log> donation_logs = donation_logRepository.findTotalMonthCurrentUser(yearMonth);
        BigDecimal total = BigDecimal.ZERO;
        for (Donation_log log : donation_logs) {
        	if (log.isProcessed()){
        		total = total.add(log.getAmount());
        	}
        }
        return total;
    }


    /**
     *  Delete the  donation_log by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Donation_log : {}", id);
        donation_logRepository.delete(id);
    }
    
    /**
     *  Get pending payments for current user
     *
     *  @return list of donation log pending payments
     */
	public List<Donation_log> findPendingPayments() {
		List<Donation_log> pendingPayments = donation_logRepository.findPendingPaymentsByCurrentUser();
		return pendingPayments;
	}

	
}
