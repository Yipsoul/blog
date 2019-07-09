### 一.个人博客功能思维导图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190709232914679.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
### 二.技术栈
-	前端 ： JQuery + Semantic UI 框架
-	后端 ： SpringBoot + SpringDataJpa + MySQL + Redis 

### 三.开发工具与环境
-	Maven 3以上
-	JDK8
-	IDEA
-	Redis 
-	MySQL

### 四.功能简介

---
### 五.博客项目导入IDEA本地运行使用
#### 1. 导入项目
1. 到GitHub上下载项目 [个人博客下载](https://github.com/Yipsoul/blog)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190709232737106.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)

2. 解压然后导入IDEA工具

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190709233108824.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)

3. 选择好解压的文件夹，Maven导入，一直next就完事了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190709233227708.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190709233336211.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190709233347721.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019070923343721.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
#### 2.数据库连接配置
1. 在本地MySQL创建数据库
2. 在代码里修改数据库与Redis配置
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019070923531362.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
#### 3.直接启动项目，会自动生成数据库表。
1. 对tb_user表插入一条管理员数据

```mysql
INSERT INTO `tb_user` VALUES ('2', 'https://unsplash.it/id/628/100/100', '2019-07-02 10:00:36', 'root@qq.com', 'root', '63a9f0ea7bb98050796b649e85481845', '1', '2019-07-02 10:00:52', 'root');
```
2. 项目启动成功后直接访问localhost为博客展示首页，localhost/admin为博客后台管理页面，用户名 ： root,密码 ： root。
