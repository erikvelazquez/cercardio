(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DiagnosisDetailController', DiagnosisDetailController);

    DiagnosisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Diagnosis'];

    function DiagnosisDetailController($scope, $rootScope, $stateParams, previousState, entity, Diagnosis) {
        var vm = this;

        vm.diagnosis = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:diagnosisUpdate', function(event, result) {
            vm.diagnosis = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
