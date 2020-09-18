#!/usr/bin/env groovy
// Define variables
List category_list = ["\"Select:selected\"","\"Vegetables\"","\"Fruits\""]
List fruits_list = ["\"apple:selected\""]
List vegetables_list = ["\"potato:selected\""]
List default_item = ["\"Not Applicable\""]
String categories = buildScript(category_list)
String vegetables = buildScript(vegetables_list)
String fruits = buildScript(fruits_list)
String items = populateItems(default_item,vegetables_list,fruits_list)
// Methods to build groovy scripts to populate data
String buildScript(List values){
  return "return $values"
}
String populateItems(List default_item, List vegetablesList, List fruitsList){
return """if(Categories.equals('Vegetables')){
     return "Vegetables"
     }
     else if(Categories.equals('Fruits')){

     return "Fruits"
     }else{
     return "default"
     }
     """
}
// Properties step to set the Active choice parameters via 
// Declarative Scripting
properties([
    parameters([
        [$class: 'ChoiceParameter',
		choiceType: 'PT_SINGLE_SELECT', 
		name: 'Categories',
		script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false,script:  categories]]],
[$class: 'DynamicReferenceParameter',
 choiceType: 'ET_TEXT_BOX',
 name: 'SNAPSHOT',
 omitValueField: true,
 randomName: 'choice-parameter-18754605303716994',
 referencedParameters: 'Categories',
		script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false,script:  items]]]
 
    ])
])
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
       echo 'Building..'
      }
    }
   }
}
