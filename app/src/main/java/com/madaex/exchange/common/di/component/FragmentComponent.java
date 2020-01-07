package com.madaex.exchange.common.di.component;


import com.madaex.exchange.common.di.module.FragmentModule;
import com.madaex.exchange.common.di.scope.FragmentScope;
import com.madaex.exchange.ui.buy.fragment.BuyFragment;
import com.madaex.exchange.ui.buy.fragment.CoinListFrament;
import com.madaex.exchange.ui.buy.fragment.DealFragment;
import com.madaex.exchange.ui.finance.c2c.fragment.EntrustOneFragment;
import com.madaex.exchange.ui.finance.c2c.fragment.EntrustTwoFragment;
import com.madaex.exchange.ui.finance.c2c.fragment.PlatformEntrustFragment;
import com.madaex.exchange.ui.finance.c2c.fragment.TransationBuyFragment;
import com.madaex.exchange.ui.finance.c2c.fragment.TransationSellerFragment;
import com.madaex.exchange.ui.finance.fragment.FinanceFragment;
import com.madaex.exchange.ui.finance.pay.activity.PlatOutFragment;
import com.madaex.exchange.ui.finance.pay.activity.PlatRecordFragment;
import com.madaex.exchange.ui.finance.transfer.fragment.NCOutFragment;
import com.madaex.exchange.ui.finance.transfer.fragment.NCRecordFragment;
import com.madaex.exchange.ui.finance.vote.fragment.RankingFragment;
import com.madaex.exchange.ui.login.fragment.ForgetPasswordEmailFragment;
import com.madaex.exchange.ui.login.fragment.ForgetPasswordPhoneFragment;
import com.madaex.exchange.ui.login.fragment.RegisterEmailFragment;
import com.madaex.exchange.ui.login.fragment.RegisterPhoneFragment;
import com.madaex.exchange.ui.market.fragment.CoinDetailFragment;
import com.madaex.exchange.ui.market.fragment.CoinInfoFragment;
import com.madaex.exchange.ui.market.fragment.EntrustCurrentFragment;
import com.madaex.exchange.ui.market.fragment.HistoryDetailFragment;
import com.madaex.exchange.ui.market.fragment.MarketFragment;
import com.madaex.exchange.ui.market.fragment.TransactionListFragment;
import com.madaex.exchange.ui.mine.fragment.HomeFragment;
import com.madaex.exchange.ui.mine.fragment.MineFragment;

import dagger.Component;

/**
 * Created by JUGG on 2016/12/9.
 */

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent extends ActivityComponent {
    void inject(RegisterEmailFragment registerEmailFragment);

    void inject(RegisterPhoneFragment registerPhoneFragment);

    void inject(ForgetPasswordEmailFragment forgetPasswordEmailFragment);

    void inject(ForgetPasswordPhoneFragment forgetPasswordPhoneFragment);

    void inject(MineFragment mineFragment);

    void inject(TransationBuyFragment transationBuyFragment);

    void inject(TransationSellerFragment transationSellerFragment);

    void inject(TransactionListFragment transactionListFragment);

    void inject(RankingFragment rankingFragment);

    void inject(BuyFragment buyFragment);


    void inject(CoinListFrament coinFrament);

    void inject(EntrustCurrentFragment entrustCurrentFragment);

    void inject(DealFragment dealFragment);

    void inject(com.madaex.exchange.ui.market.fragment.DealFragment dealFragment);

    void inject(FinanceFragment financeFragment);

    void inject(CoinDetailFragment coinDetailFragment);

    void inject(MarketFragment marketFragment);

    void inject(CoinInfoFragment coinInfoFragment);

    void inject(PlatformEntrustFragment platformEntrustFragment);

    void inject(EntrustOneFragment entrustOneFragment);

    void inject(EntrustTwoFragment entrustTwoFragment);

    void inject(PlatOutFragment platOutFragment);

    void inject(PlatRecordFragment platRecordFragment);

    void inject(NCOutFragment ncOutFragment);

    void inject(NCRecordFragment ncRecordFragment);

    void inject(HomeFragment homeFragment);

    void inject(HistoryDetailFragment historyDetailFragment);
}
