package com.hi.weiliao.skill.conf;

import com.hi.weiliao.skill.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastDFSConfiguration {

    private Logger logger = LoggerFactory.getLogger(FastDFSConfiguration.class);

    @Value("${fastdfs.tracker_servers}")
    private String tracker;

    @Value("${fastdfs.connect_timeout_in_seconds}")
    private String connectTimeout;

    @Value("${fastdfs.network_timeout_in_seconds}")
    private String networkTimeout;

    @Value("${fastdfs.charset}")
    private String charset;

    @Bean
    public FastDFSClient getFastDFSClient(){
        try {
            ClientGlobal.initByTrackers(tracker);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            return new FastDFSClient(storageClient);
        }catch (Exception e){
            logger.error("Init FastDFS client Error!", e);
            return null;
        }
    }
}
