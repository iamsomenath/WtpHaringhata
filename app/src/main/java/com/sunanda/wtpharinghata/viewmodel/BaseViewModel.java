package com.sunanda.wtpharinghata.viewmodel;

import androidx.lifecycle.ViewModel;
import com.sunanda.wtpharinghata.helper.NetworkHelper;
import com.sunanda.wtpharinghata.rest.ApiInterface;

public class BaseViewModel extends ViewModel {

    private static ApiInterface apiInterface;

    public ApiInterface getAPIInterface() {
        if (apiInterface == null) {
            apiInterface = NetworkHelper.INSTANCE.getClient().create(ApiInterface.class);
        }
        return apiInterface;
    }
}
