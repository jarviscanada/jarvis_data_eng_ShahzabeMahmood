-- Connect to the host_agent database
\c host_agent

-- Create host_info table if it doesn't exist
CREATE TABLE IF NOT EXISTS PUBLIC.host_info 
(
    id               SERIAL PRIMARY KEY, 
    hostname         VARCHAR NOT NULL UNIQUE, 
    cpu_number       INT2 NOT NULL, 
    cpu_architecture VARCHAR NOT NULL, 
    cpu_model        VARCHAR NOT NULL, 
    cpu_mhz          FLOAT8 NOT NULL, 
    l2_cache         INT4 NOT NULL, 
    total_mem        INT4 NOT NULL, 
    "timestamp"      TIMESTAMP NOT NULL 
);

-- Insert sample data into host_info table
INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, "timestamp", total_mem) 
VALUES 
('jrvs-remote-desktop-centos7-6.us-central1-a.c.spry-framework-236416.internal', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300, 256, '2019-05-29 17:49:53', 601324)
ON CONFLICT (hostname) DO NO-- Switch to host_agent database
\c host_agent

-- Create host_info table if not exists
CREATE TABLE IF NOT EXISTS host_info (
    id SERIAL PRIMARY KEY,
    hostname VARCHAR NOT NULL UNIQUE,
    cpu_number INT NOT NULL,
    cpu_architecture VARCHAR NOT NULL,
    cpu_model VARCHAR NOT NULL,
    cpu_mhz FLOAT NOT NULL,
    l2_cache INT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_mem INT
);

-- Create host_usage table if not exists
CREATE TABLE IF NOT EXISTS host_usage (
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    host_id INT REFERENCES host_info(id),
    memory_free INT NOT NULL,
    cpu_idle INT NOT NULL,
    cpu_kernel INT NOT NULL,
    disk_io INT NOT NULL,
    disk_available INT NOT NULL,
    PRIMARY KEY (timestamp, host_id)
);

