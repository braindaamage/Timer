package cl.ciisa;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class TimerActivity extends Activity {
	
	private static final String MYTAG = "TimerActivity";
	private Intent intent; 
	private TextView timeRemainingView;
	private LinearLayout linearLayout;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeRemainingView = new TextView(this);
        initLayout();
        intent = new Intent(this, BroadcastService.class);
        
    }
    
    private void initLayout(){
		this.linearLayout = new LinearLayout (this);
		this.linearLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);	   
		this.linearLayout.setLayoutParams(lp);
		this.linearLayout.addView(timeRemainingView);
		setContentView(linearLayout);
    }
    
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			updateUI(intent);       
		}
	}; 
	    
	@Override
	public void onResume() {
		super.onResume();		
		startService(intent);
		registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.BROADCAST_ACTION));
	}
		
	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
		stopService(intent); 		
	}	
		    
	private void updateUI(Intent intent) {
		Log.d(MYTAG, "updateUI");
    	String time = intent.getStringExtra("time");
    	timeRemainingView.setText(time);
    }
    
    
}