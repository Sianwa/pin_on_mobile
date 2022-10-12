package com.interswitchgroup.pinonmobile.interfaces;

import com.interswitchgroup.pinonmobile.models.GenericResponse;

public interface FailureCallback {
    void onError(GenericResponse error);
}
