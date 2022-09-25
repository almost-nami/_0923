package org.zerock.service;

import lombok.Setter;
import lombok.Value;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

/*
    트랜잭션 : 한 번에 이루어지는 작업의 단위
        -> 가장 흔한 예는 계좌이체
 */
@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService {

    @Setter(onMethod_ = {@Autowired})
    private Sample1Mapper mapper1;

    @Setter(onMethod_ = {@Autowired})
    private Sample2Mapper mapper2;

    // @Transactional : 트랜잭션 처리
    @Transactional
    @Override
    public void addData(String value) {

        log.info("mapper1 ...........");
        mapper1.insertCol1(value);

        log.info("mapper2 ...........");
        mapper2.insertCol2(value);

        log.info("end............");
    }
}
