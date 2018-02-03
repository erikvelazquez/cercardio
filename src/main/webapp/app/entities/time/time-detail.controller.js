(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimeDetailController', TimeDetailController);

    TimeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Time'];

    function TimeDetailController($scope, $rootScope, $stateParams, previousState, entity, Time) {
        var vm = this;

        vm.time = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:timeUpdate', function(event, result) {
            vm.time = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
