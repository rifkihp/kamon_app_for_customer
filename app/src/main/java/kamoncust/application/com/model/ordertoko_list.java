package kamoncust.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ordertoko_list implements Parcelable {

	ArrayList<ordertoko> listData;
	
	public ordertoko_list(ArrayList<ordertoko> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public ordertoko_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<ordertoko> getListData() {
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
    public static Creator<ordertoko_list> CREATOR = new Creator<ordertoko_list>() {

        @Override
        public ordertoko_list createFromParcel(Parcel source) {
            return new ordertoko_list(source);
        }

        @Override
        public ordertoko_list[] newArray(int size) {
            return new ordertoko_list[size];
        }

    };

}


