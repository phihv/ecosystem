CREATE user 'kafka'@'%' identified with mysql_native_password by '12345';
GRANT ALL PRIVILEGES ON *.* TO 'kafka'@'%';
