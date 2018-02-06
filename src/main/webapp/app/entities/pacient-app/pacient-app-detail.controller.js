(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientAppDetailController', PacientAppDetailController);

    PacientAppDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PacientApp', 'Pacient'];

    function PacientAppDetailController($scope, $rootScope, $stateParams, previousState, entity, PacientApp, Pacient) {
        var vm = this;

        vm.pacientApp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientAppUpdate', function(event, result) {
            vm.pacientApp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
