class CIDetails{
  String Project
  //serverName
  String ServerURL
  //serverDeployFolderName
  String BatchsFolder
  String ArchiveDate
  String FileName ="${this.Project}-${this.ArchiveDate}"
  String ArchiveFolder
  String BatchsDeployPath="\\\\${this.ServerURL}\\${this.HardDisk}\\${this.BatchsFolder}\\${this.Project}"
  //specialCharacter
  String HardDisk
  //DEPLOY_ENV
  String BuildConfiguration
  String BuildPlatforme 
  String BuildNumber
  String EnvToDeploy
  String GitLabToken
  String GitLabProjectId
}

class Helpers
{
  def BuildParameters()
  {
      return {
    choice(name: 'BuildConfiguration', choices: ['Release', 'Debug'], description: 'Configuration de la solution')
    choice(name: 'BuildPlatforme', choices: ['x86', 'x64','Any CPU'], description: 'Plateforme de la solution')
    string(name: 'gitLabProjectId', defaultValue: '27')
    string(name: 'GitLabToken', defaultValue: 'pW-SiNxUqhEj29ES8Ghi')
  }
  }
}

pipeline {
    agent any
    parameters {
    string(name: 'DEPLOY_ENV', defaultValue: 'Release', description: 'environnement de deploiement')
    string(name: 'buildNumber', defaultValue: '-1', description: 'Numero de version de production')
    booleanParam(name: 'deployToRecette', defaultValue: false, description: 'Deployer en recette ?')
    booleanParam(name: 'deployToProd', defaultValue: false, description: 'Deployer en production ?')
    string(name: 'gitLabProjectId', defaultValue: '27')
    string(name: 'gitLabToken', defaultValue: 'pW-SiNxUqhEj29ES8Ghi')
  }
stages {
   stage('Build'){
    steps {
         echo 'before'
         echo 'after'
      }
    }
   }
}