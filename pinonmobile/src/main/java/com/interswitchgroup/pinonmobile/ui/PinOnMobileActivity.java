package com.interswitchgroup.pinonmobile.ui;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;

import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.Text;
import com.facebook.litho.widget.VerticalGravity;
import com.facebook.soloader.SoLoader;
import com.facebook.yoga.YogaEdge;
import com.interswitchgroup.pinonmobile.ui.components.KeyboardComponent;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PinOnMobileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SoLoader.init(this, false);
        super.onCreate(savedInstanceState);

        final ComponentContext componentContext = new ComponentContext(this);
        Component instructionText = Text.create(componentContext)
                .text("Please enter your OTP")
                .textSizeDip(28)
                .verticalGravity(VerticalGravity.CENTER)
                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                .paddingDip(YogaEdge.ALL,10)
                .paddingDip(YogaEdge.TOP,40)
                .build();
        Component inputTextComponent = Text.create(componentContext)
                .text("1234")
                // change this with whats happening
                .textColor(Color.RED)
                .textSizeDip(28)
                .verticalGravity(VerticalGravity.CENTER)
                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                .paddingDip(YogaEdge.ALL,10)
                .build();


        Component basicView = Column.create(componentContext)
                .child(instructionText)
                .child(inputTextComponent)
                .child(KeyboardComponent.create(componentContext))
                .build();

        final LithoView lithoView = LithoView.create(
                this,
                basicView);

        setContentView(lithoView);

    }
}