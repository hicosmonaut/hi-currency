package hi.cosmonaut.currency.ui.binding;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import hi.cosmonaut.currency.R;


public class BindingAdapters {

    @BindingAdapter({"isFavorite"})
    public static void setIsFavorite(View view, boolean isFavorite) {
        Context context = view.getContext();

        ((ImageView) view).setImageDrawable(
                isFavorite ? ContextCompat.getDrawable(context, R.drawable.ic_star_active) : ContextCompat.getDrawable(context, R.drawable.ic_star_border)
        );
    }

}

