package com.madaex.exchange.ui.finance.pay.bean;

/**
 * 项目：  sun
 * 类名：  ImgCheck.java
 * 时间：  2020/6/13 18:29
 * 描述：
 */
public class ImgCheck {
    /**
     * status : 1
     * data : {"face_img_check":false,"back_img_check":false}
     * message : 操作成功
     */

    private int status;
    private DataBean data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * face_img_check : false
         * back_img_check : false
         */

        private String face_img_check;
        private String back_img_check;

        public String isFace_img_check() {
            return face_img_check;
        }

        public void setFace_img_check(String face_img_check) {
            this.face_img_check = face_img_check;
        }

        public String isBack_img_check() {
            return back_img_check;
        }

        public void setBack_img_check(String back_img_check) {
            this.back_img_check = back_img_check;
        }
    }
}
