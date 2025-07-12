package com.Japp.journelApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
@Getter
@Setter
public class Root{

    public Main main;
    public Wind wind;
    public Clouds clouds;


    public int timezone;
    public int id;
    public String name;
    public int cod;
    public class Clouds{
        public int all;
    }

     class Coord{
        public double lon;
        public double lat;
    }
    @Getter
    @Setter
     public class Main{
        public double temp;
        @JsonProperty("feels_like")
        public double feelsLike;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }






    public class Wind{
        public double speed;
        public int deg;
        public double gust;
    }

}

