import java.text.SimpleDateFormat
class Constants {
  static final String Project = 'Project'
  static final List ProjectTypeList = ['Batch', 'Service', 'WebSite']
  static final List BuildConfigurationList = ['Release', 'Debug']
  static final List BuildPlateformeList = ['x86', 'x64', 'Any CPU']
  static final String GitLabProjectIdDefaultValue = '27'
  static final String GitLabTokenDefaultValue = 'pW-SiNxUqhEj29ES8Ghi'
}
class ConstantsServer {
  static final String devServerName = 'Dev'
  static final String recetteServerName = 'Recette'
  static final String productionServerName = 'Production'
  static final String devServer = 'devcau01.srr.fr'
  static final String recetteServer = 'reclpo03.srr.fr'
  static final String productionServer = 'weblpo02.srr.fr'
}
class ConstantsFolders {
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
class ConstantsScripts {

  String buildScript(List values) {
    return "return $values"
  }

  static final List ServerList = ["\"" + ConstantsServer.devServerName + "\"", "\"" + ConstantsServer.recetteServerName + "\"", "\"" + ConstantsServer.productionServerName + "\""]
  static String ServerListScript = "return $ServerList"

  static final String ScriptToDefineServerName = '''
  def serverName = ''
  if(Server.equals("''' + ConstantsServer.devServerName + '''"))
  {
    serverName="''' + ConstantsServer.devServer + '''"
  }
  else if(Server.equals("''' + ConstantsServer.recetteServerName + '''"))
  {
    serverName="''' + ConstantsServer.recetteServer + '''"
  }else
  {
    serverName="''' + ConstantsServer.productionServer + '''"
  }
  return "<input name='value' class='setting-input' value='${serverName}' type='text'>"
  '''
}
class BuildDetails {
  String Project
  //serverName
  String ServerURL
  //serverDeployFolderName
  String DeployFolder
  String ArchiveDate
  String FileName = "${this.Project}-${this.ArchiveDate}"
  //archiveDirectory
  String ArchiveFolder = "\\\\${this.ServerURL}\\${ConstantsFolders.archiveFolderName}\\${this.FileName}"
  String BatchsDeployPath = "\\\\${this.ServerURL}\\${this.HardDisk}\\${this.DeployFolder}\\${this.Project}"
  //specialCharacter
  String HardDisk = ConstantsFolders.hardDisk
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
    if (ConstantsServer.devServerName == Server) {
      if ('Batch' == ProjectType) {
        return ConstantsFolders.devBatchsFolderName
      }
      if ('Service' == ProjectType) {
        return ConstantsFolders.devServicesFolderName
      }
      return ConstantsFolders.devWebsitesFolderName
    }
    else if (ConstantsServer.recetteServerName == Server) {
      if ('Batch' == ProjectType) {
        return ConstantsFolders.recetteBatchsFolderName
      }
      if ('Service' == ProjectType) {
        return ConstantsFolders.recetteServicesFolderName
      }
      return ConstantsFolders.recetteWebsitesFolderName
    }
      if ('Batch' == ProjectType) {
        return ConstantsFolders.productionBatchsFolderName
      }
      if ('Service' == ProjectType) {
        return ConstantsFolders.productionServicesFolderName
      }
    return ConstantsFolders.productionWebsitesFolderName
  }
}

properties([
parameters([[
$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', name: 'Server', description: 'Serveur', script: [
$class: 'GroovyScript', fallbackScript: [
script: 'return ["ERROR"]'], script: [
script: ConstantsScripts.ServerListScript]]], [
$class: 'DynamicReferenceParameter', choiceType: 'ET_FORMATTED_HTML', omitValueField: false, referencedParameters: 'Server', name: 'ServerURL', script: [
$class: 'GroovyScript', fallbackScript: [
classpath: [], sandbox: true, script: 'return[\'Could not get any info\']'], script: [
classpath: [], sandbox: false, script: ConstantsScripts.ScriptToDefineServerName]]]])])

def initializeBuildDetails() {
  return new BuildDetails(params.Project, params.ServerURL, params.Server, params.BuildConfiguration, params.BuildPlatforme, currentBuild.number.toString(), params.GitLabToken, params.GitLabProjectId, params.ProjectType)
}
envbuildDetailstest = initializeBuildDetails()
pipeline {
  agent any
  parameters {
    string(name: 'Project', defaultValue: Constants.Project)
    choice(name: 'ProjectType', choices: Constants.ProjectTypeList, description: 'Type de projet')
    choice(name: 'BuildConfiguration', choices: Constants.BuildConfigurationList, description: 'Configuration de la solution')
    choice(name: 'BuildPlatforme', choices: Constants.BuildPlateformeList, description: 'Plateforme de la solution')
    string(name: 'GitLabProjectId', defaultValue: Constants.GitLabProjectIdDefaultValue)
    string(name: 'GitLabToken', defaultValue: Constants.GitLabTokenDefaultValue)
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