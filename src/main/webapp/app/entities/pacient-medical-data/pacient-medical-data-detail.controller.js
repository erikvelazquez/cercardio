(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalDataDetailController', PacientMedicalDataDetailController);

    PacientMedicalDataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PacientMedicalData', 'Pacient', 'BloodGroup', 'Disabilities'];

    function PacientMedicalDataDetailController($scope, $rootScope, $stateParams, previousState, entity, PacientMedicalData, Pacient, BloodGroup, Disabilities) {
        var vm = this;

        vm.pacientMedicalData = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientMedicalDataUpdate', function(event, result) {
            vm.pacientMedicalData = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
