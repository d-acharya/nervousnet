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
/**
 * 
 */
package ch.ethz.coss.nervousnet.hub.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.ethz.coss.nervousnet.hub.R;
import ch.ethz.coss.nervousnet.hub.ui.view.AccelerometerSensorView;
import ch.ethz.coss.nervousnet.hub.ui.view.GyroscopeSensorView;
import ch.ethz.coss.nervousnet.lib.AccelerometerReading;
import ch.ethz.coss.nervousnet.lib.GyroReading;
import ch.ethz.coss.nervousnet.lib.SensorReading;

/**
 * @author prasad
 *
 */
public class GyroFragment extends BaseFragment {
	
	GyroscopeSensorView gyroView;
	public GyroFragment() {
	}

	public GyroFragment(int type) {
		super(type);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_gyro, container, false);
		gyroView = (GyroscopeSensorView)rootView.findViewById(R.id.gyroVizView);
		return rootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.ethz.coss.nervousnet.sample.BaseFragment#updateReadings(ch.ethz.coss.
	 * nervousnet.vm.SensorReading)
	 */
	@Override
	public void updateReadings(SensorReading reading) {
		Log.d("GyroFragment", "Inside updateReadings, X = " + ((GyroReading) reading).getGyroX());

		TextView x_value = (TextView) getActivity().findViewById(R.id.gyro_x);
		TextView y_value = (TextView) getActivity().findViewById(R.id.gyro_y);
		TextView z_value = (TextView) getActivity().findViewById(R.id.gyro_z);

		x_value.setText("" + ((GyroReading) reading).getGyroX());
		y_value.setText("" + ((GyroReading) reading).getGyroY());
		z_value.setText("" + ((GyroReading) reading).getGyroZ());
		
		float[] f = new float[3];
		f[0]=((GyroReading) reading).getGyroX();
		f[1]=((GyroReading) reading).getGyroY();
		f[2]=((GyroReading) reading).getGyroZ();
		gyroView.setGyroValues(f);
	}
}