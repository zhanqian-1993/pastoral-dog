package org.example.rdd;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.storage.StorageLevel;

import java.util.Arrays;
import java.util.List;

public class RddExample {

    private static final String appName = "RddExample";
    private static final String master = "local[*]";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);

        // cache
//        distData.cache();
        distData.persist(StorageLevel.MEMORY_ONLY());
        distData.unpersist();

        // broadcast variable
        Broadcast<int[]> broadcast = sc.broadcast(new int[] {1, 2, 3});
        // broadcast variable get value
        broadcast.value(); // return [1, 2, 3]

        //Accumulators

        //custom accumulator
    }

}
