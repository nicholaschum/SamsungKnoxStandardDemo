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

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.enterprise.EnterpriseDeviceManager;
import android.app.enterprise.RestrictionPolicy;
import android.app.enterprise.license.EnterpriseLicenseManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This app was created to demonstrate the B2B SDKs functionality.
 * <p>
 * It simply toggles the camera availability ON/OFF.
 *
 * @author support@samsung.com
 */
public class MainActivity extends Activity {

    static final int DEVICE_ADMIN_ADD_RESULT_ENABLE = 1;
    // IMPORTANT:
    // You need to get your own Samsung ELM License from seap.samsung.com/developer
    // Replace the string below with your actual ELM License key.
    //
    // DO NOT HARDCODE YOUR LICENSE CODE IN PRODUCTION!
    // Android apps can be decompiled, exposing your unique license key.
    // Normally you will send the ELM License key to the app over an encrypted connection
    // from your solution's cloud service.
    private final static String demoELMKey =
            "INSERT ELM KEY IN HERE";
    private TextView logView;
    private Button btn1;
    private Button btn2;
    @SuppressWarnings("FieldCanBeLocal")
    private DevicePolicyManager dpm;
    private EnterpriseDeviceManager edm;
    private EnterpriseLicenseManager elm;

    private ComponentName mDeviceAdmin;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("MainActivity.onActivityResult()");

        if (requestCode == DEVICE_ADMIN_ADD_RESULT_ENABLE) {

            switch (resultCode) {
                case Activity.RESULT_CANCELED:
                    log("Request failed.");
                    break;
                case Activity.RESULT_OK:
                    log("Device administrator activated.");
                    btn2.setEnabled(true);
                    btn1.setEnabled(false);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logView = findViewById(R.id.log_id);
        logView.setMovementMethod(new ScrollingMovementMethod());

        btn1 = findViewById(R.id.btn1_id);
        btn2 = findViewById(R.id.btn2_id);

        mDeviceAdmin = new ComponentName(MainActivity.this, SampleAdminReceiver.class);
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (dpm != null) {
            if (dpm.isAdminActive(mDeviceAdmin)) {
                btn1.setEnabled(false);
                btn2.setEnabled(true);
            } else {
                btn1.setEnabled(true);
                btn2.setEnabled(false);
            }
        }

        edm = new EnterpriseDeviceManager(this);
        elm = EnterpriseLicenseManager.getInstance(this);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processOne();
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processTwo();
            }
        });

        log("Activity log started. Scroll it up and down.");
    }

    /**
     * This method is invoked when Button#1 is pressed
     */
    private void processOne() {
        log("Activate new device administrator.");

        // This activity asks the user to grant device administrator rights to the app.
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
        startActivityForResult(intent, DEVICE_ADMIN_ADD_RESULT_ENABLE);
    }

    /**
     * This method is invoked when Button#2 is pressed
     */
    private void processTwo() {

        RestrictionPolicy rp = edm.getRestrictionPolicy();

        boolean cameraEnabled = rp.isCameraEnabled(false);

        // Toggle camera functionality
        try {
            rp.setCameraState(!cameraEnabled);
            log("Set camera enabled to: " + !cameraEnabled);
        } catch (SecurityException e) {
            log("Exception: " + e);
            log("Activating license.");
            log("Have you remembered to change the demoELMKey in the source code?");
            // This exception indicates that the ELM policy has not been activated, so we activate
            // it now. Note that embedding the license in the code is unsafe and it is done here for
            // demonstration purposes only.
            elm.activateLicense(demoELMKey);
        } catch (Exception e) {
            log("Exception: " + e);
        }
    }

    /**
     * Logs a line of text to the app log.
     *
     * @param text Post the string to the logcat
     */
    public void log(String text) {
        logView.append(text);
        logView.append("\n");
        logView.invalidate();
    }
}
