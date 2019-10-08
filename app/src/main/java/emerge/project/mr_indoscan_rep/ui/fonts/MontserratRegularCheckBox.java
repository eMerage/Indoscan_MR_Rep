package emerge.project.mr_indoscan_rep.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by attract on 3/12/15.
 */

public class MontserratRegularCheckBox extends CheckBox {
    public MontserratRegularCheckBox(Context context) {
        super(context);
        init();
    }

    public MontserratRegularCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MontserratRegularCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Regular.otf");
        setTypeface(tf);
    }
}
