/*
 * Copyright © 2013 Futurefleet Ltd., All Rights Reserved.
 * For licensing terms please contact Futurefleet LTD.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wqy.android.customloading;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;

/**
 * The CustomLoading represents
 * @version $Id$
 * @author wangqingyi
 */
public class LoadingView {

	private final static String TAG = "CustomLoading";
	private static Activity mContext;
	private static Dialog loadingView;

	/**
	 * This show loading
	 * @param cancelable
	 * @param context
	 * @param message
	 */
	public static void showLoading(boolean cancelable, Activity context, String message) {
		mContext = context;
		if (loadingView != null && loadingView.isShowing()) {
			return;
		}
		Message msg = new Message();
		msg.what = STATUS.SHOW.value();
		msg.arg1 = cancelable == true ? 1 : 0;
		msg.obj = message;
		handler.sendMessage(msg);
	}

	/**
	 * This close the loading view
	 */
	public static void hideLoading() {
		handler.sendEmptyMessage(STATUS.HIDE.value());

	}

	/**
	 * This change loading view text
	 * @param message
	 */
	public static void refreshLoadingMessage(String message) {
		if (loadingView != null && loadingView.isShowing()) {
			Message msg = new Message();
			msg.what = STATUS.REFRESH.value();
			msg.obj = message;
			handler.sendMessage(msg);
		}
	}

	static void showLoadingViewInWindow(final boolean cancelable, final String message) {
		mContext.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Log.i(TAG, "[show loading]" + message + cancelable);
				LayoutInflater inflater = LayoutInflater.from(mContext);
				View v = inflater.inflate(R.layout.loading_layout, null);// 得到加载view
				LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
				// main.xml中的ImageView
				GifView gif = (GifView) v.findViewById(R.id.loading_gif);
				gif.setGifImageType(GifImageType.SYNC_DECODER);
				gif.setGifImage(R.drawable.spinner);
				TextView tipTextView = (TextView) v.findViewById(R.id.identify_label);// 提示文字
				if (message != null && !"null".equals(message)) {
					tipTextView.setVisibility(View.VISIBLE);
					tipTextView.setText(message);// 设置加载信息
				} else {
					tipTextView.setVisibility(View.GONE);
					gif.setPadding(30, 30, 30, 30);
				}
				loadingView = new Dialog(mContext, R.style.loading_dialog);// 创建自定义样式dialog
				loadingView.setCancelable(cancelable);
				// loadingView.setCanceledOnTouchOutside(true);
				loadingView.setContentView(layout, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));// 设置布局
				Window dialogWindow = loadingView.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				lp.dimAmount = 0.5f;
				dialogWindow.setAttributes(lp);
				loadingView.show();
			}

		});
	}

	static void hideLoadingViewInWindow() {
		Log.i(TAG, "[hide loading]");
		if (loadingView != null && loadingView.isShowing()) {
			loadingView.dismiss();
		}
	}

	static void refreshLoadingView(String message) {
		if (loadingView != null && loadingView.isShowing()) {
			TextView tipTextView = (TextView) loadingView.findViewById(R.id.identify_label);// 提示文字
			tipTextView.setText(message);
		}
	}

	static Handler handler = new Handler() {

		/**
		 * {@inheritDoc}
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			STATUS status = STATUS.valueOf(msg.what);
			switch (status) {
				case HIDE:
					hideLoadingViewInWindow();
					break;
				case REFRESH:
					refreshLoadingView(String.valueOf(msg.obj));
					break;
				case SHOW:
					boolean cancelable = msg.arg1 == 1;
					showLoadingViewInWindow(cancelable, String.valueOf(msg.obj));
					break;
				default:
					break;

			}
			super.handleMessage(msg);
		}

	};

	public enum STATUS {
		SHOW(0),
		HIDE(1),
		REFRESH(2);

		private int value = 0;

		STATUS(int value) {
			this.value = value;
		}

		/**
		 * This
		 * @return
		 */
		public int value() {
			return this.value;
		}

		/**
		 * This
		 * @param value
		 * @return
		 */
		public static STATUS valueOf(int value) {
			switch (value) {
				case 0:
					return SHOW;
				case 1:
					return HIDE;
				case 2:
					return REFRESH;

			}
			return SHOW;
		}
	}
}
