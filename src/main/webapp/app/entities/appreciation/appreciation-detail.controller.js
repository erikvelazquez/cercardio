(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('AppreciationDetailController', AppreciationDetailController);

    AppreciationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Appreciation', 'Pacient', 'Medic'];

    function AppreciationDetailController($scope, $rootScope, $stateParams, previousState, entity, Appreciation, Pacient, Medic) {
        var vm = this;

        vm.appreciation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:appreciationUpdate', function(event, result) {
            vm.appreciation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
