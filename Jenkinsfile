pipeline {
    agent any

    tools {
        jdk 'jdk17'  // Matches your Java 17 setup, ensure this is configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat '.\\mvnw.cmd clean package'  // Windows: Use bat and mvnw.cmd
            }
        }

        stage('Test') {
            steps {
                bat '.\\mvnw.cmd test'  // Changed from sh to bat
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Build and tests completed successfully!'
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}
