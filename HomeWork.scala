import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object HomeWork {
  def main(args: Array[String]): Unit = {
    // Create SparkSession
    val spark = SparkSession.builder
      .master("local")
      .appName("HomeWork")
      .getOrCreate()

val customer = spark.read.option("delimiter", "\t").csv("/user/shishkin3-is_ca-sbrf-ru/customer.csv")
.toDF("id", "name", "email", "joinDate", "status")
.drop("email")
.drop("joinDate")
.drop("status")
.as("customer")
 

val product = spark.read.option("delimiter", "\t").csv("/user/shishkin3-is_ca-sbrf-ru/product.csv")
.toDF("id", "name", "price", "numberOfProducts")
.drop("price")
.drop("numberOfProducts")
.as("product")
 

val order = spark.read.option("delimiter", "\t").csv("/user/shishkin3-is_ca-sbrf-ru/order.csv")
.toDF("customerID", "orderID", "productID", "numberOfProduct","orderDate","status")
.drop("status")
.drop("orderDate")
.drop("orderID")
.as("order")

val joined_df = order.join(customer, col("id") === col("customerID"), "left").withColumnRenamed("id","customer_id").withColumnRenamed("name","customer_name")

val all_df = joined_df.join(product, col("product.id") === col("productID"), "left").withColumnRenamed("id","product_id").withColumnRenamed("name","product_name")


val df = all_df.drop("customerID").drop("productID").drop("customer_id").drop("product_id")


val tmp_1 = df.select(df.col("*")).groupBy("customer_name", "product_name").agg(sum("numberofproduct").as("sum_numberofproduct"))

val tmp_2 = tmp_1.groupBy("customer_name").agg(max("sum_numberofproduct").as("max_numberofproduct")).withColumnRenamed("customer_name","name")


val results = tmp_2.join(tmp_1, col("sum_numberofproduct") === col("max_numberofproduct") and col("name") === col("customer_name")).orderBy("customer_name", "product_name")

val ends = results.where(col("customer_name").isNotNull)
.drop("name")
.drop("max_numberofproduct")
.drop("sum_numberofproduct")

 

val hive_csv = ends.write.option("header", "false").option("sep", "\t").mode("overwrite").csv("end.csv")

    spark.close
  }
}
