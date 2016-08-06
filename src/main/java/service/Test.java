package service;

import static spark.Spark.get;

/**
 * Created by Administrator on 2016-08-05.
 */
public class Test {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "<p>Hello World,this<br> is<br> a<br> test<br> message<br></p>");
    }
}
