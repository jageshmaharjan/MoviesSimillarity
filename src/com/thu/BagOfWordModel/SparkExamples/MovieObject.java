package com.thu.BagOfWordModel.SparkExamples;

import java.io.Serializable;

/**
 * Created by jugs on 3/3/17.
 */
public class MovieObject implements Serializable
{
    private String title;
    private String review;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getReview()
    {
        return review;
    }

    public void setReview(String review)
    {
        this.review = review;
    }
}
