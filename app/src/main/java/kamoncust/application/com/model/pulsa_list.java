package kamoncust.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class pulsa_list implements Parcelable {

	ArrayList<pulsa> listData;
	
	public pulsa_list(ArrayList<pulsa> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public pulsa_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<pulsa> getListData() {
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
    public static Creator<pulsa_list> CREATOR = new Creator<pulsa_list>() {

        @Override
        public pulsa_list createFromParcel(Parcel source) {
            return new pulsa_list(source);
        }

        @Override
        public pulsa_list[] newArray(int size) {
            return new pulsa_list[size];
        }

    };

}


