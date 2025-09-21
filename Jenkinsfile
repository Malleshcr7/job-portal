pipeline {
    agent any

    tools {
        jdk 'jdk17'  // Matches your Java 17 setup
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat '.\\mvnw.cmd clean package'  // Windows-compatible, matches your successful build
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh './mvnw test'
                    } else {
                        bat '.\\mvnw.cmd test'  // Windows path for your agent
                    }
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'  // Added allowEmptyResults to handle missing reports
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true  // Matches your JAR: Lates-Jobs-0.0.1-SNAPSHOT.jar
            }
        }
    }

    post {
        success {
            bat 'chcp 65001 > nul'  // Ensure UTF-8 for console output
            echo 'Build and tests completed successfully!'
        }
        failure {
            bat 'chcp 65001 > nul'  // Ensure UTF-8 for console output
            echo 'Build failed!'
        }
    }
}
