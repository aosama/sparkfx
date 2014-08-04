package com.its.sparkfx.context;

import org.apache.spark.api.java.JavaSparkContext;

/**
 *
 * @author aosama
 */
public class LocalSparkContext
{

    private final JavaSparkContext sparkContext;

    private LocalSparkContext()
    {
        sparkContext = new JavaSparkContext("local[20]", "SparkFX");
    }

    public JavaSparkContext getSparkContext()
    {
        return sparkContext;
    }

    public static LocalSparkContext getInstance()
    {
        return LocalSparkContextHolder.INSTANCE;
    }

    private static class LocalSparkContextHolder
    {

        private static final LocalSparkContext INSTANCE = new LocalSparkContext();
    }
}
