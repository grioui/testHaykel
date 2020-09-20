import java.text.SimpleDateFormat
// Constants Region
class PiplineParameters {
  static final String Project = 'testHaykel.sln'
  static final String GitLabProjectIdDefaultValue = '27'
  static final String GitLabTokenDefaultValue = 'pW-SiNxUqhEj29ES8Ghi'
  static List ProjectTypeList = ['Batch', 'Service', 'WebSite']
  static List BuildConfigurationList = ['Release', 'Debug']
  static List BuildPlateformList = ['x86', 'x64', 'Any CPU']
}
class PiplineParametersDescription {
  static final String Project = 'Nom du csproj ou du sln'
  static final String ProjectType = 'Type de projet'
  static final String BuildConfiguration = 'Configuration de la solution'
  static final String BuildPlateform = 'Plateform de la solution'
  static final String GitLabProjectId = 'Id du projet GitLab'
  static final String GitLabToken = 'Token GitLab'
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
  String BuildPlatform
  String BuildNumber
  String EnvToDeploy
  String GitLabToken
  String GitLabProjectId
  String BranchName
  String OutputPath = "/Output/bin/Release"

  BuildDetails(Project, ServerURL, Server, BuildConfiguration, BuildPlatform, BuildNumber, GitLabToken, GitLabProjectId, ProjectType, BranchName) {
    this.Project = Project
    this.ServerURL = ServerURL
    this.ArchiveDate = getArchiveDate()
    this.DeployFolder = getDeployFolder(Server, ProjectType)
    this.BuildConfiguration = BuildConfiguration
    this.BuildPlatform = BuildPlatform
    this.BuildNumber = BuildNumber
    this.EnvToDeploy = Server
    this.GitLabToken = GitLabToken
    this.GitLabProjectId = GitLabProjectId
    this.BranchName=BranchName
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
parameters([
  [
    $class: 'ChoiceParameter',
    choiceType: 'PT_SINGLE_SELECT',
    name: 'Server',
    description: 'Serveur',
    script: [
      $class: 'GroovyScript',
      fallbackScript: [
        script: PiplineScriptsParameters.ErrorScript
        ],
        script: [
          script: PiplineScriptsParameters.ServerListScript
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
        script: PiplineScriptsParameters.ErrorScript
      ],
      script: [
      sandbox: false,
      script: PiplineScriptsParameters.ScriptToDefineServerName
      ]
    ]
  ]
])])

def initializeBuildDetails() {
    return new BuildDetails(params.Project, params.ServerURL, params.Server, params.BuildConfiguration, params.BuildPlatform, currentBuild.number.toString(), params.GitLabToken, params.GitLabProjectId, params.ProjectType,env.BRANCH_NAME)
}


pipeline {
  agent any
  parameters {
    string(name: 'Project', defaultValue: PiplineParameters.Project, description: PiplineParametersDescription.Project)
    string(name: 'ProjectFileName', defaultValue: PiplineParameters.Project, description: PiplineParametersDescription.Project)
    choice(name: 'ProjectType', choices: PiplineParameters.ProjectTypeList, description: PiplineParametersDescription.ProjectType)
    choice(name: 'BuildConfiguration', choices: PiplineParameters.BuildConfigurationList, description: PiplineParametersDescription.BuildConfiguration)
    choice(name: 'BuildPlatform', choices: PiplineParameters.BuildPlateformList, description: PiplineParametersDescription.BuildPlateform)
    string(name: 'GitLabProjectId', defaultValue: PiplineParameters.GitLabProjectIdDefaultValue, description: PiplineParametersDescription.GitLabProjectId)
    string(name: 'GitLabToken', defaultValue: PiplineParameters.GitLabTokenDefaultValue, description: PiplineParametersDescription.GitLabToken)
  }
  stages {
    stage('Initialize') {
      steps 
      {
        buildDetails = initializeBuildDetails()
      }
    }
    stage('Checkout SCM') {
      steps 
      {
        checkout scm
      }
    }
    stage('Build') {
      steps {
          echo "Building ${buildDetails.Project} from ${buildDetails.BranchName}"
          bat "nuget restore \"${buildDetails.Project}\""
          bat "MsBuild.exe \"${buildDetails.Project}\" /p:Configuration=${buildDetails.BuildConfiguration} /p:OutputPath=${buildDetails.OutputPath} /p:Platform=${buildDetails.BuildPlatform}"
      }
    }
    // stage('Deploy') {
    //   steps {
    //       echo "Deploying WebSite to \\\\${Server}\\${specialCharacter}\\Websites\\${project}"
    //       bat """
    //       robocopy ${WebBuildPath} \\\\${Server}\\${specialCharacter}\\Websites\\${project} /mir /r:1 /np /nfl /ndl
    //       IF %ERRORLEVEL% LEQ 3 ( 
    //           exit 0
    //       ) ELSE (
    //           exit 1
    //       )
    //       """
    //   }
    // }
  }
}
