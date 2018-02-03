(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientLaboratoyDetailController', PacientLaboratoyDetailController);

    PacientLaboratoyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PacientLaboratoy', 'Pacient'];

    function PacientLaboratoyDetailController($scope, $rootScope, $stateParams, previousState, entity, PacientLaboratoy, Pacient) {
        var vm = this;

        vm.pacientLaboratoy = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientLaboratoyUpdate', function(event, result) {
            vm.pacientLaboratoy = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
