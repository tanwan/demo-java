package com.lzy.demo.service;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import com.caucho.hessian.io.SerializerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzy.demo.service.bean.ExtendHessianMessage;
import com.lzy.demo.service.bean.HessianMessage;
import com.lzy.demo.service.service.SimpleHessianService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HessianTest {


    private static final SerializerFactory SERIALIZER_FACTORY = new SerializerFactory();


    /**
     * 测试序列化
     *
     * @throws IOException the io exception
     */
    @Test
    public void testSerializable() throws IOException {
        //序列化
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianMessage hessianMessage = buildHessianMessage();
        HessianSerializerOutput hessianSerializerOutput = new HessianSerializerOutput(os);
        hessianSerializerOutput.setSerializerFactory(SERIALIZER_FACTORY);
        hessianSerializerOutput.writeObject(hessianMessage);
        hessianSerializerOutput.flush();
        hessianSerializerOutput.close();
        byte[] bytes = os.toByteArray();
        System.out.println("hessian byte size:" + bytes.length);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("json byte size:" + objectMapper.writeValueAsBytes(hessianMessage).length);

        //反序列化
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        HessianSerializerInput hessianSerializerInput = new HessianSerializerInput(is);
        hessianSerializerInput.setSerializerFactory(SERIALIZER_FACTORY);
        HessianMessage readHessianMessage = (HessianMessage) hessianSerializerInput.readObject(HessianMessage.class);
        System.out.println(readHessianMessage);
    }

    /**
     * 测试有重写属性的序列化
     *
     * @throws IOException exception
     */
    @Test
    public void testOverrideSerializable() throws IOException {
        //序列化
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExtendHessianMessage extendHessianMessage = new ExtendHessianMessage();
        extendHessianMessage.setString("hessian");
        HessianSerializerOutput hessianSerializerOutput = new HessianSerializerOutput(os);
        hessianSerializerOutput.setSerializerFactory(SERIALIZER_FACTORY);
        hessianSerializerOutput.writeObject(extendHessianMessage);
        hessianSerializerOutput.flush();
        hessianSerializerOutput.close();

        //反序列化
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        HessianSerializerInput hessianSerializerInput = new HessianSerializerInput(is);
        hessianSerializerInput.setSerializerFactory(SERIALIZER_FACTORY);
        HessianMessage readHessianMessage = (HessianMessage) hessianSerializerInput.readObject(HessianMessage.class);
        //如果bean有重写父类属性的,hessian序列化后,此字段会为null
        //底层使用unsafe.putObject,因此不会调用set方法,unsafe.putObject在为有重写属性赋值会失败,因此这边字段会为null
        assertThat(readHessianMessage.getString()).isNull();
        System.out.println(readHessianMessage);
    }


    @SpringBootTest
    @ActiveProfiles({"hessian", "no-eureka"})
    public static class HessianRPCTest {

        @Resource
        private SimpleHessianService simpleHessianService;

        /**
         * 测试hessianRPC
         *
         * @see com.lzy.demo.service.config.HessianConfig
         */
        @Test
        public void testHessianRPC() {
            System.out.println(simpleHessianService.simpleHessian(buildHessianMessage()));
        }


    }

    private static HessianMessage buildHessianMessage() {
        HessianMessage hessianMessage = new HessianMessage();
        hessianMessage.setBigDecimal(BigDecimal.ONE);
        hessianMessage.setString("hello world");
//        hessianMessage.setLocalDateTime(LocalDateTime.now());
        hessianMessage.setNumberLong(Long.MAX_VALUE);
        HessianMessage innerMessage = new HessianMessage();
        innerMessage.setBigDecimal(BigDecimal.valueOf(0.23));
        innerMessage.setString("hello world");
//        innerMessage.setLocalDateTime(LocalDateTime.now());
        innerMessage.setNumberLong(Long.MAX_VALUE);
        hessianMessage.setInnerMessage(innerMessage);
        hessianMessage.setInnerMessageList(Collections.singletonList(innerMessage));
        Map<String, HessianMessage> messageMap = new HashMap<>();
        messageMap.put("messageMap", innerMessage);
        hessianMessage.setInnerMessageMap(messageMap);
        return hessianMessage;
    }


}
