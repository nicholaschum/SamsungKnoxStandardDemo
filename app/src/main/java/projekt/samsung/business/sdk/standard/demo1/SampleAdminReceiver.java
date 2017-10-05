/*
* DISCLAIMER: PLEASE TAKE NOTE THAT THE SAMPLE APPLICATION AND
* SOURCE CODE DESCRIBED HEREIN IS PROVIDED FOR TESTING PURPOSES ONLY.
*
*
* Samsung expressly disclaims any and all warranties of any kind, whether express or implied,
* including
* but not limited to the implied warranties and conditions of merchantability, fitness for a
* particular
* purpose and non-infringement. Further, Samsung does not represent or warrant that any portion of
* the sample application and source code is free of inaccuracies, errors, bugs or interruptions,
* or is reliable,
* accurate, complete, or otherwise valid. The sample application and source code is provided "as
* is" and
* "as available", without any warranty of any kind from Samsung.
*
* Your use of the sample application and source code is at its own discretion and risk, and
* licensee will be
* solely responsible for any damage that results from the use of the sample application and
* source code
* including, but not limited to, any damage to your computer system or platform. For the purpose
* of clarity,
* the sample code is licensed “as is” and licensee bears the risk of using it.
*
* Samsung shall not be liable for any direct, indirect or consequential damages or costs of any
* type arising out
* of any action taken by you or others related to the sample application and source code.
*/
package projekt.samsung.business.sdk.standard.demo1;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Example of a do-nothing admin class. When enabled, it lets you control
 * some of its policy and reports when there is interesting activity.
 */

public class SampleAdminReceiver extends DeviceAdminReceiver {

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: enabled");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "Deactivating this app as a device administrator removes the ability of the app to" +
                " control the device.";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: disabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw changed");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw failed");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: pw succeeded");
    }
}