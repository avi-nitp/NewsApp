package avi.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ArticleModel> articles = new ArrayList<>();
    private MyAdapter adapter;
     private static  String key="&apiKey=b20646a6b51c4f35bbf43ddcd5b1ab60";
    private static  String baseurl="https://newsapi.org/v2/top-headlines?sources=";
    private String source="google-news-in";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        source = this.getArguments().getString("msg");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ConnectivityManager connec =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isnet=internet.isInternetOn(connec);
        if(!isnet){
            Toast.makeText(getActivity(), " No Internet Connection ", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Connected ", Toast.LENGTH_LONG).show();
        }
        //Recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //swipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
         swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                loadRecyclerViewData();
            }
        });
        return view;
    }
    @Override
    public void onRefresh() {

        // Fetching data from server
        loadRecyclerViewData();
    }

    private void loadRecyclerViewData()
    {
        // Showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        new HttpGetRequest().execute();
    }
    public class HttpGetRequest extends AsyncTask<String, Void,Void> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        private String jsonresponse;
        @Override
        protected void onPreExecute() {
        }
            @Override
        protected Void doInBackground(String... Params){
          String  mainurl=baseurl+source+key;
                JSONObject jj=new JSONObject();
            URL murl= null;
            try {
                murl = new URL(mainurl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String result;
            String inputLine;
                try {
                    jsonresponse = makehttpconnection(murl);


                    JSONObject js = new JSONObject(jsonresponse);

                    JSONArray ja = js.optJSONArray("articles");

                    if (!articles.isEmpty())
                    {
                        articles.clear();
                    }

                    for (int i=0;i<ja.length();i++)
                    {
                        JSONObject jao = ja.optJSONObject(i);

                        String title = jao.optString("title");



                        String urlimage = jao.optString("urlToImage");

                        String urlart = jao.optString("url");

                        String description=jao.optString("description");

                            articles.add(new ArticleModel(title,description,urlart,urlimage));

                    }
                    adapter = new MyAdapter(getActivity(), articles);
                   // mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (JSONException e)
                {

                }
           // swipeRefreshLayout.setRefreshing(false);
            return null ;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                swipeRefreshLayout.setRefreshing(false);
                mRecyclerView.setAdapter(adapter);
            }


            private String makehttpconnection(URL url) throws IOException
            {
                String realres="";
                HttpURLConnection hulc = null;
                InputStream is = null;

                if (url!=null)
                {

                    hulc = (HttpURLConnection) url.openConnection();
                    hulc.setRequestMethod("GET");
                    hulc.setReadTimeout(10000);
                    hulc.setConnectTimeout(15000);
                    hulc.connect();

                    is = hulc.getInputStream();

                    realres = getstring(is);
                }

                if (hulc!=null)
                {
                    hulc.disconnect();
                }

                if (is!=null)
                {
                    is.close();
                }
                return realres;
            }


            private String getstring(InputStream is) throws IOException
            {
                StringBuilder sb = new StringBuilder();
                if (is!=null) {
                    InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8") );

                    BufferedReader bf = new BufferedReader(isr);
                    String line = bf.readLine();

                    while (line!=null)
                    {
                        sb.append(line);
                        line = bf.readLine();
                    }
                }
                return sb.toString();
            }

        }

    }