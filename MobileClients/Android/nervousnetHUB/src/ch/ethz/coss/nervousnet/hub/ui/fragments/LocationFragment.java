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
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.ethz.coss.nervousnet.hub.R;
import ch.ethz.coss.nervousnet.lib.LocationReading;
import ch.ethz.coss.nervousnet.lib.SensorReading;

public class LocationFragment extends BaseFragment {

	public LocationFragment() {
	}

	public LocationFragment(int type) {
		super(type);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_location, container, false);

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
		double[] location = ((LocationReading) reading).getLatnLong();
		FragmentActivity fragAct = getActivity();
		if (fragAct == null)
			System.out.println("FragmentAcvitivity is null");

		TextView latitude = (TextView) fragAct.findViewById(R.id.lat);
		latitude.setText("" + location[0]);

		TextView longitude = (TextView) getActivity().findViewById(R.id.longitude);
		longitude.setText("" + location[1]);

		TextView altitude = (TextView) getActivity().findViewById(R.id.alti);
		altitude.setText("" + ((LocationReading) reading).getAltitude());

	}

}
