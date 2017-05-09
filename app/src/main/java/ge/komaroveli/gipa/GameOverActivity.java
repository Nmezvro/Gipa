package ge.komaroveli.gipa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class GameOverActivity extends Activity
{
	Activity thisActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		thisActivity = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gameover);
		Intent iin= getIntent();
		Bundle b = iin.getExtras();
		
		if(b!=null)
		{
			((TextView)findViewById(R.id.twGameOverScore)).setText("Level: " + b.get("Level") + ". Score: " + b.get("Score") + ".");
		}
	}
	
}
