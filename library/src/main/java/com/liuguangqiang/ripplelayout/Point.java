package com.liuguangqiang.ripplelayout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eric on 2016/11/2.
 */
public class Point implements Parcelable {

    public int x;

    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.x);
        dest.writeInt(this.y);
    }

    protected Point(Parcel in) {
        this.x = in.readInt();
        this.y = in.readInt();
    }

    public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel source) {
            return new Point(source);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };
}
