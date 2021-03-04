package kamoncust.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class pengiriman_list implements Parcelable {

	ArrayList<pengiriman> listData;
	
	public pengiriman_list(ArrayList<pengiriman> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public pengiriman_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<pengiriman> getListData() {
		return this.listData;
	}
	
	@Override
    public int describeContents() {
        return 0;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(listData);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<pengiriman_list> CREATOR = new Creator<pengiriman_list>() {

        @Override
        public pengiriman_list createFromParcel(Parcel source) {
            return new pengiriman_list(source);
        }

        @Override
        public pengiriman_list[] newArray(int size) {
            return new pengiriman_list[size];
        }

    };

}


