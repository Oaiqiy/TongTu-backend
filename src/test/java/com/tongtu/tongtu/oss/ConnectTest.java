package com.tongtu.tongtu.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.internal.ResponseParsers;
import com.aliyun.oss.model.*;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.google.gson.Gson;
import com.tongtu.tongtu.api.oss.CallbackController;
import com.tongtu.tongtu.api.oss.CallbackForm;
import com.tongtu.tongtu.data.FileInfoRepository;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.FileInfo;
import com.tongtu.tongtu.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebConnection;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest
public class ConnectTest {
    @Autowired
    private OSS oss;

    @Autowired
    private IAcsClient client;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Test
    public void connect(){
        String bucketName = "examplesbucket";
        ObjectListing objectListing = oss.listObjects(bucketName);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }

    }

    @Test
    public void sts(){
        AssumeRoleRequest request = new AssumeRoleRequest();

        request.setRoleArn("acs:ram::1482221404522785:role/oss");
        request.setRoleSessionName("tongtu");

        //发起请求，并得到响应。
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);

            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    @Test
    public void ossCallback() throws Exception{
        System.out.println("skip callback");
//        PutObjectRequest putObjectRequest = new PutObjectRequest("examplesbucket","Animee/Java.zip",new File("F:\\QQDownload\\XML_DOM.zip"));
//        Callback callback = new Callback();
//        //System.out.println(MvcUriComponentsBuilder.fromMethodName(CallbackController.class, "ossCallback", new CallbackForm("f",1l,3l,"f","f")).toUriString());
//        callback.setCallbackUrl("http://api.tongtu.xyz/oss/callback");
//        //callback.setCallbackHost("oss-cn-beijing.aliyuncs.com");
//        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
//        //callback.setCallbackBody("{\\\"mimeType\\\":\\\"text\\\",\\\"size\\\":1024}");
//        callback.setCallbackBody("{\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},\\\"user\\\":${x:user},\\\"bucket\\\":${bucket},\\\"object\\\":${object}}");
//        Map<String,String> var = new HashMap<>();
//        var.put("x:user","123456");
//        callback.setCallbackVar(var);
//        callback.addCallbackVar("test","ffff");
//
//        putObjectRequest.setCallback(callback);
//
//        PutObjectResult putObjectResult = oss.putObject(putObjectRequest);
//        System.out.println("begin_______________________");
//        Scanner scanner = new Scanner(putObjectResult.getResponse().getContent(),"UTF-8");
//        System.out.println(scanner.next());

    }
    @Test
    public void delete(){
        oss.deleteObject("examplesbucket","test.txt");
    }

//    @Test
//    public void callbackFormTest(){
//        CallbackForm callbackForm =new CallbackForm(123L,"afdsa", 1,3421L,"a",1L,"fa","fasdf");
//        fileInfoRepository.save(callbackForm.toFileInfo(new User()));
//    }
}
