* install ubuntu 20
* install docker https://docs.docker.com/engine/install/ubuntu/ with docker-compose sudo apt  install docker-compose
* copy private key part to instance for clone repository 
``
scp -i ~/.ssh/itmo.pem ~/.ssh/gitlab_itmo  ubuntu@ec2-13-51-162-148.eu-north-1.compute.amazonaws.com:~/.ssh
``
* check gitlab connection
``
ssh -i ~/.ssh/gitlab_itmo -T git@gitlab.com
sudo apt install maven
ssh-agent bash -c 'ssh-add ~/.ssh/gitlab_itmo; git clone git@gitlab.com:itmo-124-12/cars-itmo.git'
ssh-agent bash -c 'ssh-add ~/.ssh/gitlab_itmo; git pull'
mvn package -f pom.xml
``
* прописать .env c WEBROOT