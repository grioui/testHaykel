import java.text.SimpleDateFormat
// Constants Region
class PiplineParameters {
  static final String Project = 'Project'
  static final List ProjectTypeList = ['Batch', 'Service', 'WebSite']
  static final List BuildConfigurationList = ['Release', 'Debug']
  static final List BuildPlateformeList = ['x86', 'x64', 'Any CPU']
  static final String GitLabProjectIdDefaultValue = '27'
  static final String GitLabTokenDefaultValue = 'pW-SiNxUqhEj29ES8Ghi'
}
class PiplineScriptsParameters {
  static List ServerList = ["\"" + ServerDetails.devServerName + "\"", "\"" + ServerDetails.recetteServerName + "\"", "\"" + ServerDetails.productionServerName + "\""]
  static String ServerListScript = "return $ServerList"
  static String ErrorScript = 'return ["ERROR"]'
  static final String ScriptToDefineServerName = '''
  def serverName = ''
  if(Server.equals("''' + ServerDetails.devServerName + '''"))
  {
    serverName="''' + ServerDetails.devServer + '''"
  }
  else if(Server.equals("''' + ServerDetails.recetteServerName + '''"))
  {
    serverName="''' + ServerDetails.recetteServer + '''"
  }else
  {
    serverName="''' + ServerDetails.productionServer + '''"
  }
  return "<input name='value' class='setting-input' value='${serverName}' type='text'>"
  '''
}
class ServerDetails {
  static final String devServerName = 'Dev'
  static final String recetteServerName = 'Recette'
  static final String productionServerName = 'Production'
  static final String devServer = 'devcau01.srr.fr'
  static final String recetteServer = 'reclpo03.srr.fr'
  static final String productionServer = 'weblpo02.srr.fr'
}
class FolderNames {
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
}
// End Constants Region

class BuildDetails {
  String Project
  //serverName
  String ServerURL
  //serverDeployFolderName
  String DeployFolder
  String ArchiveDate
  String FileName = "${this.Project}-${this.ArchiveDate}"
  //archiveDirectory
  String ArchiveFolder = "\\\\${this.ServerURL}\\${FolderNames.archiveFolderName}\\${this.FileName}"
  String BatchsDeployPath = "\\\\${this.ServerURL}\\${this.HardDisk}\\${this.DeployFolder}\\${this.Project}"
  //specialCharacter
  String HardDisk = FolderNames.hardDisk
  //DEPLOY_ENV
  String BuildConfiguration
  String BuildPlatforme
  String BuildNumber
  String EnvToDeploy
  String GitLabToken
  String GitLabProjectId

  BuildDetails(Project, ServerURL, Server, BuildConfiguration, BuildPlatforme, BuildNumber, GitLabToken, GitLabProjectId, ProjectType) {
    this.Project = Project
    this.ServerURL = ServerURL
    this.ArchiveDate = getArchiveDate()
    this.DeployFolder = getDeployFolder(Server, ProjectType)
    this.BuildConfiguration = BuildConfiguration
    this.BuildPlatforme = BuildPlatforme
    this.BuildNumber = BuildNumber
    this.EnvToDeploy = Server
    this.GitLabToken = GitLabToken
    this.GitLabProjectId = GitLabProjectId
  }
  @NonCPS
  def getArchiveDate() {
    def date = new Date()
    def sdf = new SimpleDateFormat("yyyyMMdd")
    return sdf.format(date)
  }
  @NonCPS
  def getDeployFolder(Server, ProjectType) {
    if (ServerDetails.devServerName == Server) {
      if ('Batch' == ProjectType) {
        return FolderNames.devBatchsFolderName
      }
      if ('Service' == ProjectType) {
        return FolderNames.devServicesFolderName
      }
      return FolderNames.devWebsitesFolderName
    }
    else if (ServerDetails.recetteServerName == Server) {
      if ('Batch' == ProjectType) {
        return FolderNames.recetteBatchsFolderName
      }
      if ('Service' == ProjectType) {
        return FolderNames.recetteServicesFolderName
      }
      return FolderNames.recetteWebsitesFolderName
    }
      if ('Batch' == ProjectType) {
        return FolderNames.productionBatchsFolderName
      }
      if ('Service' == ProjectType) {
        return FolderNames.productionServicesFolderName
      }
    return FolderNames.productionWebsitesFolderName
  }
}

properties([
parameters([[
$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', name: 'Server', description: 'Serveur', script: [
$class: 'GroovyScript', fallbackScript: [
script: PiplineScriptsParameters.ErrorScript], script: [
script: PiplineScriptsParameters.ServerListScript]]], [
$class: 'DynamicReferenceParameter', choiceType: 'ET_FORMATTED_HTML', omitValueField: false, referencedParameters: 'Server', name: 'ServerURL', script: [
$class: 'GroovyScript', fallbackScript: [
classpath: [], sandbox: true, script: PiplineScriptsParameters.ErrorScript], script: [
classpath: [], sandbox: false, script: PiplineScriptsParameters.ScriptToDefineServerName]]]])])

def initializeBuildDetails() {
  return new BuildDetails(params.Project, params.ServerURL, params.Server, params.BuildConfiguration, params.BuildPlatforme, currentBuild.number.toString(), params.GitLabToken, params.GitLabProjectId, params.ProjectType)
}
envbuildDetailstest = initializeBuildDetails()
pipeline {
  agent any
  parameters {
    string(name: 'Project', defaultValue: PiplineParameters.Project)
    choice(name: 'ProjectType', choices: PiplineParameters.ProjectTypeList, description: 'Type de projet')
    choice(name: 'BuildConfiguration', choices: PiplineParameters.BuildConfigurationList, description: 'Configuration de la solution')
    choice(name: 'BuildPlatforme', choices: PiplineParameters.BuildPlateformeList, description: 'Plateforme de la solution')
    string(name: 'GitLabProjectId', defaultValue: PiplineParameters.GitLabProjectIdDefaultValue)
    string(name: 'GitLabToken', defaultValue: PiplineParameters.GitLabTokenDefaultValue)
  }
  stages {
    stage('Build') {
      steps {
        echo 'before'
        echo envbuildDetailstest.DeployFolder
        echo envbuildDetailstest.GitLabToken
        echo 'after'
      }
    }
  }
}