package com.madaex.exchange.ui.mine.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  BannerData.java
 * 时间：  2020/1/6 15:49
 * 描述：
 */
public class BannerData {

    /**
     * status : 1
     * data : [{"id":12,"name":"1","title":"qtum","url":"","img":"http://alsc.uoou.net/Uploads/ad/2019-12-31-20-28-302177420191231202445.jpg","type":"2","sort":1,"addtime":1577795215,"endtime":1577795216,"status":1},{"id":13,"name":"2","title":"qtum","url":"","img":"http://alsc.uoou.net/Uploads/ad/2019-12-31-20-27-124462920191231202505.jpg","type":"2","sort":2,"addtime":1577795235,"endtime":1577795236,"status":1},{"id":14,"name":"3","title":"qtum","url":"","img":"http://alsc.uoou.net/Uploads/ad/2019-12-31-20-27-304040320191231202511.jpg","type":"2","sort":3,"addtime":1577795252,"endtime":1577795254,"status":1}]
     * message : 操作成功
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * name : 1
         * title : qtum
         * url :
         * img : http://alsc.uoou.net/Uploads/ad/2019-12-31-20-28-302177420191231202445.jpg
         * type : 2
         * sort : 1
         * addtime : 1577795215
         * endtime : 1577795216
         * status : 1
         */

        private int id;
        private String name;
        private String title;
        private String url;
        private String img;
        private String type;
        private int sort;
        private int addtime;
        private int endtime;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getEndtime() {
            return endtime;
        }

        public void setEndtime(int endtime) {
            this.endtime = endtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
