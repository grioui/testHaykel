properties(
    [
            [
                    $class              : 'ParametersDefinitionProperty',
                    parameterDefinitions: [
                            [
                                    $class     : 'ChoiceParameterDefinition',
                                    choices    : 'aaa\nbbb',
                                    description: 'select your choice : ',
                                    name       : 'choice1'
                            ]

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
          echo "Crossed param validation"
        } }
      }
  }
}
