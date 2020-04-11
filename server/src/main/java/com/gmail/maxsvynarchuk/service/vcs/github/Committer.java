package com.gmail.maxsvynarchuk.service.vcs.github;

import java.util.Date;
import java.util.StringJoiner;

public class Committer {
    private String name;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Committer.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("date=" + date)
                .toString();
    }
}
