package com.interswitchgroup.pinonmobile.interfaces;

import com.interswitchgroup.pinonmobile.models.GenericResponse;

public interface SuccessCallback<T extends GenericResponse> {
    void onSuccess(T successResponse);
}
