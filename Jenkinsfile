// YOU HAVE TO :
// 1. install the Extended Choice Parameter Plugin : https://wiki.jenkins.io/display/JENKINS/Extended+Choice+Parameter+plugin
// 2. Allow the instanciation of ExtendedChoiceParameterDefinition in you script approval admin page https://myjenkins/scriptApproval/

List params = []
List props = []

// https://github.com/jenkinsci/extended-choice-parameter-plugin/blob/master/src/main/java/com/cwctravel/hudson/plugins/extended_choice_parameter/ExtendedChoiceParameterDefinition.java#L427
// https://issues.jenkins-ci.org/browse/JENKINS-34617

com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition test = new com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition(
        "name",
        "PT_CHECKBOX",
        "VALUE, A, B",
        null,//project name
        null,
        null,
        null,
        null,// bindings
        null,
        null, // propertykey
        "VALUE, B", //default value
        null,
        null,
        null,
        null, //default bindings
        null,
        null,
        null, //descriptionPropertyValue
        null,
        null,
        null,
        null,
        null,
        null,
        null,// javascript file
        null, // javascript
        false, // save json param to file
        false, // quote
        2, // visible item count
        "DESC",
        ","
    )
params << test
props << parameters(params)

properties(props)
pipeline {
  environment {
         vari = ""
  }
  agent any
  stages {
      stage ("Example") {
        steps {
         script{
          echo 'Hello'
          echo "${params.Env}"
          echo "${params.Server}"
          if (params.Server.equals("Could not get Environment from Env Param")) {
              echo "Must be the first build after Pipeline deployment.  Aborting the build"
              currentBuild.result = 'ABORTED'
              return
          }
          echo "Crossed param validation"
        } }
      }
  }
}
