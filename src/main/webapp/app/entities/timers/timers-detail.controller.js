(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimersDetailController', TimersDetailController);

    TimersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Timers'];

    function TimersDetailController($scope, $rootScope, $stateParams, previousState, entity, Timers) {
        var vm = this;

        vm.timers = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:timersUpdate', function(event, result) {
            vm.timers = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
