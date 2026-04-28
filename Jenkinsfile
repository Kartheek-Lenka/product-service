pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        ACCOUNT_ID = '271443695313'
        ECR_REPO = "${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/product-service"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                sh '''
                docker run --rm \
                  -v "$PWD":/app \
                  -w /app \
                  maven:3.9.9-eclipse-temurin-17 \
                  mvn clean package -DskipTests
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t product-service:latest .'
            }
        }

        stage('Login to ECR') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-creds'
                ]]) {
                    sh '''
                    aws ecr get-login-password --region $AWS_REGION \
                    | docker login --username AWS --password-stdin $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                    '''
                }
            }
        }

        stage('Tag Image') {
            steps {
                sh 'docker tag product-service:latest $ECR_REPO:latest'
            }
        }

        stage('Push to ECR') {
            steps {
                sh 'docker push $ECR_REPO:latest'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/'
            }
        }

        stage('Verify') {
            steps {
                sh 'kubectl get pods'
                sh 'kubectl get svc'
            }
        }
    }
}