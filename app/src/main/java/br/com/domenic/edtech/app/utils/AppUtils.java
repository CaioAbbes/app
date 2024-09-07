package br.com.domenic.edtech.app.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import br.com.domenic.edtech.app.R;

public class AppUtils {

    public static void initVisuals(AppCompatActivity activity, int layoutResId) {
        if (activity != null) {
            // Define o layout
            activity.setContentView(layoutResId);

            // Configura o modo claro
            int currentNightMode = activity.getResources().getConfiguration().uiMode
                    & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

            if (currentNightMode != android.content.res.Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            activity.getWindow().setStatusBarColor(
                    ContextCompat.getColor(activity, R.color.md_theme_background)
            );
        }
    }
}