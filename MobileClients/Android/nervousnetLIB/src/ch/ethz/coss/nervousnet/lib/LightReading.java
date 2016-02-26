
package ch.ethz.coss.nervousnet.lib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author prasad
 */
public class LightReading extends SensorReading {

	private float value;

	public LightReading(long timestamp, float value) {
		this.type = Constants.SENSOR_LIGHT;
		this.timestamp = timestamp;
		this.value = value;
	}

	/**
	 * @param in
	 */
	public LightReading(Parcel in) {
		readFromParcel(in);
	}

	public void readFromParcel(Parcel in) {

		timestamp = in.readInt();
		value = in.readFloat();
	}

	public float getLuxValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(timestamp);
		out.writeFloat(value);

	}

	public static final Parcelable.Creator<LightReading> CREATOR = new Parcelable.Creator<LightReading>() {
		@Override
		public LightReading createFromParcel(Parcel in) {
			return new LightReading(in);
		}

		@Override
		public LightReading[] newArray(int size) {
			return new LightReading[size];
		}
	};

}