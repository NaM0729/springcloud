package com.zyn.freemarker.util;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Properties;

// <dependency>
//            <groupId>org.freemarker</groupId>
//            <artifactId>freemarker</artifactId>
//            <version>2.3.28</version>
//        </dependency>
//        <dependency>
//            <groupId>com.aspose</groupId>
//            <artifactId>aspose-words</artifactId>
//            <version>15.8.0</version>
//        </dependency>

/**
 * @Author: zyn
 * @CreateTime: 2020-11-02 10:36
 * @Description:
 * @Version: v0.0.1
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 * <p>
 * 利用 freemarker 将 。ftl 模板生成 word ，然后根据 word 转换为 PDF
 */
@Slf4j
@Component
public class WordUtil {
    static String tempFilePath;
    static String ftlTemplatePath;

    @PostConstruct
    private void checkFTLFile() {
        Properties properties = System.getProperties();
        String os = properties.get("os.name").toString();
        log.info("操作系统:{}", os);
        // 本地测试使用
        if (os.contains("Windows")) {
            tempFilePath = "D:\\test\\detection";
            ftlTemplatePath = "D:\\test\\detection\\template\\WordTemplate.ftl";
            return;
        }

        // file:/home/detection/detection.jar!/BOOT-INF/classes!/
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String substring = path.substring(path.lastIndexOf(":") + 1, path.indexOf("/BOOT-INF") - 1);
        File file = new File(substring);
        // /home/detection/
        tempFilePath = file.getParent();
        ftlTemplatePath = tempFilePath + File.separator + "WordTemplate.ftl";

        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("com/zyn/freemarker/bak/template/detection/WordTemplate.ftl")) {
            Files.copy(in, Paths.get(ftlTemplatePath), StandardCopyOption.COPY_ATTRIBUTES);
            ftlTemplatePath = tempFilePath + File.separator + "WordTemplate.ftl";
        } catch (Exception e) {
            log.error("ftl模板复制出错:{}", e.getMessage());
        }
        log.info("tempFilePath跟路径路径：{}", tempFilePath);
        log.info("ftlTemplatePath跟路径路径：{}", ftlTemplatePath);
    }

    /**
     * @param map      要封装的数据
     * @param fileName 文件名称  如："工商银行“
     * @param response
     */
    public static void exportWord(Map<String, Object> map, String fileName, HttpServletResponse response) {
        // 临时文件路径信息
        String filePath = tempFilePath + fileName + ".doc";

        OutputStream os = null;
        try {
            generateWord(map, filePath);

            // 设置文件ContentType类型为word
//            response.setContentType("application/octet-stream");
            response.setContentType("application/msword");
//            response.setHeader("content-type", "application/octet-stream");
            // 设置文件头：设置下载文件名(encode防止非法字符)
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".doc");
//            response.setHeader("Content-Length", String.valueOf(bytes.length));

            response.flushBuffer();
            os = response.getOutputStream();
            os.write(IoUtil.readFileToByteArray(new File(filePath)));
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                    Files.delete(Paths.get(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 使用FreeMarker自动生成Word文档
     * 目前只针对 青海需求的 ftl 模板。后期模板增多还需再次调整 zyn 2020-11-05
     *
     * @param dataMap  生成文档所需要的数据
     * @param fileName 生成doc文档的路径+名称
     * @throws Exception
     */
    private static void generateWord(Map<String, Object> dataMap, String fileName) throws Exception {

        // 生成doc文档的ftl文件路径
        File ftlTemplateFile = new File(ftlTemplatePath);

        // 设置FreeMarker的版本和编码格式
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");

        // 设置FreeMarker生成Word文档所需要的模板的路径
//        configuration.setDirectoryForTemplateLoading(new File("d:/test/webdemo/Word/Template/"));
        configuration.setDirectoryForTemplateLoading(new File(ftlTemplateFile.getParent()));
        // 设置FreeMarker生成Word文档所需要的模板
//        Template t = configuration.getTemplate("WordTemplate.ftl", "UTF-8");
        Template t = configuration.getTemplate(ftlTemplateFile.getName(), "UTF-8");
        // 创建一个Word文档的输出流
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)), "UTF-8"));
        //FreeMarker使用Word模板和数据生成Word文档
        t.process(dataMap, out);
        out.flush();
        out.close();
    }

    /**
     * 生成PDF文件
     *
     * @param map      数据模型
     * @param fileName 文件名称
     * @param response
     */
    public static void exportPdf(Map<String, Object> map, String fileName, HttpServletResponse response) {

        // 临时 doc 文件路径信息
        String filePath = tempFilePath + fileName + ".doc";
        // 临时 pdf 文件路径信息
        String filePathPdf = tempFilePath + fileName + ".pdf";

        OutputStream os = null;
        try {
            // 先生成doc文件
            generateWord(map, filePath);
            // doc 转 PDF
            word2Pdf(filePath, filePathPdf);
            // 设置文件ContentType类型为pdf
//            response.setContentType("application/pdf");
            response.setHeader("content-type", "application/pdf");
            // 设置文件头：设置下载文件名(encode防止非法字符)
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".pdf");
            response.flushBuffer();
            os = response.getOutputStream();
            os.write(IoUtil.readFileToByteArray(new File(filePathPdf)));
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                    Files.delete(Paths.get(filePath));
                    Files.delete(Paths.get(filePathPdf));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 使用aspose.word把word文档转为pdf文档
     *
     * @param sourceFile word文档绝对路径(如:D:/templates/order.doc)
     * @param destFile   pdf文档绝对路径(如:D:/templates/order.pdf)
     */
    private static String word2Pdf(String sourceFile, String destFile) throws Exception {
        destFile = Strings.isEmpty(destFile) ? sourceFile.replace(".doc", ".pdf") : destFile;
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            throw new Exception("生成PDF文档,验证License失败!");
        }
        try {
            File file = new File(destFile);  //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(sourceFile);//通过sourceFile创建word文档对象
            doc.save(os, SaveFormat.PDF);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("生成PDF文档失败!");
        }
        return destFile;
    }

    /**
     * @Description: 验证aspose.word组件是否授权：无授权的文件有水印标记
     */
    private static boolean getLicense() {
        boolean result = false;
        try {
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(s.getBytes());
            //InputStream inputStream = Xml2Word2Pdf.class.getClassLoader().getResourceAsStream("\\license.xml");
            com.aspose.words.License license = new com.aspose.words.License();
            license.setLicense(inputStream);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
