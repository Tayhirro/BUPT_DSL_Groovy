# DSL_Groovy
一个实现DSL语法的前后端网页


#docker:
docker pull tayhirro/mysql:8.0
docker pull tayhirro/work-app:latest

#docker运行：
docker run -d -p 5000:5000 -p 5173:5173 --name work_app tayhirro/work-app:latest
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 --name my_mysql tayhirro/mysql:8.0

