/*******************************************************************************
 *
 *  *     Nervousnet - a distributed middleware software for social sensing. 
 *  *      It is responsible for collecting and managing data in a fully de-centralised fashion
 *  *
 *  *     Copyright (C) 2016 ETH Zürich, COSS
 *  *
 *  *     This file is part of Nervousnet Framework
 *  *
 *  *     Nervousnet is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     Nervousnet is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with NervousNet. If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  * 	Contributors:
 *  * 	Prasad Pulikal - prasad.pulikal@gess.ethz.ch  -  Initial API and implementation
 *******************************************************************************/
package ch.ethz.coss.nervousnet.hub;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import ch.ethz.coss.nervousnet.hub.ui.StartUpActivity;
import ch.ethz.coss.nervousnet.vm.NervousnetVM;
import ch.ethz.coss.nervousnet.vm.storage.SensorDataImpl;

public class Application extends android.app.Application {
	private static String LOG_TAG = Application.class.getSimpleName();
	private static int NOTIFICATION = R.string.local_service_started;

	private static NotificationManager mNM;
	private NervousnetVM nn_VM;

	public Application() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable e) {
				handleUncaughtException(thread, e);
			}
		});

		init();

	}

	/**
	 * 
	 */
	private void init() {
		Log.d(LOG_TAG, "Inside Application init()");
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nn_VM = new NervousnetVM(getApplicationContext());
		
	}

	private void handleUncaughtException(Thread thread, Throwable e) {
		Log.e(LOG_TAG, "Inside handleUncaughtException: Exception thrown here.");

		e.printStackTrace();
		System.exit(0);
	}

	public byte getState(Context context) {
		return nn_VM.getState();
	}

	public void setState(Context context, byte state) {
		nn_VM.storeNervousnetState(state);
	}

	public void startService(Context context) {
		Log.d(LOG_TAG, "inside startService");
		Toast.makeText(context, "Service Started", Toast.LENGTH_SHORT).show();
		Intent sensorIntent = new Intent(context, NervousnetHubApiService.class);
		context.startService(sensorIntent);
	}

	public void stopService(Context context) {
		Log.d(LOG_TAG, "inside stopService");
		Toast.makeText(context, "Service Stopped", Toast.LENGTH_SHORT).show();
		Intent sensorIntent = new Intent(context, NervousnetHubApiService.class);
		context.stopService(sensorIntent);
		removeNotification();

	}

	/**
	 * Show a notification while this service is running.
	 */
	public void showNotification() {
		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		CharSequence text = getText(R.string.local_service_started);

		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, StartUpActivity.class), 0);

		// Set the info for the views that show in the notification panel.
		Notification notification = new Notification.Builder(this).setSmallIcon(getNotificationIcon()) // the
																										// status
																										// icon
				.setTicker(text) // the status text
				.setWhen(System.currentTimeMillis()) // the time stamp
				.setContentTitle(getText(R.string.local_service_label)) // the
																		// label
																		// of
																		// the
																		// entry
				.setContentText(text) // the contents of the entry
				.setContentIntent(contentIntent) // The intent to send when the
													// entry is clicked
				.build();

		if (mNM != null)
			mNM.notify(NOTIFICATION, notification);
	}

	public void removeNotification() {
		if (mNM != null)
			mNM.cancel(NOTIFICATION);
	}

	private int getNotificationIcon() {
		boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
		return useWhiteIcon ? R.drawable.ic_logo_white : R.drawable.ic_logo;
	}

	public void storeSensor(SensorDataImpl sensorData) {
		nn_VM.storeSensorAsync(sensorData);
	}

}
