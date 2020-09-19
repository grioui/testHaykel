class Constants {

  static final List ServerList = ["\"Dev:selected\"", "\"Recette\"", "\"Production\""]
  static final String devServer = 'devcau01.srr.fr'
  static final String recetteServer = 'reclpo03.srr.fr'
  static final String productionServer = 'weblpo02.srr.fr'

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

class Helpers {

  static List category_list = ["\"Select:selected\"", "\"Vegetables\"", "\"Fruits\""]
  static List fruits_list = ["\"apple:selected\""]
  static List vegetables_list = ["\"potato:selected\""]

}

String ServerList = buildScript(Constants.ServerList)
String vegetables = buildScript(Helpers.vegetables_list)
String fruits = buildScript(Helpers.fruits_list)
// Methods to build groovy scripts to populate data
String buildScript(List values) {
  return "return $values"
}
properties([
parameters([
  [
    $class: 'ChoiceParameter',
    choiceType: 'PT_SINGLE_SELECT',
    name: 'ServerList',
    script: [
      $class: 'GroovyScript',
      fallbackScript: [
        script: 'return ["ERROR"]'
        ],
      script: [
        script: ServerList
      ]
    ]
  ],
[$class: 'DynamicReferenceParameter',
 choiceType: 'ET_FORMATTED_HTML',
  omitValueField: false,
   referencedParameters: 'ServerList',
     name: 'TEST2',
      randomName: 'choice-parameter-46431548642',
       script: [
         $class: 'GroovyScript',
         fallbackScript: [
           classpath: [], sandbox: true, script: 'return[\'Could not get any info\']'],
           script: [classpath: [], sandbox: false, script: '''
            if(Categories.equals('Vegetables'))
            {return "<input name=\\"value\\" value=\\"Vegetables\\" class=\\"setting-input\\" type=\\"text\\">"}
              else if(Categories.equals('Fruits')){
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
