package skku.teamplay.activity.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by woorim on 2018. 5. 15..
 */

public class EverytimeParseTestActivity extends Activity{

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
        webView.loadUrl("https://everytime.kr/login");
        webView.setWebViewClient(new WebViewClient() {
            String purl = "";
            @Override
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(EverytimeParseTestActivity.this, url, 0).show();
                Log.e("TeamPlay", url);
                if(purl.equals("https://everytime.kr/user/login") && url.equals("https://everytime.kr")) {
                    Toast.makeText(EverytimeParseTestActivity.this, "로그인에 실패하였습니다", 0).show();
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
    }

}
