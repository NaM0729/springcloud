package com.zyn.freemarker.rest;

import com.zyn.freemarker.util.WordUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("doc")
public class DocumentsRest {

    @RequestMapping("getWord")
    public void getWord(HttpServletResponse response) {
        WordUtil.exportWord(getData(), "测试数据", response);
    }

    @RequestMapping("getPdf")
    public void getPdf(HttpServletResponse response) {
        WordUtil.exportPdf(getData(), "测试数据", response);
    }

    private Map<String, Object> getData() {

        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> wordVo = new HashMap<>();


        wordVo.put("domainName", "www.baidu.com");
        wordVo.put(".time", "2020-11-02 10:53:00");
        wordVo.put(".resul", "不支持");
        wordVo.put(".url", "http://www.baidu.com");
        wordVo.put(".rule", "链接是否可被访问");
        wordVo.put(".innerOne", "123");
        wordVo.put(".outerOne", "234");
        wordVo.put(".innerTwo", "2");
        wordVo.put(".outerTwo", "3");
        wordVo.put(".innerThree", "134");
        wordVo.put(".outerThree", "23");
        wordVo.put(".innerTotal", "12345");
        wordVo.put(".outerTotal", "234");
        wordVo.put(".links", "23455");
        dataMap.put("wordVo", wordVo);
        return dataMap;
    }
}
