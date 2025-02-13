FROM registry.cn-hongkong.aliyuncs.com/hzqingrui/base_jdk:2.0.0

ADD sifubuy-wms.jar /home/admin/wms/wms.jar
ADD application.yml /home/admin/wms/application.yml

ENTRYPOINT java -Dproject.name=wms $JAVA_OPTIONS -Dspring.config.location=/home/admin/wms/application.yml -jar /home/admin/wms/wms.jar
