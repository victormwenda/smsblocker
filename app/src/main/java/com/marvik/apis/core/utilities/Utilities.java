package com.marvik.apis.core.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Set;

/**
 * Created by victor on 11/7/2015.
 */
public class Utilities {

    private Context mContext;

    public Utilities(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    private void initAll(Context context) {

        this.mContext = context;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////Start Activity////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(cls, new Bundle(), 0);
    }

    public void startActivity(String action, Class<? extends Activity> cls) {
        startActivity(action, cls, new Bundle(), 0);
    }

    public void startActivity(Class<? extends Activity> cls, @NonNull Bundle bundle) {
        startActivity(cls, bundle, 0);
    }

    public void startActivity(Class<? extends Activity> cls, int flags) {
        startActivity(cls, new Bundle(), flags);
    }


    public void startActivity(Class<? extends Activity> cls, @NonNull Bundle extras, int flags) {
        startActivity(Intent.ACTION_MAIN, cls, extras, flags);
    }

    public void startActivity(String action, Class<? extends Activity> cls, int flags) {
        startActivity(action, cls, new Bundle(), flags);
    }

    public void startActivity(String action, Class<? extends Activity> cls, @NonNull Bundle extras, int flags) {

        getContext().startActivity(new Intent(getContext(), cls).addFlags(flags).putExtras(extras).setAction(action));
    }


    public void startService(Class<? extends Service> cls) {
        startService(cls, new Bundle(), 0);
    }

    public void startService(Class<? extends Service> cls, @NonNull Bundle bundle) {
        startService(cls, bundle, 0);
    }

    public void startService(Class<? extends Service> cls, int flags) {
        startService(cls, new Bundle(), flags);
    }

    public void startService(Class<? extends Service> cls, @NonNull Bundle extras, int flags) {
        getContext().startService(new Intent(getContext(), cls).addFlags(flags).putExtras(extras));
    }

    public void stopService(Class<? extends Service> cls) {
        stopService(cls, new Bundle(), 0);
    }

    public void stopService(Class<? extends Service> cls, @NonNull Bundle bundle) {
        stopService(cls, bundle, 0);
    }

    public void stopService(Class<? extends Service> cls, int flags) {
        stopService(cls, new Bundle(), flags);
    }

    public void stopService(Class<? extends Service> cls, @NonNull Bundle extras, int flags) {
        getContext().stopService(new Intent(getContext(), cls).addFlags(flags).putExtras(extras));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// SEND BROADCASTS //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void sendBroadcast(Class<? extends Activity> cls) {
        getContext().sendBroadcast(new Intent(getContext(), cls).putExtra(Intent.ACTION_MAIN, Intent.ACTION_MAIN), new String(""));
    }

    public void sendBroadcast(String action) {
        sendBroadcast(action, new Bundle());

    }

    public void sendBroadcast(String action, @NonNull Bundle extras) {
        sendBroadcast(action, extras, null);
    }

    public void sendBroadcast(String action, int flags) {
        sendBroadcast(action, flags, null);
    }

    public void sendBroadcast(String action, @NonNull Bundle extras, int flags) {
        sendBroadcast(action, extras, flags, null);
    }


    public void sendBroadcast(String action, String permission) {
        sendBroadcast(action, 0, permission);
    }

    public void sendBroadcast(String action, @NonNull Bundle extras, String permission) {
        sendBroadcast(action, extras, 0, permission);
    }

    public void sendBroadcast(String action, int flags, String permission) {
        sendBroadcast(action, new Bundle(), flags, permission);
    }

    public void sendBroadcast(String action, @NonNull Bundle extras, int flags, String permission) {
        getContext().sendBroadcast(new Intent(action).addFlags(flags).putExtras(extras), permission);
    }

    public void sendBroadcast(Intent intent, String permission) {
        getContext().sendBroadcast(intent, permission);
    }

    public void sendBroadcast(Intent intent) {
        getContext().sendBroadcast(intent, null);
    }


    @NonNull
    public String getString(@NonNull TextView textView) {
        return textView.getText().toString();
    }

    public void toast(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    public void toast(String text, int duration) {
        // TODO Auto-generated method stub
        Toast toast = new Toast(getContext());
        TextView view = new TextView(getContext());
        view.setPadding(10, 10, 10, 10);
        view.setBackgroundColor(Color.rgb(180, 180, 180));
        view.setTextColor(Color.BLACK);
        view.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public boolean isEmpty(@NonNull TextView[] textViews) {
        boolean isEmpty = false;
        for (TextView textView : textViews) {
            if (textView.getText().length() == 0) {
                textView.setError("Cannot be null");
                textView.setHintTextColor(Color.RED);
                isEmpty = true;
            } else {
                textView.setHintTextColor(Color.GRAY);
            }
        }
        return isEmpty;
    }

    @NonNull
    public ArrayList<String> formatToArray(@NonNull Set<String> set) {

        ArrayList<String> arrayList = new ArrayList<String>(set.size());
        for (int i = 0; i < set.size(); i++) {
            arrayList.add(set.iterator().next());
        }
        return arrayList;
    }

    public void hideViews(@NonNull View[] views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public void showViews(@NonNull View[] views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public <T extends TextView> void resetInputs(@NonNull T[] textViews) {
        for (T textView : textViews) {
            textView.setText("");
        }
    }


    public boolean isNetworkConnected(boolean alert) {
        return isNetworkConnected(alert, new Random(System.currentTimeMillis()).nextInt(), null, null, 0x0001, 0x0002);
    }

    public boolean isNetworkConnected(boolean alert, int notificationId, String title, String message, int smallIcon, int largeIcon) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean networkConnected = false;

        if (networkInfo != null) {
            networkConnected = networkInfo.isAvailable() && networkInfo.isConnected();
        }

        if (!networkConnected && alert) {
            sendNotification(notificationId, title, message, smallIcon, largeIcon);
        }
        return networkConnected;
    }

    private void sendNotification(int notificationId, String title, String message, int smallIcon, int largeIcon) {
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getContext());
        notificationCompat.build();
        notificationCompat.mContentTitle = title;
        notificationCompat.mContentText = message;
        notificationCompat.setSmallIcon(smallIcon);
        notificationCompat.setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(), largeIcon));
        notificationManager.notify(notificationId, notificationCompat.build());
    }

    public Drawable getUserAvatar() throws FileNotFoundException {
        String profilePicUrl = null;
        if (profilePicUrl == null) {
            return null;
        }
        return Drawable.createFromStream(new FileInputStream(new File(profilePicUrl)), profilePicUrl);
    }


    public Bitmap getFileBitmap(String fileUri) throws FileNotFoundException {
        if (fileUri == null) {
            return null;
        }

        return BitmapFactory.decodeStream(new FileInputStream(new File(fileUri)));
    }

    public ProgressDialog showCustomProgressDialog(String title, String message, boolean cancelable) {
        ProgressDialog mDialog = new ProgressDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(cancelable);
        mDialog.show();
        return mDialog;
    }

    public AlertDialog.Builder showAlertDialog(String title, String message, int textColor,
                                               String positiveButtonText, final Intent positiveIntent,
                                               String negativeButtonText, final Intent negativeIntent,
                                               String neutralButtonText, final Intent neutralIntent) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle(title);
        TextView mTvMessage = new TextView(getContext());
        mTvMessage.setPadding(16, 16, 16, 16);
        mTvMessage.setTextColor(textColor);
        mTvMessage.setText(message);
        alertDialog.setView(mTvMessage);

        if (positiveButtonText != null) {
            alertDialog.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (positiveIntent != null) {
                        sendBroadcast(positiveIntent);
                    }

                }
            });
        }

        if (negativeButtonText != null) {
            alertDialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (negativeIntent != null) {
                        sendBroadcast(negativeIntent);
                    }

                }
            });
        }
        if (neutralButtonText != null) {
            alertDialog.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (neutralIntent != null) {
                        sendBroadcast(neutralIntent);
                    }

                }
            });
        }
        alertDialog.show();
        return alertDialog;
    }

    public boolean isExists(@NonNull Uri uri, @NonNull String[] columns, @NonNull String[] columnValues) {

        String where = null;

        if (columns.length != columnValues.length) {
            throw new IllegalArgumentException("Missing values for Columns " + Arrays.deepToString(columns) + ", You provided " + Arrays.deepToString(columnValues));
        }

        if (columns.length == columnValues.length) {

            for (int i = 0; i < columns.length; i++) {
                if (i == 0) {
                    where = "";
                }
                where += columns[i] + "='" + columnValues[i] + "' ";
                if (i < columns.length - 1) {
                    where += " AND ";
                }
            }
        }

        Log.i("WHERE_CLAUSE", "isExists(" + where + ")");

        Cursor cursor = getContext().getContentResolver().query(uri, null, where, null, null);

        boolean isExists = false;

        if (cursor != null) {
            isExists = cursor.getCount() > 0;
            cursor.close();
        }

        return isExists;
    }

    private String getColumnsValues(@NonNull Uri uri, @NonNull String[] index, @NonNull String[] indexColumn, String targetColumn) {
        String where = null;
        if (index.length == 0 && indexColumn.length == 0) {
            where = null;
        } else {
            where = "";
            if (index.length != indexColumn.length) {
                throw new IllegalArgumentException("Missing params for Columns " + Arrays.deepToString(indexColumn) + ", You provided " + Arrays.deepToString(index));
            }

            if (index.length == indexColumn.length) {
                for (int i = 0; i < indexColumn.length; i++) {
                    where += indexColumn[i] + "='" + index[i] + "' ";
                    if (i < (index.length - 1)) {
                        where += " AND ";
                    }
                }
            }
        }

        Log.i("WHERE_CLAUSE", "getColumnsValues(" + where + ")");

        Cursor cursor = getContext().getContentResolver().query(uri, null, where, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            String foundValue = null;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                foundValue = cursor.getString(cursor.getColumnIndex(targetColumn));
            }
            if (cursor != null) cursor.close();
            return foundValue;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public String formatTime(String pattern, long timeMillis) {
        return new SimpleDateFormat(pattern).format(new Date(timeMillis));
    }

    public static final long TIME_SCOPE_ONE_DAY = 1 * 24 * 60 * 60 * 1000;
    public static final long TIME_SCOPE_TWO_DAYS = 2 * 24 * 60 * 60 * 1000;

    public static final String DATE_TODAY = "Today";
    public static final String DATE_YESTERDAY = "Yesterday";

    public String getFormattedDate(long timeMillis) {
        if (getTimeScope(timeMillis) <= TIME_SCOPE_TWO_DAYS) {
            if (getTimeScope(timeMillis) <= TIME_SCOPE_ONE_DAY) {
                if (formatTime("EEE dd/LLL/yyy", timeMillis).equals(formatTime("EEE dd/LLL/yyy", System.currentTimeMillis()))) {
                    return DATE_TODAY;
                }
                return DATE_YESTERDAY;
            }

        }

        //This is a very old date --  Return The  Date
        return formatTime("EEE dd/LLL/yyy", timeMillis);
    }


    private long getTimeScope(long callTime) {
        return System.currentTimeMillis() - callTime;
    }

    public String getHumanFriendlyFormattedTime(long timeMillis) {
        return getFormattedDate(timeMillis);
    }

    public String getFormattedTime(String pattern, long timeMillis) {
        return formatTime(pattern, timeMillis);
    }

    public Cursor getPersonContactsInfo(String phonenumber) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + phonenumber + "'";

        return getContext().getContentResolver().query(uri, null, selection, null, null);
    }

    //Get User Display Name
    public String getPersonDisplayName(Cursor cursor) {
        String displayName = null;
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                break; //Make Sure we just iterate once
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return displayName;
    }

    public String getContactDisplayName(String phonenumber) {
        return getPersonDisplayName(getPersonContactsInfo(phonenumber));
    }


    public Uri getContactPhotoUri(int contactId, PhotoType photoType) {
        Uri personUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(personUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

        if (photoType == PhotoType.PHOTO_TYPE_REAL_IMAGE) {
            photoUri = Uri.withAppendedPath(personUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
        }
        if (photoType == PhotoType.PHOTO_TYPE_THUMBNAIL) {
            Uri.withAppendedPath(personUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        }
        if (photoType == PhotoType.PHOTO_TYPE_BASE64) {
            photoUri = Uri.withAppendedPath(personUri, ContactsContract.Contacts.Photo.PHOTO);
        }
        return photoUri;
    }

    public int getPersonId(Cursor cursor) {
        int personContactId = -1;
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                personContactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                break; //Make Sure we just iterate once
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return personContactId;
    }


    public String calculateMediaLength(int duration) {

        int dSeconds = (int) (duration / 1000) % 60;
        int dMinutes = (int) ((duration / (1000 * 60)) % 60);
        int dHours = (int) ((duration / (1000 * 60 * 60)) % 24);

        if (dHours == 0) {
            return String.format("%02d:%02d", dMinutes, dSeconds);
        } else {
            return String.format("%02d:%02d:%02d", dHours, dMinutes, dSeconds);
        }

    }

    public String getPersonContactsDataItem(Uri contactUri, String dataColumn) {

        String dataItem = null;

        Cursor cursor = getContext().getContentResolver().query(contactUri, new String[]{dataColumn}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            dataItem = cursor.getString(cursor.getColumnIndex(dataColumn));
        }
        if (cursor != null) {
            if (!cursor.isClosed()) cursor.close();
        }

        return dataItem;
    }

    public String getActiveLine1Number() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String line1Number = telephonyManager.getLine1Number();
        telephonyManager = null;
        return line1Number;
    }

    public enum PhotoType {
        PHOTO_TYPE_REAL_IMAGE, PHOTO_TYPE_THUMBNAIL, PHOTO_TYPE_BASE64
    }

    public InputStream checkForContactAvatar(Uri contactAvatarUri, PhotoType photoType) {
        InputStream is = null;
        try {
            return getContext().getContentResolver().openInputStream(contactAvatarUri);
        } catch (Exception e) { //Possible Exceptions include FileNotFound
            return null;
        }
    }

    public InputStream getContactAvator(Uri contactAvatarUri) {

        try {
            return getContext().getContentResolver().openInputStream(contactAvatarUri);
        } catch (Exception e) { //Possible Exceptions include FileNotFound

            return null;
        }

    }

    //THIS METHOD IS POORLY WRITTEN!
    public Bitmap getContactAvatar(String phonenumber) {
        return BitmapFactory.decodeStream(
                getContactAvator(
                        getContactPhotoUri(getPersonId(getPersonContactsInfo(phonenumber)), PhotoType.PHOTO_TYPE_REAL_IMAGE)));
    }
}
