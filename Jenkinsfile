pipeline {

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    agent any

    tools {
        maven 'maven_3.8.7'
        
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
        }/**

        stage('SonarQube Quality Check') {
            environment {
                scannerHome = tool 'qube'
            }
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh "${scannerHome} /bin/sonar-server"
                    sh 'mvn sonar:server'
                }
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        } **/  
         
        stage('Building & Tag Docker Image') {
            steps {
                echo 'Starting Building Docker Image'
                sh 'docker build -t shubhamveer111/flipkart-ms .'
                sh 'docker build -t flipkart-ms .'
                echo 'Completed  Building Docker Image'
            }
        }
        stage('Docker Image Scanning') {
            steps {
                echo 'Docker Image Scanning Started'
                sh 'java -version'
                echo 'Docker Image Scanning Completed'
            }
        }
        stage(' Docker push to Docker Hub') {
           steps {
              script {
                 withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]){
                 sh 'docker login docker.io -u shubhamveer111 -p ${dockerhubCred}'
                 echo "Push Docker Image to DockerHub : In Progress"
                 sh 'docker push shubhamveer111/flipkart-ms:latest'
                 echo "Push Docker Image to DockerHub : Completed"
                 sh 'whoami'
                 }
              }
            }
        }
        stage(' Docker Image Push to Amazon ECR') {
           steps {
              script {
                 withDockerRegistry([credentialsId:'ecr:us-east-1:ecr-credentials', url:"https://144161705399.dkr.ecr.us-east-1.amazonaws.com"]){
                 sh """
                 echo "List the docker images present in local"
                 docker images
                 echo "Tagging the Docker Image: In Progress"
                 docker tag flipkart-ms:latest 144161705399.dkr.ecr.us-east-1.amazonaws.com/flipkart-ms:latest
                 echo "Tagging the Docker Image: Completed"
                 echo "Push Docker Image to ECR : In Progress"
                 docker push 144161705399.dkr.ecr.us-east-1.amazonaws.com/flipkart-ms:latest
                 echo "Push Docker Image to ECR : Completed"
                 """
                 }
              }
           }
        }
	stage('VPC Creation ') {
           steps {
              script {
                 sh """
		 aws configure
                 AKIASDEFVBW3W3WWMN62
		 NBs2t3triyIa7GLWLG3dKY4VsIet7PZA5mKuqn1v
                 us-east-1
		 json
                 aws cloudformation deploy --template-file amazon-eks-vpc-private-subnets.yaml --stack-name my-new-stack
                 echo "Stack is Created : Successfully"
                 """
                 }
              }
           }
    }
}
