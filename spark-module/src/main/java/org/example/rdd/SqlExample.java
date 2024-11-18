package org.example.rdd;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.rdd.entity.Person;
import org.apache.spark.api.java.function.MapFunction;

public class SqlExample {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("SqlExample")
                .master("local[*]")
                .getOrCreate();


        // Interoperating with RDDs
        JavaRDD<Person> peopleRDD = spark.read()
                .textFile("examples/src/main/resources/people.txt")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split(",");
                    Person person = new Person();
                    person.setName(parts[0]);
                    person.setAge(Integer.parseInt(parts[1].trim()));
                    return person;
                });
        // Apply a schema to an RDD of JavaBeans to get a DataFrame
        Dataset<Row> dataFrame = spark.createDataFrame(peopleRDD, Person.class);
        Encoder<String> stringEncoder = Encoders.STRING();

        // SQL statements can be run by using the sql methods provided by spark
        Dataset<Row> teenagersDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
        Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
                stringEncoder);
        // or by field name
        Dataset<String> teenagerNamesByFieldDF = teenagersDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.<String>getAs("name"),
                stringEncoder);
        teenagerNamesByFieldDF.show();


        //Programmatically Specifying the Schema


    }
}
