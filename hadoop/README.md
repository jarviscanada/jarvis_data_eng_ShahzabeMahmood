# Introduction
Designed and implemented a scalable big data processing solution using Google Cloud Platform (GCP) Dataproc to efficiently analyze large datasets. The project integrates Apache Zeppelin for interactive data exploration and utilizes the Hive interpreter for SQL-based querying on distributed data. This solution enables real-time analytics and scalable data processing, making it ideal for enterprise-level applications.

- Managed Hadoop clusters and optimized SQL queries in Hive for improved performance.
- Developed a Hive-based data processing pipeline on a Dataproc cluster with Zeppelin as the primary interactive interface.
- Leveraged partitioning, bucketing, and ORC file format to enhance storage efficiency and query execution.

# Hadoop-Cluster
Cluster architecture diagram:


### Hardware specifications (3-node Hadoop cluster):

#### Cluster Mode: Standard (1 Master, 2 Workers)

#### Master Node:
- Machine Type: 2 vCPUs, 13GB Memory
- Primary Disk: 100GB (Standard Persistent Disk)

#### Worker Nodes (2):
- Machine Type: 2 vCPUs, 13GB Memory
- Primary Disk: 100GB (Standard Persistent Disk)

#### Resource Management:
- YARN Cores: 4
- YARN Memory: 22GB
- Image Version: 2.2.47-debian12

# Hive-Project
- Implemented partitioning and bucketing to improve query execution speed.
- Used ORC file format for efficient storage and columnar access.
- Tuned Hive queries for better performance.



# Improvements
- **Enhanced Query Performance:** Further fine-tune queries with indexing and cost-based optimization.
- **Automation:** Implement an automated ETL pipeline for data ingestion and transformation.
- **Scalability:** Explore dynamic cluster scaling for cost-effective resource allocation.
