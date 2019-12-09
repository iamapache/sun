package com.madaex.exchange.common.di.component;

import android.app.Activity;
import android.content.Context;

import com.madaex.exchange.common.di.module.ActivityModule;
import com.madaex.exchange.common.di.scope.ActivityScope;
import com.madaex.exchange.common.di.scope.ContextLife;
import com.madaex.exchange.ui.finance.activity.AssetActivity;
import com.madaex.exchange.ui.finance.activity.BillActivity;
import com.madaex.exchange.ui.finance.activity.BuyBillActivity;
import com.madaex.exchange.ui.finance.activity.BuyCoinActivity;
import com.madaex.exchange.ui.finance.activity.ConfirmTransaActivity;
import com.madaex.exchange.ui.finance.activity.SellerCoinActivity;
import com.madaex.exchange.ui.finance.activity.TransaListActivity;
import com.madaex.exchange.ui.finance.activity.TransationDetailActivity;
import com.madaex.exchange.ui.finance.address.activity.AddAddressActivity;
import com.madaex.exchange.ui.finance.address.activity.AddressListActivity;
import com.madaex.exchange.ui.finance.address.activity.EditAddressActivity;
import com.madaex.exchange.ui.finance.bank.activity.AddBankActivity;
import com.madaex.exchange.ui.finance.bank.activity.BankListActivity;
import com.madaex.exchange.ui.finance.bank.activity.SelectBankActivity;
import com.madaex.exchange.ui.finance.c2c.activity.AppealActivity;
import com.madaex.exchange.ui.finance.c2c.activity.C2CEntrustDetailActivity;
import com.madaex.exchange.ui.finance.c2c.activity.C2CListActivity;
import com.madaex.exchange.ui.finance.c2c.activity.C2CTransationDetailActivity;
import com.madaex.exchange.ui.finance.c2c.activity.C2CTransationListActivity;
import com.madaex.exchange.ui.finance.pay.activity.BankAddActivity;
import com.madaex.exchange.ui.finance.pay.activity.BankEditActivity;
import com.madaex.exchange.ui.finance.pay.activity.WXActivity;
import com.madaex.exchange.ui.finance.pay.activity.WayActivity;
import com.madaex.exchange.ui.finance.pay.activity.ZFBActivity;
import com.madaex.exchange.ui.finance.transfer.activity.NcActivity;
import com.madaex.exchange.ui.finance.transfer.activity.NcListActivity;
import com.madaex.exchange.ui.finance.vote.activity.RankingDetailActivity;
import com.madaex.exchange.ui.login.activity.LoginActivity;
import com.madaex.exchange.ui.market.activity.DealActivity;
import com.madaex.exchange.ui.market.activity.EntrustDetailActivity;
import com.madaex.exchange.ui.market.activity.MessageListActivity;
import com.madaex.exchange.ui.market.fragment.KLineFragment;
import com.madaex.exchange.ui.mine.activity.AuthenticationActivity;
import com.madaex.exchange.ui.mine.activity.LoginPasswordActivity;
import com.madaex.exchange.ui.mine.activity.TransactionPasswordActivity;

import dagger.Component;


/**
 * Created by JUGG on 2016/12/9.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();




    void inject(LoginActivity loginActivity);

    void inject(LoginPasswordActivity loginPasswordActivity);

    void inject(TransactionPasswordActivity transactionPasswordActivity);

    void inject(AuthenticationActivity authenticationActivity);

    void inject(BankListActivity bankListActivity);

    void inject(AddBankActivity addBankActivity);

    void inject(C2CTransationListActivity c2CTransationListActivity);

    void inject(C2CTransationDetailActivity c2CTransationDetailActivity);

    void inject(AssetActivity assetActivity);

    void inject(BuyCoinActivity buyCoinActivity);

    void inject(SellerCoinActivity sellerCoinActivity);

    void inject(ConfirmTransaActivity confirmTransaActivity);

    void inject(AddressListActivity addressListActivity);

    void inject(AddAddressActivity addAddressActivity);

    void inject(EditAddressActivity editAddressActivity);

    void inject(TransaListActivity transaListActivity);

    void inject(MessageListActivity messageListActivity);

    void inject(BuyBillActivity buyBillActivity);

    void inject(BillActivity billActivity);

    void inject(TransationDetailActivity transationDetailActivity);

    void inject(DealActivity dealActivity);

    void inject(RankingDetailActivity rankingDetailActivity);

    void inject(KLineFragment kLineFragment);

    void inject(SelectBankActivity selectBankActivity);

    void inject(EntrustDetailActivity entrustDetailActivity);

    void inject(C2CListActivity c2CListActivity);

    void inject(ZFBActivity zfbActivity);

    void inject(BankAddActivity bankAddActivity);

    void inject(BankEditActivity bankEditActivity);

    void inject(C2CEntrustDetailActivity c2CEntrustDetailActivity);

    void inject(WXActivity wxActivity);

    void inject(WayActivity wayActivity);

    void inject(AppealActivity appealActivity);

    void inject(NcActivity ncActivity);

    void inject(NcListActivity ncListActivity);
}
