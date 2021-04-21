package com.javarush.task.task33.task3308;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlType(name = "shop")
public class Shop {

    @XmlType(name = "goods")
    public static class Goods {
        @XmlElement
        public List<String> names = new LinkedList<>(Arrays.asList("S1", "S2"));
    }

    public Goods goods;
    public int count = 12;
    public double profit = 123.4;
    public String[] secretData = {"String1", "String2", "String3", "String4", "String5"};
}
