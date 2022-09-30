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
public class SampleServiceTests {

    /*
        <aop:aspectj-autoproxy></aop:aspectj-autoproxy> 가 정상적으로 동작하고
        LogAdvice의 설정 문제가 없다면 service 변수의 클래스는
        단순히 org.zerock.service.SampleServiceImpl의
        인스턴스가 아닌 생성된 Proxy클래스의 인스턴스가 됨
     */
    @Setter(onMethod_ = @Autowired)
    private SampleService service;

    @Test
    public void testClass() {

        log.info(service);  // toString()의 결과
        log.info(service.getClass().getName());

    }

    @Test
    public void testAdd() throws Exception {

        // LogAdvice의 설정이 같이 적용되어서 결과가 나옴
        log.info(service.doAdd("123", "456"));

    }

    @Test
    public void testAddError() throws Exception {

        log.info(service.doAdd("123", "ABC"));

    }
}
