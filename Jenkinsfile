pipeline {
    agent any

    tools {
        jdk 'jdk17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat '.\\mvnw.cmd clean package'
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh './mvnw test'
                    } else {
                        bat '.\\mvnw.cmd test'
                    }
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // It's a good practice to use a variable for the image name
                    def imageName = "your-dockerhub-username/lates-jobs:latest"

                    // Build the Docker image
                    bat "docker build -t ${imageName} ."

                    // If you want to push to a Docker registry, uncomment the following lines
                    // Make sure you have configured your Docker credentials in Jenkins
                    /*
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        bat "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                        bat "docker push ${imageName}"
                    }
                    */
                }
            }
        }
    }

    post {
        success {
            echo 'Build, tests, and Docker image build completed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}