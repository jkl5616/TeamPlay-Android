package skku.teamplay.activity.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.UpdateTimeTable;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.User;

/**
 * Created by woorim on 2018. 5. 15..
 */

public class EverytimeParseTestActivity extends Activity implements OnRestApiListener{

    Handler handler = new Handler();
    Dialog dialog;

    @JavascriptInterface
    public void parse(final String json) {
        dialog.dismiss();
        Toast.makeText(EverytimeParseTestActivity.this, "parse", 0).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(EverytimeParseTestActivity.this);
                textView.setText(json);
                setContentView(textView);
                User user = TeamPlayApp.getAppInstance().getUser();
                new RestApiTask(EverytimeParseTestActivity.this).execute(new UpdateTimeTable(user.getEmail(), user.getPw(), json));
            }
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final WebView webView = new WebView(this) { @Override public boolean onCheckIsTextEditor() { return true; } };
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(this,"TeamPlay");
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        webView.loadUrl("https://everytime.kr/login?redirect=%2Ftimetable");
        webView.setWebViewClient(new WebViewClient() {
            String purl = "";
            @Override
            public void onPageFinished(WebView view, String url) {
                //Toast.makeText(EverytimeParseTestActivity.this, url, 0).show();
                Log.e("TeamPlay", url);
                if(purl.equals(url)
                        && url.equals("https://everytime.kr/login?redirect=%2Ftimetable")) {
                    Toast.makeText(EverytimeParseTestActivity.this, "로그인에 실패하였습니다. 다시 시도하세요.", 0).show();
                }
                if(url.equals("https://everytime.kr/")) {
                    Toast.makeText(EverytimeParseTestActivity.this, "login succes", 0).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("https://everytime.kr/timetable");
                        }
                    }, 300);
                } else if(url.equals("https://everytime.kr/timetable")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:function round5(x){if(x%5==0)return x;else return Math.ceil(x/5)*5-5}" +
                                    "var height=document.getElementsByClassName(\"time\")[0].offsetHeight;var timetable=new Array(7);var data=document.getElementsByClassName(\"cols\");for(var i=0;i<7;i++){timetable[i]=new Array();var data_day=data[i];var data_subs=data_day.getElementsByClassName(\"subject\");for(var j=0;j<data_subs.length;j++){var sub=new Object();sub.name=data_subs[j].innerHTML.split(\"<h3>\")[1].split(\"</\")[0];sub.prof=data_subs[j].innerHTML.split(\"em>\")[1].split(\"</\")[0];sub.top=parseInt(data_subs[j].style.top,10);sub.start=(sub.top-height*9)*6/(height/10);sub.height=parseInt(data_subs[j].style.height,10);sub.time=round5(sub.height*6/(height/10));timetable[i].push(sub)}}" +
                                    "TeamPlay.parse(JSON.stringify(timetable))");
                        }
                    }, 1000);
                }
                purl = url;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        dialog = new AlertDialog.Builder(this).setView(webView).create();
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        finish();
    }
}
