package com.example.volleydemoc;

import org.json.JSONObject;

import android.os.SystemClock;
import android.test.AndroidTestCase;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class Text extends AndroidTestCase {
	//	Context context;

	//	private void loadNetworkImageView() {
	//		RequestQueue mQueue = Volley.newRequestQueue(getContext());
	//		String url = "http://avatar.csdn.net/3/0/3/1_changyanmanman.jpg";
	//		NetworkImageView view = (NetworkImageView) this.findViewById(R.id.network_image_view);
	//		//		view.setImageUrl(null, null);
	//		view.setImageUrl(url, new ImageLoader(mQueue, new BitmapCache()));
	//	}
	@Override
	protected void runTest() throws Throwable {
		super.runTest();
		getjson();
	}

	private void getjson() {
		System.out.println("执行了getContext()="+getContext());
		RequestQueue mQueue = Volley.newRequestQueue(getContext());
		String url = "http://market.konkacloud.com/client/recommend?type=2";
		mQueue.add(new JsonObjectRequest(Method.GET, url, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d("cgp", "response : " + response.toString());
				System.out.println("执行了" + response.toString());
			}

		}, e));
		mQueue.start();
//		SystemClock.sleep(5000);
	}
	ErrorListener e=new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError arg0) {
			System.out.println("出错了");
		}
	};
}
