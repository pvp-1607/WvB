package com.example.wvb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.reflect.Method;
import java.util.Objects;

public class MainActivity<Main, BrowserActivity, OnActivityResult, uploadMessage> extends AppCompatActivity {
    private static final int FILE_CHOOSER_RESULT_CODE = 0;
    private WebView mywebView;
    private String sFileName, sUrl, sUserAgent;
    private TextView textView;
    private Object TextView;
    private String PageURL;
    private String PageTitle;
    ProgressBar progressBar;
    SwipeRefreshLayout mySwipeRefreshLayout;
    private WebView view;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private static ValueCallback<Uri[]> mUploadMessageArr;
    private OnActivityResult onActivityResult;
    private String UploadMessage;
    private Object uploadMessage;
    private Object uploadMessageAboveL;
    private Object onActivityResultAboveL;
    private Object onReceiveValue;
    private Object shouldOverrideUrlLoading;

    public void onReceiveValue() {

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    @Override


    protected void onCreate(Bundle savedInstanceState) throws AssertionError {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        mywebView = findViewById(R.id.webview);
        TextView textView = (android.widget.TextView) TextView;
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.setWebChromeClient(new WebChromeClient());
        mywebView.getSettings().setJavaScriptEnabled(true);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();



        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }



        mywebView.loadUrl("http://musicandmovies.rf.gd/");
        mywebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mywebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mywebView.getSettings().setAppCacheEnabled(false);
        mywebView.getSettings().setGeolocationEnabled(true);
        mywebView.getSettings().setUseWideViewPort(true);
        mywebView.getSettings().setLoadWithOverviewMode(true);
        mywebView.getSettings().setSupportMultipleWindows(true);
        mywebView.getSettings().setSafeBrowsingEnabled(true);
        mywebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebView.getSettings().setBuiltInZoomControls(true);
        mywebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mywebView.getSettings().setDomStorageEnabled(true);
        mywebView.getSettings().setGeolocationEnabled(true);
        mywebView.getSettings().setAllowFileAccess(true);
        mywebView.getSettings().setAllowContentAccess(true);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);






        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mySwipeRefreshLayout.setRefreshing(false);
                        mywebView.loadUrl("javascript:window.location.reload( true )" );
                    }
                },  3000);
            }
        });



        WebSettings mywebsettings = null;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mywebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mywebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }



        // Get the widgets reference from XML layout
        progressBar = findViewById(R.id.progress);
        mywebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Visible the progressbar
                progressBar.setVisibility(View.VISIBLE);
            }
            /*@Override
            public void onPageFinished(WebView view, String url) {
                finalMySwipeRefreshLayout1.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }*/
        });

        mywebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                ValueCallback<Uri[]> uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });





        mywebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading (WebView view, String url) {
                if (url.endsWith(".pdf")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    // if want to download pdf manually create AsyncTask here
                    // and download file
                    return true;
                }
                return false;
            }
        });



        mywebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.setDescription("Downloading file...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }

            BroadcastReceiver onComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(getApplicationContext(), "Downloading Complete", Toast.LENGTH_SHORT).show();
                }
            };



        });





    }
    private void checkDownloadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }



    final void setMySwipeRefreshLayout() {
        mySwipeRefreshLayout = findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    final public void onRefresh() {
                        mywebView.reload();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }



    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
    }



    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }


    static class mywebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }



    @Override
    public void onBackPressed() {
        if (mywebView.canGoBack()) {
            mywebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isAppInstalled(String packageName) {
        try {
            Context activity = null;
            if (BuildConfig.DEBUG) {
                throw new AssertionError("Assertion failed");
            }
            Objects.requireNonNull(activity).getPackageManager().getApplicationInfo(
                    packageName, 0);
            return true;
        } catch (ActivityNotFoundException ex1) {
            return false;
        } catch (PackageManager.NameNotFoundException ex2) {
            return false;
        } catch (NullPointerException ex3) {
            return false;
        } catch (Exception ex4) {
            return false;
        }

    }
    @SuppressWarnings("deprecation")

    public boolean shouldOverrideUrlLoading(WebView webView, String url) {

        if (url.startsWith("magnet")) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            try {
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getBaseContext(), "Team localpeers:  No bittorent client installed in system.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return false;
    }
    @TargetApi(Build.VERSION_CODES.N)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url=request.getUrl().toString();
        if (url.startsWith("magnet")) { Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            try {
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getBaseContext(), "Team localpeers:  No bittorent client installed in Android.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    static class BaseActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        }
    }
    
}



















