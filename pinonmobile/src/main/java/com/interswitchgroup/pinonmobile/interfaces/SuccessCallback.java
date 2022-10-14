package com.interswitchgroup.pinonmobile.interfaces;

import com.interswitchgroup.pinonmobile.models.ResponsePayloadModel;

public interface SuccessCallback<T extends ResponsePayloadModel> {
    void onSuccess(T successResponse);
}
