package avi.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MyActivity extends AppCompatActivity {
   private TextView tv_head;
    private TextView tv_desc;
    private Button fullart;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        tv_head=(TextView)findViewById(R.id.content_Headline);
        tv_desc=(TextView)findViewById(R.id.content_Description);
        fullart=(Button)findViewById(R.id.article_button);
        Intent intent = getIntent();
        String headline = intent.getStringExtra("i_headline");
        String desc = intent.getStringExtra("i_description");
        String imgurl = intent.getStringExtra("i_imgurl");
        url = intent.getStringExtra("i_url");
        tv_head.setText(headline);
        tv_desc.setText(desc);
        ImageView collapsingImage = (ImageView) findViewById(R.id.collapsingImage);
        Glide.with(this)
                .load(imgurl)
                .centerCrop()
                .error(R.drawable.ic_placeholder)
                .crossFade()
                .into(collapsingImage);

        fullart.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View v) {
                                          openWebViewActivity();
                                       }
                                   });
    }
    private void openWebViewActivity() {
        Intent browserIntent = new Intent(MyActivity.this, WebViewActivity.class);
        browserIntent.putExtra("urlart", url);
        startActivity(browserIntent);
    }



}

