package ge.komaroveli.gipa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import ge.komaroveli.gipa.Classes.Answer;
import ge.komaroveli.gipa.Classes.Colour;

public class MainActivity extends AppCompatActivity
{
	ArrayList<Integer> colorType;
	ArrayList<Colour> colors;
	Random rGen;
	TextView twScore, twColor, twColorType, twProgress, twLevel;
	Button btnStart;
	Answer answer;
	int Score = 0;
	CountDownTimer countDownTimer;
	private ProgressBar mProgress;
	int Level = 1;
	boolean inGame = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rGen = new Random();
		colorType = new ArrayList<>();
		colorType.add(1);
		colorType.add(2);
		colors = new ArrayList<>();
		colors.add(new Colour(Color.argb(255, 255, 0, 0), "Red"));
		colors.add(new Colour(Color.argb(255, 0, 255, 0), "Green"));
		colors.add(new Colour(Color.argb(255, 0, 0, 255), "Blue"));
		colors.add(new Colour(Color.argb(255, 0, 0, 0), "Black"));
		colors.add(new Colour(Color.argb(255, 255, 255, 255), "White"));
		
		twScore = (TextView) findViewById(R.id.twScore);
		twColor = (TextView) findViewById(R.id.twColor);
		twProgress = (TextView) findViewById(R.id.twProgress);
		twColorType = (TextView) findViewById(R.id.twColorType);
		twLevel = (TextView) findViewById(R.id.twLevel);
		btnStart = (Button) findViewById(R.id.btnStart);
		mProgress = (ProgressBar) findViewById(R.id.progressBar);
		mProgress.setMax(500);
		
		View.OnClickListener onClickListener = new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if(!inGame) return;
				String ansText = answer.getColor().getText();
				CharSequence btnText = ((TextView) view).getText();
				if (btnText.toString().equals(ansText))
				{
					Score = Score + 10;
				}
				else
				{
					Score = Score - 10;
				}
				twScore.setText("Score: " + Score);
				if (Score < 0)
					GameOver();
				else
					setRandomAnswer();
			}
		};
		
		findViewById(R.id.btnRed).setOnClickListener(onClickListener);
		findViewById(R.id.btnGreen).setOnClickListener(onClickListener);
		findViewById(R.id.btnBlue).setOnClickListener(onClickListener);
		findViewById(R.id.btnBlack).setOnClickListener(onClickListener);
		findViewById(R.id.btnWhite).setOnClickListener(onClickListener);
		
		btnStart.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				setRandomAnswer();
				btnStart.setVisibility(View.INVISIBLE);
			}
		});
		//View.OnLongClickListener longClick = new View.OnLongClickListener()
		//{
		//	@Override
		//	public boolean onLongClick(View view)
		//	{
		//		//showMenu(view);
		//		Intent intent = new Intent(MainActivity.this, Main2Activity.class);
		//		//intent.putExtra("Items2", );
		//		startActivity(intent);
		//		return true;
		//	}
		//};
		
		//findViewById(R.id.btnShare).setOnLongClickListener(longClick);
		//findViewById(R.id.btnComment).setOnLongClickListener(longClick);
	}
	
	void RestartTimer()
	{
		if (countDownTimer != null)
			countDownTimer.cancel();
		int MaxSec = 5000;
		int jer = Score / 50;
		for (int i = 0; i < jer; i++)
		{
			MaxSec = MaxSec - 1000;
		}
		if (MaxSec <= 0) return;
		Level = jer + 1;
		twLevel.setText("Level: " + Level + ". Speed " + (MaxSec / 1000) + "sec.");
		mProgress.setMax(MaxSec / 10);
		countDownTimer = new CountDownTimer(MaxSec, 10)
		{
			public void onTick(long time)
			{
				mProgress.setProgress((int) time / 10);
				String result = new DecimalFormat("0.00").format(time / 1000.0);
				twProgress.setText(result);
			}
			
			public void onFinish()
			{
				GameOver();
				twProgress.setText("0.00");
			}
		};
		countDownTimer.start();
	}
	
	void GameOver()
	{
		inGame = false;
		if (countDownTimer != null)
			countDownTimer.cancel();
		Intent i = new Intent(MainActivity.this, GameOverActivity.class);
		i.putExtra("Score", Score < 0 ? 0 : Score);
		i.putExtra("Level", Level);
		startActivity(i);
		Score = 0;
		Level = 1;
		twScore.setText("Score: " + Score);
		btnStart.setVisibility(View.VISIBLE);
	}
	
	void setRandomAnswer()
	{
		inGame = true;
		RestartTimer();
		answer = new Answer();
		answer.setColor(colors.get(rGen.nextInt(colors.size())));
		answer.setColorType(colorType.get(rGen.nextInt(colorType.size())));
		
		switch (answer.getColorType())
		{
			case 1:
			{
				twColorType.setText("Color");
				twColor.setTextColor(answer.getColor().getColor());
				twColor.setText(getOtherColor(answer.getColor()).getText());
				break;
			}
			case 2:
			{
				twColorType.setText("Word");
				twColor.setTextColor(getOtherColor(answer.getColor()).getColor());
				twColor.setText(answer.getColor().getText());
				break;
			}
		}
	}
	
	Colour getOtherColor(Colour except)
	{
		Colour colour = colors.get(rGen.nextInt(colors.size()));
		return colour == except ? getOtherColor(except) : colour;
	}
	/*
	
	int getRandomColor()
	{
		Random r = new Random();
		return Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.mi_Item1:
				Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
				break;
			case R.id.mi_Item2:
				Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
				break;
		}
		return true;
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.addedit_context, menu);
	}
	
	void showMenu(View v)
	{
		PopupMenu p = new PopupMenu(this, v);
		p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				switch (item.getItemId())
				{
					case R.id.mi_Item1:
						Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
						break;
					case R.id.mi_Item2:
						Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
						break;
				}
				return true;
			}
		});
		getMenuInflater().inflate(R.menu.custom_menu, p.getMenu());
		p.show();
	}
	*/
}
