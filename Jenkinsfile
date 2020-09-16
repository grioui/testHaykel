
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
                bat "nuget restore test2.sln"
                bat "\"${tool 'MsBuild 4.0 x64'}\" ${project}.csproj /p:Configuration=${params.DEPLOY_ENV} /p:OutputPath=${env.WORKSPACE}/Super/bin/${params.DEPLOY_ENV} /p:Platform=\"Any CPU\""
            }
        }
    }
}