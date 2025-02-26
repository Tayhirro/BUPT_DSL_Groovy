# DSL_Groovy
一个实现DSL语法的前后端网页

- 简单介绍：
- 本模型已实现通过自定义脚本语言运行客服机器人流程 
- 本网页可以部署在服务器上，或者进行本地运行 
- 本对话机器人可以调用阿里系大模型API进行对话回复
- 数据库JWT加密功能
- token认证功能
- 后端session认证，聊天记录存储功能等功能 （待完善）
- 技术栈：flask +Vue + mysql + groovy    

- docker：

  ```bash
  docker pull tayhirro/mysql:8.0
  ```

  ```
  docker pull tayhirro/work-app:latest
  ```

- docker运行：

  ```bash
  docker run -d -p 5000:5000 -p 5173:5173 --name work_app tayhirro/work-app:latest
  ```

  ```
  docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 --name my_mysql tayhirro/mysql:8.0
  ```

  

- 操作手册在操作手册.md中

- 验收报告在验收报告.pdf中



