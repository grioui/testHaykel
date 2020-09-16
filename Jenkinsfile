
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & SonarQube analysis') {
            steps {
                echo "Building..."
                bat "C:\\nuget\\nuget.exe restore testHaykel.sln"
                bat "MsBuild testHaykel.sln"
            }
        }
    }
}