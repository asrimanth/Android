package com.example.srimanth.cryptoconverter;

/**
 * Created by srimanth on 2/11/18.
 */

class DataClass {

    private String rank,name,currency,variation;

    void setRank(String r)
    {
        rank=r;
    }
    void setNames(String n)
    {
        name=n;
    }
    void setCurrency(String c)
    {
        currency=c;
    }
    void setVariation(String v)
    {
        variation=v;
    }


    String getRank()
    {
        return rank;
    }
    String getNames()
    {
        return name;
    }
    String getCurrency()
    {
        return currency;
    }
    String getVariation()
    {
        return variation;
    }

}
