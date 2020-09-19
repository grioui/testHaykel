class CIDetails {
  String Project
  //serverName
  String ServerURL
  //serverDeployFolderName
  String BatchsFolder
  String ArchiveDate
  String FileName = "${this.Project}-${this.ArchiveDate}"
  String ArchiveFolder
  String BatchsDeployPath = "\\\\${this.ServerURL}\\${this.HardDisk}\\${this.BatchsFolder}\\${this.Project}"
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

class Helpers {
}

properties([
parameters([[$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', name: 'Categories', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false, script: categories]]], [$class: 'DynamicReferenceParameter', choiceType: 'ET_TEXT_BOX', name: 'SNAPSHOT', omitValueField: true, randomName: 'choice-parameter-18754605303716994', referencedParameters: 'Categories', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false, script: items]]], [$class: 'DynamicReferenceParameter', choiceType: 'ET_FORMATTED_HTML', omitValueField: true, referencedParameters: 'Categories', description: 'Test', name: 'TEST2', randomName: 'choice-parameter-46431548642', script: [
$class: 'GroovyScript', fallbackScript: [
classpath: [], sandbox: true, script: 'return[\'Could not get any info\']'], script: [
classpath: [], sandbox: false, script: '''
            if(Categories.equals('
Vegetables ')){
                             return "<input name=\\"value\\" value=\\"Vegetables\\" class=\\"setting-input\\" type=\\"text\\">" 

     }
     else if(Categories.equals('
Fruits ')){
                                  return "<input name=\\"value\\" value=\\"Fruits\\" class=\\"setting-input\\" type=\\"text\\">" 

     }else{
                                       return "<input name=\\"value\\" value=\\"default\\" class=\\"setting-input\\" type=\\"text\\">" 

     }
                        ''']]]])])
pipeline {
  agent any
  parameters {
    choice(name: 'BuildConfiguration', choices: ['Release', 'Debug'], description: 'Configuration de la solution')
    choice(name: 'BuildPlatforme', choices: ['x86', 'x64', 'Any CPU'], description: 'Plateforme de la solution')
    string(name: 'gitLabProjectId', defaultValue: '27')
    string(name: 'GitLabToken', defaultValue: 'pW-SiNxUqhEj29ES8Ghi')
  }

  stages {
    stage('Build') {
      steps {
        echo 'before'
        echo 'after'
      }
    }
  }
}