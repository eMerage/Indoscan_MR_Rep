package emerge.project.mr_indoscan_rep.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by attract on 3/12/15.
 */

public class MontserratBold extends TextView {
    public MontserratBold(Context context) {
        super(context);
        init();
    }

    public MontserratBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MontserratBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Bold.otf");
        setTypeface(tf);
    }
}
