package cl.ciisa;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


public class BroadcastService extends Service 
{
	private static final String TAG = "BroadcastService";
	private final Handler handler = new Handler();
	private Intent intent;
	private int counter = 60;
	public static final String BROADCAST_ACTION = "cl.ciisa.displayevent";
	
	@Override
	public void onCreate() {
		super.onCreate();
    	intent = new Intent(BROADCAST_ACTION);	
	}
	
    @Override
    public void onStart(Intent intent, int startId) {
    	Log.d(TAG, "Broadcast: onStart");
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000);//1 second
	}

    private Runnable sendUpdatesToUI = new Runnable() {
    	public void run() {
    		Log.d(TAG, "Broadcast: sendUpdatesToUI");
    		DisplaySecondsRemaining();    		
    	    handler.postDelayed(this, 1000); // 1 second
    	}
    };    
    
    private void DisplaySecondsRemaining() {
    	if(counter == 60)counter = 0;
    	intent.putExtra("time",String.valueOf(counter++) + " segs.");
    	sendBroadcast(intent);
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {		
        handler.removeCallbacks(sendUpdatesToUI);		
		super.onDestroy();
	}
}
