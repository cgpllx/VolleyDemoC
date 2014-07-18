package com.example.volleydemoc;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.volleydemoc.cache.memory.BitmapCache;

public class MainActivity extends Activity {
	public static String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadNetworkImageView();
//		getjson1();
		 imageloder();
	}

	//	  private void requestVolley() {
	//	        // Volley でリクエスト
	//	        String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
	//	        mQueue.add(new JsonObjectRequest(Method.GET, url, null,
	//	                new Listener<JSONObject>() {
	//	                    @Override
	//	                    public void onResponse(JSONObject response) {
	//	                        long time = System.currentTimeMillis() - requestStartTime;
	//	                        Log.d(TAG, "Volley Request finished : " + time);
	//	                    }
	//	                }, null));
	//	        requestStartTime = System.currentTimeMillis();
	//	        mQueue.start();
	//	    }

	//	    private void requestHttpClient() {
	//	        // HttpClient  
	//	        getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<JSONObject>() {
	//	            @Override
	//	            public Loader<JSONObject> onCreateLoader(int id, Bundle bundle) {
	//	                requestStartTime = System.currentTimeMillis();
	//	                return new JSONLoader(getApplicationContext());
	//	            }
	//	            @Override
	//	            public void onLoadFinished(Loader<JSONObject> loader, JSONObject result) {
	//	                long time = System.currentTimeMillis() - requestStartTime;
	//	                Log.d(TAG, "HttpClient Request finished : " + time);
	//	                getSupportLoaderManager().destroyLoader(0);
	//	            }
	//	            @Override
	//	            public void onLoaderReset(Loader<JSONObject> loader) {
	//	            }
	//	        });
	//	    }

	private void loadNetworkImageView() {
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		String url = "http://avatar.csdn.net/3/0/3/1_changyanmanman.jpg";
		NetworkImageView view = (NetworkImageView) this.findViewById(R.id.network_image_view);
		//		view.setImageUrl(null, null);
		view.setImageUrl(url, new ImageLoader(mQueue, new BitmapCache()));
	}

	private void imageloder() {
		String url = "http://avatar.csdn.net/3/0/3/1_changyanmanman.jpg";
		ImageView imageView = (ImageView) this.findViewById(R.id.ImageView1);
		ImageListener listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);  
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		ImageLoader mImageLoader= new ImageLoader(mQueue, new BitmapCache());
		mImageLoader.get(url, listener);  

	}

	private void getjsonArray() {
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		String url = "http://market.konkacloud.com/client/recommend?type=2";
		mQueue.add(new JsonArrayRequest(url, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray arg0) {
				System.out.println("执行了" + arg0.toString());
			}
		}, null) {
		});
		mQueue.start();

	}

	private void getjsonObject() {
		//		System.out.println("执行了getContext()="+getContext());
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		String url = "http://market.konkacloud.com/client/recommend?type=2";
		mQueue.add(new JsonObjectRequest(Method.GET, url, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d("cgp", "response : " + response.toString());
				System.out.println("执行了" + response.toString());
			}

		}, errorListener));
		mQueue.start();
	}

	ErrorListener errorListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError arg0) {
			System.out.println("出错了arg0==" + arg0);
		}
	};
}
