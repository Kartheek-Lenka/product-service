pipeline {
    agent any

    stages {

        stage('Build JAR') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-17'
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t product-service:latest .'
            }
        }

        stage('List Images') {
            steps {
                sh 'docker images'
            }
        }
    }
}