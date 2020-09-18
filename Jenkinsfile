node {
       properties([
           buildDiscarder(
               logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
               parameters([
                       [$class: 'ExtensibleChoiceParameterDefinition', 
                           choiceListProvider: [
                              $class: 'TextareaChoiceListProvider', 
                              choiceListText: 'foo\nbar',
                              defaultChoice: 'bar',
                              addEditedValue: false,
                          ], 
                          description: 'blah blah blah blah', 
                          editable: true, 
                          name: 'choose_mnt'
                       ], 
                       booleanParam(defaultValue: false, description: '[TO DO]', name: 'include_installers')
                   ]), 
                   pipelineTriggers([])
               ])

       stage('Do Work') {
           print("-----------------------------------")
           print("Build ID: ${currentBuild.id}")
           sh 'date'
           sh 'pwd'
       }
}
