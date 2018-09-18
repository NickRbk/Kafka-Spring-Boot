package com.corevalue.spark.config;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class SchemaDefinitions {
    public static final StructType rssSchema = new StructType()
            .add("url", DataTypes.StringType)
            .add("title", DataTypes.StringType)
            .add("type", DataTypes.StringType)
            .add("description", DataTypes.StringType)
            .add("publishedDate", DataTypes.LongType)
            .add("author", DataTypes.StringType);
}