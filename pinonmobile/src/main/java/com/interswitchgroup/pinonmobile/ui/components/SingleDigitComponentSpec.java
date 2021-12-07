package com.interswitchgroup.pinonmobile.ui.components;

import android.text.Layout;
import android.util.Log;

import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.litho.widget.VerticalGravity;

@LayoutSpec()
public class SingleDigitComponentSpec {
    @OnCreateLayout
    static Component onCreateLayout(ComponentContext c , @Prop String symbol) {

        return Text.create(c)
                .text(symbol)
                .textSizeDip(28)
                .verticalGravity(VerticalGravity.CENTER)
                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                .flex(1)
                .build();
    }

    @OnEvent(ClickEvent.class)
    static void onClearClick(ComponentContext c, @Param String symbol) {
        String text = "";
        // Clear the TextInput inside TextInputContainerComponent
        Log.d("MyComponent", "Color changed: " + symbol);

        switch (symbol) {
            case "<":
                StringBuffer sb = new StringBuffer(text);
                sb.deleteCharAt(text.length());
                break;
            case ">":
                Log.d("SingleDigitComponent", "proceed");
                break;
            default:
                Log.d("SingleDigitComponent", "Color changed: " + symbol);
        }
    }
}
