package com.interview.assignment.data.wrappers;

import org.jetbrains.annotations.NotNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteWear {

    @PrimaryKey
    private int id;
    private int topWearIndex;
    private int bottomWearIndex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopWearIndex() {
        return topWearIndex;
    }

    public void setTopWearIndex(int topWearIndex) {
        this.topWearIndex = topWearIndex;
    }

    public int getBottomWearIndex() {
        return bottomWearIndex;
    }

    public void setBottomWearIndex(int bottomWearIndex) {
        this.bottomWearIndex = bottomWearIndex;
    }

    @NotNull
    @Override
    public String toString() {
        return "s{" +
                "id=" + id +
                ", topWearIndex=" + topWearIndex +
                ", bottomWearIndex=" + bottomWearIndex +
                '}';
    }
}
