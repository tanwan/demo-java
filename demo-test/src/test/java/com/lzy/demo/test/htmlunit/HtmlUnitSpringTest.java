package com.lzy.demo.test.htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HtmlUnitSpringTest {

    @Resource
    private WebClient webClient;

    private HtmlPage page;

    @BeforeAll
    public void init() throws Exception {
        page = webClient.getPage("/input.html");
    }

    @AfterAll
    public void close() throws Exception {
        webClient.close();
    }


    /**
     * 测试提交表单
     *
     * @throws IOException the io exception
     */
    @Test
    public void testSubmitForm() throws IOException {
        HtmlForm form = page.getFormByName("demo-form");
        // 通过html获取input进行设值
        HtmlTextInput messageText = page.getHtmlElementById("form-field1");
        messageText.setValueAttribute("value1");
        // 通过form获取input(byName)进行设值
        form.getInputByName("form-field2").setValueAttribute("value2");

        // 获取submit
        HtmlButton submit = form.getOneHtmlElementByAttribute("button", "type", "submit");
        // 提交表单
        HtmlPage newPage = submit.click();

        assertThat(newPage.getHtmlElementById("result").getTextContent()).contains("value1").contains("value2");
    }

    /**
     * 测试点击
     *
     * @throws IOException the io exception
     */
    @Test
    public void testClickAnchor() throws IOException {
        HtmlAnchor htmlAnchor = page.querySelector("#anchor");
        HtmlPage newPage = htmlAnchor.click();
        assertThat(newPage.getHtmlElementById("result").getTextContent()).contains("clickAnchor");
    }
}
