#!/bin/bash
version="0.9.2.RELEASE"
mkdir -p bin
if [ ! -f bin/choerodon-tool-liquibase.jar ]
then
    curl https://oss.sonatype.org/content/groups/public/io/choerodon/choerodon-tool-liquibase/${version}/choerodon-tool-liquibase-${version}.jar -o ./bin/choerodon-tool-liquibase.jar
fi
java -Dspring.datasource.url="jdbc:mysql://127.0.0.1:3306/hzero_platform?useUnicode=true&characterEncoding=utf-8&useSSL=false" \
 -Dspring.datasource.username=root \
 -Dspring.datasource.password=root \
 -Ddata.drop=false -Ddata.init=true \
 -Ddata.dir=./src/main/resources \
 -jar target/choerodon-tool-liquibase.jar