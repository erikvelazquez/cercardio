(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicalAnalysisDetailController', MedicalAnalysisDetailController);

    MedicalAnalysisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MedicalAnalysis', 'Medic', 'Pacient'];

    function MedicalAnalysisDetailController($scope, $rootScope, $stateParams, previousState, entity, MedicalAnalysis, Medic, Pacient) {
        var vm = this;

        vm.medicalAnalysis = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:medicalAnalysisUpdate', function(event, result) {
            vm.medicalAnalysis = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
