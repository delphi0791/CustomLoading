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

import android.app.ProgressDialog;
import android.content.Context;

/**
 * The DefaultLoading represents
 * @version $Id$
 * @author wangqingyi
 */
public class DefaultLoading {

	private static ProgressDialog mypDialog;

	/**
	 * This show loading (progressbar)
	 * @param context
	 * @param msg
	 * @return
	 */
	public static ProgressDialog showProgressBar(Context context, String msg) {
		mypDialog = new ProgressDialog(context);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mypDialog.setMessage(msg);
		mypDialog.setIndeterminate(false);
		mypDialog.setCancelable(true);
		mypDialog.show();
		return mypDialog;
	}

	/**
	 * This change progress bar message
	 * @param message
	 */
	public static void changeProgressBarMessage(String message) {
		mypDialog.setMessage(message);
	}

	/**
	 * This close ProgressBar
	 */
	public static void closeProgressBar() {
		if (mypDialog != null && mypDialog.isShowing()) {
			mypDialog.dismiss();
		}
	}
}
