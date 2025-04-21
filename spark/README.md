# London Gift Shop Retail Analytics with PySpark

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Databricks PySpark Implementation](#databricks-pyspark-implementation)
- [Key Analytics Performed](#key-analytics-performed)
- [Architecture](#architecture)
- [Future Improvements](#future-improvements)

## Project Overview
This project is a data analytics proof-of-concept (PoC) for London Gift Shop, a UK-based online retailer. The goal was to derive customer insights using PySpark on a retail transactions dataset.

The dataset includes over 1 million synthetic records representing transactions made by customers over a period of time.

You performed the full implementation using PySpark in Databricks, focusing on key metrics like customer growth, sales trends, and customer segmentation (RFM).

## Technologies Used
- PySpark (Databricks Notebooks): Used for ETL, aggregation, and analytics.
- Apache Spark Structured APIs
- Azure Blob Storage and Databricks File System (DBFS) for file storage.
- Hive Metastore for data cataloging and table persistence.

## Databricks PySpark Implementation

This end-to-end pipeline processes raw retail data and delivers actionable insights via transformations and group-level analytics in Spark.

### Data Wrangling & Cleansing:
- Renamed columns and enforced schema types.
- Created derived metrics such as `total_amount`.
- Cleaned invoice IDs and filtered invalid rows.

### Behavioral Analytics & Insights:
- Monthly Sales and Growth Trends
- Monthly Active Users
- New vs Existing User Growth
- Total Invoice Distribution
- RFM Segmentation using:
  - Recency (last transaction)
  - Frequency (number of transactions)
  - Monetary (total spend)
- Customer Segments:
  - Champions, Loyal Customers, At Risk, New Customers, and more based on RFM scoring.

## Architecture

- Storage: Dataset stored in Azure Blob Storage and accessed via DBFS.
- Compute: Databricks cluster with autoscaling enabled.
- Processing: Spark SQL & DataFrame APIs.
- Visualization: Native `display()` from Databricks notebooks.


            +--------------------------+
            |      Azure Blob Storage  |
            +--------------------------+
                       |
                       v
            +--------------------------+
            |     Databricks (PySpark) |
            |   - Data Cleansing       |
            |   - Transformations      |
            |   - Analytics            |
            +--------------------------+
                       |
                       v
            +--------------------------+
            | Hive Metastore (optional)|
            +--------------------------+


## Key Analytics Performed

| Metric                        | Description                                           |
|------------------------------|-------------------------------------------------------|
| Monthly Sales                | Total transaction revenue by month                   |
| Sales Growth                 | % change in revenue compared to the previous month   |
| Monthly Active Users         | Unique customers per month                           |
| New vs Existing Users        | First-time vs returning customers by month           |
| RFM Segmentation             | Customer lifetime value segmentation using RFM       |
| Invoice Outlier Distribution | Cleaned and visualized invoices outside 85th percentile |

## Future Improvements

- Customer Lifetime Value (CLV) Segmentation: Predict future value per customer.
- A/B Testing: Validate targeting strategies based on RFM or marketing experiments.
- Product-Level Analytics: Drill down to SKU-level trends and cross-sell opportunities.
- Deeper Segmentation: Use clustering (K-Means) or ML models to enhance behavioral segments beyond RFM.


