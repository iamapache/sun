package com.madaex.exchange.common.util;

import com.madaex.exchange.ui.mine.bean.ImgBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目：  shop
 * 包名：  com.app.shop.utils
 * 类名：  ImageUploadUtil.java
 * 作者：  彭决
 * 时间：  2017/7/4 10:12
 * 描述：  ${TODO}
 */

public class ImageUploadUtil {

    public static MultipartBody filesToMultipartBody(List<ImgBean> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < files.size(); i++) {
            File file = new File(files.get(i).getFile());
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            if(files.get(i).getType()==1){
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                builder.addFormDataPart("face_img", file.getName(), requestBody);
            }else {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                builder.addFormDataPart("back_img", file.getName(), requestBody);
            }

        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    public static MultipartBody filesToMultipartBody2(List<String> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < files.size(); i++) {
            File file = new File(files.get(i));
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("fileList", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<ImgBean> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (int i = 0; i < files.size(); i++) {
            File file = new File(files.get(i).getFile());
            //        File file = new File(pathList.get(0));
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
// MultipartBody.Part  和后端约定好Key，这里的partName是用image

            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            if(files.get(i).getType()==1){
                MultipartBody.Part multipartBody =
                        MultipartBody.Part.createFormData("face_img", file.getName(), requestFile);
                parts.add(multipartBody);
            }else {
                MultipartBody.Part multipartBody =
                        MultipartBody.Part.createFormData("back_img", file.getName(), requestFile);
                parts.add(multipartBody);
            }

        }

//        for (File file : files) {
//            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
//            parts.add(part);
//        }
        return parts;
    }

    /**
     * 将文件路径数组封装为{@link List <MultipartBody.Part>}
     * @param key 对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     * 同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key,
                                                       String[] filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key, file.getName(), requestBody);
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     * @param key 同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key,
                                                     String[] filePaths,
                                                     MediaType imageType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(imageType, file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
}
