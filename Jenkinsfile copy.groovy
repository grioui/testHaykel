import java.text.SimpleDateFormat
class Constants {

  static final String Project = 'Project'

  static final String devServerName = 'Dev'
  static final String recetteServerName = 'Recette'
  static final String productionServerName = 'Production'
  static final String devServer = 'devcau01.srr.fr'
  static final String recetteServer = 'reclpo03.srr.fr'
  static final String productionServer = 'weblpo02.srr.fr'

  static final String devBatchsFolderName = 'Batch'
  static final String recetteBatchsFolderName = 'Batchs'
  static final String productionBatchsFolderName = 'Batchs'

  static final String devWebsitesFolderName = 'Websites'
  static final String recetteWebsitesFolderName = 'Websites'
  static final String productionWebsitesFolderName = 'Websites'

  static final String devServicesFolderName = 'services'
  static final String recetteServicesFolderName = 'Services'
  static final String productionServicesFolderName = 'Services'

  static final String archiveFolderName = 'Archive'
  static final String hardDisk = 'd$'

  static List BuildConfigurationList = ['Release', 'Debug']
  //static List ProjectTypeList = ['Batch', 'Service', 'WebSite']
  enum ProjectTypeEnum{
  Batch, Service, WebSite
  }
  static List BuildPlateformeList = ['x86', 'x64', 'Any CPU']

  static final String GitLabProjectIdDefaultValue = '27'
  static final String GitLabTokenDefaultValue = 'pW-SiNxUqhEj29ES8Ghi'
}
class ConstantsScripts {

  static List ServerList = ["\""+Constants.devServerName+"\"", "\""+Constants.recetteServerName+"\"", "\""+Constants.productionServerName+"\""]
  static final String ServerListScript =  "return $ServerList"
  static final String ErrorScript = 'return ["ERROR"]'
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

  BuildDetails(Project, ServerURL,Server,BuildConfiguration,BuildPlatforme,BuildNumber,GitLabToken,GitLabProjectId,ProjectType) {  
        this.Project = Project
        this.ServerURL = ServerURL
        this.ArchiveDate = getArchiveDate()
        this.BatchsFolder = getBatchsFolder(Server,ProjectType)
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
    def getBatchsFolder(Server,ProjectType)
    {
      if(Constants.devServerName==Server)
      {
        if(ProjectTypeEnum.Batch==ProjectType)
        {
          return Constants.devBatchsFolderName
        }
      }
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
        script: ConstantsScripts.ErrorScript
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
        script: ConstantsScripts.ErrorScript,
        script: [
          classpath: [],
          sandbox: false,
          script: ConstantsScripts.ScriptToDefineServerName
          ]
      ]]])])

def initializeBuildDetails()
{
   return new BuildDetails(params.Project, params.ServerURL,params.Server,params.BuildConfiguration,params.BuildPlatforme,currentBuild.number.toString(),params.GitLabToken,params.GitLabProjectId)
}

buildDetails= initializeBuildDetails()
pipeline {
  agent any
  parameters {
    string(name: 'Project', defaultValue: Constants.Project, description: 'Nom du projet ou de la solution')
    choice(name: 'ProjectType', choices: Constants.ProjectTypeEnum.values(), description: 'Type de projet')
    choice(name: 'BuildConfiguration', choices: Constants.BuildConfigurationList, description: 'Configuration de la solution')
    choice(name: 'BuildPlatforme', choices: Constants.BuildPlateformeList, description: 'Plateforme de la solution')
    string(name: 'GitLabProjectId', defaultValue: Constants.GitLabProjectIdDefaultValue, description: 'Id du projet gitlab')
    string(name: 'GitLabToken', defaultValue: Constants.GitLabTokenDefaultValue, description: 'Token gitlab')
  }
  stages {
    stage('Build') {
      steps {
        echo 'before'
        echo buildDetails.ServerURL
        echo buildDetails.GitLabToken
        echo ServerURL
        echo 'after'
      }
    }
  }
}
