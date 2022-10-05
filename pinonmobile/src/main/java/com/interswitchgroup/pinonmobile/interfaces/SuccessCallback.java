package com.interswitchgroup.pinonmobile.interfaces;

import com.interswitchgroup.pinonmobile.models.SuccessModel;

public interface SuccessCallback<T extends SuccessModel> {
    void onSuccess(T successResponse);
}
