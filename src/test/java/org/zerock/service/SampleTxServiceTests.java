package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class SampleTxServiceTests {

    @Setter(onMethod_ = {@Autowired})
    private SampleTxService service;

    /*
        테스트에 사용한 문자열 str은 82bytes
        -> @Transactional이 없을 때는 tbl_sample1에는 데이터가 추가되지만,
            tbl_sample2에는 길이 제한으로 실패하게 됨
        -> @Transactional을 추가한 뒤에는 rollback() 처리 됨
     */
    @Test
    public void testLong() {
        String str = "Starry\r\n" +
                "Starry night\r\n" +
                "Paint your palette blue and gray\r\n" +
                "Look out on a summer`s day";

        log.info(str.getBytes().length);

        service.addData(str);
    }
}
