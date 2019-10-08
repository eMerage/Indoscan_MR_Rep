package emerge.project.mr_indoscan_rep.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by attract on 3/12/15.
 */

public class MontserratMedium extends TextView {
    public MontserratMedium(Context context) {
        super(context);
        init();
    }

    public MontserratMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MontserratMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Medium.otf");
        setTypeface(tf);
    }
}
