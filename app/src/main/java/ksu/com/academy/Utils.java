package ksu.com.academy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.HOUR_IN_MILLIS;

public class Utils {

    public static int pxDisplayWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dpDisplayWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    public static CharSequence formatDateTime(Context context, Date dateTime) {
        return DateUtils.getRelativeDateTimeString(
                context,
                dateTime.getTime(),
                HOUR_IN_MILLIS,
                5 * DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE
        );
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void sendEmail(Context context, String subject, String message) {
        final String mailto = "mailto";
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(mailto, "", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_app)));
        } else {
            Toast.makeText(context, R.string.no_app_to_send_email, Toast.LENGTH_SHORT).show();
        }
    }

    public static void openLink(Context context, String packageName, String link, String userNik) {
        if (isPackageInstalled(context, packageName)) {
            openApp(context, packageName, link, userNik);
        } else {
            openBrowser(context, link, userNik);
        }
    }

    private static void openApp(Context context, String packageName, String link, String userNik) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link + userNik));
        }
        List activities = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (activities.size() > 0) {
            context.startActivity(intent);
        }
    }

    private static void openBrowser(Context context, String link, String userNik) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link + userNik));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_app)));
        } else {
            Toast.makeText(context, R.string.no_app_to_open_link, Toast.LENGTH_SHORT).show();
        }
    }
}
