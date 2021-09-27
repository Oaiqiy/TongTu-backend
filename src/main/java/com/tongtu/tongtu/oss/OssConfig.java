package com.tongtu.tongtu.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(value = "com.tongtu.tongtu.oss")

public class OssConfig {
    private OssProperties ossProperties;
    OssConfig(OssProperties ossProperties){
        this.ossProperties=ossProperties;
    }

    @Bean
    public OSS oss(){
        return new OSSClientBuilder().build(ossProperties.getEndpoint(),ossProperties.getAccessKeyId(),ossProperties.getAccessKeySecret());
    }

    @Bean
    public IAcsClient iAcsClient(){
        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing",ossProperties.getAccessKeyId(),ossProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }

    @Bean
    public OssUtils ossUtils(){
        return new OssUtils(iAcsClient());
    }
}
