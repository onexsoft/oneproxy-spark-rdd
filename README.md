# oneproxy-spark-rdd
A Spark JDBC RDD for OneProxy to Share the Data Distribution Information

In OneProxy (version 5.8.6 or later) we can run "EXPLAIN2 sql" to get the data partitions involved from OneProxy, and then build the Spark RDD partitions relatively for faster parallize process.

For xample

    mysql> explain2 select * from my_range where id = 100;
    +----------------------------------------------------+
    | SQLLIST                                            |
    +----------------------------------------------------+
    | select * from my_range_0_a my_range where id = 100 |
    | select * from my_range_0_b my_range where id = 100 |
    | select * from my_range_0_c my_range where id = 100 |
    | select * from my_range_0_d my_range where id = 100 |
    +----------------------------------------------------+
    4 rows in set (0.00 sec)

    mysql> explain2 select * from my_range where col2 = 100;
    +------------------------------------------------------+
    | SQLLIST                                              |
    +------------------------------------------------------+
    | select * from my_range_0_a my_range where col2 = 100 |
    | select * from my_range_1_a my_range where col2 = 100 |
    | select * from my_range_2_a my_range where col2 = 100 |
    | select * from my_range_3_a my_range where col2 = 100 |
    +------------------------------------------------------+
    4 rows in set (0.00 sec)
    
  The uploaded file is not verified now, it's just a craft version

#contact
flou@onexsoft.com
