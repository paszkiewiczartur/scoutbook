angular.module('scoutbookApp')
.run(function($state, $rootScope, $location, $transitions, $stateParams) {

    $transitions.onError({}, function(transition) {
        console.log('error', transition.error().message, transition);
        console.log("inside transitions");
        if(transition.error().message == "The transition errored"){
            $state.go("register");
            console.log("Przekierowałem do register");
        }
    });
});
/*angular.module('scoutbookApp')
.run($trace => $trace.enable());*/