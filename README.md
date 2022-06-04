跟着b站码神之路做的博客  
技术栈：springboot、mybatisPlus、mysql、maven、vue、spring security、jwt、ThreadLocal、redis  

#docker方式部署步骤：

##1.安装、启动docker

yum update

yum install -y yum-utils device-mapper-persistent-data lvm2

yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

yum install -y docker-ce

docker -v

/bin/systemctl start docker.service

##2.拉取镜像

docker pull nginx

docker pull redis:5.0.3

docker pull java:8

docker pull mysql:5.7

##3.启动mysql容器

mkdir /mnt/docker/mysql

cd /mnt/docker/mysql

mkdir conf

mkdir data

mkdir logs

cd /mnt/docker/mysql/conf

vim my.cnf（见my.cnf文件）

docker run -id \
  -p 3307:3306 \
  --name=c_mysql \
  -v /mnt/docker/mysql/conf:/etc/mysql/conf.d \
  -v /mnt/docker/mysql/logs:/logs \
  -v /mnt/docker/mysql/data:/var/lib/mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  mysql:5.7
  
docker inspect c_mysql 查看mysql ip

##4.启动redis容器

docker run -id --name=redis -p 6379:6379 redis5.0.3

docker inspect redis 查看redis ip

##5.应用容器化

（1）maven package打包 springboot.jar

（2）编写dockerfile，生成镜像（见dockerfile文件）

docker build -f dockerfile(文件路径) -t 镜像名称:版本 .

##6.docker compose服务编排

（1）安装docker-compose

curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose

chmod +x /usr/local/bin/docker-compose

docker-compose -version

（2）docker-compose.yml

mkdir /mnt/docker/docker-compose

cd /mnt/docker/docker-compose

编写docker-compose.yml（见docker-compose.yml文件）

（3）nginx配置

mkdir /mnt/docker/docker-compose/nginx/

cd /mnt/docker/docker-compose/nginx/

（见nginx.conf、mime.types文件）

############################
（4）docker-compose启动

docker-compose up

docker-compose up -d #后台启动

