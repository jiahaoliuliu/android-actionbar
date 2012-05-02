package com.markupartist.android.actionbar.example;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import android.util.Log;

public class HomeActivity extends Activity {
	
	private static final String LOG_TAG = HomeActivity.class.getSimpleName();
	private ActionBar actionBar;
	private Action shareAction;
	private boolean progressStarted = false;
	private int titleGravity = Gravity.LEFT;
	private float defaultTitleSize = 0.0f;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        actionBar = (ActionBar) findViewById(R.id.actionbar);
        //actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.ic_title_home_demo));
        
        actionBar.setTitle("Home");
        actionBar.setTitleGravity(Gravity.LEFT);
        titleGravity = Gravity.LEFT;
        
        shareAction = new IntentAction(this, createShareIntent(), R.drawable.ic_title_share_default);
        actionBar.addAction(shareAction);
        final Action otherAction = new IntentAction(this, new Intent(this, OtherActivity.class), R.drawable.ic_title_export_default);
        actionBar.addAction(otherAction);

        Button startProgress = (Button) findViewById(R.id.change_progress);
        startProgress.setOnClickListener(onClickListener);
                
        Button removeActions = (Button) findViewById(R.id.remove_all_actions);
        removeActions.setOnClickListener(onClickListener);

        Button addAction = (Button) findViewById(R.id.add_action);
        addAction.setOnClickListener(onClickListener);

        Button removeAction = (Button) findViewById(R.id.remove_action);
        removeAction.setOnClickListener(onClickListener);

        Button removeShareAction = (Button) findViewById(R.id.remove_share_action);
        removeShareAction.setOnClickListener(onClickListener);
        
        Button changeTitleGravityAction = (Button) findViewById(R.id.change_title_gravity);
        changeTitleGravityAction.setOnClickListener(onClickListener);
        
        Button increaseTitleSizeAction = (Button) findViewById(R.id.increase_title_size);
        increaseTitleSizeAction.setOnClickListener(onClickListener);

        Button originalTitleSizeAction = (Button) findViewById(R.id.original_title_size);
        originalTitleSizeAction.setOnClickListener(onClickListener);

    }
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()){
			case (R.id.change_progress): {
				if (!progressStarted) {
					actionBar.setProgressBarVisibility(View.VISIBLE);
					progressStarted = true;
				} else {
					actionBar.setProgressBarVisibility(View.GONE);
					progressStarted = false;
				}
                break;
			}
			case (R.id.remove_all_actions): {
                actionBar.removeAllActions();
                break;
			}
			case (R.id.add_action): {
                actionBar.addAction(new Action() {
                    @Override
                    public void performAction(View view) {
                        Toast.makeText(HomeActivity.this, "Added action.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public int getDrawable() {
                        return R.drawable.ic_title_share_default;
                    }
                });
                break;
			}
			case (R.id.remove_action): {
                int actionCount = actionBar.getActionCount();
                if (actionCount > 0) {
	                actionBar.removeActionAt(actionCount - 1);
	                Toast.makeText(HomeActivity.this, "Removed action." , Toast.LENGTH_SHORT).show();
                }
                break;
			}
			case (R.id.remove_share_action): {
                actionBar.removeAction(shareAction);
                break;
			}
			case (R.id.change_title_gravity): {
				if (titleGravity == Gravity.LEFT) {
					actionBar.setTitleGravity(Gravity.CENTER);
					titleGravity = Gravity.CENTER;
				} else if (titleGravity == Gravity.CENTER) {
					actionBar.setTitleGravity(Gravity.RIGHT);
					titleGravity = Gravity.RIGHT;
				} else if (titleGravity == Gravity.RIGHT) {
					actionBar.setTitleGravity(Gravity.LEFT);
					titleGravity = Gravity.LEFT;
				}
				break;
			}
			case (R.id.increase_title_size): {
				float actualTitleSize = actionBar.getTitleSize();
				if (defaultTitleSize == 0.0f) {
					defaultTitleSize = actualTitleSize;
				}
				float newTitleSize = actualTitleSize + 1.0f;
				actionBar.setTitleSize(newTitleSize);
				break;
			}
			case (R.id.original_title_size): {
				if (defaultTitleSize != 0.0f) {
					actionBar.setTitleSize(defaultTitleSize);
				}
				break;
			}
			}
		}
	};

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    private Intent createShareIntent() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Shared from the ActionBar widget.");
        return Intent.createChooser(intent, "Share");
    }
}