FROM mysql

ENV MYSQL_ROOT_PASSWORD secretadmin

ADD ["sql/sources.sql", "/usr/sql/sources.sql"]

RUN /etc/init.d/mysql start && \
        mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE DATABASE lba" && \
    	mysql -u root -p${MYSQL_ROOT_PASSWORD} -D lba < /usr/sql/sources.sql && \
    	rm -rd /usr/sql && \