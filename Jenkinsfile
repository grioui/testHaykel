import java.text.SimpleDateFormat
class Constants {

  static final String Project = 'Project'

  static final String devServerName = 'Dev'
  static final String recetteServerName = 'Recette'
  static final String productionServerName = 'Production'

  static final String devBatchsFolderName = 'Batch'
  static final String recetteBatchsFolderName = 'Batchs'
  static final String productionBatchsFolderName = 'Batchs'

  static final String archiveFolderName = 'Archive'
  static final String hardDisk = 'd$'

  enum ProjectTypeEnum{
  Batch, Service, WebSite
  }
  static final String devServer = 'devcau01.srr.fr'
  static final String recetteServer = 'reclpo03.srr.fr'
  static final String productionServer = 'weblpo02.srr.fr'

  static final List BuildConfigurationList = ['Release', 'Debug']
  static final List BuildPlateformeList = ['x86', 'x64', 'Any CPU']

  static final String GitLabProjectIdDefaultValue = '27'
  static final String GitLabTokenDefaultValue = 'pW-SiNxUqhEj29ES8Ghi'
}
class ConstantsScripts {

  String buildScript(List values) {
    return "return $values"
  }

  static final List ServerList = ["\""+Constants.devServerName+"\"", "\""+Constants.recetteServerName+"\"", "\""+Constants.productionServerName+"\""]
  static String ServerListScript =  "return $ServerList"

  static final String ScriptToDefineServerName =
  '''
  def serverName = ''
  if(Server.equals("''' + Constants.devServerName + '''"))
  {
    serverName="''' + Constants.devServer + '''"
  }
  else if(Server.equals("''' + Constants.recetteServerName + '''"))
  {
    serverName="''' + Constants.recetteServer + '''"
  }else
  {
    serverName="''' + Constants.productionServer + '''"
  }
  return "<input name='value' class='setting-input' value='${serverName}' type='text'>"
  '''
}
class BuildDetails {
  String Project
  //serverName
  String ServerURL
  //serverDeployFolderName
  String BatchsFolder
  String ArchiveDate
  String FileName = "${this.Project}-${this.ArchiveDate}"
  //archiveDirectory
  String ArchiveFolder = "\\\\${this.ServerURL}\\${Constants.archiveFolderName}\\${this.FileName}"
  String BatchsDeployPath = "\\\\${this.ServerURL}\\${this.HardDisk}\\${this.BatchsFolder}\\${this.Project}"
  //specialCharacter
  String HardDisk = Constants.hardDisk
  //DEPLOY_ENV
  String BuildConfiguration
  String BuildPlatforme
  String BuildNumber
  String EnvToDeploy
  String GitLabToken
  String GitLabProjectId

  BuildDetails(Project, ServerURL,Server,BuildConfiguration,BuildPlatforme,BuildNumber,GitLabToken,GitLabProjectId) {  
        this.Project = Project
        this.ServerURL = ServerURL
        this.ArchiveDate = getArchiveDate()
        this.BatchsFolder = getBatchsFolder(Server)
        this.BuildConfiguration = BuildConfiguration
        this.BuildPlatforme = BuildPlatforme
        this.BuildNumber = BuildNumber
        this.EnvToDeploy = Server
        this.GitLabToken = GitLabToken
        this.GitLabProjectId = GitLabProjectId  
    }
    @NonCPS
    def getArchiveDate()
    {
      def date = new Date()
      def sdf = new SimpleDateFormat("yyyyMMdd")
      return sdf.format(date)
    }
    @NonCPS
    def getBatchsFolder(Server)
    {
      println Constants.devServerName
      println Server
      if(Constants.devServerName==Server)
        {return Constants.devBatchsFolderName}
      else if(Constants.recetteServerName==Server)
        {return Constants.recetteBatchsFolderName}
      return Constants.productionBatchsFolderName
    }
}

properties([
parameters([
  [
    $class: 'ChoiceParameter',
    choiceType: 'PT_SINGLE_SELECT',
    name: 'Server',
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
    referencedParameters: 'Server',
    name: 'ServerURL',
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

def initializeBuildDetails()
{
   test= new BuildDetails(params.Project, params.ServerURL,params.Server,params.BuildConfiguration,params.BuildPlatforme,currentBuild.number.toString(),params.GitLabToken,params.GitLabProjectId)
    echo test.ServerURL
    return test
}
   envbuildDetailstest= new BuildDetails(params.Project, params.ServerURL,params.Server,params.BuildConfiguration,params.BuildPlatforme,currentBuild.number.toString(),params.GitLabToken,params.GitLabProjectId)

pipeline {
  agent any
  parameters {
    string(name: 'Project', defaultValue: Constants.Project)
    choice(name: 'ProjectType', choices: Constants.ProjectTypeEnum.values()*.value, description: 'Type de projet')
    choice(name: 'BuildConfiguration', choices: Constants.BuildConfigurationList, description: 'Configuration de la solution')
    choice(name: 'BuildPlatforme', choices: Constants.BuildPlateformeList, description: 'Plateforme de la solution')
    string(name: 'GitLabProjectId', defaultValue: Constants.GitLabProjectIdDefaultValue)
    string(name: 'GitLabToken', defaultValue: Constants.GitLabTokenDefaultValue)
  }
  environment {
    envbuildDetails=initializeBuildDetails()
    ServerURL="${envbuildDetails.ServerURL}"
  }
  stages {
    stage('Build') {
      steps {
        echo 'before'
        echo envbuildDetailstest.ServerURL
        echo envbuildDetailstest.GitLabToken
        echo ServerURL
        echo 'after'
      }
    }
  }
}