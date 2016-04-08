package xzh.com.listviewhover.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xiangzhihong on 2016/4/7 on 17:55.
 */
public class TestModel implements Parcelable {

    protected TestModel(Parcel in) {
    }

    public static final Creator<TestModel> CREATOR = new Creator<TestModel>() {
        @Override
        public TestModel createFromParcel(Parcel in) {
            return new TestModel(in);
        }

        @Override
        public TestModel[] newArray(int size) {
            return new TestModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
