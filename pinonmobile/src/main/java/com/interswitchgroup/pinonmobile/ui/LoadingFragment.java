package com.interswitchgroup.pinonmobile.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.interswitchgroup.pinonmobile.databinding.CustomLoadingScreenBinding;

public class LoadingFragment extends DialogFragment {
    CustomLoadingScreenBinding binding;
    public static String TAG = "LoadingDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomLoadingScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY); //FILL ENTIRE SCREEN
        return dialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
