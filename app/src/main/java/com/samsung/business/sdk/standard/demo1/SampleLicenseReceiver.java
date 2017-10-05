/*
* DISCLAIMER: PLEASE TAKE NOTE THAT THE SAMPLE APPLICATION AND
* SOURCE CODE DESCRIBED HEREIN IS PROVIDED FOR TESTING PURPOSES ONLY.
*
*
* Samsung expressly disclaims any and all warranties of any kind, whether express or implied, including
* but not limited to the implied warranties and conditions of merchantability, fitness for a particular
* purpose and non-infringement. Further, Samsung does not represent or warrant that any portion of
* the sample application and source code is free of inaccuracies, errors, bugs or interruptions, or is reliable,
* accurate, complete, or otherwise valid. The sample application and source code is provided "as is" and
* "as available", without any warranty of any kind from Samsung.
*
* Your use of the sample application and source code is at its own discretion and risk, and licensee will be
* solely responsible for any damage that results from the use of the sample application and source code
* including, but not limited to, any damage to your computer system or platform. For the purpose of clarity,
* the sample code is licensed “as is” and licensee bears the risk of using it.
*
* Samsung shall not be liable for any direct, indirect or consequential damages or costs of any type arising out
* of any action taken by you or others related to the sample application and source code.
*/
package com.samsung.business.sdk.standard.demo1;

import android.app.enterprise.license.EnterpriseLicenseManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SampleLicenseReceiver extends BroadcastReceiver {

	void showToast(Context context, CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action != null && action.equals(EnterpriseLicenseManager.ACTION_LICENSE_STATUS)) {
			String result = intent.getStringExtra(EnterpriseLicenseManager.EXTRA_LICENSE_STATUS); 
			showToast(context, "License activation: " + result);
		}
	}
}