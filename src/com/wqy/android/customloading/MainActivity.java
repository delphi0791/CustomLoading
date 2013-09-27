package com.wqy.android.customloading;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity implements OnClickListener {

	private CheckBox cancelableBox;
	private Button showBtn, hideBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
	}

	private void findViews() {
		this.showBtn = (Button) findViewById(R.id.btn_show);
		this.showBtn.setOnClickListener(this);
		this.hideBtn = (Button) findViewById(R.id.btn_hide);
		this.hideBtn.setOnClickListener(this);
		this.cancelableBox = (CheckBox) findViewById(R.id.checkBox1);
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
		if (v.getId() == R.id.btn_show) {
			boolean cancelable = this.cancelableBox.isChecked();
			LoadingView.showLoading(cancelable, this, getString(R.string.loading_msg));
		} else {

		}

	}

}
