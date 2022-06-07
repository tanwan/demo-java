package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 自定义序列化和反序列器
 * 适用于需要特定的序列化和反序列化
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonSerializer {

    public SimpleJsonSerializer() {
    }

    public SimpleJsonSerializer(String property, LocalDateTime localDateTime) {
        this.property = property;
        this.localDateTime = localDateTime;
    }

    //使用自定义序列化和反序列化
    @JsonDeserialize(using = CustomDeserializer.class)
    @JsonSerialize(using = CustomSerializer.class)
    private String property;

    //解析LocalDateTime
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    /**
     * 泛型为序列化的源类型
     */
    public static class CustomSerializer extends StdSerializer<String> {

        public CustomSerializer() {
            super(String.class);
        }

        @Override
        public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("custom" + s);
        }
    }

    /**
     * 泛型为反序列化的目标类型
     */
    public static class CustomDeserializer extends StdDeserializer<String> {
        public CustomDeserializer() {
            super(String.class);
        }

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            final String date = jsonParser.getText();
            return date.replace("custom", "");
        }

    }
}
