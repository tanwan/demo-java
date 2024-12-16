package com.lzy.demo.test.htmlunit;

import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlDivision;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HtmlUnitTest {

    private WebClient webClient;

    private HtmlPage page;

    @BeforeAll
    public void init() throws Exception {
        // WebClient也可以指定浏览器
        webClient = new WebClient();
        page = webClient.getPage("https://htmlunit.sourceforge.io/");
    }

    @AfterAll
    public void close() throws Exception {
        webClient.close();
    }

    /**
     * 测试基础用法
     */
    @Test
    public void testBase() {
        assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());

        // 获取为xml
        String pageAsXml = page.asXml();
        assertTrue(pageAsXml.contains("<body class=\"topBarDisabled\">"));

        // 获取为可视化的文本
        String pageAsText = page.asNormalizedText();
        System.out.println(pageAsText);
        assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
    }

    /**
     * 测试获取元素
     */
    @Test
    public void testGetElements() {
        // 使用id获取
        HtmlElement element = page.getHtmlElementById("breadcrumbs");
        System.out.println(element);
    }

    /**
     * 测试CSS选择器
     */
    @Test
    public void testCSSSelectors() {
        // 根据选择器获取所有元素
        DomNodeList<DomNode> divs = page.querySelectorAll("div");
        for (DomNode div : divs) {
            DomNode byCssId = div.querySelector("#breadcrumbs");
            if (byCssId != null) {
                System.out.println(byCssId.asXml());
            }
        }
        // 根据选择器获取单个元素
        DomNode div = page.querySelector("div#breadcrumbs");
        System.out.println(div.asXml());
    }

    /**
     * 测试使用XPath
     */
    @Test
    public void testXPath() {
        // 使用XPath获取元素
        List<HtmlElement> divs = page.getByXPath("//div");
        for (HtmlElement e : divs) {
            System.out.println(e.getNodeName());
        }

        // 使用XPath获取元素
        HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@id='banner']").get(0);
        System.out.println(div.asXml());
    }

}
