pipeline {

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    agent any

    tools {
        maven 'maven_3.8.6'
    }
    stages {
        stage('Code Compilation') {
            steps {
                echo 'code compilation is starting'
                sh 'mvn clean compile'
				echo 'code compilation is completed'
            }
        }
        stage('Code Package') {
            steps {
                echo 'code packing is starting'
                sh 'mvn clean package'
				echo 'code packing is completed'
            }
        }
        stage('Code Quality Check') {
            steps {
                echo 'code packing is starting'
                sh 'java --version'
				echo 'code packing is completed'
            }
        }
        stage('Building & Tag Docker Image') {
            steps {
                echo 'Starting Building Docker Image'
                sh 'docker build -t satyam88/flipkart-ms .'
                sh 'docker build -t flipkart-ms .'
                echo 'Completed  Building Docker Image'
            }
        }
        stage('Docker Image Scanning') {
            steps {
                echo 'Docker Image Scanning Started'
                sh 'java -version'
                echo 'Docker Image Scanning Started'
            }
        }
        stage(' Docker push to Docker Hub') {
           steps {
              script {
                 withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]){
                 sh 'docker login docker.io -u satyam88 -p ${dockerhubCred}'
                 echo "Push Docker Image to DockerHub : In Progress"
                 sh 'docker push satyam88/flipkart-ms:latest'
                 echo "Push Docker Image to DockerHub : In Progress"
                 sh 'whoami'
                 }
              }
            }
        }
        stage(' Docker Image Push to Amazon ECR') {
           steps {
              script {
                 withDockerRegistry([credentialsId:'ecr:ap-south-1:ecr-credentials', url:"https://559220132560.dkr.ecr.ap-south-1.amazonaws.com"]){
                 sh """
                 echo "List the docker images present in local"
                 docker images
                 echo "Tagging the Docker Image: In Progress"
                 docker tag flipkart-ms:latest 559220132560.dkr.ecr.ap-south-1.amazonaws.com/flipkart-ms:latest
                 echo "Tagging the Docker Image: Completed"
                 echo "Push Docker Image to ECR : In Progress"
                 docker push 559220132560.dkr.ecr.ap-south-1.amazonaws.com/flipkart-ms:latest
                 echo "Push Docker Image to ECR : Completed"
                 """
                 }
              }
           }
        }
        stage('Upload the docker Image to Nexus') {
           steps {
              script {
                 withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
                 sh 'docker login http://13.127.62.197:8085/repository/flipkart-ms/ -u admin -p ${PASSWORD}'
                 echo "Push Docker Image to Nexus : In Progress"
                 sh 'docker tag flipkart-ms 13.127.62.197:8085/flipkart-ms:latest'
                 sh 'docker push 13.127.62.197:8085/flipkart-ms'
                 echo "Push Docker Image to Nexus : Completed"
                 }
              }
            }
        }
        stage('Delete Docker Images from Jenkins ') {
            steps {
                echo 'Docker Image Scanning Started'
                sh 'docker rmi flipkart-ms:latest'
                sh 'sleep 2'
                sh 'docker rmi 13.127.62.197:8085/flipkart-ms:latest'
                sh 'sleep 2'
                sh 'docker rmi satyam88/flipkart-ms:latest'
                sh 'sleep 2'
                sh 'docker rmi 559220132560.dkr.ecr.ap-south-1.amazonaws.com/flipkart-ms:latest'
                sh 'sleep 2'
                echo 'Docker Image Scanning Started'
                echo 'docker images on linux'
                sh 'docker images'
            }
        }
        stage('Deploy into Kubernetes') {
            steps {
                echo 'Docker Image Scanning Started'
                sh 'java -version'
                echo 'Docker Image Scanning Started'
            }
        }
    }
}