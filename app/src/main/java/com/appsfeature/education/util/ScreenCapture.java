package com.appsfeature.education.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.appsfeature.education.R;
import com.helper.task.TaskRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

public class ScreenCapture {

    private static final String DOWNLOAD_DIRECTORY = "AasanilazInvoice";

    public static void takeScreenShot(Activity activity, View llScroll , boolean isPrint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TaskRunner.getInstance().executeAsync(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String filePath = createPdf(activity, loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight()));
                    return filePath;
                }
            }, new TaskRunner.Callback<String>() {
                @Override
                public void onComplete(String filePath) {
                    sharePdfFile(activity, filePath, isPrint);
                }
            });
        }
    }

    private static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String createPdf(Activity activity, Bitmap bitmap) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String fileName = "Invoice" + System.currentTimeMillis() + ".pdf";
        final String mFilePath = Environment.getExternalStorageDirectory() + "/" + DOWNLOAD_DIRECTORY;

        File apkStorage = null;
        if (isSDCardPresent()) {
            apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + DOWNLOAD_DIRECTORY);
        }
        //If File is not present create directory
        if (apkStorage != null && !apkStorage.exists()) {
            apkStorage.mkdir();
            // Log.e(TAG, "Directory Created.");
        }
        File filePath = new File(apkStorage, fileName);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        document.close();
        return filePath.getPath();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String createImage(Activity activity, Bitmap bitmap) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Canvas canvas = page.getCanvas();
//        Paint paint = new Paint();
//        canvas.drawPaint(paint);
//        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//
//        paint.setColor(Color.BLUE);
//        canvas.drawBitmap(bitmap, 0, 0, null);
//        document.finishPage(page);

        // write the document content
        String fileName = "Invoice" + System.currentTimeMillis() + ".pdf";
        final String mFilePath = Environment.getExternalStorageDirectory() + "/" + DOWNLOAD_DIRECTORY;

        File apkStorage = null;
        if (isSDCardPresent()) {
            apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + DOWNLOAD_DIRECTORY);
        }
        //If File is not present create directory
        if (apkStorage != null && !apkStorage.exists()) {
            apkStorage.mkdir();
            // Log.e(TAG, "Directory Created.");
        }
        File filePath = new File(apkStorage, fileName);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }
//        document.close();
        return filePath.getPath();
    }



    public static void sharePdfFile(Activity activity, String filePath, boolean isPrint) {
        File file = new File(filePath);
        if(isPrint){
            doPrint(activity, "Invoice", file);
        }else {
            Uri fileUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
            shareFile(activity, fileUri);
        }
    }

    private static void showFile(Activity activity, Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }
    }

    public static void shareFile(Activity context, Uri fileUri) {
        try {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Share"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void doPrint(Activity activity, final String fileName, final File fileToPrint) {
        PrintDocumentAdapter pda = new PrintDocumentAdapter() {
            @Override
            public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
                InputStream input = null;
                OutputStream output = null;
                try {
                    input = new FileInputStream(fileToPrint);
                    output = new FileOutputStream(destination.getFileDescriptor());
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buf)) > 0) {
                        output.write(buf, 0, bytesRead);
                    }
                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                } catch (Exception e) {
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
                if (cancellationSignal.isCanceled()) {
                    callback.onLayoutCancelled();
                    return;
                }
                PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(fileName).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
                callback.onLayoutFinished(pdi, true);
            }
        };

        try {
            PrintAttributes attributes = new PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(new PrintAttributes.Resolution("id", Context.PRINT_SERVICE, 200, 200))
                    .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .build();

            PrintManager printManager = (PrintManager) activity.getSystemService(Context.PRINT_SERVICE);
            String jobName = activity.getString(R.string.app_name) + " Document";
            printManager.print(jobName, pda, attributes);
        } catch (NullPointerException e) {
            Toast.makeText(activity, "Printer option not support in this Android version", Toast.LENGTH_LONG).show();
        }
    }

}
