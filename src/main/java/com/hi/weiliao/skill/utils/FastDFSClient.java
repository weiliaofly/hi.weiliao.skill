package com.hi.weiliao.skill.utils;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class FastDFSClient {

    private StorageClient1 storageClient;

    public FastDFSClient(StorageClient1 storageClient){
        this.storageClient = storageClient;
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    /**
     * 上传文件,传fileName
     *
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @return null为失败
     */
    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    /**
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @param extName  文件的扩展名 如 txt jpg等
     * @return null为失败
     */
    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileContent 文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metas       文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileContent, extName, metas);
        return result;
    }

    /**
     * 上传文件
     *
     * @param fileContent 文件的字节数组
     * @return null为失败
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    /**
     * 上传文件
     *
     * @param fileContent 文件的字节数组
     * @param extName     文件的扩展名 如 txt  jpg png 等
     * @return null为失败
     */
    public String uploadFile(byte[] fileContent, String extName) throws Exception  {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 文件下载到磁盘
     *
     * @param path   图片路径
     * @param output 输出流 中包含要输出到磁盘的路径
     * @return -1失败,0成功
     */
    public int downloadFile(String path, BufferedOutputStream output) throws Exception {
        int result = -1;
        byte[] b = storageClient.download_file1(path);
        try {
            if (b != null) {
                output.write(b);
                result = 0;
            }
        } catch (Exception e) {
            throw e;
        } //用户可能取消了下载
        finally {
            if (output != null)
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    /**
     * 获取文件数组
     *
     * @param path 文件的路径 如group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return
     */
    public byte[] downloadDytes(String path) {
        byte[] b = null;
        try {
            b = storageClient.download_file1(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 删除文件
     *
     * @param group       组名 如：group1
     * @param storagePath 不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     */
    public Integer deleteFile(String group, String storagePath) {
        int result = -1;
        try {
            result = storageClient.delete_file(group, storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param storagePath 文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     * @throws IOException
     * @throws Exception
     */
    public Integer deleteFile(String storagePath) {
        int result = -1;
        try {
            result = storageClient.delete_file1(storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取远程服务器文件资源信息
     * @param groupName   文件组名 如：group1
     * @param remoteFileName M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return
     */
    public FileInfo getFile(String groupName, String remoteFileName){
        try {
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取远程服务器文件资源信息
     * @param id group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return
     */
    public FileInfo getFile(String id){
        try {
            return storageClient.get_file_info1(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
