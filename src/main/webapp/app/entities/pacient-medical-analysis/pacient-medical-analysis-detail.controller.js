(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalAnalysisDetailController', PacientMedicalAnalysisDetailController);

    PacientMedicalAnalysisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PacientMedicalAnalysis', 'Medic', 'Pacient'];

    function PacientMedicalAnalysisDetailController($scope, $rootScope, $stateParams, previousState, entity, PacientMedicalAnalysis, Medic, Pacient) {
        var vm = this;

        vm.pacientMedicalAnalysis = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientMedicalAnalysisUpdate', function(event, result) {
            vm.pacientMedicalAnalysis = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
