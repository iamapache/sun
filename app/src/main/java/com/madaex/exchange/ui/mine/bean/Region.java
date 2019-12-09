package com.madaex.exchange.ui.mine.bean;

import java.io.Serializable;

/**
 * 项目：  wallet-android
 * 类名：  Region.java
 * 时间：  2018/6/12 16:17
 * 描述：  ${TODO}
 */
public class Region implements Serializable{


        private String englishName;
        private String chineseName;
        private String abbreviation;
        private String phoneCode;
        private int torontoDiff;

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
        }

        public int getTorontoDiff() {
            return torontoDiff;
        }

        public void setTorontoDiff(int torontoDiff) {
            this.torontoDiff = torontoDiff;
        }
}
