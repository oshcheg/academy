package ksu.com.academy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class Utils {

    public static boolean isPackageInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void sendEmail(Context context, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(context.getString(R.string.mailto), "", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.default_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_app)));
        } else {
            Toast.makeText(context, R.string.no_app_to_send_email, Toast.LENGTH_SHORT).show();
        }
    }

    public static void openLink(Context context, int packageNameID, int linkID) {
        openLink(context, context.getString(packageNameID), context.getString(linkID));
    }

    public static void openLink(Context context, String packageName, String link) {
        Intent intent;
        if (isPackageInstalled(context, packageName)) {
            intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_app)));
            } else {
                Toast.makeText(context, R.string.no_app_to_open_link, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
