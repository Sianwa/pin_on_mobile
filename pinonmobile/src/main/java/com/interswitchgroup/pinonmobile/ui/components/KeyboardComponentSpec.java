package com.interswitchgroup.pinonmobile.ui.components;

import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.yoga.YogaJustify;

@LayoutSpec()
public class KeyboardComponentSpec {
    private static Component createNumber(ComponentContext componentContext, String number1, String number2, String number3){
        return Row.create(componentContext)
                .child(SingleDigitComponent.create(componentContext).symbol(number1))
                .child(SingleDigitComponent.create(componentContext).symbol(number2))
                .child(SingleDigitComponent.create(componentContext).symbol(number3))
                .heightPercent(25)
                .build();

    }

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext c) {
        return Column.create(c)
                .justifyContent(YogaJustify.SPACE_AROUND)
                .heightPercent(100)
                .child(
                        createNumber(c,"1","2","3"))
                .child(createNumber(c,"4","5","6"))
                .child(createNumber(c,"7","8","9"))
                .child(createNumber(c,"X","0",">"))
                .heightPercent(60)
                .build();
    }
}
