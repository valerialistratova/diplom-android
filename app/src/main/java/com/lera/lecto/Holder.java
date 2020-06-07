package com.lera.lecto;

public class  Holder {
    public static Holder holder;

    public static Holder getInstance() {
        if (holder == null) holder = new Holder();
        return holder;
    }

    public Integer userId;
}
