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
List category_list = ["\"Select:selected\"", "\"Vegetables\"", "\"Fruits\""]
List fruits_list = ["\"apple:selected\""]
List vegetables_list = ["\"potato:selected\""]
List default_item = ["\"Not Applicable\""]
String categories = buildScript(category_list)
String vegetables = buildScript(vegetables_list)
String fruits = buildScript(fruits_list)
// Methods to build groovy scripts to populate data
String buildScript(List values) {
  return "return $values"
}
properties([
parameters([
      choice(name: 'BuildConfiguration', choices: ['Release', 'Debug'], description: 'Configuration de la solution2'),
    choice(name: 'BuildPlatforme', choices: ['x86', 'x64', 'Any CPU'], description: 'Plateforme de la solution'),
    string(name: 'gitLabProjectId', defaultValue: '27'),
    string(name: 'GitLabToken', defaultValue: 'pW-SiNxUqhEj29ES8Ghi'),
  [$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', name: 'Categories', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false, script: categories]]], 
[$class: 'DynamicReferenceParameter',
 choiceType: 'ET_FORMATTED_HTML',
  omitValueField: false,
   referencedParameters: 'Categories',
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
  

  stages {
    stage('Build') {
      steps {
        echo 'before'
        echo 'after'
      }
    }
  }
}