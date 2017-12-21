package com.example.kidos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kidos.adapter.KidosActivityPhotosFullScreenAdapter;
import com.example.kidos.adapter.KidosActivityPhotosGridAdapter;
import com.example.kidos.beans.KidosActivityPhotosGridItemBean;

public class KidosActivityPhotosGridView extends Activity {

	
	
	private static final String TAG = KidosActivityPhotosGridView.class.getSimpleName();
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private KidosActivityPhotosGridAdapter mGridAdapter;
    private ArrayList<KidosActivityPhotosGridItemBean> mGridData;
    private String FEED_URL = "http://javatechig.com/?json=get_recent_posts&count=45";
    private ArrayList<String> mItemUrlList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_activity_photos_grid_view);
		
		mItemUrlList=new ArrayList<String>();
		
		   mGridView = (GridView) findViewById(R.id.grid_activityphotos);
	        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_kidosgrid);

	        //Initialize with empty data
	        mGridData = new ArrayList<KidosActivityPhotosGridItemBean>();
	        mGridAdapter = new KidosActivityPhotosGridAdapter(this, R.layout.layout_kidos_activity_photo_grid_item, mGridData);
	        mGridView.setAdapter(mGridAdapter);

	        
	        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

	        		Intent intent = new Intent(KidosActivityPhotosGridView.this, KidosActivityFullScreenPhotoView.class);
	        	    
	        		Bundle bundle = new Bundle();
	            	
	    			bundle.putStringArrayList("images", mItemUrlList);
	    			bundle.putInt("currentImgIndex", position);
	    			intent.putExtras(bundle);
	        	                
	        	         
	        			//Start details activity
	        			startActivity(intent);
	        		}
	        	});
	        
	        
	        //Start download
	        new AsyncHttpTask().execute(FEED_URL);
	        mProgressBar.setVisibility(View.VISIBLE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_activity_photos_grid_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    
    
    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            } else {
                Toast.makeText(KidosActivityPhotosGridView.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }

    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results and get the list
     * @param result
     */
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            KidosActivityPhotosGridItemBean item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                item = new KidosActivityPhotosGridItemBean();
                item.setTitle(title);
                JSONArray attachments = post.getJSONArray("attachments");
                if (null != attachments && attachments.length() > 0) {
                    JSONObject attachment = attachments.getJSONObject(0);
                    if (attachment != null)
                    {
                    	item.setImage(attachment.getString("url"));
                    	mItemUrlList.add(attachment.getString("url"));
                    }
                    	
                }
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
	
}
