# Introduction  

This proof-of-concept (PoC) project was developed for London Gift Shop (LGS), a UK-based online retailer specializing in giftware. Facing stagnant revenue growth, LGS aims to utilize data analytics to gain deeper insights into customer behavior and enhance its marketing efforts. This project provides valuable data-driven insights to support targeted campaigns and drive sales growth.  

# Implementation  

## Project Architecture  

London Gift Shop's web application is hosted within a Microsoft Azure resource group. The front end utilizes Azure CDN for content delivery, while the back end is powered by API Management and a microservices architecture running on a scalable Azure Kubernetes Service (AKS) cluster. The transactional data is stored in a single SQL Server database designed for online transaction processing (OLTP).  

For this PoC, a sample dataset was extracted from the main database and transferred to a separate PostgreSQL database for analysis. The data analysis was conducted using Jupyter Notebooks.  

## Architecture Diagram  

![Architecture Diagram](image)  

# Data Analytics and Wrangling  

### Jupyter Notebook Analysis  

To address LGS's revenue stagnation, the analysis in this PoC focused on:  

- **Monthly Trends:** Examining customer growth, order volume, and sales trends.  
- **User Behavior Analysis:** Identifying activity patterns among customers.  
- **RFM Segmentation:** Classifying customers based on Recency, Frequency, and Monetary value to tailor marketing efforts.  

These insights enable LGS to assess the effectiveness of past marketing campaigns, understand customer segments, and refine advertising strategies for better engagement and conversion.  

# Improvements  

To further enhance this analysis, the following improvements could be made:  

1. **Customer Lifetime Value (CLV) Segmentation:** Predict long-term revenue potential from customers and refine targeting strategies.  
2. **Stronger RFM Segmentation Analysis:** Explore specific customer categories identified in the RFM segmentation to optimize engagement strategies.  

