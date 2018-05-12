package com.insight.divyamjoshi.insight.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.insight.divyamjoshi.insight.db.InsightContract;
import com.insight.divyamjoshi.insight.network.UtilsNetwork;


public class SyncUtils {
    public static final String ACCOUNT_TYPE = "com.insight.divyamjoshi.insight.syncadapter";
    private static final long SYNC_FREQUENCY = 50 * 50 * 3;
    private static final String CONTENT_AUTHORITY = InsightContract.AUTHORITY;
    private static final String PREF_SETUP_COMPLETE = "setup_complete";

    public static void CreateSyncAccount(Context context) {
        //getting all the category form news api
        new UtilsNetwork().getCategory(null, context);

        boolean newAccount = false;
        boolean setupCompleter = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

        Account account = AccountService.getAccount(ACCOUNT_TYPE);

        AccountManager accountManager = (AccountManager) context.getSystemService(context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(account, null, null)) {

            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);

            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);

            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, new Bundle(), SYNC_FREQUENCY);

            newAccount = true;
        }

        if (newAccount || !setupCompleter) {
            TriggerRefresh();
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREF_SETUP_COMPLETE, true).apply();
        }
    }

    private static void TriggerRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountService.getAccount(ACCOUNT_TYPE), InsightContract.AUTHORITY, bundle);

    }

}
