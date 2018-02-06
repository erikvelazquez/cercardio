(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DisabilitiesDetailController', DisabilitiesDetailController);

    DisabilitiesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Disabilities'];

    function DisabilitiesDetailController($scope, $rootScope, $stateParams, previousState, entity, Disabilities) {
        var vm = this;

        vm.disabilities = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:disabilitiesUpdate', function(event, result) {
            vm.disabilities = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
