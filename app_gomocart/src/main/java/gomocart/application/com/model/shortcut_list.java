package gomocart.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class shortcut_list implements Parcelable {

	ArrayList<shortcut> listData;
	
	public shortcut_list(ArrayList<shortcut> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public shortcut_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<shortcut> getListData() {
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
    public static Creator<shortcut_list> CREATOR = new Creator<shortcut_list>() {

        @Override
        public shortcut_list createFromParcel(Parcel source) {
            return new shortcut_list(source);
        }

        @Override
        public shortcut_list[] newArray(int size) {
            return new shortcut_list[size];
        }

    };

}


