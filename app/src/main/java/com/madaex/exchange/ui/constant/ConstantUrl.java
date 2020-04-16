package com.madaex.exchange.ui.constant;

/**
 * 项目：  madaexchange
 * 类名：  ConstantUrl.java
 * 时间：  2018/8/29 12:09
 * 描述：  ${TODO}
 */

public class ConstantUrl {

//    项目详情链接index.php/Appapi/trade/Prodetails
//    平台简介链接index.php/Appapi/index/about
//    协议说明链接index.php/Appapi/trade/protocol
//    法律说明链接index.php/Appapi/trade/legal
//    隐私条款链接index.php/Appapi/trade/privacy
//    用户协议链接index.php/Appapi/trade/greement

    public static String Prodetails = "appapi/trade/Prodetails";
    public static String Issuedetails = "appapi/trade/Issuedetails";
    public static String about = "appapi/index/about";
    public static String protocol = "appapi/trade/protocol";
    public static String legal = "trade/legal";
    public static String privacy = "trade/privacy";
    public static String greement = "appapi/trade/greement";
    public static String INVITE = "appapi/user/invite";
    public static String vote = "appapi/trade/vote";
    public static String fundraise  = "appapi/trade/fundraise";
    public static String service  = "appapi/trade/service";
    public static String appdow   = "appapi/index/appdow ";
    public static String particulars  = "appapi/index/particulars";
    public static String ENTRUSTCURRENT = "1";

    public static String ENTRUSTHISTORY = "2";

    //充币
    public static String TRANS_TYPE_BUY = "1";
    //提币
    public static String TRANS_TYPE_SELLER = "2";
    public static String TYPE = "type";
    //首页菜单
    public static String TRADE_HOME_INDEX_TOP = "market.getCoinList";
    //首页列表
    public static String TRADE_HOME_INDEX = "market.getMartketList";
    //首页列表--交易币详情
    public static String TRADE_HOME_INDEX_DETAIL = "market.home_index_detail";
    //首页列表--交易币详情的K线图
    public static String TRADE_HOME_INDEX_DETAIL_KLINE = "market.home_index_detail_kline";

    //用户
    public static String VERIFY_SEND_CODE = "Verify.send_code";

    public static String USER_LOGIN = "User.login";

    public static String USER_REGISTER = "User.register";
    //找回密码
    public static String USER_FINDPASS = "User.findpass";
    //重置密码|交易密码:
    public static String USER_SETPASSWORD = "User.setpassword";
    //实名认证
    public static String USER_REALNAME = "User.realname";
    //用户信息状态
    public static String USER_USERINFO_STATUS = "User.userinfo_status";

    public static String MARKET_GETHOTCOIN = "market.getHotCoin";
    //个人中心消息未读数
    public static String USER_NEWS_TOTAL = "User.news_total";


    public static String USER_NEWS_LIST = "User.news_list";

    public static String USER_ADD_READ_NEWS = "User.add_read_news";



    //升级检测：
    public static String VERSION_CHECK = "Version.check";
    //财务
    //添加银行卡
    public static String FINANCE_INSERT_BANK = "Finance.insert_bank";
    //用户银行卡列表
    public static String FINANCE_USER_BANK_LIST = "Finance.user_bank_list";
    //银行列表
    public static String FINANCE_BANK_LIST = "Finance.bank_list";
    //删除用户银行卡
    public static String FINANCE_DEAL_USER_BANK_CARD = "Finance.deal_user_bank_card";
    //c2c
    //充值操作
    public static String FINANCE_RECHARGE = "Finance.recharge";
    //卖出操作
    public static String FINANCE_CASH = "Finance.cash";

    //交易页面
    public static String FINANCE_C2C_VIEW = "Finance.c2c_view";
    //明细列表
    public static String FINANCE_C2C_LIST = "Finance.c2c_list";
    //交易明细里的付款信息
    public static String FINANCE_PAYMENT_INFORMATION = "Finance.payment_information";
    //资产
    //资产列表
    public static String TRADE_ASSETS_LIST = "Trade.assets_list";

    public static String VOTE_GO_ISSUE = "Vote.go_issue";
    public static String VOTE_NOWVOTE = "Vote.nowVote";
    //提币的第一个页面
    public static String TRADE_CASH_COIN = "Trade.cash_coin";

    //提币、充币地址列表
    public static String TRADE_COIN_ADDRESS_LIST = "Trade.coin_address_list";
    //提币、充币地址新增提交处理
    public static String TRADE_INSERT_COIN_ADDRESS = "Trade.insert_coin_address";
    //提币、充币地址的编辑提交处理
    public static String TRADE_SAVE_COIN_ADDRESS = "Trade.save_coin_address";

    //提币确认的提交处理
    public static String TRADE_CONFIRM_CASH = "Trade.confirm_cash";
    //充币确认的提交处理
    public static String TRADE_CONFIRM_RECHARGE = "Trade.confirm_recharge";


    public static String TRADE_CASH_RECHARGE = "Trade.cash_recharge";


    //提币列表
    public static String TRADE_CASH_LIST = "Trade.cash_list";
    //资产列表：账单
    public static String TRADE_BILL_LIST = "Trade.bill_list";
    //充币列表
    public static String TRADE_RECHARGE_LIST = "Trade.recharge_list";
    //提币详情
    public static String TRADE_CASH_INFO = "Trade.cash_info";
    //充币详情
    public static String TRADE_RECHARGE_INFO = "Trade.recharge_info";
    //币的列表
    public static String TRADE_COIN_LIST = "market.getFavCoinList";
    //交易的买入、卖出功能
    public static String TRADE_UPTRADE = "Trade.upTrade";
    public static String User_form_token = "User.form_token";

    public static String Contract_upContract = "Contract.upContract";

    public static String Contract_UPTRADE = "Contract.upTrade";
    //币的详情页面的简介
    public static String TRADE_INTRODUCE = "Trade.introduce";
    public static String TRADE_coin_deal_list = "Trade.coin_deal_list";
    //交易的买入、卖出界面的左下角数据
    public static String TRADE_TRADE = "Trade.trade";
    public static String Contract_contractAssets = "Contract.contractAssets";
    //当前委托列表
    public static String TRADE_CURRENT_ENTRUST = "Trade.current_entrust";

    public static String Contract_CURRENT_ENTRUST = "Contract.current_entrust";
    //当前委托的撤销功能
    public static String TRADE_REVOKE = "Trade.revoke";
    public static String Contract_REVOKE = "Contract.revoke";
    //历史委托列表
    public static String TRADE_HISTORY_ENTRUST = "Trade.history_entrust";
    public static String Contract_HISTORY_ENTRUST = "Contract.history_entrust";
    //当前委托列表的委托明细
    public static String TRADE_CURRENT_ENTRUST_DETAIL = "Trade.current_entrust_detail";
    public static String Contract_CURRENT_ENTRUST_DETAIL = "Contract.current_entrust_detail";
    //投票上币列表
    public static String MARKET_VOTE_LIST = "Vote.vote_list";
    //投票上币之我参与
    public static String MARKET_MY_VOTE_LIST = "Vote.my_vote_list";
    //确认投票
    public static String MARKET_CONFIRM_VOTE = "Vote.confirm_vote";

    //参与公募列表
    public static String MARKET_PUBLIC_OFFER_LIST = "Vote.public_offer_list";
    //我参与的公募列表
    public static String MARKET_MY_PARTAKE_PUBLIC = "Vote.my_partake_public";
    //确认公募
    public static String MARKET_CONFIRM_PUBLIC = "Vote.confirm_public";

    //收藏币种
    public static String TRADE_IS_COLLECTION = "market.setCollection";

    //详情页面的挂单
    public static String TRADE_DESIGNATES = "Trade.designates";

    //详情页面的成交列表
    public static String TRADE_COIN_DEAL_LIST = "Trade.coin_deal_list";



    //收款方式列表
    public static String USER_PAYMENTLIST = "User.PaymentList";


    //收款方式添加
    public static String USER_PAYMENTADD = "User.PaymentAdd";
    //收款方式编辑
    public static String USER_PAYMENTEDIT = "User.PaymentEdit";

    //收款方式保存
    public static String USER_PAYMENTSAVE = "User.PaymentSave";

    //收款方式删除
    public static String USER_PAYMENTDEL = "User.PaymentDel";


    //申诉添加
    public static String TRADE_APPEAL = "Trade.appeal";
    //平台委托
    public static String FIAT_ENTRUST_PLATFORM = "Fiat.entrust_platform";

    //我的委托-委托中
    public static String FIAT_MY_ENTRUST_IN = "Fiat.my_entrust_in";

    //我的委托-待付款（待付款）0、待收款（已支付）1、已完成（已确认、已关闭、已取消）2
    public static String FIAT_MY_ORDER_IN = "Fiat.my_order_in";

    //我的委托-委托明细
    public static String FIAT_MY_ENTRUST_DETAIL = "Fiat.my_entrust_detail";


    //法币挂单-买入 or  卖出
    public static String FIAT_GOODS_ADD = "Fiat.goods_add";
    //法币挂单-提交申诉
    public static String FIAT_SUBMIT_APPEAL = "Fiat.submit_appeal";

    //法币挂单-取消
    public static String FIAT_GOODS_CANCEL = "Fiat.goods_cancel";
    //法币挂单-订单确认收款操作
    public static String FIAT_ORDER_CONFIRM = "Fiat.order_confirm";
    //法币挂单-订单已支付操作
    public static String FIAT_ORDER_PAY = "Fiat.order_pay";

    //法币挂单-下订单
    public static String FIAT_ORDER_PLACE = "Fiat.order_place";

    //法币挂单-买入、卖出手续费、挂单金额最低值
    public static String FIAT_FEE_CONFIG = "Fiat.fee_config";


    //平台互转-转出
    public static String TRADE_PLAT_OUT = "Trade.plat_out";


    //站内转账	确认提交
    public static String TRADE_INTERNAL_TRANSFER = "Trade.internal_transfer";
    public static String TRADE_COIN_ENAME_LIST = "Trade.coin_ename_list";

    //平台互转-转出
    public static String TRADE_INTERNAL_TRANSFER_LIST = "Trade.internal_transfer_list";


    //平台互转-转账记录
    public static String TRADE_PLAT_RECORD = "Trade.plat_record";


    //充币：目前仅限充值 SNRC
    public static String COIN_COIN_CZ_ADD = "Coin.coin_cz_add";

    public static String COIN_COIN_CZ_LIST = "Coin.coin_cz_list";



    //合约
    public static String Trade_contract_coin_info = "Trade.contract_coin_info";
    public static String Trade_transfer_wallet_info = "Trade.transfer_wallet_info";
    public static String Trade_contract_bills = "Trade.contract_bills";
    public static String Trade_is_open_contract_trade = "Trade.is_open_contract_trade";

    public static String Trade_contract_transfer = "Trade.contract_transfer";
    public static String Userlevel_add = "Userlevel.add";

    public static String Trade_transfer_bills = "Trade.transfer_bills";
}
