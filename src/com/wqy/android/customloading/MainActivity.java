package com.wqy.android.customloading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity implements OnClickListener {

	private CheckBox cancelableBox;
	private Button defaultLoadBtn, showBtn, animBtn;
	private final static int DEFAULT = 0, CUSTOM = 1, ANIMATION = 2;
	private final static int HIDE_TIME = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
	}

	private void findViews() {
		this.defaultLoadBtn = (Button) findViewById(R.id.defaultLoadBtn);
		this.defaultLoadBtn.setOnClickListener(this);
		this.showBtn = (Button) findViewById(R.id.btn_show);
		this.showBtn.setOnClickListener(this);
		this.cancelableBox = (CheckBox) findViewById(R.id.checkBox1);
		this.animBtn = (Button) findViewById(R.id.animBtn);
		this.animBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		boolean cancelable = this.cancelableBox.isChecked();
		if (id == R.id.btn_show) {
			LoadingView.showLoading(cancelable, this, getString(R.string.loading_msg));
			this.handler.sendEmptyMessageDelayed(CUSTOM, HIDE_TIME);
		} else if (id == R.id.defaultLoadBtn) {
			DefaultLoading.showProgressBar(this, getString(R.string.loading_msg));
			this.handler.sendEmptyMessageDelayed(DEFAULT, HIDE_TIME);
		} else if (id == R.id.animBtn) {
			AnimationLoading.showLoading(cancelable, this, getString(R.string.loading_msg));
			this.handler.sendEmptyMessageDelayed(ANIMATION, HIDE_TIME);
		}
	}

	Handler handler = new Handler() {

		/**
		 * {@inheritDoc}
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == DEFAULT) {
				DefaultLoading.closeProgressBar();
			} else {
				LoadingView.hideLoading();
			}
			super.handleMessage(msg);
		}

	};
}
