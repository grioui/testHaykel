class Constants {

  static final String devServerName = 'Dev'
  static final String recetteServerName = 'Recette'
  static final String productionServerName = 'Production'

  static final String devServer = 'devcau01.srr.fr'
  static final String recetteServer = 'reclpo03.srr.fr'
  static final String productionServer = 'weblpo02.srr.fr'

}
class ConstantsScripts {

  String buildScript(List values) {
    return "return $values"
  }

  static final List ServerList = [Constants.devServerName, Constants.recetteServerName, Constants.productionServerName]
  static String ServerListScript =  "return $ServerList"

  static final String ScriptToDefineServerName =
  '''
  def serverName = ''
  if(ServerList.equals("''' + Constants.devServerName + '''"))
  {
    serverName="''' + Constants.devServer + '''"
  }
  else if(ServerList.equals("''' + Constants.recetteServerName + '''"))
  {
    serverName="''' + Constants.recetteServer + '''"
  }else
  {
    serverName="''' + Constants.productionServer + '''"
  }
  return "<input name='value' class='setting-input' value='${serverName}' type='text'>"
  '''
}
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

properties([
parameters([
  [
    $class: 'ChoiceParameter',
    choiceType: 'PT_SINGLE_SELECT',
    name: 'ServerList',
    description:'Serveur',
    script: [
      $class: 'GroovyScript',
      fallbackScript: [
        script: 'return ["ERROR"]'
        ],
      script: [
        script: ConstantsScripts.ServerListScript
      ]
    ]
  ],
  [
    $class: 'DynamicReferenceParameter',
    choiceType: 'ET_FORMATTED_HTML',
    omitValueField: false,
    referencedParameters: 'ServerList',
    name: 'TEST2',
    script: [
      $class: 'GroovyScript',
      fallbackScript: [
        classpath: [],
        sandbox: true,
        script: 'return[\'Could not get any info\']'],
        script: [
          classpath: [],
           sandbox: false,
            script: ConstantsScripts.ScriptToDefineServerName]]]])])
pipeline {
  agent any
  parameters {
    choice(name: 'BuildConfiguration', choices: ['Release', 'Debug'], description: 'Configuration de la solution')
    choice(name: 'BuildPlatforme', choices: ['x86', 'x64', 'Any CPU'], description: 'Plateforme de la solution')
    string(name: 'gitLabProjectId', defaultValue: '27')
    string(name: 'GitLabToken', defaultValue: 'pW-SiNxUqhEj29ES8Ghi')
  }
  environment {
    serverDetails = getServerDetails()
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
