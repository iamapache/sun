package com.madaex.exchange.ui.market.bean;

/**
 * 项目：  madaexchange
 * 类名：  CoinDetail.java
 * 时间：  2018/10/22 10:57
 * 描述：  ${TODO}
 */

public class CoinDetail {

    /**
     * status : 1
     * data : {"js_sm":"<div style=\"background:#001E3A;color:#fff;padding:10%;margin:0 !important;\">\r\n\t<br />\r\n&nbsp;<br />\r\n\t<div>\r\n\t\t&nbsp;项目名称<br />\r\nBasic&nbsp;Attention&nbsp;Token（BAT）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />\r\n&nbsp;<br />\r\n二、项目定位<br />\r\n基于区块链的数字广告平台&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />\r\n&nbsp;<br />\r\n三、项目介绍<br />\r\n项目基于Brave浏览器开展去中心化数字广告业务，通过零知识证明的运用保护用户隐私，同时可以使用户的关注得到回报，项目团队实力较强，方案设计合理，且有一定进展，主要风险在于上线主流交易所仅两家，代币集中度较高，总体而言投资价值明确。<br />\r\n&nbsp;<br />\r\n四、项目进展&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />\r\n发布Brave&nbsp;browser&nbsp;0.19.95；12月7日，向Youtube用户进行了300000BAT的空投，用户可以在Youtube上使用BAT进行支付。<br />\r\n&nbsp;<br />\r\n五、项目特点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />\r\nBrave浏览器匿名监控用户注意力，使用BAT代币对内容商进行奖励。<br />\r\n&nbsp;<br />\r\n六、项目团队&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />\r\nCEO：BrendenErich,Mozilla项目的联合创始人、JavaScript的创造者。<br />\r\nBrian&nbsp;Bondy:&nbsp;BAT项目联合创始人&amp;浏览器首席工程师，先前从事KhanAcademy,&nbsp;Mozilla,&nbsp;Evernote研发。<br />\r\n&nbsp;<br />\r\n七、ICO概况<br />\r\nICO时间：2017年6月1日<br />\r\n项目代币：BAT<br />\r\nICO比例：66.7%<br />\r\n代币成本：0.038美元<br />\r\n&nbsp;<br />\r\n\t<\/div>\r\n<br />\r\n<\/div>"}
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
         * js_sm : <div style="background:#001E3A;color:#fff;padding:10%;margin:0 !important;">
         <br />
         &nbsp;<br />
         <div>
         &nbsp;项目名称<br />
         Basic&nbsp;Attention&nbsp;Token（BAT）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />
         &nbsp;<br />
         二、项目定位<br />
         基于区块链的数字广告平台&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />
         &nbsp;<br />
         三、项目介绍<br />
         项目基于Brave浏览器开展去中心化数字广告业务，通过零知识证明的运用保护用户隐私，同时可以使用户的关注得到回报，项目团队实力较强，方案设计合理，且有一定进展，主要风险在于上线主流交易所仅两家，代币集中度较高，总体而言投资价值明确。<br />
         &nbsp;<br />
         四、项目进展&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />
         发布Brave&nbsp;browser&nbsp;0.19.95；12月7日，向Youtube用户进行了300000BAT的空投，用户可以在Youtube上使用BAT进行支付。<br />
         &nbsp;<br />
         五、项目特点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />
         Brave浏览器匿名监控用户注意力，使用BAT代币对内容商进行奖励。<br />
         &nbsp;<br />
         六、项目团队&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />
         CEO：BrendenErich,Mozilla项目的联合创始人、JavaScript的创造者。<br />
         Brian&nbsp;Bondy:&nbsp;BAT项目联合创始人&amp;浏览器首席工程师，先前从事KhanAcademy,&nbsp;Mozilla,&nbsp;Evernote研发。<br />
         &nbsp;<br />
         七、ICO概况<br />
         ICO时间：2017年6月1日<br />
         项目代币：BAT<br />
         ICO比例：66.7%<br />
         代币成本：0.038美元<br />
         &nbsp;<br />
         </div>
         <br />
         </div>
         */

        private String js_sm;

        public String getJs_sm() {
            return js_sm;
        }

        public void setJs_sm(String js_sm) {
            this.js_sm = js_sm;
        }
    }
}
