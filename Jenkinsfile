// Properties step to set the Active choice parameters via 
// Declarative Scripting
properties([ 
    parameters([
        [$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', description: 'Select a choice', filterLength: 1, filterable: true, name: 'choice1', randomName: 'choice-parameter-7601235200970', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script: [classpath: [], sandbox: false, script: 'return[\'aaa\',\'bbb\']']]], 
        [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT', description: 'Active Choices Reactive parameter', filterLength: 1, filterable: true, name: 'choice2', randomName: 'choice-parameter-7601237141171', referencedParameters: 'choice1', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["error"]'], script: [classpath: [], sandbox: false, script: 'if(choice1.equals("aaa")){return [\'a\', \'b\']} else {return [\'aaaaaa\',\'fffffff\']}']]]
    ])
])
pipeline {
    agent any
stages {
   stage('Build'){
    steps {
       echo 'Building..'
      }
    }
   }
}
