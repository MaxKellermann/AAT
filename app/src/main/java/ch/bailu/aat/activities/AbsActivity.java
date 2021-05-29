package ch.bailu.aat.activities;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;

import ch.bailu.aat.preferences.PreferenceLoadDefaults;
import ch.bailu.aat.util.AppPermission;
import ch.bailu.aat.views.ErrorView;

public abstract class AbsActivity extends Activity {

    private static int instantiated=0;
    private static int created=0;


    public AbsActivity() {
        instantiated++;
    }

    private ErrorView errorView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorView = new ErrorView(this);
        errorView.attach();

        new PreferenceLoadDefaults(this);
        created++;
    }


    protected ErrorView getErrorView() {
        return errorView;
    }


    @Override
    public void onRequestPermissionsResult (int requestCode,
                                            @NonNull String[] permissions,
                                            @NonNull int[] grantResults) {
        AppPermission.onRequestPermissionsResult(this, requestCode);
    }


    @Override
    public void onDestroy() {
        errorView.detach();
        created--;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void finalize () throws Throwable {
        instantiated--;
        super.finalize();
    }

    public void appendStatusText(StringBuilder builder) {
        builder.append("<h1>");
        builder.append(getClass().getSimpleName());
        builder.append("</h1>");
        builder.append("<p>Instantiated activities: ");
        builder.append(instantiated);
        builder.append("<br>Created activities: ");
        builder.append(created);
        builder.append("<br>Count of application starts: ");
        builder.append(PreferenceLoadDefaults.getStartCount(this));
        builder.append("</p>");

    }

}
