package com.geoinformatica.wilsoncajisaca.diinlab.Common;

import android.os.Parcel;
import android.os.Parcelable;

public class contactosChips implements Parcelable {

    String name;
    String phone;
    String id;
    boolean selected;

    public contactosChips() {
    }

    public contactosChips(String name, String id, boolean selected,String phone) {
        this.name = name;
        this.id = id;
        this.selected = selected;
        this.phone=phone;
    }

    protected contactosChips(Parcel in) {

        this.name = in.readString();
        this.phone=in.readString();
        this.id = in.readString();
        this.selected = in.readByte() != 0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static final Parcelable.Creator<contactosChips> CREATOR = new Parcelable.Creator<contactosChips>() {
        @Override
        public contactosChips createFromParcel(Parcel in) {
            return new contactosChips(in);
        }

        @Override
        public contactosChips[] newArray(int size) {
            return new contactosChips[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeByte(selected ? (byte) 1 : (byte) 0);

    }
}
